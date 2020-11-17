package com.tomina.workflow.automation.service;

import static io.restassured.RestAssured.given;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.rmi.CORBA.Tie;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import jxl.Sheet;
import jxl.Workbook;

public class JiraResponse {

	public static String JIRA_RESPONSE;
	public static String WORKLOGDATE;
	public static long TIMEINSECOND;
	public static String jiraResponseMethod(long timeInSeconds, String workLogDate) throws Exception {
		WORKLOGDATE= workLogDate;
		TIMEINSECOND = timeInSeconds;
				// Login scenario
				RestAssured.baseURI = "https://jira.tomiaglobal.com";
				// String baseUrl = "https://jira.tomiaglobal.com";
				String readMessageUri = "/rest/api/2/issue/INT-3/worklog";

				// This will store the session so whenever the code is executed it will treat as
				// logged In user for following methods.

				SessionFilter session = new SessionFilter();
				String response = given().relaxedHTTPSValidation().log().all()
						.header("Content-Type", "application/json")
						.body("{\"username\":\"ashua\",\"password\":\"Jan@2020\"}").filter(session).when()
						.post("/rest/auth/1/session").then().log().all().extract().response().asString();

				// workLogDate = "20201110111301";
				// workLogDate = "2020-11-09T09:00:00";

				JIRA_RESPONSE = given().pathParam("IssueName", "INT-3").log().all()
						.header("Content-Type", "application/json")
						.body("{\r\n" + "  \"timeSpentSeconds\": " + timeInSeconds + ",\r\n" + "  \"started\": \""
								+ workLogDate + "\"\r\n" + "}")
						.filter(session).when().post("/rest/api/2/issue/{IssueName}/worklog").then().log().all()
						.statusCode(201).extract().response().asString();
		return JIRA_RESPONSE;
	}

	
	public static boolean updateWorklog(long timeInSeconds, String workLog) throws Exception {
		
		
		ExcelData ed = new ExcelData();
		int noOfRows = ed.getNoOfRows();
		for (int i = 1; i < noOfRows; i++) {
			String cellValueByCollumAndRowNum = ed.getCellValueByCollumAndRowNum(i, 0, 0);
			System.out.println("Value: " + cellValueByCollumAndRowNum + " WorkLogDate: " + workLog);
			Boolean bool = cellValueByCollumAndRowNum.equals(workLog);
			System.out.println("Boolean Value: " + bool);

			if (cellValueByCollumAndRowNum.equals(workLog)) {
			//	jiraResponseMethod(timeInSeconds, workLog);
				
				return false;
			}
			
			
		}
		return true;
	}
	public static void main(String[] args) throws Exception {
		JiraResponse jr = new JiraResponse();
		jr.updateWorklog(100, "2020-11-16T05:00:00.000+0530");
	}
}
