package com.demo.config;

import com.aventstack.extentreports.MediaEntityBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.demo.config.ExtentReport.test;
import static com.demo.properties.FilePaths.*;
import static com.demo.properties.TestData.*;
import static org.apache.commons.io.FileUtils.cleanDirectory;



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
public class BasicTestConfig {

    public static WebDriver     driver;
    public static WebDriverWait wait;


    static final Logger LOG = LogManager.getLogger(BasicTestConfig.class);

    /**
     * Used for screenshot generating
     * @param driver, name
     * @throws Exception
     */
    public static File takeScreenshot (WebDriver driver, String imageName) throws IOException {
        TakesScreenshot takesScreenshot = ((TakesScreenshot)driver);
        File imageFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File destination = new File(screenshots_actual_folder + imageName + ".png");
        FileUtils.copyFile(imageFile, destination);
        return destination;
    }


    private static String getScreenShot(WebDriver driver) throws IOException {
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destination = screenshots_failed_folder + dateName + ".png";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return destination;
    }



    @AfterMethod(alwaysRun = true)
    public void generateReport(ITestContext context, ITestResult result) throws Exception {
        String fileName   = context.getName() + ".json";
        Path file         = Paths.get(report_json_folder + fileName);
        String methodName = String.format("%s", result.getMethod().getRealClass().getSimpleName());
        File jsonReportFile = new File(report_json_folder + fileName);

        switch (result.getStatus()) {

            case ITestResult.SUCCESS:

                if (!jsonReportFile.exists()) {
                    //  Print into HTML generateReport file
                    test.pass("<pre>"
                            + "<br/>"
                            + "<center><b>* * * * * * * *    R E S P O N S E    * * * * * * * *</b></center>"
                            + "<br/>"
                            + "<br/>"
                            + "Response Code    : " + responseCode
                            + "<br/>"
                            //   + "Response Message : " + responseMsg
                            + "<br/>"
                            + "<br/>"
                            //     + responseHeaders
                            + "<br/>"
                            + "<br/>"
                            + "<br/>"
                            + responseBody
                            + "<br/>"
                            + "<br/>"
                            + "</pre>");
                } else if (Files.exists(file) == false) {
                    System.out.println("TEST PASSED");
                }
                break;

            case ITestResult.FAILURE:
                Throwable throwable = result.getThrowable();
                LOG.error("| FAILED | " + testName);
                if (!jsonReportFile.exists()) {

                    // Print into HTML generateReport file
                    test.fail("<pre>"
                            + "<br/>"
                            + "<center><b>* * * * * * * *    R E S P O N S E    * * * * * * * *</b></center>"
                            + "<br />"
                            + "<br />"
                            + "Response Code  : " + responseCode
                            + "<br />"
                            + "Error Message  : " + responseBody
                            + "<br />"
                            + "<br />"
                            //      + responseHeaders
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

                } else {
                    File fileFail;
                    fileFail = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                    FileUtils.copyFile(fileFail, new File(screenshots_failed_folder + methodName + ".png"));

                    test.fail("<pre><b>FAILED ON SCREEN</b><br>", MediaEntityBuilder.createScreenCaptureFromPath(screenshots_failed_folder + methodName + ".png", "<br>" + throwable).build());
                    test.fail("<pre>" + throwable.toString() + "</pre>");
                    System.out.println("\n" + throwable);
                }
        }
    }


    @BeforeClass
    @Parameters("browser")
    public static void browserConfig(String browser) throws Exception {
        DesiredCapabilities capability = new DesiredCapabilities();

        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", chrome_driver_file);

            ChromeOptions options = new ChromeOptions();
            options.addArguments("scripts-type");
            options.addArguments("start-maximized");
            options.addArguments("--disable-search-geolocation-disclosure");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--incognito");
            driver = new ChromeDriver(options);
            LOG.info("| Chrome browser launched successfully |");

        } else if (browser.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", firefox_driver_file);
            FirefoxProfile profile = new FirefoxProfile();
            profile.setAcceptUntrustedCertificates(true);
            profile.setAssumeUntrustedCertificateIssuer(true);

            FirefoxOptions options = new FirefoxOptions();
            options.setLogLevel(FirefoxDriverLogLevel.TRACE);
            driver = new FirefoxDriver();
            LOG.info("| Firefox browser launched successfully |");

        } else if (browser.equalsIgnoreCase("safari")) {
            capability.setCapability("browserstack.safari.driver", "3.141.59");
            capability.setCapability("browserstack.safari.enablePopups", false);
            capability.setCapability("browserstack.debug", true);
            capability.setCapability("browserstack.console", "debug");
            capability.setCapability("browserstack.networkLogs", true);

            SafariOptions sOptions = new SafariOptions();
            sOptions.setUseTechnologyPreview(true);
            SafariOptions.fromCapabilities(capability);
            capability.setCapability(SafariOptions.CAPABILITY, sOptions);
            driver = new SafariDriver();
            LOG.info("| Safari browser launched successfully |");

        } else if (browser.equalsIgnoreCase("none")) {
            System.setProperty("webdriver.chrome.driver", chrome_driver_file);
            ChromeOptions options = new ChromeOptions();
            options.addArguments("scripts-type");
            driver = new ChromeDriver(options);

            Point position = new Point(-2000, 0);
            driver.manage().window().setPosition(position);
            LOG.info("| Browser is not started |");
        }
    }



    /**
     * Delete all reporting files from previous tests
     */
    @BeforeSuite
    public void cleanReportData() {
        File reportJsonDir    = new File(report_json_folder);
        File reportFailedDir  = new File(screenshots_failed_folder);
        File reportActual     = new File(screenshots_actual_folder);
        File reportBuffer     = new File(screenshots_buffer_folder);
        File reportVideos     = new File(video_files);

        try {
            if (!reportJsonDir.exists() && !reportFailedDir.exists()) {
                reportJsonDir.mkdir();
                reportFailedDir.mkdir();
            } else {
                cleanDirectory(new File(report_json_folder));
                cleanDirectory(new File(screenshots_failed_folder));
            } if (!reportActual.exists() && !reportBuffer.exists()) {
                reportFailedDir.mkdir();
                reportActual.mkdir();
            } else {
                cleanDirectory(new File(screenshots_failed_folder));
                cleanDirectory(new File(screenshots_actual_folder));
            } if (!reportVideos.exists()) {
                reportVideos.mkdir();
            } else {
                cleanDirectory(new File(video_files));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void flushReportData() {
        driver.close();
        driver.quit();
    }
}

