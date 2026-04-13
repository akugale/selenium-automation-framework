package com.careir.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotUtil {

    private static final Logger LOG = LogManager.getLogger(ScreenshotUtil.class);

    private ScreenshotUtil() {
    }

    /**
     * Saves a PNG; overwrites same name. For unique names (e.g. CI artifacts), use {@link #takeScreenshotUnique}.
     */
    public static String takeScreenshot(WebDriver driver, String name) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String folderPath = System.getProperty("user.dir") + "/screenshots/";
            File folder = new File(folderPath);
            if (!folder.exists() && !folder.mkdirs()) {
                LOG.warn("Could not create screenshots folder: {}", folderPath);
            }
            String filePath = folderPath + sanitize(name) + ".png";
            File dest = new File(filePath);
            Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return filePath;
        } catch (Exception e) {
            LOG.error("Screenshot failed", e);
            return null;
        }
    }

    public static String takeScreenshotUnique(WebDriver driver, String name) {
        String stamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        return takeScreenshot(driver, sanitize(name) + "_" + stamp);
    }

    private static String sanitize(String name) {
        return name.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
