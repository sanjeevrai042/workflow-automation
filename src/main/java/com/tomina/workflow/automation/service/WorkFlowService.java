package com.tomina.workflow.automation.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.tomina.workflow.automation.httpclient.HttpClient;

@Service
public class WorkFlowService {
	
	@Autowired
	HttpClient client;
	
	private JsonReader reader  = new JsonReader();
	
	
	public String processFlow(String token, String body) {
		 try {
			String response = client.callOutlookAPI(token, body, "POST");
			
			 if(response != null) {
				 TreeNode root = reader.getParentNode(response);
				 reader.getvalues(root);
			 }
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
