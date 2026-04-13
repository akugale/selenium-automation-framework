package com.careir.automation.tests.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.careir.automation.base.DriverFactory;
import com.careir.automation.reports.ExtentManager;
import com.careir.automation.utils.ScreenshotUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentTestListener implements ITestListener {

    private static final Logger LOG = LogManager.getLogger(ExtentTestListener.class);
    private static final ExtentReports EXTENT = ExtentManager.getInstance();
    private static final ThreadLocal<ExtentTest> TEST = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        String name = result.getMethod().getMethodName();
        String groups = String.join(",", result.getMethod().getGroups());
        String title = groups.isEmpty() ? name : name + " [" + groups + "]";
        TEST.set(EXTENT.createTest(title));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTest t = TEST.get();
        if (t != null) {
            t.pass("Passed");
        }
        TEST.remove();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest t = TEST.get();
        Throwable th = result.getThrowable();
        if (t != null) {
            t.fail(th);
        }
        try {
            WebDriver driver = DriverFactory.getDriver();
            String path = ScreenshotUtil.takeScreenshotUnique(driver, result.getMethod().getMethodName());
            if (t != null && path != null) {
                t.addScreenCaptureFromPath(path);
            }
            LOG.info("Failure screenshot: {}", path);
        } catch (IllegalStateException ex) {
            LOG.warn("No driver for screenshot: {}", ex.getMessage());
        }
        TEST.remove();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest t = TEST.get();
        if (t != null) {
            t.skip(result.getThrowable() != null ? result.getThrowable().getMessage() : "Skipped");
        }
        TEST.remove();
    }

    @Override
    public void onFinish(ITestContext context) {
        EXTENT.flush();
    }
}
