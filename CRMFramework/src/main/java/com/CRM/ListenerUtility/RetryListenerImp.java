package com.CRM.ListenerUtility;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryListenerImp implements IRetryAnalyzer{
int count = 0;
int limitcount = 5;

@Override
public boolean retry(ITestResult result) {
if(count<limitcount) {
	count++;
	return true;
}
	return false;
}
}
