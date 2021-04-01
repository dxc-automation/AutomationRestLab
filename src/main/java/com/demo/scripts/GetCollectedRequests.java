package com.demo.scripts;

import com.demo.config.ExtentReport;
import com.demo.config.RestAssuredConfig;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Listeners;

import static com.demo.config.ExtentReport.startTestReport;
import static com.demo.config.ExtentReport.test;
import static com.demo.properties.Environments.HOST;
import static com.demo.properties.Environments.getCollectedRequestsPath;
import static com.demo.properties.TestData.*;
import static com.jayway.restassured.RestAssured.given;

@Listeners(com.demo.config.TestNGListener.class)
public class GetCollectedRequests extends RestAssuredConfig {
    static final Logger LOG = LogManager.getLogger(GetCollectedRequests.class);

    private String scheme;
    private String host;
    private String path;
    private Response response;


    //*** Send request and receive response method
    public void getCollectedRequests(String basketName) throws Exception {
        //***   Print test name and test description
        testAuthor      = "Pavel Popov";
        functionality   = "Baskets";
        testName        = "Collected Requests";
        testDescription = "The purpose of this test is to verify that the login functionality is working as expected" +
                "<br><br><b>*****   D E S C R I P T I O N   *****</b><br><br>" +
                "[1] Check that the login page can be opened and displayed with correct title.<br>" +
                "[2] Check the visualization of the login form element by image comparing based on RGB color model.<br>" +
                "[3] Check login with valid credentials.";
        startTestReport(testName, testDescription, testAuthor, functionality);


        //*** Create URI for request
        scheme = "https";
        host   = HOST;
        path   = getCollectedRequestsPath(basketName);
        url = getURL(scheme, host, path);

        //***   Call print report method from ExtentReport.class
        ExtentReport extentReport = new ExtentReport();
        extentReport.generateRequestReport(scheme, host, path, "get");

        response = given(requestSpecification(url, "", accessToken))
                .log().all()
                .when()
                .get(url)
                .then()
                .log().all()
                .extract()
                .response();

        //***   Get parameter value from response
        try {
            ResponseBody body = response.getBody();
        test.info("<pre>"
                    + "<br/>"
                    + "<center><b>* * * * * * * *    I N F O R M A T I O N    * * * * * * * *</b></center>"
                    + "<br />"
                    + "<br />"
                    + "accessToken:   " + accessToken
                    + "<br />"
                    + "<br/>"
                    + "</pre>");
        } catch (Exception exception) {
            System.out.println("Get values from the response body has failed");
        }
        createLogFile(response.asString());
        responseHeaders = getResponseHeaders(response);
        responseBody    = getJsonResponseBody(response);
        statusCode      = getStatusCode(response);

        Assert.assertTrue(statusCode > 199 && statusCode < 300);
    }
}
