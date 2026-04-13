package com.careir.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

/**
 * Loads {@code config.properties}, then overlays environment-specific file
 * {@code config-&lt;env&gt;.properties} where env is {@code -Denv=qa} or {@code TEST_ENV}.
 */
public final class ConfigReader {

    private static final Logger LOG = LogManager.getLogger(ConfigReader.class);
    private static final Properties PROP = new Properties();

    static {
        String env = firstNonBlank(
                System.getProperty("env"),
                System.getenv("TEST_ENV"),
                "qa"
        );
        loadResource("config.properties", true);
        boolean overlayLoaded = loadResource("config-" + env + ".properties", false);
        LOG.info("Automation config env='{}' overlayLoaded={}", env, overlayLoaded);
    }

    private ConfigReader() {
    }

    private static boolean loadResource(String name, boolean required) {
        try (InputStream in = ConfigReader.class.getClassLoader().getResourceAsStream(name)) {
            if (in == null) {
                if (required) {
                    throw new IllegalStateException("Missing required resource: " + name);
                }
                return false;
            }
            PROP.load(in);
            return true;
        } catch (Exception e) {
            throw new IllegalStateException("Cannot load " + name, e);
        }
    }

    private static String firstNonBlank(String a, String b, String fallback) {
        if (a != null && !a.isBlank()) {
            return a.trim();
        }
        if (b != null && !b.isBlank()) {
            return b.trim();
        }
        return fallback;
    }

    public static String get(String key) {
        return PROP.getProperty(key);
    }

    public static String get(String key, String defaultValue) {
        return PROP.getProperty(key, defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        String v = PROP.getProperty(key);
        if (v == null || v.isBlank()) {
            return defaultValue;
        }
        return Integer.parseInt(v.trim());
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String v = PROP.getProperty(key);
        if (v == null || v.isBlank()) {
            return defaultValue;
        }
        return Boolean.parseBoolean(v.trim());
    }
}
