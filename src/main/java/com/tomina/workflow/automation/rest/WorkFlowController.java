package com.tomina.workflow.automation.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
public class WorkFlowController {

	
	@PostMapping("/api/v1/messages")
	public ResponseEntity<?> readMessage(@RequestHeader("Authorization") String authorization,
			@RequestHeader("Content-Type") String contentType, @RequestBody String body){
		
		return null;
	}
}
