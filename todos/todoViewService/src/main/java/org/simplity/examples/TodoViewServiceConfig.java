package org.simplity.examples;

import java.io.InputStream;
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
import org.simplity.json.JSONObject;
import org.simplity.service.JavaAgent;
import org.simplity.service.ServiceData;
import org.simplity.service.ServiceProtocol;

import io.swagger.models.HttpMethod;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

public class TodoViewServiceConfig extends ResourceConfig {
	private static String api_path;

	public TodoViewServiceConfig() {
		Swagger swagger = new SwaggerParser().read(api_path);

		final Resource.Builder resourceBuilder = Resource.builder();
		resourceBuilder.path(swagger.getBasePath());

		Map<String, Path> paths = swagger.getPaths();
		for (Entry<String, Path> path : paths.entrySet()) {
			for (Entry<HttpMethod, Operation> method : path.getValue().getOperationMap().entrySet()) {
				Builder childResource = resourceBuilder.addChildResource(path.getKey());
				final ResourceMethod.Builder methodBuilder = childResource.addMethod(method.getKey().name());
				final String serviceName = method.getValue().getVendorExtensions().get("x-serviceName").toString(); 
				List<MediaType> mt = new ArrayList<MediaType>();
				for (String mediaType : method.getValue().getConsumes()) {
					String[] mediaTypeValues = mediaType.split("/");
					mt.add(new MediaType(mediaTypeValues[0], mediaTypeValues[1]));
				}
				methodBuilder.consumes(mt);

				mt = new ArrayList<MediaType>();
				for (String mediaType : method.getValue().getProduces()) {
					String[] mediaTypeValues = mediaType.split("/");
					mt.add(new MediaType(mediaTypeValues[0], mediaTypeValues[1]));
				}
				methodBuilder.produces(mt).handledBy(new Inflector<ContainerRequestContext, String>() {
					@Override
					public String apply(ContainerRequestContext containerRequestContext) {
						ServiceData outData = JavaAgent.getAgent("100", null).serve(serviceName, null);
						return outData.getResponseJson();
					}
				});
				;
			}
		}
		registerResources(resourceBuilder.build());
	}

	public static void setApiPath(String apiPath) {
		api_path = apiPath;
	}
}