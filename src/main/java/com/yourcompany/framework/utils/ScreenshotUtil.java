package com.yourcompany.framework.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;

public class ScreenshotUtil {

    public static String takeScreenshot(WebDriver driver, String name) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            String folderPath = System.getProperty("user.dir") + "/screenshots/";
            File folder = new File(folderPath);
            if (!folder.exists()) folder.mkdirs();

            String filePath = folderPath + name + ".png";
            File dest = new File(filePath);

            Files.copy(src.toPath(), dest.toPath());

            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
