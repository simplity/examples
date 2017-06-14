package org.simplity.examples;

import java.io.InputStream;

import javax.ws.rs.container.ContainerRequestContext;

import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.Resource.Builder;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.simplity.json.JSONObject;
import org.simplity.service.JavaAgent;
import org.simplity.service.ServiceData;

public class TodoViewServiceConfig extends ResourceConfig {
	String basePath = "/todos";
	String[][] pathParams = { { "GET", "/", "application/json", "application/json", "1" } };

	public TodoViewServiceConfig() {
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
						outData = JavaAgent.getAgent("100", null).serve("filter_todosDBTable", null);
						return outData.getResponseJson();
					default:
						System.out.println("Path not defined");
						break;
					}
					return "Serviced requests";
				}
			});

		}
		registerResources(resourceBuilder.build());
	}

}