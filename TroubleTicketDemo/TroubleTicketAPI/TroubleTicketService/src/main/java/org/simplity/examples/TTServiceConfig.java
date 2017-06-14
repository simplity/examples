package org.simplity.examples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.Resource.Builder;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.simplity.json.JSONObject;
import org.simplity.service.JavaAgent;
import org.simplity.service.ServiceData;

public class TTServiceConfig extends ResourceConfig {
	String basePath = "/todos";
	String[][] pathParams = { { "POST", "/", "application/json", "application/json", "1" },
			{ "PUT", "/{id}", "application/json", "application/json", "2" },
			{ "DELETE", "/{id}", "application/json", "application/json", "3" } };

	public TTServiceConfig() {
		final Resource.Builder resourceBuilder = Resource.builder();
		resourceBuilder.path(basePath);
		for (String[] pathParam : pathParams) {
			final int localSwitch = Integer.parseInt(pathParam[4]);
			Builder childResource = resourceBuilder.addChildResource(pathParam[1]);
			final ResourceMethod.Builder methodBuilder = childResource.addMethod(pathParam[0]);
			methodBuilder.consumes(pathParam[2]);
			methodBuilder.produces(pathParam[3]).handledBy(new Inflector<ContainerRequestContext, String>() {

				@Override
				public String apply(ContainerRequestContext containerRequestContext) {
					ServiceData outData;
					String data = null;
					int id;
					JSONObject jSONObject;
					InputStream in = null;
					switch (localSwitch) {
					case 1:
						data = readInputStream(containerRequestContext.getEntityStream());
						jSONObject = new JSONObject(data);
						jSONObject.put("action", "post");
						data = jSONObject.toString();
						outData = JavaAgent.getAgent("100", null).serve("writeJMS", data);
						return outData.getResponseJson();
					case 2:
						data = readInputStream(containerRequestContext.getEntityStream());
						jSONObject = new JSONObject(data);
						id = Integer.parseInt(
								containerRequestContext.getUriInfo().getPathParameters().get("id").toArray()[0]
										.toString());
						jSONObject.put("id", id);
						jSONObject.put("action", "put");
						data = jSONObject.toString();
						outData = JavaAgent.getAgent("100", null).serve("writeJMS", data);
						return outData.getResponseJson();
					case 3:
						id = Integer.parseInt(
								containerRequestContext.getUriInfo().getPathParameters().get("id").toArray()[0]
										.toString());
						data = "{'id':" + id + ",'action':'delete'}";
						outData = JavaAgent.getAgent("100", null).serve("writeJMS", data);
						return outData.getResponseJson();
					default:
						System.out.println("Hello World!");
						break;
					}
					return "Hello World!";
				}

				private String readInputStream(InputStream in) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder result = new StringBuilder();
					String line;
					try {
						while ((line = reader.readLine()) != null) {
							result.append(line);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return result.toString();
				}
			});

		}
		registerResources(resourceBuilder.build());
	}

}