package org.simplity.examples.service;

import javax.ws.rs.core.Response;

/**
 * An interface used to communicate with the TodoViewService microservice
 */
public interface TodosViewServiceClient {
	public Response getTodosList();
}
