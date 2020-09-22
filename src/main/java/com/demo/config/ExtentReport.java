package com.demo.config;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.demo.properties.FilePaths.report_config_xml_file;
import static com.demo.properties.FilePaths.report_html_file;
import static com.demo.properties.TestData.env;
import static com.demo.properties.TestData.url;
import static com.demo.utilities.FileUtility.getFormattedJson;


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
    private static ExtentHtmlReporter htmlReporter;

    private static String osName    = System.getProperty("os.name");
    private static String osVersion = System.getProperty("os.version");
    private static String osArch    = System.getProperty("os.arch");

    public static ExtentReports GetExtent() throws UnknownHostException {
        if (extent != null)
            return extent;
        extent = new ExtentReports();
        extent.attachReporter(getHtmlReporter());
        extent.attachReporter(htmlReporter);

        InetAddress localHost = InetAddress.getLocalHost();
        String hostname = localHost.getHostName();

        extent.setSystemInfo("Local Host", localHost.getHostAddress());
        extent.setSystemInfo("Host Name",  hostname);
        extent.setSystemInfo("OS",         osName);
        extent.setSystemInfo("OS Version", osVersion);
        extent.setSystemInfo("OS Arch",    osArch);

        if (env.equalsIgnoreCase("internal")) {
            extent.setSystemInfo("Environment",    "internal.degiro.eu");
        } else if (env.equalsIgnoreCase("webtrader")) {
            extent.setSystemInfo("Environment",    "test-webtrader.internal.degiro.eu");
        } else if (env.equalsIgnoreCase("weekly")) {
            extent.setSystemInfo("Environment",    "test-weekly-webtrader.internal.degiro.eu");
        }
        return extent;
    }

    private static ExtentHtmlReporter getHtmlReporter() {
        htmlReporter = new ExtentHtmlReporter(report_html_file);
        htmlReporter.loadXMLConfig(report_config_xml_file);
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setEncoding("UTF-8");
        return htmlReporter;
    }


    //***   Method that start report listener
    public static void startTestReport(String testName, String testDescription) throws Exception {
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
                + "Path:     " + path + "/" + url.getQuery()
                + "<br/>"
                + "<br/>"
                + getFormattedJson(jsonPostData)
                + "<br/>"
                + "<br/>"
                + "</pre>");
    }
}
