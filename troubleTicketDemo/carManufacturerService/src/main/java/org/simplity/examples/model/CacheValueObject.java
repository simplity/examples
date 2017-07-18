package org.simplity.examples.model;

import org.simplity.service.ServiceData;

public class CacheValueObject {
	
	private ServiceData inData;
	private ServiceData outData;
	private String servicename;
	
	public CacheValueObject(){
		
	}
	
	public CacheValueObject(ServiceData inData, ServiceData outData, String servicename) {
		super();
		this.inData = inData;
		this.outData = outData;
		this.servicename = servicename;
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
	public String getServicename() {
		return servicename;
	}
	public void setServicename(String servicename) {
		this.servicename = servicename;
	}
	
	

}
