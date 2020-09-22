package com.demo.utilities.web_services;

import com.demo.config.BasicTestConfig;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import static com.demo.properties.Environments.HOST;
import static com.demo.properties.TestData.*;

public class RestAssured extends BasicTestConfig {

    public static RequestSpecBuilder builder;
    public static Response           response;
    public static RequestSpecification requestSpecification;


    private static final ResponseSpecification responseSpec = new ResponseSpecBuilder().build();


    public static RequestSpecification requestSpecification(JSONObject requestBody) {
        if (requestBody != null) {
            builder = new RequestSpecBuilder();
            builder.addHeader("x-api-key", API_KEY);
            builder.setContentType(ContentType.JSON);
            builder.setBody(requestBody);
        } else {
            builder = new RequestSpecBuilder();
            builder.addHeader("x-api-key", API_KEY);
            builder.setContentType(ContentType.JSON);
        }
        return requestSpecification;
    }


    //***   Take environment parameter from XML file
    @BeforeClass
    @Parameters("environment")
    public static void setEnvironmentHostUserPass(String environment) {
        env = environment;
        if (environment.equalsIgnoreCase("internal")) {
            HOST = "internal.degiro.eu";
            USER = "dgtraderie";
            PASS = "Test_web2020";
        } else if (environment.equalsIgnoreCase("webtrader")) {
            HOST = "test-webtrader.internal.degiro.eu";
            USER = "web2879nl";
            PASS = "Test600";
        } else if (environment.equalsIgnoreCase("weekly")) {
            HOST = "test-weekly-webtrader.internal.degiro.eu";
            USER = "dgtraderie";
            PASS = "Test_web2020";
        } else if (environment.equalsIgnoreCase("test")) {
            HOST = "api.mailslurp.com";
            USER = "sandboxqa11@gmail.com";
            MAIL = "automation";
        }
    }

    public static JSONObject getJsonObjectBody(Response response) {
        JSONObject jsonObject = new JSONObject(response.body().prettyPrint());
        return jsonObject;
    }


    public static int getStatusCode() {
        int status_code = response.getStatusCode();
        return status_code;
    }
}
