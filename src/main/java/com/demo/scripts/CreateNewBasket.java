package com.demo.scripts;

import com.demo.config.ExtentReport;
import com.demo.config.RestAssuredConfig;
import com.jayway.restassured.response.ResponseBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Listeners;

import static com.demo.config.ExtentReport.startTestReport;
import static com.demo.config.ExtentReport.test;
import static com.demo.properties.Environments.HOST;
import static com.demo.properties.Environments.getCreateNewBasketPath;
import static com.demo.properties.TestData.*;
import static com.jayway.restassured.RestAssured.given;

@Listeners(com.demo.config.TestNGListener.class)
public class CreateNewBasket extends RestAssuredConfig {
    static final Logger LOG = LogManager.getLogger(CreateNewBasket.class);

    //*** Send request and receive response method
    public void newBasket(String basketName, int basketCapacity) throws Exception {
        //***   Print test name and test description
        testAuthor      = "Pavel Popov";
        functionality   = "Baskets";
        testName        = "Create New Basket";
        testDescription = "The purpose of this test is to verify that the login functionality is working as expected" +
                "<br><br><b>*****   D E S C R I P T I O N   *****</b><br><br>" +
                "[1] Check that the login page can be opened and displayed with correct title.<br>" +
                "[2] Check the visualization of the login form element by image comparing based on RGB color model.<br>" +
                "[3] Check login with valid credentials.";
        startTestReport(testName, testDescription, testAuthor, functionality);


        //*** Create URI for request
        scheme = "https";
        host   = HOST;
        path   = getCreateNewBasketPath(basketName);
        url    = getURL(scheme, host, path);


        //***   Request Body
        JSONObject jsonPostData = new JSONObject();
        jsonPostData.put("forward_url",   "");
        jsonPostData.put("proxy_response",false);
        jsonPostData.put("insecure_tls",  false);
        jsonPostData.put("expand_path",   true);
        jsonPostData.put("capacity",      basketCapacity);

        //***   Call print report method from ExtentReport.class
        ExtentReport extentReport = new ExtentReport();
        extentReport.generateRequestReport(scheme, host, path, "post", gson.toJson(jsonPostData));

        response = given(requestSpecification(url, jsonPostData.toJSONString(), ""))
                .log().all()
                .body(jsonPostData)
                .when()
                .post(url)
                .then()
                .log().all()
                .extract()
                .response();


        //***   Get parameter value from response
        try {
            ResponseBody body = response.getBody();
            accessToken = body.jsonPath().getJsonObject("token");
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
        statusCode      = getStatusCode(response);

        Assert.assertTrue(statusCode > 199 && statusCode < 300);
    }
}
