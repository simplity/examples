package org.simplity.examples;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.simplity.json.JSONObject;
import org.simplity.service.JavaAgent;
import org.simplity.service.PayloadType;
import org.simplity.service.ServiceData;

@Path("/todos")
public class TodoViewService {
	
	@GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response test() {
		System.out.println("JerseyEntryPoint");
		ServiceData outData = JavaAgent.getAgent("100", null).serve("filter_todos", null);		
		 return Response.ok(outData.getPayLoad()).build();
    }

}
