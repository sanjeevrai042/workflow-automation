package com.tomina.workflow.automation.httpclient;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpClient {

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${ms.outlook.graph.api.url}")
	private String url;
	
	@Value("${jira.service.url}")
	private String jiraUrl;
	/**
	 * Method to make http call
	 * @param token
	 * @param body
	 * @param method
	 * @return String
	 */
	
	 public String callOutlookAPI(String token,String body,String method,String uri) {
		 return executeHttpRequest(token, body, method,uri);
	 }
	 
	 public String callJiraAPI(String token,String body,String method,String uri) {
		 return executeHttpRequest(token, body, method,uri);
	 }
	
	private String executeHttpRequest(String token,String body,String method,String uri){
		RequestEntity<?> buildRequest = buildRequest(token, body, method,uri);
		String response = restTemplate.exchange(buildRequest, String.class).getBody();
		return response;
		
	}
	
	/**
	 * Set request parameters
	 * @param token
	 * @param body
	 * @param method
	 * @return RequestEntity
	 */
	private RequestEntity<?> buildRequest(String token,String body,String method,String uri){
		try {
		switch (method) {
			case "GET":
				return RequestEntity.get(new URI(url+uri)).headers(buildHeaders(token)).build();
			case "POST":
				return RequestEntity.post(new URI(url+uri)).headers(buildHeaders(token)).body(body);
			case "JIRA":
				return RequestEntity.post(new URI(jiraUrl+uri)).headers(buildHeaders(token)).body(body);
			case "DELETE":
				break;

			default:
				break;
			}
		} 
		catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Set Http Header parameters
	 * @param token
	 * @return HttpHeaders
	 */
	private HttpHeaders buildHeaders(String token) {
		HttpHeaders h = new HttpHeaders();
		h.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
		h.add(HttpHeaders.AUTHORIZATION, token);
		return h;
	}
}
 