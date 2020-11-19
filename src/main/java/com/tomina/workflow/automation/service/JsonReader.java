package com.tomina.workflow.automation.service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tomina.workflow.automation.httpclient.HttpClient;

@Component
public class JsonReader {

	public static String START_DATE_TIME;
	public static String END_DATE_TIME;
	ObjectMapper o = new ObjectMapper();
	
	@Autowired
	private HttpClient client;
	

	private List<String> getsworkingHours(JsonNode root) throws JsonProcessingException, IOException {
		ObjectNode workingHours = (ObjectNode) root;
		ArrayNode daysOfWeek = (ArrayNode) workingHours.get("daysOfWeek");
		if (workingHours == null)
			return Collections.EMPTY_LIST;

		String startTime = workingHours.get("startTime").asText();
		String endTime = workingHours.get("endTime").asText();
		String timeZone = workingHours.get("timeZone").get("name").asText();
		for (int i = 0; i < daysOfWeek.size(); i++) {
			String day = daysOfWeek.get(i).asText();
		}
		return null;
	}

	private Map<String, String> getscheduleItems(JsonNode root) throws Exception {
		ArrayNode scheduleItems = (ArrayNode) root;
		if (scheduleItems == null)
			return Collections.EMPTY_MAP;

		if (scheduleItems.size() > 0) {
			for (int i = 0; i < scheduleItems.size(); i++) {
				boolean isPrivate = scheduleItems.get(i).get("isPrivate").asBoolean();
				String status = scheduleItems.get(i).get("status").asText();
				if (status != null && status.equals("busy")) {

					ObjectNode start = (ObjectNode) scheduleItems.get(i).get("start");
					ObjectNode end = (ObjectNode) scheduleItems.get(i).get("end");
					String startDateTime = start.get("dateTime").asText();
					String startTimeZone = start.get("timeZone").asText();

					String endDateTime = end.get("dateTime").asText();
					String endTimeZone = end.get("timeZone").asText();
					
					System.out.println("status: " + status + " startDateTime: " + startDateTime + " endDateTime: " + endDateTime);	
					System.out.println("****************TIME CALCULATOR******************");
					long timeDiffInSeconds = TimeCalculator.formatDate(startDateTime, endDateTime);
					String formatDateForJira = TimeCalculator.formatDateForJira(startDateTime);
					System.out.println("****************TIME CALCULATOR END******************");
					System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
					System.out.println("**************JIRA RESPONSE****************");
					
					
					  boolean isHoliday = updateWorklog(timeDiffInSeconds,formatDateForJira); 
					  
					  if(isHoliday) {
						  callJira(timeDiffInSeconds, formatDateForJira);
					  }
					System.out.println("**************JIRA RESPONSE END****************");
					
				}
				String subject = scheduleItems.get(i).get("subject").asText();
				String location = scheduleItems.get(i).get("location").asText();
			}

		}
		return null;
	}

	public void callJira(long timeDiffInSeconds,String formatDateForJira) {
		String jiraAuthUri = "/rest/auth/1/session";
		String securityBody = "{\"username\":\"ashua\",\"password\":\"Jan@2020\"}";
		String jiraTokenResponse  = client.callJiraAPI(null, securityBody, "JIRA", jiraAuthUri);
		String jiraToken;
		try {
			jiraToken = "Basic "+parseJiraToken(jiraTokenResponse);
			String issueUri = "/rest/api/2/issue/INT-3/worklog";
			JiraPayload j = new JiraPayload(timeDiffInSeconds, formatDateForJira);
			String payload = o.writeValueAsString(j);
			String response = client.callJiraAPI(jiraToken, payload, "JIRA", issueUri);
			System.out.println(response);
		
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	public List<String> getvalues(TreeNode root) throws Exception {
		TreeNode values = root.get("value");
		if (values == null)
			return Collections.EMPTY_LIST;
		if (values.isArray()) {
			ArrayNode valueArr = (ArrayNode) values;
			if (valueArr.size() > 0) {
				for (int i = 0; i < valueArr.size(); i++) {
					String scheduleId = valueArr.get(i).get("scheduleId").asText();
					getscheduleItems(valueArr.get(i).get("scheduleItems"));
					getsworkingHours(valueArr.get(i).get("workingHours"));
				}
			} else {
				System.out.println("empty array");
			}
		}
		return null;
	}

	/**
	 * Starting point
	 * 
	 * @return TreeNode
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public TreeNode getParentNode(String json) throws JsonProcessingException, IOException {
		return o.readTree(json);

	}
	
	private String parseJiraToken(String content) throws JsonProcessingException, IOException {
		TreeNode node =  getParentNode(content);
		ObjectNode obj = (ObjectNode) node.get("session");
		return obj.get("value").asText();
	}
	public static boolean updateWorklog(long timeInSeconds, String workLog) throws Exception {
		ExcelData ed = new ExcelData();
		int noOfRows = ed.getNoOfRows();
		for (int i = 1; i < noOfRows; i++) {
			String cellValueByCollumAndRowNum = ed.getCellValueByCollumAndRowNum(i, 0, 0);
			System.out.println("Value: " + cellValueByCollumAndRowNum + " WorkLogDate: " + workLog);
			Boolean bool = cellValueByCollumAndRowNum.equals(workLog);
			System.out.println("Boolean Value: " + bool);
			if (cellValueByCollumAndRowNum.equals(workLog)) 
				return false;

		}
		return true;
	}

}