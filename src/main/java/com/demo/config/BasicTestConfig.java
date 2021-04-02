package com.demo.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.demo.config.ExtentReport.extent;
import static com.demo.config.ExtentReport.test;
import static com.demo.properties.FilePaths.report_json_folder;
import static com.demo.properties.TestData.testName;



/**
 *                          This class contains all methods for taking screenshots,
 *                          browser initialization and generateReport generation.
 *          List:
 *   [1]    takeScreenshot  Capture screenshot and save the file with PNG extension.
 *                          Example:            takeScreenshot(driver, "FileName");
 *   [2]    browserConfig           Launch web browser. Value must be setted in testng.xml
 *   [3]    generateReport          Describes the result of a scripts and print result values.
 *   [4]    finishReport    Writes scripts information from the started reporters to
 *                          their output view.
 *   [5]    tearDown        Stop web driver and close the browser.
 */

@Listeners(com.demo.config.TestNGListener.class)
public class BasicTestConfig extends RestAssuredConfig {

    static final Logger LOG = LogManager.getLogger(BasicTestConfig.class);


    //***   Used for generate report
    @AfterMethod(alwaysRun = true)
    public void generateReport(ITestContext context, ITestResult result) throws Exception {
        String fileName   = context.getName() + ".json";
        Path file         = Paths.get(report_json_folder + fileName);
        String methodName = String.format("%s", result.getMethod().getRealClass().getSimpleName());
        File jsonReportFile = new File(report_json_folder + fileName);

        switch (result.getStatus()) {

            case ITestResult.SUCCESS:

                //  Print into HTML generateReport file
                test.pass("<pre>"
                        + "<br/>"
                        + "<center><b>* * * * * * * *    R E S P O N S E    * * * * * * * *</b></center>"
                        + "<br/>"
                        + "<br/>"
                        + "Response Code    : " + statusCode
                        + "<br/>"
                        //   + "Response Message : " + responseMsg
                        + "<br/>"
                        + "<br/>"
                        + responseHeaders
                        + "<br/>"
                        + "<br/>"
                        + "<br/>"
                        + response.body().prettyPrint()
                        + "<br/>"
                        + "<br/>"
                        + "</pre>");

                System.out.println("TEST PASSED");
                break;


            case ITestResult.FAILURE:
                Throwable throwable = result.getThrowable();
                LOG.error("| FAILED | " + testName);

                    // Print into HTML generateReport file
                    test.fail("<pre>"
                            + "<br/>"
                            + "<center><b>* * * * * * * *    R E S P O N S E    * * * * * * * *</b></center>"
                            + "<br />"
                            + "<br />"
                            + "Response Code  : " + statusCode
                            + "<br />"
                            + "Error Message  : " + response.body().prettyPrint()
                            + "<br />"
                            + "<br />"
                            + responseHeaders
                            + "<br />"
                            + "<br />"
                            + "<br />"
                            + "<center><b>********  E X C E P T I O N  ********</b></center>"
                            + "<br />"
                            + throwable
                            + "<br />"
                            + "<br />"
                            + "<br />"
                            + "</pre>");
                        //test.fail("EXCEPTION \n" + exception.getMessage() + "\n" + response.asString());

                    System.out.println("\n" + throwable);
                }
        }



    /**
     * Delete all reporting files from previous tests
     */
    @BeforeSuite
    public void cleanReportData() {
        File reportJsonDir = new File(report_json_folder);
        reportJsonDir.mkdir();
    }

    @AfterClass
    public void endReport() {
        extent.flush();
    }
}

