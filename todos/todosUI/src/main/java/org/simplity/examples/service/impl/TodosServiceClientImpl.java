package org.simplity.examples.service.impl;

import javax.ws.rs.core.Response;

import org.simplity.kernel.value.Value;
import org.simplity.service.ServiceContext;
import org.simplity.tp.HttpClient;
import org.simplity.kernel.data.InputData;
import org.simplity.kernel.data.OutputData;

import org.simplity.examples.service.TodosServiceClient;

public class TodosServiceClientImpl implements TodosServiceClient {
	/**
	 * Makes a simple HTTP Get request to the TodoService microservice
	 * 
	 * @return Response to the todosUI
	 */

	public Response add(String data) {
		String urlString = "http://localhost:8083/todos/";
		String httpMethod = "POST";
		String contentType = "application/json";
		OutputData requestData = null;
		String requestFieldName = "data";
		InputData responseData = null;
		String responseFieldName = "outputData";
		boolean isJson = true;
		boolean isXml = false;
		
		HttpClient httpClient = new HttpClient(urlString, httpMethod, contentType, requestData, requestFieldName, responseData, responseFieldName, isJson, isXml);
		ServiceContext ctx = new ServiceContext("unknown", Value.newTextValue("100"));
		ctx.setTextValue("data", data);
		httpClient.doAct(ctx);
		Response response =  Response.ok(ctx.getTextValue("data")).build();
		return response;
	}
	
	public Response update(int id, String data) {
		String urlString = "http://localhost:8083/todos/" + id;
		String httpMethod = "PUT";
		String contentType = "application/json";
		OutputData requestData = null;
		String requestFieldName = "data";
		InputData responseData = null;
		String responseFieldName = "outputData";
		boolean isJson = true;
		boolean isXml = false;
		
		HttpClient httpClient = new HttpClient(urlString, httpMethod, contentType, requestData, requestFieldName, responseData, responseFieldName, isJson, isXml);
		ServiceContext ctx = new ServiceContext("unknown", Value.newTextValue("100"));
		ctx.setTextValue("data", data);
		httpClient.doAct(ctx);
		Response response =  Response.ok(ctx.getTextValue("data")).build();
		return response;
		
		/*
		String urlString = "http://localhost:8083/todos/" + id;
		String requestMethod = "PUT";
		String jsonString = new HttpUtility().getHttpResponse(urlString, requestMethod, data);
		Response response = Response.ok(jsonString).build();
		return response;
		*/
	}

	public Response delete(int id) {
		String urlString = "http://localhost:8083/todos/" + id;
		String httpMethod = "DELETE";
		String contentType = "application/json";
		OutputData requestData = null;
		String requestFieldName = "";
		InputData responseData = null;
		String responseFieldName = "outputData";
		boolean isJson = true;
		boolean isXml = false;
		
		HttpClient httpClient = new HttpClient(urlString, httpMethod, contentType, requestData, requestFieldName, responseData, responseFieldName, isJson, isXml);
		ServiceContext ctx = new ServiceContext("unknown", Value.newTextValue("100"));
		httpClient.doAct(ctx);
		Response response =  Response.ok(ctx.getTextValue("data")).build();
		return response;
		
		/*
		String urlString = "http://localhost:8083/todos/" + id;
		String requestMethod = "DELETE";
		String inputData = "{'id':" + id + "}";
		String jsonString = new HttpUtility().getHttpResponse(urlString, requestMethod, inputData);
		Response response = Response.ok(jsonString).build();
		return response;
		*/
	}

}
