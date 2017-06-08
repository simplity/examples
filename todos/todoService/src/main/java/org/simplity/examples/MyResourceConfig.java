package org.simplity.examples;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceMethod;
 
 
public class MyResourceConfig extends ResourceConfig {
 
    public MyResourceConfig() {
        final Resource.Builder resourceBuilder = Resource.builder();
        resourceBuilder.path("helloworld");
 
        final ResourceMethod.Builder methodBuilder = resourceBuilder.addMethod("GET");
        methodBuilder.produces(MediaType.TEXT_PLAIN_TYPE)
                .handledBy(new Inflector<ContainerRequestContext, String>() {
 
            @Override
            public String apply(ContainerRequestContext containerRequestContext) {
                return "Hello World!";
            }
        });
 
        final Resource resource = resourceBuilder.build();
        registerResources(resource);
    }
}