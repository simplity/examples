package org.simplity.examples.service.impl;

import javax.ws.rs.core.Response;

import org.simplity.kernel.value.Value;
import org.simplity.service.ServiceContext;
import org.simplity.tp.HttpClient;
import org.simplity.kernel.data.InputData;
import org.simplity.kernel.data.OutputData;

import org.simplity.examples.service.TodosViewServiceClient;

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
		
		HttpClient httpClient = new HttpClient(urlString, httpMethod, contentType, requestData, requestFieldName, responseData, responseFieldName, isJson, isXml);
		ServiceContext ctx = new ServiceContext("unknown", Value.newTextValue("100"));
		httpClient.doAct(ctx);
		
		Response response =  Response.ok(ctx.getTextValue("outputData")).build();
		return response;
	}
	
}
