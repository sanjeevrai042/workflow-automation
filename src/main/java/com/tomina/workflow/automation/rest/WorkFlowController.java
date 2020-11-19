package com.tomina.workflow.automation.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.tomina.workflow.automation.service.WorkFlowService;


@RestController
public class WorkFlowController {

	@Autowired
	private WorkFlowService service;
	
	@PostMapping("/api/v1/automate")
	public ResponseEntity<?> readMessage(@RequestHeader("Authorization") String authorization,
			@RequestHeader("Content-Type") String contentType, @RequestBody String body){
		service.processFlow(authorization, body);
		return null;
	}
}
