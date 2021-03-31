package com.demo.config;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentKlovReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.demo.properties.FilePaths.report_html_file;


/**
 *                              This class contains methods for HTML generateReport generation.
 *          List:
 *   [1]    ExtentReports       Print information about machine.
 *   [2]    ExtentHtmlReporter  HTML file design configuration.
 *   [3]    ExtentTest          Create a new scripts object.
 */

public class ExtentReport {

    public static ExtentTest    test;
    public static ExtentReports extent;
    private static ExtentSparkReporter htmlReporter;
    private static ExtentKlovReporter  kiovReporter;

    private static final String osName    = System.getProperty("os.name");
    private static final String osVersion = System.getProperty("os.version");
    private static final String osArch    = System.getProperty("os.arch");

    public static ExtentReports GetExtent() throws UnknownHostException {
        if (extent != null)
            return extent;
        extent = new ExtentReports();
        extent.attachReporter(getHtmlReporter(), kiovReporter);

        InetAddress localHost = InetAddress.getLocalHost();
        String hostname = localHost.getHostName();

        extent.setSystemInfo("Local Host", localHost.getHostAddress());
        extent.setSystemInfo("Host Name",  hostname);
        extent.setSystemInfo("OS",         osName);
        extent.setSystemInfo("OS Version", osVersion);
        extent.setSystemInfo("OS Arch",    osArch);

        return extent;
    }

    private static ExtentSparkReporter getHtmlReporter() {
        htmlReporter = new ExtentSparkReporter(report_html_file);
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setEncoding("UTF-8");
        return htmlReporter;
    }


    //***   Method that start report listener
    public static void startTestReport(String testName, String testDescription, String testAuthor, String functionality) throws Exception {
        extent = GetExtent();
        test   = extent.createTest(
                "<b>" + testName + "</b>",
                "<pre>"
                        + "<center><b>* * * * * * * *    I N F O R M A T I O N    * * * * * * * *</b></center>"
                        + "<p align=justify>"
                        + testDescription
                        + "</p>"
                        + "</pre>");
        extent.setAnalysisStrategy(AnalysisStrategy.TEST);
        test.assignDevice(osName + " " + osVersion);
        test.assignAuthor(testAuthor);
        test.assignCategory(functionality);

    }


    public static void generateRequestReport(String scheme, String host, String path, String jsonPostData) {
        //***   Print request details
        test.info("<pre>"
                + "<br/>"
                + "<center><b>* * * * * * * *    R E Q U E S T    * * * * * * * *</b></center>"
                + "<br />"
                + "<br />"
                + "Host:     " + scheme + "://" + host
                + "<br />"
                + "Path:     " + path
                + "<br/>"
                + "<br/>"
                + jsonPostData
                + "<br/>"
                + "<br/>"
                + "</pre>");
    }
}
