package org.simplity.examples.troubleTicketUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.Resource.Builder;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.simplity.kernel.ApplicationError;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import io.swagger.models.HttpMethod;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.models.auth.OAuth2Definition;
import io.swagger.models.auth.SecuritySchemeDefinition;
import io.swagger.parser.SwaggerParser;

public class TTUIConfig extends ResourceConfig {
	private static String api_path;
	private static Map<String, SecuritySchemeDefinition> secDefs;
	private static String TTService = "http://localhost:8085";
	private static String AuthService = "http://localhost:8090";
	private static String TTUI = "http://localhost:8095";

	public TTUIConfig() {
		final Swagger swagger = new SwaggerParser().read(api_path);

		final Resource.Builder resourceBuilder = Resource.builder();
		resourceBuilder.path(swagger.getBasePath());

		Map<String, Path> paths = swagger.getPaths();

		for (Entry<String, Path> path : paths.entrySet()) {
			for (Entry<HttpMethod, Operation> method : path.getValue().getOperationMap().entrySet()) {
				Builder childResource = resourceBuilder.addChildResource(path.getKey());
				final ResourceMethod.Builder methodBuilder = childResource.addMethod(method.getKey().name());
				final String serviceName = method.getValue().getOperationId();
				secDefs = swagger.getSecurityDefinitions();

				boolean authFlag = false;
				List<String> scopes = new ArrayList<String>();
				// check if required oauth authorization
				if (method.getValue().getSecurity() != null) {
					for (Map<String, List<String>> secs : method.getValue().getSecurity()) {
						if (secs.keySet() != null) {
							for (String lsecs : secs.keySet()) {
								if (secDefs.containsKey(lsecs)) {
									if (secDefs.get(lsecs).getType().equals("oauth2")) {
										for (String oscope : ((OAuth2Definition) secDefs.get(lsecs)).getScopes()
												.keySet()) {
											scopes.add(oscope);
										}
										;
										authFlag = true;
									}
								}
							}
						}
					}
				}

				final boolean requiresOAuth = authFlag;
				final String scopeList = scopes.toString();
				List<MediaType> mt = new ArrayList<MediaType>();

				if (method.getValue().getConsumes() != null) {
					for (String mediaType : method.getValue().getConsumes()) {
						String[] mediaTypeValues = mediaType.split("/");
						mt.add(new MediaType(mediaTypeValues[0], mediaTypeValues[1]));
					}
					methodBuilder.consumes(mt);
				}

				if (method.getValue().getProduces() != null) {
					mt = new ArrayList<MediaType>();
					for (String mediaType : method.getValue().getProduces()) {
						String[] mediaTypeValues = mediaType.split("/");
						mt.add(new MediaType(mediaTypeValues[0], mediaTypeValues[1]));
					}
				}

				methodBuilder.produces(mt);

				methodBuilder.handledBy(new Inflector<ContainerRequestContext, String>() {
					@Override
					public String apply(ContainerRequestContext request) {
						String output = null;
						try {
							String accessCode = "";
							if (requiresOAuth) {
								if (!request.getUriInfo().getQueryParameters().containsKey("code")) {
									String url = AuthService + "?";
									url += "redirect_uri=";
									url += TTUI;
									url += "&state=";
									url += "fixed";
									url += "&scope=";
									url += scopeList.replace("[", "").replace("]", "").replaceAll("\\s", "");
									url += "&response_type=";
									url += "code";
									url += "&client_id=";
									url += "TTUIMain";

									return "{\"url\":\"" + URLEncoder.encode(url, "UTF-8") + "\"}";
								} 
								if (request.getUriInfo().getQueryParameters().containsKey("code")) {
									//get Access token
									String url = AuthService +"/auth/token" +"?";
									url += "redirect_uri=";
									url += TTUI +"/api/" +request.getUriInfo().getPath();
									url += "&grant_type=";
									url += "authorization_code";
									url += "&code=";
									url += request.getUriInfo().getQueryParameters().getFirst("code");
									url += "&client_id=";
									url += "TTUIMain";
									url += "&client_secret=";
									url += "TTUIMain";
									
									ObjectMapper mapper = new ObjectMapper();
									JsonNode jsonData = mapper.readTree(getHttpResponse(url, request));
									accessCode = jsonData.get("access_token").asText();
									
									
									url = TTService + "/api/" + request.getUriInfo().getPath() + "?";
									url += "access_token=";
									url += accessCode;
									output = getHttpResponse(url, request);
								}
							}

							// access the service with the access Code, pass on
							// the parameters
							// receive response and pass it on the client

						} catch (Exception e) {
							e.printStackTrace();
						}
						return output;
					}

				});

			}
		}
		registerResources(resourceBuilder.build());
	}

	private static String[] getRoles(List<Map<String, List<String>>> secs) {
		if (secs != null) {
			List<String> roles = new ArrayList<String>();
			for (Map<String, List<String>> sec : secs) {
				for (String lsec : sec.keySet()) {
					roles.add(secDefs.get(lsec).getType());
				}
			}
			return roles.toArray(new String[roles.size()]);
		}
		return null;
	}

	public static void setApiPath(String apiPath) {
		api_path = apiPath;
	}

	private String getHttpResponse(String urlStr, ContainerRequestContext request) {
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = null;

			/*
			 * get connection
			 */
			conn = (HttpURLConnection) url.openConnection();

			/*
			 * despatch request
			 */
			conn.setRequestMethod(request.getMethod());
			conn.setDoOutput(true);

			byte[] buffer = new byte[1024];
			int len;
			while ((len = request.getEntityStream().read(buffer)) != -1) {
				conn.getOutputStream().write(buffer, 0, len);
			}

			/*
			 * receive response
			 */
			int resp = conn.getResponseCode();
			if (resp != 200) {
				throw new ApplicationError("Http call for url " + url + " returned with a non200 status " + resp);
			}
			return readResponse(conn);
		} catch (ApplicationError e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return null;
	}

	private static String readResponse(HttpURLConnection conn) throws IOException {
		BufferedReader reader = null;
		StringBuilder sbf = new StringBuilder();
		try {
			reader = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			int ch;
			while ((ch = reader.read()) > -1) {
				sbf.append((char) ch);
			}
			reader.close();
			return sbf.toString();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					//
				}
			}
		}
	}
}