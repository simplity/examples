package org.simplity.examples.service.impl;

import javax.ws.rs.core.Response;

import org.simplity.examples.service.TodosViewServiceClient;
import org.simplity.kernel.value.Value;
import org.simplity.service.InputData;
import org.simplity.service.OutputData;
import org.simplity.service.ServiceContext;
import org.simplity.tp.HttpClient;

public class TodosViewServiceClientImpl implements TodosViewServiceClient {
	/**
	 * Makes a simple HTTP Get request to the TodoViewService microservice
	 * 
	 * @return The path to the image
	 */
	
	public Response getTodosList() {
		String urlString = "http://localhost:8081/todos";
		String httpMethod = "GET";
		String contentType = "application/json";
		OutputData requestData = null;
		String requestFieldName = null;
		InputData responseData = null;
		String responseFieldName = "outputData";
		boolean isJson = false;
		boolean isXml = false;
		
		//HttpClient httpClient = new HttpClient(urlString, httpMethod, contentType, requestData, requestFieldName, responseData, responseFieldName, isJson, isXml);
		HttpClient httpClient = new HttpClient();
		ServiceContext ctx = new ServiceContext("unknown", Value.newTextValue("100"));
		//httpClient.doAct(ctx);
		httpClient.act(ctx, null);
		Response response =  Response.ok(ctx.getTextValue("outputData")).build();
		return response;
	}
	
}
