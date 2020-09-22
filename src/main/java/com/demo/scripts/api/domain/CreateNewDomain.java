package com.demo.scripts.api.domain;

import com.demo.config.BasicTestConfig;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.specification.RequestSpecification;
import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Listeners;

import static com.demo.config.ExtentReport.*;
import static com.demo.properties.Environments.CREATE_NEW_DOMAIN;
import static com.demo.properties.Environments.HOST;
import static com.demo.properties.TestData.*;
import static com.demo.utilities.FileUtility.createLogFile;
import static com.jayway.restassured.RestAssured.given;

@Listeners(com.demo.config.TestNGListener.class)
public class CreateNewDomain extends BasicTestConfig {
    static final Logger LOG = LogManager.getLogger(CreateNewDomain.class);

    private static String scheme;
    private static String host;
    private static String path;
    private static String status;


    //*** Send request and receive response method
    public static void create_new_domain(String domain_name) throws Exception {
        //***   Print test name and test description
        testName = "Create_New_Domain";
        testDescription = "The purpose of this test is to verify that the login functionality is working as expected" +
                "<br><br><b>*****   D E S C R I P T I O N   *****</b><br><br>" +
                "[1] Check that the login page can be opened and displayed with correct title.<br>" +
                "[2] Check the visualization of the login form element by image comparing based on RGB color model.<br>" +
                "[3] Check login with valid credentials.";
        startTestReport(testName, testDescription);

        //*** Create URI for request
        scheme = "https";
        host = HOST;
        path = CREATE_NEW_DOMAIN;
        url = new URIBuilder()
                .setScheme(scheme)
                .setHost(host)
                .setPath(path)
                .build();

        //***   Request Body
        JSONObject jsonPostData = new JSONObject();
        jsonPostData.put("domain", domain_name);


        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(url);
        builder.setBody(jsonPostData.toJSONString());
        builder.addHeader("x-api-key", API_KEY);
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

        responseBody  = response.getBody().asString();
        responseCode  = response.getStatusCode();
        formattedJson = response.toString();
        createLogFile();


        //***   Get parameter value from response
        try {
        test.info("<pre>"
                    + "<br/>"
                    + "<center><b>* * * * * * * *    I N F O R M A T I O N    * * * * * * * *</b></center>"
                    + "<br />"
                    + "<br />"
                    + "Session ID:   " + "ss"
                    + "<br />"
                    + "<br/>"
                    + "</pre>");
        } catch (Exception exception) {
            System.out.println("Get values from the response body has failed");
            test.fail("EXCEPTION\n" + exception.getCause());
        }

        Assert.assertTrue(responseCode > 199 && responseCode < 300);
        Assert.assertEquals(status, "success");
    }
}
