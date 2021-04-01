package com.demo.properties;

import com.demo.config.BasicTestConfig;



public class Environments extends BasicTestConfig {
    private static String path;

    //***** HOSTS
    public static String HOST = "rbaskets.in";


    //***** PATHS
    public static String getCreateNewBasketPath(String basketName) {
        path = "/api/baskets/" + basketName;
        return path;
    }

    public static String getCollectedRequestsPath(String basketName) {
        path = "/api/baskets/" + basketName + "/requests";
        return path;
    }
}