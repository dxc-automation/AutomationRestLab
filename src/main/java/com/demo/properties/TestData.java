package com.demo.properties;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.ResponseSpecification;

import java.net.URI;
import java.util.List;

public class TestData {


    public static String USER;
    public static String PASS;
    public static String PRODUCT;
    public static String MAIL;


    public static String testName;
    public static String testDescription;


    public static String API_KEY = "bdc764f95c3982fc5a5bfb4196014f6314fbe8734a5fbaf3f6d90b7b26b7180d";


    //  * * * *    RESPONSE
    public static List<Header> responseHeaders;
    public static Response     response;
    public static int          responseCode;

    public static String accessToken;
    public static String credentials;
    public static String user_city;
    public static URI url;
    public static String env;
    public static String responseBody;
    public static String responseMessage;
    public static Exception exception;
    public static String formattedJson;


    public static RequestSpecBuilder builder;
    public static ResponseSpecification responseSpec;
}
