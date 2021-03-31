package com.demo.tests;

import com.demo.config.BasicTestConfig;
import com.demo.scripts.CreateNewBasket;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.demo.properties.TestData.testName;

@Listeners(com.demo.config.TestNGListener.class)
public class TestCase_01 extends BasicTestConfig {


    @Test(description = "API", priority = 0)
    public void add_new_basket(Method method) throws Exception {
        CreateNewBasket createNewBasket = new CreateNewBasket();
        testName = method.getName();
        createNewBasket.newBasket("automation_test_1", 220);
    }
}