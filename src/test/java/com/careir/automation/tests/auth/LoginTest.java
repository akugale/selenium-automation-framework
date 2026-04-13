package com.careir.automation.tests.auth;

import com.careir.automation.base.BaseTest;
import com.careir.automation.pages.LoginPage;
import com.careir.automation.tests.listeners.RetryAnalyzer;
import com.careir.automation.utils.ConfigReader;
import com.careir.automation.utils.ExcelReader;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        String sheet = ConfigReader.get("testdata.login.excel.sheet", "Sheet1");
        String excelPath = ConfigReader.get("testdata.login.excel.path", "").trim();
        if (!excelPath.isEmpty()) {
            log.info("Reading login data from uploaded Excel path: {}", excelPath);
            return ExcelReader.readSheet(excelPath, sheet);
        }
        log.info("Reading login data from classpath: testdata/login.xlsx");
        try {
            return ExcelReader.readSheetFromClasspath("testdata/login.xlsx", sheet);
        } catch (RuntimeException e) {
            throw new IllegalStateException(
                    "Login Excel not found. Upload an Excel file and set testdata.login.excel.path in config-qa.properties",
                    e);
        }
    }

    @Test(dataProvider = "loginData", groups = {"smoke", "regression"}, retryAnalyzer = RetryAnalyzer.class)
    public void testLogin(String username, String password) {
        log.info("Executing login smoke for user {}", username);
        new LoginPage().open().login(username, password);
    }
}
