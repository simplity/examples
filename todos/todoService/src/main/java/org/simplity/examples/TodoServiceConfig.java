package org.simplity.examples;

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

public class TodoServiceConfig extends ResourceConfig {
	String basePath = "/todos";
	String[][] pathParams = { { "GET", "/{id}", "", "", "1" }, { "POST", "/", "", "", "2" }, { "PUT", "/{id}", "", "", "3" },
			{ "DELETE", "/{id}", "", "","4" } };

	public TodoServiceConfig() {
		final Resource.Builder resourceBuilder = Resource.builder();
		resourceBuilder.path(basePath);
		for (String[] pathParam : pathParams) {
			final int localSwitch = Integer.parseInt(pathParam[4]);
			Builder childResource = resourceBuilder.addChildResource(pathParam[1]);
			final ResourceMethod.Builder methodBuilder = childResource.addMethod(pathParam[0]);
			methodBuilder.consumes(MediaType.APPLICATION_JSON);
			methodBuilder.produces(MediaType.APPLICATION_JSON)
					.handledBy(new Inflector<ContainerRequestContext, String>() {

						@Override
						public String apply(ContainerRequestContext containerRequestContext) {
							ServiceData outData;
							String data = null;
							int id;
							JSONObject jSONObject;
							switch (localSwitch) {
							case 1:
								outData = JavaAgent.getAgent("100", null).serve("writeJMS", null);
								return outData.getResponseJson();
							case 2:
								//data = containerRequestContext.
								jSONObject = new JSONObject(data);
								jSONObject.put("action", "post");
								data = jSONObject.toString();
								outData = JavaAgent.getAgent("100", null).serve("writeJMS", data);
								return outData.getResponseJson();
							case 3:
								jSONObject = new JSONObject(data);
								id = Integer.parseInt(containerRequestContext.getUriInfo().getPathParameters().get("id").toArray()[0].toString());
								jSONObject.put("id", id);
								jSONObject.put("action", "put");
								data = jSONObject.toString();
								outData = JavaAgent.getAgent("100", null).serve("writeJMS", data);
								return outData.getResponseJson();
							case 4:
							    id = Integer.parseInt(containerRequestContext.getUriInfo().getPathParameters().get("id").toArray()[0].toString());
								data = "{'id':"+id+",'action':'delete'}";
								outData = JavaAgent.getAgent("100", null).serve("writeJMS", data);
								return outData.getResponseJson();
							default:
								System.out.println("Hello World!");
								break;
							}

							return "Hello World!";
						}
					});

		}
		registerResources(resourceBuilder.build());
	}

}