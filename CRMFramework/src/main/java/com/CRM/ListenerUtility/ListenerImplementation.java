package com.CRM.ListenerUtility;


import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.CRM.BaseClass.BaseClass;
import com.CRM.generic.WebDriverUtility.UtilityClassObject;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ListenerImplementation implements ITestListener, ISuiteListener{
	public static ExtentReports report;
	public ExtentSparkReporter spark;
	public static ExtentTest test;
	@Override
	public void onStart(ISuite suite) {
		System.out.println("Report Configuration");
		String time = new Date().toString().replace(" ", "_").replace(":", "_");
		 //spark report config
   		ExtentSparkReporter spark = new ExtentSparkReporter("./AdvanceReport/report_"+time+".html");
   		spark.config().setDocumentTitle("CRM Test Suite Results");
   		spark.config().setReportName("CRM Report");
   		spark.config().setTheme(Theme.STANDARD);
   		
   		//add env information 
   		 report = new ExtentReports();
   		report.attachReporter(spark);
   		report.setSystemInfo("OS", "Window-10");
   		report.setSystemInfo("Browser", "Chrome-100"); 
	}

	@Override
	public void onFinish(ISuite suite) {
		System.out.println("Report Backup");
		report.flush();
	}

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("======"+result.getMethod().getMethodName()+"====Start====");
		 test = report.createTest(result.getMethod().getMethodName());
		 UtilityClassObject.setTest(test);
		 test.log(Status.INFO, result.getMethod().getMethodName()+"====> STARTED <=====");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("======"+result.getMethod().getMethodName()+"====End====");
		test.log(Status.PASS, result.getMethod().getMethodName()+"====> COMPLETED <=====");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String testcasename = result.getMethod().getMethodName();
		TakesScreenshot ts = (TakesScreenshot)BaseClass.sdriver;
		String filepath = ts.getScreenshotAs(OutputType.BASE64);
		String time = new Date().toString().replace(" ", "_").replace(":", "_");
		
		test.addScreenCaptureFromBase64String(filepath, testcasename+"_"+time);
		test.log(Status.FAIL, result.getMethod().getMethodName()+"====> FAILED <=====");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		
	}
  
}
