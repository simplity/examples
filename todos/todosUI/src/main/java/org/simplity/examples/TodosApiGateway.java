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

import org.simplity.examples.service.TodosServiceClient;
import org.simplity.examples.service.TodosViewServiceClient;
import org.simplity.examples.service.impl.TodosServiceClientImpl;
import org.simplity.examples.service.impl.TodosViewServiceClientImpl;

/**
 * The TodosApiGateway aggregates calls to microservices (todoService, todoViewService) 
 * and communicate with todosUI application
 */
@Path("/todos")
public class TodosApiGateway {

	//@Resource
	//private TodoServiceClient todoServiceClient;

	//@Resource
	//private TodoViewServiceClient todoViewServiceClient;

	/**
	 * Fetches todos record
	 */
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getData() {
		System.out.println("Inside getProductDesktop");
		//return todoViewServiceClient.getTodosList();
		TodosViewServiceClient todoViewServiceClient1 = new TodosViewServiceClientImpl();
		Response response = todoViewServiceClient1.getTodosList();
		return response;
	}

	/**
	 * Add todos record
	 */
	@POST
	@Path("/add")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
	public Response add(String data) {
		//return todoServiceClient.add(data);
		TodosServiceClient todoServiceClient1 = new TodosServiceClientImpl();
		return todoServiceClient1.add(data);
	}

	/**
	 * Update todos record
	 */
	@PUT
	@Path("/update/{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
	public Response update(@PathParam("id") int id, String data) {
		//return todoServiceClient.update(id, data);
		TodosServiceClient todoServiceClient1 = new TodosServiceClientImpl();
		return todoServiceClient1.update(id, data);
	}

	/**
	 * Delete todos record
	 */
	@DELETE
	@Path("/delete/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response delete(@PathParam("id") int id) {
		//return todoServiceClient.delete(id);
		TodosServiceClient todoServiceClient1 = new TodosServiceClientImpl();
		return todoServiceClient1.delete(id);
	}

}
