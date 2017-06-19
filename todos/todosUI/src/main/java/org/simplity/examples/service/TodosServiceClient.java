package org.simplity.examples.service;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * An interface used to communicate with the TodoService microservice
 */
public interface TodosServiceClient {
	public Response add(String data);
	public Response update(@PathParam("id") int id, String data);
	public Response delete(@PathParam("id") int id);
}
