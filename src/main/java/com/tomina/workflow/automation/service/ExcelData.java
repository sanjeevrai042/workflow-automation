package com.tomina.workflow.automation.service;

import java.io.File;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ExcelData {

	String excelpath = "D:\\HolidayList.xls";
	
	
	public String getCellValueByCollumAndRowNum(int rowNumber, int colNumber, int sheetNumber) throws Exception {
		String value = "";
		Workbook workbook = null;
		Sheet sheet = null;
		try {
			workbook = openWorkbook(excelpath);
			sheet = getSheetHandel(workbook, sheetNumber);
			System.out.println("rowNumber: "+rowNumber+" colNumber: "+colNumber);
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
	
	public Workbook openWorkbook(String excelpath) throws Exception {
		return Workbook.getWorkbook(new File(excelpath));
	}

	public Sheet getSheetHandel(Workbook workbook, int sheetIndex) throws Exception {
		return workbook.getSheet(sheetIndex);
	}
	
	public void closeWorkbook(Workbook workbook) throws Exception {
		workbook.close();
	}
	
	public int fetchNoOfRows(Sheet sheet) {
		int noOfRows = 0;
		try {
			noOfRows = sheet.getRows();
			System.out.println("Number Of Rows: "+noOfRows);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return noOfRows;
	}
	
	public int getNoOfRows() throws Exception {
		int noOfrows = 0;
		Workbook workbook = openWorkbook(excelpath);
		Sheet sheet = getSheetHandel(workbook, 0);
		return noOfrows = fetchNoOfRows(sheet);
	}
	
	
	public static void main(String[] args) throws Exception {
		//ExcelData ed = new ExcelData();
		/*
		 * ExcelData ed = new ExcelData(); int noOfRows = ed.getNoOfRows(); for (int i =
		 * 0; i <= noOfRows-1; i++) { String cellValueByCollumAndRowNum =
		 * ed.getCellValueByCollumAndRowNum(i, 0, 0);
		 * System.out.println("Value: "+cellValueByCollumAndRowNum); }
		 */
		
	}
}
