package com.demo.utilities.web_services;

import com.demo.config.BasicTestConfig;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;
import org.json.JSONObject;

import static com.demo.properties.TestData.API_KEY;

public class RestAssured extends BasicTestConfig {

    public static RequestSpecBuilder builder;
    public static Response           response;
    public static RequestSpecification requestSpecification;


    private static final ResponseSpecification responseSpec = new ResponseSpecBuilder().build();


    public static void requestSpecification() {
        builder = new RequestSpecBuilder();
        builder.addHeader("x-api-key", API_KEY);
        builder.setContentType(ContentType.JSON);
        requestSpecification = builder.build();
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
