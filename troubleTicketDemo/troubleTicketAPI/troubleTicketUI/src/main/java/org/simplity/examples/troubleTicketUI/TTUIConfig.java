package org.simplity.examples.troubleTicketUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.json.JsonObject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.Resource.Builder;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import io.swagger.models.HttpMethod;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.SecurityRequirement;
import io.swagger.models.Swagger;
import io.swagger.models.auth.OAuth2Definition;
import io.swagger.models.auth.SecuritySchemeDefinition;
import io.swagger.parser.SwaggerParser;

public class TTUIConfig extends ResourceConfig {
	final static Logger logger = LoggerFactory.getLogger(TTUIConfig.class);

	private static String api_path;
	private static Map<String, SecuritySchemeDefinition> secDefs;
	private static String TTService = "http://localhost:8085";
	private static String AuthService = "http://localhost:8090/oauth2/";
	private static String TTUI = "http://localhost:8095";

	public TTUIConfig() {
		final Swagger swagger = new SwaggerParser().read(api_path);

		final Resource.Builder resourceBuilder = Resource.builder();
		resourceBuilder.path(swagger.getBasePath());

		Map<String, Path> paths = swagger.getPaths();
		Set<String> scopes = new HashSet<String>();
		boolean authFlag = false;
		secDefs = swagger.getSecurityDefinitions();
		if (swagger.getSecurity() != null) {

			for (SecurityRequirement sec : swagger.getSecurity()) {
				for (String lsecs : sec.getRequirements().keySet()) {
					if (secDefs.containsKey(lsecs)) {
						if (secDefs.get(lsecs).getType().equals("oauth2")) {
							for (String oscope : ((OAuth2Definition) secDefs.get(lsecs)).getScopes().keySet()) {
								scopes.add(oscope);
							}
							;
							authFlag = true;
						}
					}
				}
			}
		}
		for (Entry<String, Path> path : paths.entrySet()) {
			for (Entry<HttpMethod, Operation> method : path.getValue().getOperationMap().entrySet()) {
				Builder childResource = resourceBuilder.addChildResource(path.getKey());
				final ResourceMethod.Builder methodBuilder = childResource.addMethod(method.getKey().name());
				final String serviceName = method.getValue().getOperationId();

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
						try {
							String output = null;
							try {
								String accessCode = "";
								if (requiresOAuth) {
									if (!request.getUriInfo().getQueryParameters().containsKey("code")) {
										String url = AuthService + "oauth/authorize?" + "redirect_uri=" + TTUI
												+ "&state=" + "fixed" + "&response_type=" + "code" + "&client_id="
												+ "my-trusted-client-with-secret" + "&client_secret=somesecret"
												+ "&correlationId=" + MDC.get("correlationId");
										logger.info("redirect to authorize request");
										return "{\"url\":\"" + URLEncoder.encode(url, "UTF-8") + "\"}";
									}
									if (request.getUriInfo().getQueryParameters().containsKey("code")) {
										accessCode = request.getUriInfo().getQueryParameters().getFirst("code");
										String[] tokens;
										String url;
										String access_token;
										String refresh_token; 
										if ((tokens = TTUIMain.internalCache.get(accessCode)) == null) {
											// get Access token
											url = AuthService + "oauth/token" + "?"
													+ "&grant_type=authorization_code" + "&redirect_uri=" + TTUI
													+ "&state=" + "fixed" + "&code="
													+ request.getUriInfo().getQueryParameters().getFirst("code")
													+ "&client_id=" + "my-trusted-client-with-secret"
													+ "&client_secret=" + "somesecret" + "&correlationId="
													+ MDC.get("correlationId");

											ObjectMapper mapper = new ObjectMapper();
											JsonNode jsonData = mapper
													.readTree(getHttpResponse(url, "POST", false, request, true));
											 access_token = jsonData.get("access_token").asText();
											 refresh_token = jsonData.get("refresh_token").asText();

											TTUIMain.internalCache.put(accessCode, new String[]{access_token,refresh_token});
										}else{
											access_token =tokens[0];
											refresh_token = tokens[1];
										}
											url = TTService + "/api/" + request.getUriInfo().getPath() + "?"
													+ "access_token=" + access_token + "&refresh_token=" + refresh_token
													+ "&correlationId=" + MDC.get("correlationId");

											for (Entry<String, List<String>> parm : request.getUriInfo()
													.getQueryParameters().entrySet()) {
												if (parm.getKey().equals("access_token")
														|| parm.getKey().equals("correlationId")
														|| parm.getKey().equals("code")) {
													continue;
												}
												url += "&" + parm.getKey() + "=" + parm.getValue().get(0);
											}

											logger.info("fetch the token " + url);
											output = getHttpResponse(url, request.getMethod(), true, request, false);
										
									}
								}

								// access the service with the access Code, pass
								// on
								// the parameters
								// receive response and pass it on the client

							} catch (Exception e) {
								logger.error("Error in service", e);
							}
							return output;

						} catch (Exception e) {
							logger.error("Error in service", e);
						}
						return null;
					}
				});
			}
		}
		registerResources(resourceBuilder.build());
	}

	public static void setApiPath(String apiPath) {
		api_path = apiPath;
	}

	private String getHttpResponse(String urlStr, String method, boolean extractRequest,
			ContainerRequestContext request, boolean authHeader) {
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = null;

			/*
			 * get connection
			 */
			conn = (HttpURLConnection) url.openConnection();

			if (authHeader) {
				String userPassword = "my-trusted-client-with-secret:somesecret";
				String encoding = new String(Base64.getEncoder().encode(userPassword.getBytes()));
				conn.setRequestProperty("Authorization", "Basic " + encoding);
			}

			/*
			 * despatch request
			 */
			conn.setRequestMethod(method);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			if (extractRequest) {
				byte[] buffer = new byte[1024];
				int len;
				while ((len = request.getEntityStream().read(buffer)) != -1) {
					conn.getOutputStream().write(buffer, 0, len);
				}
			}
			/*
			 * receive response
			 */
			int resp = conn.getResponseCode();
			if (resp != 200) {
				logger.error("Http call for url " + url + " returned with a non200 status " + resp);
			}
			return readResponse(conn);
		} catch (Exception e) {
			logger.error("Error in service", e);
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
					logger.error("Error reading response", e);
				}
			}
		}
	}
}