package com.demo.config;

import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.internal.IResultListener;

public class TestNGListener  implements IResultListener {

    public static final Logger LOG = LogManager.getLogger(TestNGListener.class);

    public static String testName;
  //  private ExtentReports reporter =  new ExtentReports("build/SimpleReport.html", true, DisplayOrder.NEWEST_FIRST, NetworkMode.OFFLINE, Locale.ENGLISH);
    private ExtentTest    testReporter;


    @Override
    public void onFinish(ITestContext context) {
        System.out.println("\n[ TEST FINISH ] " + context.getName().toUpperCase());
    }


    @Override
    public void onTestStart(ITestResult arg0) {
        System.out.println("\n[ TEST STARTED ] " + arg0.getName().toUpperCase());
    }


    @Override
    public void onTestSuccess(ITestResult arg0) {
        System.out.println("\n[ TEST PASSED ] " + LOG.getName());
        }


    @Override
    public void onTestFailure(ITestResult result) {
    }


    @Override
    public void onTestSkipped(ITestResult arg0) {
        System.out.println("\n[ TEST SKIPPED ] " + arg0.getName().toUpperCase());
    }


    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
        // TODO Auto-generated method stub

    }



}