package com.demo.scripts.api.user;

import com.demo.config.BasicTestConfig;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.ResponseBody;
import com.jayway.restassured.specification.RequestSpecification;
import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Listeners;

import static com.demo.config.ExtentReport.*;
import static com.demo.config.RestAssuredConfig.getResponseInfo;
import static com.demo.properties.Environments.HOST;
import static com.demo.properties.Environments.LOGIN;
import static com.demo.properties.TestData.*;
import static com.jayway.restassured.RestAssured.given;

@Listeners(com.demo.config.TestNGListener.class)
public class Login extends BasicTestConfig {
    static final Logger LOG = LogManager.getLogger(Login.class);

    private static String scheme;
    private static String host;
    private static String path;


    //*** Send request and receive response method
    public static void user_login() throws Exception {
        //***   Print test name and test description
        testName = "User_Login";
        testDescription = "The purpose of this test is to verify that the login functionality is working as expected" +
                "<br><br><b>*****   D E S C R I P T I O N   *****</b><br><br>" +
                "[1] Check that the login page can be opened and displayed with correct title.<br>" +
                "[2] Check the visualization of the login form element by image comparing based on RGB color model.<br>" +
                "[3] Check login with valid credentials.";
        startTestReport(testName, testDescription);


        //*** Create URI for request
        scheme = "https";
        host = HOST;
        path = LOGIN;
        url  = new URIBuilder()
                .setScheme(scheme)
                .setHost(host)
                .setPath(path)
                .addParameter("accessToken", accessToken)
                .build();


        //***   Request Body
        JSONObject jsonPostData = new JSONObject();
        jsonPostData.put("credentialsType", "email");
        jsonPostData.put("countryCodeId", 221);
        jsonPostData.put("email", credentials);
        jsonPostData.put("phoneNumber", "7777999000");
        jsonPostData.put("password","1234");
        jsonPostData.put("credentialsType", "pin");
        jsonPostData.put("challenge",      "1234");


        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(url);
        builder.setBody(jsonPostData.toJSONString());
        builder.setContentType(ContentType.JSON);
        RequestSpecification requestSpecification = builder.build();

        //***   Call print report method from ExtentReport.class
        generateRequestReport(scheme, host, path, jsonPostData.toJSONString());


        response = given()
                .spec(requestSpecification)
                .log().all()
                .body(jsonPostData)
                .when()
                .post(url)
                .then()
                .log().all()
                .extract()
                .response();


        //*** Get all response details
        getResponseInfo(response);


        //***   Get parameter value from response
        try {
            ResponseBody responseBody = response.getBody();
            accessToken = responseBody.jsonPath().getJsonObject("accessToken");
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
            test.fail("EXCEPTION\n" + exception.getCause());
        }

        Assert.assertTrue(responseCode > 199 && responseCode < 300);
    }
}
