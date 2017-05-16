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

@Path("/entry-point")
public class JerseyEntryPoint {
	
	@GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response test() {
		System.out.println("JerseyEntryPoint");
		ServiceData outData = JavaAgent.getAgent("100", null).serve("helloworld", null);		
		 return Response.ok(outData.getPayLoad()).build();
    }
	
	@POST
    @Path("/post")
    @Produces(MediaType.APPLICATION_JSON)
    public Response test1(String data) {
		JSONObject obj = new JSONObject(data);
		System.out.println("JerseyEntryPoint");
		//ServiceData outData = JavaAgent.getAgent("100", null).serve("helloworld", null);
		
		 return Response.ok(obj).build();
    }
	
	@GET
    @Path("/getJmsQueue")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getJmsQueue() {
		System.out.println("JerseyEntryPoint");
		ServiceData outData = JavaAgent.getAgent("100", null).serve("jmsConsumer", null);		
		 return Response.ok(outData.getPayLoad()).build();
    }
	
	@GET
    @Path("/putJmsQueue")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,MediaType.TEXT_PLAIN})
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,MediaType.TEXT_PLAIN})
    public Response putJmsQueue() {
		System.out.println("JerseyEntryPoint");
		String data = "{'test':'Sample message'}";
		ServiceData outData = JavaAgent.getAgent("100", null).serve("jmsProducer", data, PayloadType.JSON);
		return Response.ok(outData.getPayLoad()).build();
    }
}
