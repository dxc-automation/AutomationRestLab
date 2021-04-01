package com.demo.tests;

import com.demo.config.BasicTestConfig;
import com.demo.properties.TestData;
import com.demo.scripts.CreateNewBasket;
import com.demo.scripts.GetCollectedRequests;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

import static com.demo.properties.TestData.*;

@Listeners(com.demo.config.TestNGListener.class)
public class TestCase_01 extends BasicTestConfig {


    @Test(description = "API", priority = 0)
    public void add_new_basket(Method method) throws Exception {
        TestData testData = new TestData();
        basketName = testData.getRandomGeneratedString();

        testName = method.getName();
        CreateNewBasket createNewBasket = new CreateNewBasket();
        createNewBasket.newBasket(basketName, 220);
    }


    @Test(description = "API", priority = 1)
    public void get_collected_requests(Method method) throws Exception {
        testName = method.getName();

        GetCollectedRequests getCollectedRequests = new GetCollectedRequests();
        getCollectedRequests.getCollectedRequests(basketName);
    }
}