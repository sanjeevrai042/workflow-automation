package com.tomina.workflow.automation.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

//import org.openqa.selenium.WebDriver;

//import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

public class GlobalVar {

	public static Connection conn = null;

	public static String CURRENT_EXECUTION_MODE;
	public static String CURRENT_PROJECT_PATH;
	public static String CURRENT_SUITE_NAME;
	public static String CURRENT_TEST_CASE_NAME;
	public static String CUR_KEWORD_EXECUTE_SEQ_NO;
	public static String CURRENT_KEYWORD_NAME;
	public static int TEST_CASE_ITERATION;
	public static String BrowserName;
	public static String screenshotParameter;
	public static String url;
	public static String TestexcelPath;
//	public static WebDriver driver;
	public static String NSEUrl;
	public static String BSEUrl;
	public static boolean etpStepsReport;
	public static HashMap<String, String> TEST_DATA;
	public static long pageSyncTime = 15000;
	public static int initialDivCount;
	public static int laterDivCont;
	public static HashMap<String, String> TransactionData;
//	public static WebDriverBackedSelenium selenium;
//	public static HashMap<TableHeaders, String> tableRowdData;
//	public static HashMap<TableHeaders, String> MFtableRowdData;
	public static String month;
	public static String day;
	public static String year;
	public static String half_Yearly = "HALF_YEARLY";
	public static String full_Yearly = "FULL_YEARLY";

//	public static ArrayList<MutualFundTableData> tableRowDataForSIP;
	public static HashMap<String, String> mobileTransactionData;
	public static HashMap<String, String> tableRowsData;
	public static HashMap<String, String> rowDataGeneric;
	public static HashMap<String, String> URLPASSID;
	public static String createdWatchList;
	public static HashMap<TimeUnit, Long> timeData;
//	public static HashMap<TableHeaders, String> reportTableRowData;
	public static double ciiYearData1;
	public static double ciiYearData2;
	public static double indexed;
	public static double priceBuy;
	public static double priceSell;
	public static double stockIndexed;
	public static double mutualFundIndexed;
	public static double etfIndexed;

	public static List<String> portfolioname;

//	public static HashMap<TableHeaders, String> dataOfSourcePortfolio;

//	public static HashMap<TableHeaders, String> dataOfTargetedPortfolio;

//	public static HashMap<TableHeaders, String> goalPerTableRowdData;

	public static double myNetWorthValue;

//	public static HashMap<TableHeaders, String> transValues;

	public static double buyPrice;

	public static List<String> companiesName;

	public static List<Double> columnData;

	public static List<String> columnDate;

	public static double bseTradepriceValue;

	public static double bseNetChangeValue;

	public static double bseChangeP;

	public static String currentMessage;

	public static double amount;

	public static String PID;
	public static String registrationUrl;

	public static boolean marketStatus;

	public static HashMap<String, String> LoanTableData;

	public static String mainWindow;

	public static HashMap<String, String> manualLoanTableData;

	public static String TransactionCompleteMessage = "Economic Times Succesfully done";
	public static int screenHeight = 200;

	public static HashMap<String, String> PNLTransactionTab;

	public static int yearDays = 365;

	public static int HoldingPeriod;

	public static double closingPrice;

	public static double getPrice;

	public static void TEST_DATA(String string) {
		// TODO Auto-generated method stub
		
	}
}
