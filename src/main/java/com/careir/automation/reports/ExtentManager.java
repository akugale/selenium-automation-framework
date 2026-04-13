package com.careir.automation.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public final class ExtentManager {

    private static ExtentReports extent;

    private ExtentManager() {
    }

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            String reportPath = "reports/ExtentReport.html";
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setReportName("CareIR — Internal Rating");
            spark.config().setDocumentTitle("Automation Report");
            extent = new ExtentReports();
            extent.setSystemInfo("Framework", "Selenium + TestNG + POM");
            extent.attachReporter(spark);
        }
        return extent;
    }
}
