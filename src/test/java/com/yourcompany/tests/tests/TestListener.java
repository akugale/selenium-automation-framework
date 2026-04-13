package com.yourcompany.framework.core;

import com.aventstack.extentreports.*;
import org.testng.*;
import org.testng.ITestResult;
import com.yourcompany.framework.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;

public class TestListener implements ITestListener {
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    public void onTestSuccess(ITestResult result) {
        test.get().pass("Test passed");
    }

    public void onTestFailure(ITestResult result) {
        WebDriver driver = DriverFactory.getDriver();
        String path = ScreenshotUtil.takeScreenshot(driver, result.getMethod().getMethodName());
        System.out.println("Screenshot Path: " + path);
    }

    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
