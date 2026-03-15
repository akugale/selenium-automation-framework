package com.yourcompany.tests.tests;

import com.yourcompany.framework.core.DriverFactory;
import com.yourcompany.framework.core.ConfigReader;
import com.yourcompany.framework.pages.LoginPage;
import com.yourcompany.framework.utils.ExcelReader;
import org.testng.annotations.*;

public class LoginTest {
    @BeforeMethod
    public void setup() {
        DriverFactory.initDriver();
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return ExcelReader.readSheet("src/main/resources/testdata/login.xlsx", "Sheet1");
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password) {
        LoginPage lp = new LoginPage();
        lp.open();
       // lp.login(username, password);
        // add assertions after login (e.g., check user icon)
    }
}
