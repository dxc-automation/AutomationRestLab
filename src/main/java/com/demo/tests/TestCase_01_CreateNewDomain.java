package com.demo.tests;

import com.demo.config.BasicTestConfig;
import org.testng.annotations.*;

import java.lang.reflect.Method;

import static com.demo.config.ExtentReport.extent;
import static com.demo.config.ExtentReport.startTestReport;
import static com.demo.scripts.api.domain.CreateNewDomain.create_new_domain;
import static com.demo.properties.TestData.*;

@Listeners(com.demo.config.TestNGListener.class)
public class TestCase_01_CreateNewDomain extends BasicTestConfig {

    @AfterTest
    public static void tearDown() { extent.flush();
    }


    @Test(description = "API", priority = 0)
    public void post_new_email(Method method) throws Exception {
        testName = method.getName();
        create_new_domain("Test_Domain");
    }
}