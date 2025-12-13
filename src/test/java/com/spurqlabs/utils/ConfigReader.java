package com.spurqlabs.utils;

import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Utility class to read configuration from config.json
 */
public class ConfigReader {
    private static JsonObject config;
    private static final String CONFIG_PATH = "src/test/resources/config.json";

    static {
        loadConfig();
    }

    /**
     * Load configuration from JSON file
     */
    private static void loadConfig() {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(CONFIG_PATH);
            config = gson.fromJson(reader, JsonObject.class);
            reader.close();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.json: " + e.getMessage());
        }
    }

    /**
     * Get the entire configuration object
     */
    public static JsonObject getConfig() {
        return config;
    }

    /**
     * Get base URL from config
     */
    public static String getBaseUrl() {
        return config.get("baseUrl").getAsString();
    }

    /**
     * Get timeout value from config
     */
    public static int getTimeout() {
        return config.get("timeout").getAsInt();
    }

    /**
     * Get headless mode setting
     */
    public static boolean isHeadless() {
        return config.get("headless").getAsBoolean();
    }

    /**
     * Get browser type from config
     */
    public static String getBrowser() {
        return config.get("browser").getAsString();
    }

    /**
     * Get slow motion delay
     */
    public static int getSlowMo() {
        return config.has("slowMo") ? config.get("slowMo").getAsInt() : 0;
    }
}
