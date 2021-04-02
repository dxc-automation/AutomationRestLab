package com.demo.properties;

import java.util.Random;

public class TestData {

    public String getRandomGeneratedString() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }


    //  * * * *    TEST INFO
    public static String testName;
    public static String testDescription;
    public static String testAuthor;
    public static String functionality;


    //  * * * *    ENDPOINT
    public static String scheme;
    public static String host;
    public static String path;


    //  * * * *    RESPONSE
    public static String basketName;
    public static String accessToken;
    public static String url;
    public static Exception exception;

}
