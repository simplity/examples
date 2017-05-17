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

import org.simplity.service.JavaAgent;
import org.simplity.service.ServiceData;

@Path("/todos")
public class TodoService {
	

	@POST
    @Path("/")	
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,MediaType.TEXT_PLAIN})
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,MediaType.TEXT_PLAIN })
    public Response add(String data) {
		
		ServiceData outData = JavaAgent.getAgent("100", null).serve("writeJMS", data);
		return Response.ok(outData.getResponseJson()).build();
    }
	
	@PUT
    @Path("/{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,MediaType.TEXT_PLAIN})
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,MediaType.TEXT_PLAIN })
    public Response update(String data) {
		ServiceData outData = JavaAgent.getAgent("100", null).serve("writeJMS", data);
		return Response.ok(outData.getResponseJson()).build();
    }
	
	@DELETE
    @Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response delete(@PathParam("id") int id) {
		String data = "{'id':"+id+"}";
		ServiceData outData = JavaAgent.getAgent("100", null).serve("writeJMS", data);
		return Response.ok(outData.getResponseJson()).build();
    }
	
}
