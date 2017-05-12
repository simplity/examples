package org.simplity.examples;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.simplity.examples.entity.ToDo;
import org.simplity.json.JSONObject;
import org.simplity.service.JavaAgent;
import org.simplity.service.ServiceData;

@Path("/todos")
public class JerseyEntryPoint {
	
	@GET
    @Path("/todo")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,MediaType.TEXT_PLAIN })
    public Response get() {
		System.out.println("JerseyEntryPoint");
		ServiceData outData = JavaAgent.getAgent("100", null).serve("filter_todos", null);
		return Response.ok(outData.getResponseJson()).build();
    }
	
	@POST
    @Path("/todo")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,MediaType.TEXT_PLAIN})
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,MediaType.TEXT_PLAIN })
    public Response add(String data) {
		System.out.println("JerseyEntryPoint");
		ServiceData outData = JavaAgent.getAgent("100", null).serve("addTask", data);
		return Response.ok(outData.getResponseJson()).build();
    }
	
	@PUT
    @Path("/todo")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,MediaType.TEXT_PLAIN})
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,MediaType.TEXT_PLAIN })
    public Response update(String data) {
		System.out.println("JerseyEntryPoint");
		ServiceData outData = JavaAgent.getAgent("100", null).serve("updateTask", data);
		return Response.ok(outData.getResponseJson()).build();
    }
	
	@DELETE
    @Path("/todo/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response delete(@PathParam("id") int id) {
		System.out.println("JerseyEntryPoint");
		ServiceData outData = JavaAgent.getAgent("100", null).serve("deleteTask","{'id':"+id+"}");
		return Response.ok(outData.getResponseJson()).build();
    }
}
