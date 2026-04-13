package com.careir.automation.base;

import com.careir.automation.utils.ConfigReader;
import com.careir.automation.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Base for all page objects: centralizes driver, waits, and PageFactory init.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final int explicitWaitSec;

    protected BasePage() {
        this.driver = DriverFactory.getDriver();
        this.explicitWaitSec = ConfigReader.getInt("explicit.wait", 15);
        PageFactory.initElements(driver, this);
    }

    protected void navigateTo(String pathOrUrl) {
        if (pathOrUrl.startsWith("http://") || pathOrUrl.startsWith("https://")) {
            driver.get(pathOrUrl);
        } else {
            String base = ConfigReader.get("app.base.url", "");
            String sep = base.endsWith("/") || pathOrUrl.startsWith("/") ? "" : "/";
            String path = pathOrUrl.startsWith("/") ? pathOrUrl.substring(1) : pathOrUrl;
            driver.get(base + sep + path);
        }
    }

    protected WaitUtils waits() {
        return new WaitUtils(driver, explicitWaitSec);
    }
}
