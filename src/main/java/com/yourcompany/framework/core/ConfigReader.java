package com.yourcompany.framework.core;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties prop = new Properties();
    static {
        try (InputStream in = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            prop.load(in);
        } catch (Exception e) {
            throw new RuntimeException("Cannot load config.properties", e);
        }
    }
    public static String get(String key) { return prop.getProperty(key); }
    public static int getInt(String key) { return Integer.parseInt(prop.getProperty(key)); }
}
