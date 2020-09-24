package com.demo.tests;

import com.demo.config.RestAssuredConfig;
import org.testng.annotations.*;

import java.lang.reflect.Method;

import static com.demo.config.ExtentReport.extent;
import static com.demo.properties.TestData.testName;
import static com.demo.scripts.api.user.Bootstrap.*;
import static com.demo.scripts.api.user.RegisterNewUser.register_new_user;

@Listeners(com.demo.config.TestNGListener.class)
public class TestCase_01_UserRegistration extends RestAssuredConfig {

    @AfterTest
    public static void tearDown() { extent.flush();
    }


    @Test(description = "API", priority = 0)
    public void api_bootstrap(Method method) throws Exception {
        testName = method.getName();
        bootstrap("iOS", "6.0", "iPhone X");
    }

    @Test(description = "API", priority = 1)
    public void create_new_user(Method method) throws Exception {
        testName = method.getName();
        register_new_user();
    }
}