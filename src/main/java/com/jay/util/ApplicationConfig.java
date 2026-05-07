package com.jay.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ApplicationConfig {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = ApplicationConfig.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new IllegalStateException("application.properties not found in classpath");
            }
            PROPERTIES.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load application.properties", e);
        }
    }

    private ApplicationConfig() {
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    public static String get(String key, String defaultValue) {
        return PROPERTIES.getProperty(key, defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        String value = PROPERTIES.getProperty(key);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return Integer.parseInt(value.trim());
    }
}