package org.simplity.examples;

import java.io.Serializable;

import org.simplity.service.ServiceData;

public class CacheValueObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String serviceName;
	private ServiceData inData;
	private ServiceData outData;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public ServiceData getInData() {
		return inData;
	}

	public void setInData(ServiceData inData) {
		this.inData = inData;
	}

	public ServiceData getOutData() {
		return outData;
	}

	public void setOutData(ServiceData outData) {
		this.outData = outData;
	}

}