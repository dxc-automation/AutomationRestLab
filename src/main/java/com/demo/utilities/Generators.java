package com.demo.utilities;

import com.demo.config.BasicTestConfig;
import com.github.javafaker.Faker;

import java.util.Random;

public class Generators extends BasicTestConfig {


    //***   Generate random MAC address
    public static String generateMacAddress(){
        Random rand = new Random();
        byte[] macAddr = new byte[6];
        rand.nextBytes(macAddr);
        macAddr[0] = (byte)(macAddr[0] & (byte)254);  //zeroing last 2 bytes to make it unicast and locally adminstrated

        StringBuilder sb = new StringBuilder(18);
        for(byte b : macAddr){
            if(sb.length() > 0)
                sb.append(":");
                sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }


    //***   Generate random email
    public static String generateEmail() {
        Faker faker = new Faker();
        return faker.internet().emailAddress();
    }
}
