package com.demo.tests;

import com.demo.config.RestAssuredConfig;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.demo.config.ExtentReport.extent;
import static com.demo.properties.TestData.testName;
import static com.demo.scripts.api.user.Bootstrap.bootstrap;
import static com.demo.scripts.api.user.Login.user_login;
import static com.demo.scripts.api.user.RegisterNewUser.register_new_user;

@Listeners(com.demo.config.TestNGListener.class)
public class TestCase_02_UserLogin extends RestAssuredConfig {

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

    @Test(description = "API", priority = 1)
    public void login(Method method) throws Exception {
        testName = method.getName();
        user_login();
    }
}