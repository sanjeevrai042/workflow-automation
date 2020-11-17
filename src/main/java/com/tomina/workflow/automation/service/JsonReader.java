package com.tomina.workflow.automation.service;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonReader {

	public static String START_DATE_TIME;
	public static String END_DATE_TIME;
	
	public static void main(String[] args) throws Exception {
		JsonReader r = new JsonReader();
		
		String outlook_json = null;
		
	 // "some response from rest call as a string";
		TreeNode root = r.getParentNode(outlook_json);
		r.getvalues(root);
	}

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

	private List<String> getscheduleItems(JsonNode root) throws Exception {
		ArrayNode scheduleItems = (ArrayNode) root;
		if (scheduleItems == null)
			return Collections.EMPTY_LIST;

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
					
				boolean isHoliday =	JiraResponse.updateWorklog(timeDiffInSeconds, formatDateForJira);
				if(isHoliday) {
					JiraResponse.jiraResponseMethod(timeDiffInSeconds, formatDateForJira);
				}
					
					System.out.println("**************JIRA RESPONSE END****************");
					
				}
				String subject = scheduleItems.get(i).get("subject").asText();
				String location = scheduleItems.get(i).get("location").asText();
			}

		}
		return null;
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
		ObjectMapper o = new ObjectMapper();
		return o.readTree(json);

	}

}