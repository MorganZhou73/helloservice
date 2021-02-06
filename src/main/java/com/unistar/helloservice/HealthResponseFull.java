package com.unistar.helloservice;

public class HealthResponseFull implements HealthResponse{
	private String currentTime;
	private String application;

	// for deserialisation
	public HealthResponseFull(){
	}

	public HealthResponseFull(String currentTime, String application){
		this.currentTime = currentTime;
		this.application = application;
	}

	public String getCurrentTime() {
		return currentTime;
	}

	public String getApplication() {
		return application;
	}
}
