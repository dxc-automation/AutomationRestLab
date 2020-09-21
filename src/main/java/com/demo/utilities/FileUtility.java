package com.demo.utilities;

import com.demo.config.BasicTestConfig;
import com.google.gson.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.demo.properties.FilePaths.report_json_folder;
import static com.demo.properties.TestData.*;

public class FileUtility extends BasicTestConfig {

    public static final JsonParser parser = new JsonParser();
    public static final JSONParser jsonParser = new JSONParser();
    private static Gson gson = new GsonBuilder().setLenient().setPrettyPrinting().create();

    private static String date;


    public static String getDate() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.now();
        date = dateTimeFormatter.format(localDate);
        return date;
    }


    public static Timestamp getTime(Long timestamp) throws Exception {
        Timestamp time = new Timestamp(timestamp);
        return time;
    }


    public static String readJsonResponseFile() {
        try {
            JsonObject object = parser.parse(new FileReader(report_json_folder + testName)).getAsJsonObject();
            String formattedJson = gson.toJson(object);
            return formattedJson;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getFormattedJson(String responseBody) {
        try {
            Gson gson = new GsonBuilder().setLenient().setPrettyPrinting().create();
            JsonObject jsonObject    = parser.parse(responseBody).getAsJsonObject();
            String formattedJson = gson.toJson(jsonObject);
            return formattedJson;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //***   Print JSON response into created file
    public static File createLogFile() {
        try {
            JsonObject jsonObject = parser.parse(responseBody).getAsJsonObject();
            formattedJson = gson.toJson(jsonObject);
            File file = new File(report_json_folder + testName + ".json");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(formattedJson);
            fileWriter.flush();
            fileWriter.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}