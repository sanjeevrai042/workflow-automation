package com.tomina.workflow.automation.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeCalculator {

	public static String FORMATTED_START_DATE;
	public static String FORMATTED_END_DATE;
	public static long TIME_DIFFERENCE;
	public static String JIRA_START_DATE;

	public static long formatDate(String startDate, String endDate) {

		String dateToParseStart = startDate;
		String dateToParseEnd = endDate;
		
		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		SimpleDateFormat outputFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
		Date sDate = null;
		Date eDate = null;
		try {
			sDate = inputFormat.parse(dateToParseStart);
			eDate = inputFormat.parse(dateToParseEnd);

		} catch (ParseException e) {

			e.printStackTrace();
		}
		FORMATTED_START_DATE = outputFormat.format(sDate);
		FORMATTED_END_DATE = outputFormat.format(eDate);
		TIME_DIFFERENCE = timeDifference(FORMATTED_START_DATE, FORMATTED_END_DATE);
		
		return TIME_DIFFERENCE;

	}

	public static long timeDifference(String dateStart, String dateStop) {
		dateStart = FORMATTED_START_DATE;
		dateStop = FORMATTED_END_DATE;

		SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

		Date d1 = null;
		Date d2 = null;
		try {
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long diff = d2.getTime() - d1.getTime();
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000);

		long temp;
		temp = diffSeconds + (60 * diffMinutes) + (3600 * diffHours);
		return temp;
	}

	public static String formatDateForJira(String StartDate) {

		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		Date stDate = null;
		try {
			System.out.println("Start Date In Jira: "+StartDate);
			stDate = inputFormat.parse(StartDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		 JIRA_START_DATE = outputFormat.format(stDate);
		System.out.println("JIRA_START_DATE: " + JIRA_START_DATE);
		return JIRA_START_DATE;
	}

	public static void main(String[] args) {
		long tst = formatDate("2020-11-02T05:00:00.0000000", "2020-11-02T05:15:00.0000000");
		System.out.println(tst);
		TimeCalculator cal = new TimeCalculator();
		cal.formatDateForJira("2020-11-09T09:00:00");

	}

}
