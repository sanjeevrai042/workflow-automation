package com.tomina.workflow.automation.service;

import java.io.Serializable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JiraPayload implements Serializable{
	
	private long timeSpentSeconds;
	private String started;
	
	public JiraPayload(long timeSpentSeconds,String started) {
		this.timeSpentSeconds = timeSpentSeconds;
		this.started=started;
	}

	public long getTimeSpentSeconds() {
		return timeSpentSeconds;
	}

	public void setTimeSpentSeconds(long timeSpentSeconds) {
		this.timeSpentSeconds = timeSpentSeconds;
	}

	public String getStarted() {
		return started;
	}

	public void setStarted(String started) {
		this.started = started;
	}

	

}
