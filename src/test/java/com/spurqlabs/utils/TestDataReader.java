package com.spurqlabs.utils;

import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Utility class to read test data from JSON files
 */
public class TestDataReader {
    
    /**
     * Read test data from JSON file
     */
    public static JsonObject readTestData(String fileName) {
        try {
            String filePath = "src/test/resources/data/" + fileName;
            Gson gson = new Gson();
            FileReader reader = new FileReader(filePath);
            JsonObject data = gson.fromJson(reader, JsonObject.class);
            reader.close();
            return data;
        } catch (Exception e) {
            throw new RuntimeException("Failed to read test data from " + fileName + ": " + e.getMessage());
        }
    }

    /**
     * Get login credentials for valid user
     */
    public static JsonObject getValidUserCredentials() {
        JsonObject loginData = readTestData("loginData.json");
        return loginData.getAsJsonObject("validUser");
    }

    /**
     * Get login credentials for invalid user
     */
    public static JsonObject getInvalidUserCredentials() {
        JsonObject loginData = readTestData("loginData.json");
        return loginData.getAsJsonObject("invalidUser");
    }

    /**
     * Get login data by key
     */
    public static java.util.Map<String, String> getLoginData(String key) {
        JsonObject loginData = readTestData("loginData.json");
        JsonObject userData = loginData.getAsJsonObject(key);
        java.util.Map<String, String> result = new java.util.HashMap<>();
        result.put("username", userData.get("username").getAsString());
        result.put("password", userData.get("password").getAsString());
        return result;
    }

    /**
     * Get employee data by key
     */
    public static JsonObject getEmployeeData(String key) {
        JsonObject employeeData = readTestData("employeeData.json");
        return employeeData.getAsJsonObject(key);
    }
}
