package com.tomina.workflow.automation.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ExcelUtility {

	// Utility comnUtil = new Utility();

	public static ExcelUtility getInstance() {
		return new ExcelUtility();
	}

	public void fetchTestCaseData(int rowNumber) throws Exception {
		HashMap<String, String> rowData = new HashMap<String, String>();
		Workbook workbook = openWorkbook(GlobalVar.TestexcelPath);
		Sheet sheet = getSheetHandel(workbook, 2);
		rowData = fetchRowData(sheet, rowNumber);
		closeWorkbook(workbook);
		GlobalVar.TEST_DATA = rowData;

	}

	public Workbook openWorkbook(String xlFilePath) throws Exception {
		return Workbook.getWorkbook(new File(xlFilePath));
	}

	public Sheet getSheetHandel(Workbook workbook, String sheetName) throws Exception {
		return workbook.getSheet(sheetName);
	}

	public Sheet getSheetHandel(Workbook workbook, int sheetIndex) throws Exception {
		return workbook.getSheet(sheetIndex);
	}

	public HashMap<String, String> fetchRowData(Sheet sheet, int rowNumber) {
		HashMap<String, String> rowData = new HashMap<String, String>();
		for (int i = 0; i < sheet.getColumns(); i++) {
			rowData.put(sheet.getCell(i, 0).getContents().trim(), sheet.getCell(i, rowNumber).getContents().trim());
		}
		return rowData;
	}

	public HashMap<String, String> fetchRow_TestData(Sheet sheet, int rowNumber) {
		HashMap<String, String> rowData = new HashMap<String, String>();
		for (int i = 2; i < sheet.getColumns(); i++) {
			rowData.put(sheet.getCell(i, 0).getContents(), sheet.getCell(i, rowNumber).getContents());
		}
		return rowData;
	}

	public void closeWorkbook(Workbook workbook) throws Exception {
		workbook.close();
	}

	public HashMap<String, String> getStockDataFromExcel(String excelPath) throws Exception {

		HashMap<String, String> rowData = new HashMap<String, String>();
		Workbook workbook = openWorkbook(excelPath);
		Sheet sheet = getSheetHandel(workbook, 1);
		for (int i = 0; i < sheet.getColumns(); i++) {
			for (int j = 0; j < sheet.getRows(); j++) {
				rowData.put(sheet.getCell(i, j).getContents().trim(), sheet.getCell(i, j).getContents().trim());
			}

		}

		closeWorkbook(workbook);
		return rowData;
	}

	public void fetchTransactionData(int rowNumber) throws Exception {
		HashMap<String, String> rowData = new HashMap<String, String>();
		Workbook workbook = openWorkbook(GlobalVar.TestexcelPath);
		Sheet sheet = getSheetHandel(workbook, 2);
		rowData = fetchRowData(sheet, rowNumber);
		closeWorkbook(workbook);
		GlobalVar.TransactionData = rowData;

	}

	public void fetchTransactionData(int rowNumber, int sheetNumber) throws Exception {
		HashMap<String, String> rowData = new HashMap<String, String>();
		Workbook workbook = openWorkbook(GlobalVar.TestexcelPath);
		Sheet sheet = getSheetHandel(workbook, sheetNumber);
		rowData = fetchRowData(sheet, rowNumber);
		closeWorkbook(workbook);
		GlobalVar.TransactionData = rowData;

	}

	public List<String> fetchColumnData(String columnName, int sheetnumber) {

		List<String> columnData = null;
		int noOfrows = 0;
		int colNumber = 0;
		;
		try {
			columnData = new ArrayList<String>();
			Workbook workbook = openWorkbook(GlobalVar.TestexcelPath);
			Sheet sheet = getSheetHandel(workbook, sheetnumber);
			noOfrows = fetchNoOfRows(sheet);
			colNumber = returnColumnPosition(columnName, sheet);
			for (int i = 1; i < noOfrows; i++) {
				System.out.println("column value=" + sheet.getCell(colNumber, i).getContents().trim());
				columnData.add(sheet.getCell(colNumber, i).getContents().trim());
			}
			closeWorkbook(workbook);

		} catch (Exception e) {

		}
		return columnData;
	}

	public int fetchNoOfTransactions() throws Exception {
		int noOfrows;
		Workbook workbook = openWorkbook(GlobalVar.TestexcelPath);
		Sheet sheet = getSheetHandel(workbook, 2);
		noOfrows = fetchNoOfRows(sheet);
		closeWorkbook(workbook);
		return noOfrows;

	}

	public void fetchMobileTransactionData(int rowNumber) throws Exception {

		HashMap<String, String> rowData = new HashMap<String, String>();
		Workbook workbook = openWorkbook(GlobalVar.TestexcelPath);
		Sheet sheet = getSheetHandel(workbook, 3);
		rowData = fetchRowData(sheet, rowNumber);
		closeWorkbook(workbook);
		GlobalVar.mobileTransactionData = rowData;
	}

	public int returnColumnPosition(String headerName, Sheet sheet) {
		int columnCount = -1;
		int totalColumn = 0;
		String header = "";

		totalColumn = sheet.getColumns();
		// System.out.println(totalColumn + " " + sheet.getRows());

		for (int i = 0; i < totalColumn; i++) {
			Cell cell = sheet.getCell(i, 0);
			header = cell.getContents().trim();

			if (header.equalsIgnoreCase(headerName)) {
				columnCount = i;
				break;
			}
		}

		return columnCount;
	}

	public int returnRowPosition(String rowHeaderName, Sheet sheet) {

		int rowCount = -1;
		int totalRows = 0;
		String header = "";

		totalRows = sheet.getRows();
		for (int i = 0; i < totalRows; i++) {
			Cell cell = sheet.getCell(0, i);
			header = cell.getContents().trim();

			if (header.equalsIgnoreCase(rowHeaderName)) {
				rowCount = i;
				break;
			}
		}

		return rowCount;
	}

	public String getCellValueByCollumHeader(String colHeader, int rowNum, int sheetNumber) throws Exception {
		String value = "";
		int colNumber;
		Workbook workbook = null;
		Sheet sheet = null;
		try {
			workbook = openWorkbook(GlobalVar.TestexcelPath);
			sheet = getSheetHandel(workbook, sheetNumber);
			colNumber = returnColumnPosition(colHeader, sheet);
			Cell cell = sheet.getCell(colNumber, rowNum);
			value = cell.getContents().trim();
		}

		catch (Exception E) {

		}

		finally {
			if (workbook != null) {
				closeWorkbook(workbook);
			}
		}
		return value;
	}

	public String getCellValueByCollumAndRowHeader(String colHeader, String rowHeader, int sheetNumber)
			throws Exception {
		String value = "";
		int colNumber;
		int rowNumber;
		Workbook workbook = null;
		Sheet sheet = null;
		try {
			workbook = openWorkbook(GlobalVar.TestexcelPath);
			sheet = getSheetHandel(workbook, sheetNumber);
			colNumber = returnColumnPosition(colHeader, sheet);
			rowNumber = returnRowPosition(rowHeader, sheet);
			Cell cell = sheet.getCell(colNumber, rowNumber);
			value = cell.getContents().trim();
		}

		catch (Exception E) {

		}

		finally {
			if (workbook != null) {
				closeWorkbook(workbook);
			}
		}
		return value;
	}

	/*
	 * public float getIntrestRate(int sheetNumber,Date date) throws Exception {
	 * String value1 = ""; String value2 = ""; float intrestRate=0.0f; int
	 * rowNumber; Workbook workbook = null; Sheet sheet = null; SimpleDateFormat sdf
	 * = new SimpleDateFormat("dd-MM-yyyy"); try { workbook =
	 * openWorkbook(GlobalVar.TestexcelPath); sheet = getSheetHandel(workbook,
	 * sheetNumber); rowNumber = fetchNoOfRows(sheet); for (int i = 1; i <rowNumber;
	 * i++) { Cell cell1 = sheet.getCell(0, i); value1 = cell1.getContents().trim();
	 * Cell cell2 = sheet.getCell(1, i); value2 = cell2.getContents().trim(); value1
	 * = Keywords.changeDateFormate(value1); value2 =
	 * Keywords.changeDateFormate(value2); Date date1 = sdf.parse(value1); Date
	 * date2 = sdf.parse(value2); //System.out.println(sdf.format(date1));
	 * //System.out.println(sdf.format(date2)); Cell cell3=null; if
	 * (date1.compareTo(date) <0 && date2.compareTo(date) > 0) { cell3 =
	 * sheet.getCell(2, i); intrestRate =
	 * Float.parseFloat(cell3.getContents().trim());
	 * //System.out.println("Date1 is after Date2"); break; } else if
	 * (date1.compareTo(date) == 0 || date2.compareTo(date) == 0) { cell3 =
	 * sheet.getCell(2, i); intrestRate =
	 * Float.parseFloat(cell3.getContents().trim()); break; } else {
	 * //System.out.println("How to get here?"); } }
	 * 
	 * }
	 * 
	 * catch (Exception E) {
	 * 
	 * }
	 * 
	 * finally { if (workbook != null) { closeWorkbook(workbook); } } return
	 * intrestRate; }
	 */

	public int fetchNoOfRows(Sheet sheet) {
		int noOfRows = 0;
		try {
			noOfRows = sheet.getRows();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return noOfRows;
	}

	public int fetchNoOfCols(Sheet sheet) {
		int noOfCols = 0;
		try {
			noOfCols = sheet.getColumns();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return noOfCols;
	}

	public static void main(String args[]) throws Exception {
		ExcelUtility eu = new ExcelUtility();
		
		 GlobalVar.TestexcelPath="D:\\HolidayList.xls";
		// int noOfTrans=eu.fetchNoOfTransactions();
		// System.out.println(noOfTrans);

		// HashMap< String, String>
		// testData=eu.getStockDataFromExcel("D:\\Crestech-Data\\ETProject\\ETPortFolioMobileAutomation\\TestRepository\\TestData\\ET-Stocks.xls");
		// /testData.get("TransactionDate");
		// System.out.println(eu.getCellValueByCollumAndRowNum(2, 0, 0));
		 String cellValueByCollumAndRowNum = eu.getCellValueByCollumAndRowNum(2, 0, 0);
		 System.out.println(cellValueByCollumAndRowNum);
	}

	public String getCellValueByCollumAndRowNum(int rowNumber, int colNumber, int sheetNumber) throws Exception {
		String value = "";
		Workbook workbook = null;
		Sheet sheet = null;
		try {
			workbook = openWorkbook(GlobalVar.TestexcelPath);
			sheet = getSheetHandel(workbook, sheetNumber);
			Cell cell = sheet.getCell(colNumber, rowNumber);
			value = cell.getContents().trim();
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			if (workbook != null) {
				closeWorkbook(workbook);
			}
		}
		return value;
	}
}
