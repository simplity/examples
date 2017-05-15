package org.simplity.examples;

import javax.ws.rs.ext.Provider;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

@Provider
public class CorsFilter implements ContainerResponseFilter {

    private final String HEADERS = "Origin, Content-Type, Accept";
   
    @Override
	public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {
	       response.getHttpHeaders().add("Access-Control-Allow-Origin", "*");
	       response.getHttpHeaders().add("Access-Control-Allow-Headers", HEADERS);
	       response.getHttpHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		return response;
	}
}
