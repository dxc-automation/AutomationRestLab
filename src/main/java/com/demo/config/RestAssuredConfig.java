package com.demo.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Header;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;
import org.apache.http.client.utils.URIBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static com.demo.properties.FilePaths.report_json_folder;
import static com.demo.properties.TestData.testName;

public class RestAssuredConfig {

    private RequestSpecBuilder builder = new RequestSpecBuilder();
    public Gson gson = new GsonBuilder().setLenient().setPrettyPrinting().create();
    public RequestSpecification requestSpecification;

    public static List<Header> responseHeaders;
    public static Response response;
    public static int statusCode;

    private String requestBody;
    private String authorizationHeader;


    private static final ResponseSpecification responseSpec = new ResponseSpecBuilder().build();


    public RequestSpecification requestSpecification(String url, String requestBody, String authorizationHeader) {
        if (requestBody != null) {
            builder.setBaseUri(url);
            builder.addHeader("Authorization", authorizationHeader);
            builder.setContentType(ContentType.JSON);
            builder.setBody(requestBody);
            requestSpecification = builder.build();
        } else {
            builder.setBaseUri(url);
            builder.addHeader("Authorization", authorizationHeader);
            builder.setContentType(ContentType.JSON);
            requestSpecification = builder.build();
        }
        return requestSpecification;
    }


    public String getURL(String scheme, String host, String path) throws URISyntaxException {
        scheme = scheme;
        host = host;
        path = path;
        URI getUrl = new URIBuilder()
                .setScheme(scheme)
                .setHost(host)
                .setPath(path)
                .build();

        return getUrl.toString();
    }


    //***   Get response details
    public List<Header> getResponseHeaders(Response response) throws IOException {
        return responseHeaders = response.getHeaders().asList();
    }


    public int getStatusCode(Response response) {
        statusCode = response.getStatusCode();
        return statusCode;
    }


    //***   Print JSON response into created file
    public void createLogFile(String response) throws IOException {
        File file = new File(report_json_folder + testName + ".json");
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(response);
        fileWriter.flush();
        fileWriter.close();
    }
}
