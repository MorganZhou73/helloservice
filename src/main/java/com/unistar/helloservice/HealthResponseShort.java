package com.unistar.helloservice;

public class HealthResponseShort implements HealthResponse{
	private String status;

	// for deserialisation
	public HealthResponseShort(){
	}

	public HealthResponseShort(String status){
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
