package org.simplity.examples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.Resource.Builder;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.simplity.json.JSONObject;
import org.simplity.service.JavaAgent;
import org.simplity.service.ServiceData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import io.swagger.models.HttpMethod;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.models.auth.SecuritySchemeDefinition;
import io.swagger.parser.SwaggerParser;

public class TTServiceConfig extends ResourceConfig {
	final static Logger logger = LoggerFactory.getLogger(TTServiceConfig.class);

	private static String api_path;
	public static Map<String, SecuritySchemeDefinition> secDefs;
	private static String AuthService = "http://localhost:8090";

	public TTServiceConfig() {
		final Swagger swagger = new SwaggerParser().read(api_path);

		final Resource.Builder resourceBuilder = Resource.builder();
		resourceBuilder.path(swagger.getBasePath());

		Map<String, Path> paths = swagger.getPaths();

		for (Entry<String, Path> path : paths.entrySet()) {
			for (Entry<HttpMethod, Operation> method : path.getValue().getOperationMap().entrySet()) {
				Builder childResource = resourceBuilder.addChildResource(path.getKey());
				final ResourceMethod.Builder methodBuilder = childResource.addMethod(method.getKey().name());
				final String serviceName = method.getValue().getOperationId();
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

				secDefs = swagger.getSecurityDefinitions();
				methodBuilder.handledBy(new Inflector<ContainerRequestContext, String>() {
					@Override
					public String apply(ContainerRequestContext containerRequestContext) {
						try {
							if (containerRequestContext.getUriInfo().getQueryParameters().containsKey("access_token")) {
								// verify if token issued is correct
								logger.info("redirect to Auth service for authorization");
								String url = AuthService 
										+ "/auth/token/valid" 
										+ "?access_token="
										+ containerRequestContext.getUriInfo().getQueryParameters()
										.getFirst("access_token")
										+ "&correlation_Id="
										+ MDC.get("correlationId");
								
								getHttpResponse(url, "GET");
							}

							BufferedReader reader = new BufferedReader(
									new InputStreamReader(containerRequestContext.getEntityStream()));
							String line = "";
							StringBuffer sb = new StringBuffer();
							try {
								while ((line = reader.readLine()) != null) {
									sb.append(line);
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
							JSONObject jObj = new JSONObject();
							if (sb.length() != 0 && containerRequestContext.getMediaType()
									.isCompatible(MediaType.APPLICATION_JSON_TYPE)) {
								jObj = new JSONObject(sb.toString());
							}
							MultivaluedMap<String, String> pathParams = containerRequestContext.getUriInfo()
									.getPathParameters();
							for (Entry<String, List<String>> pathParam : pathParams.entrySet()) {
								for (String pathParamValue : pathParam.getValue()) {
									jObj.put(pathParam.getKey(), pathParamValue);
								}

							}
							MultivaluedMap<String, String> queryParams = containerRequestContext.getUriInfo()
									.getQueryParameters();
							for (Entry<String, List<String>> queryParam : queryParams.entrySet()) {
								for (String queryParamValue : queryParam.getValue()) {
									jObj.put(queryParam.getKey(), queryParamValue);
								}
							}

							MultivaluedMap<String, String> headerParams = containerRequestContext.getHeaders();
							for (Entry<String, List<String>> headerParam : headerParams.entrySet()) {
								for (String headerParamValue : headerParam.getValue()) {
									jObj.put(headerParam.getKey(), headerParamValue);
								}
							}
							String username = "100";
							String pwd = null;

							if(!jObj.has("correlation_Id"))
								jObj.append("correlation_Id", MDC.get("correlationId"));
System.out.println(jObj.toString());
							ServiceData outData = JavaAgent.getAgent(username, pwd).serve(serviceName, jObj.toString());
							return outData.getResponseJson();

						} catch (Exception e) {
							logger.error("Error in the Service config",e);
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

	private String getHttpResponse(String urlStr, String method) {
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
			conn.setRequestMethod(method);
			conn.setDoOutput(true);
		
			/*
			 * receive response
			 */
			int resp = conn.getResponseCode();
			if (resp != 200) {
				logger.error("Http call for url " + url + " returned with a non200 status " + resp);
			}
			return readResponse(conn);		
		} catch (Exception e) {
			logger.error("Error in service",e);
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
					logger.error("Error reading response",e);
				}
			}
		}
	}
}