package com.demo.config;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterTest;
import org.testng.internal.IResultListener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

import static com.demo.config.BasicTestConfig.driver;
import static com.demo.properties.FilePaths.report_json_folder;
import static com.demo.properties.FilePaths.screenshots_actual_folder;
import static com.demo.properties.FilePaths.screenshots_failed_folder;
import static com.demo.utilities.FileUtility.readJsonResponseFile;
import static com.demo.config.ExtentReport.*;

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