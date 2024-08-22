package com.Bhaba.code;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Recordset;

import dataProvider.FilloExcelReader;
import dataProvider.ReadPropertyFile;
import listeners.ExtentReport;

public class Main2 {

	public static Recordset mainRecordSet = null, testRecordSet = null, dataRecordSet = null, valueRedcordSet = null;
	public static String mainSheetPath;
	public static String maiSheetName;
	public static String srNo;
//	public static String runstatus;
	public static String browser;
	public static String module;
//	public static String description;
	public static String sheetPath;

//		~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static String dataSheetPath;
	public static String dataSheetName;
	public static String No;
	public static String pageName;
	public static String control;
	public static String objectType;
	public static String locatorName;
	public static String locatorValue;
	public static String dataField;
	public static String action;
	public static String testCaseNo;
	public static String testCaseType;
	public static String description;
	public static String scenarioID;
	public static String scenarioDescription;

	public static String dataFieldValue;
//	~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static String testName;
	public static String details;
	public static boolean reportStatus;
	public static String stringToAppend = "";
	public static String extentReportTestCaseNo = "";
	public static boolean addLogs = false;
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public static WebDriver driver = null;
	public static WebDriverWait wait;

	public static void main(String[] args) {

		System.out.println("Exicution Start....................");

		ReadPropertyFile.read();

//		********************   mainsheet read start **************
		try {

			mainSheetPath = System.getProperty("user.dir") + File.separator + "MainController.xlsx";
			maiSheetName = "mainsheet";

			mainRecordSet = FilloExcelReader.getRecord(mainSheetPath, maiSheetName);

			while (mainRecordSet.next()) {
				srNo = mainRecordSet.getField("srNo");
				browser = mainRecordSet.getField("browser");
				module = mainRecordSet.getField("module");
				sheetPath = mainRecordSet.getField("sheetPath");

				ExtentReport.startReport(module);

//				***************datasheet read start*******************
				dataSheetPath = System.getProperty("user.dir") + File.separator + "DataSheet" + File.separator
						+ sheetPath;
				dataSheetName = "sheet2";
				dataRecordSet = FilloExcelReader.getRecord(dataSheetPath, dataSheetName);
				
				while (dataRecordSet.next()) 
				{
					// exicute all data record in sheet2 one by one 
					
					testRecordSet = FilloExcelReader.getRecord(dataSheetPath, "sheet1");
					while (testRecordSet.next()) {

						No = testRecordSet.getField("No");
						pageName = testRecordSet.getField("pageName");
						control = testRecordSet.getField("control");
						objectType = testRecordSet.getField("objectType");
						locatorName = testRecordSet.getField("locatorName");
						locatorValue = testRecordSet.getField("locatorValue");
						dataField = testRecordSet.getField("dataField");
						action = testRecordSet.getField("action");
						testCaseNo = testRecordSet.getField("testCaseNo");
						String input = testCaseNo;
						String[] parts = input.split("_");
						testCaseNo = parts[0] + "_" + parts[1];
						testCaseType = testRecordSet.getField("testCaseType");
						description = testRecordSet.getField("description");
						scenarioID = testRecordSet.getField("scenarioID");
						scenarioDescription = testRecordSet.getField("scenarioDescription");
						dataFieldValue = "";
						System.out.println("step no :" + No + " is exicuting\n");

						// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
						if (extentReportTestCaseNo.trim().isEmpty())
							extentReportTestCaseNo = testCaseNo;
						else if (!extentReportTestCaseNo.equalsIgnoreCase(testCaseNo)) {
							reportStatus = false;
							extentReportTestCaseNo = testCaseNo;
							// ExtentReport.endTest();
						}

						if (reportStatus != true) {
							if (testCaseType.equalsIgnoreCase("positive")) {
								testName = "<font color='Blue'>" + scenarioID + "_" + "</font>"
										+ "<font color='maroon'>" + module + "_" + "</font>" + "<font color='green'>"
										+ testCaseNo + "</font>" + "<font color='black'>" + "(" + description + ")"
										+ "</font>";
								ExtentReport.startTest(testName);
								reportStatus = true;
							} else if (testCaseType.equalsIgnoreCase("Negative")) {
								testName = "<font color='Blue'>" + scenarioID + "_" + "</font>"
										+ "<font color='maroon'>" + module + "_" + "</font>" + "<font color='Red'>"
										+ testCaseNo + "</font>" + "<font color='black'>" + "(" + description + ")"
										+ "</font>";
								ExtentReport.startTest(testName);
								reportStatus = true;
							}
						}

						// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

//					********* Read DataField value from sheet2 **********
						if (dataField != null && !dataField.trim().isEmpty()) {
							dataFieldValue = dataRecordSet.getField(dataField);
							stringToAppend = "<font color=\"Royal blue\"><b> DataField - </b></font>" + dataField
									+ "<font color=\"Royal blue\"><b> DataField Value - </b></font>" + dataFieldValue;

						}
						details = "<font color=\"purple\"><b>Step - </b></font>" + No
								+ "<font color=\"maroon\"><b> Module - </b></font>" + module
								+ "<font color='crimson'><b> Action -</b></font>" + action + "<br>" + stringToAppend;

						Functions.actions(objectType, locatorName, locatorValue, dataFieldValue, action);
						System.out.println("\n \n");

						// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
						if (addLogs != true) {
							ExtentReport.addLogs("info", details);
							ExtentReport.endTest();// flush for every line
							stringToAppend = "";
						}
						addLogs = false;
						// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
					}
				}
			}
		} catch (FilloException e) {
			e.printStackTrace();
		}

		System.out.println("#################Successfully Exicuted##############");

	}

}
