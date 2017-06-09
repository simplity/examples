package org.simplity.examples;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/server")
public class ServerStopService {
	

	@GET
    @Path("/stop")	
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,MediaType.TEXT_PLAIN })
    public Response stop() {
		try {
			TodoViewServiceMain.server.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.ok("{'server':'stopped'}").build();
    }
}
