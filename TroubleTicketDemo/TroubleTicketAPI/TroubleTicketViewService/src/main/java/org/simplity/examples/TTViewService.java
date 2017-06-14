package org.simplity.examples;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.simplity.service.JavaAgent;
import org.simplity.service.ServiceData;

@Path("/todos")
public class TTViewService {
	
	@GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response test() {
		System.out.println("JerseyEntryPoint");
		ServiceData outData = JavaAgent.getAgent("100", null).serve("filter_todosDBTable", null);		
		 return Response.ok(outData.getPayLoad()).build();
    }

}
