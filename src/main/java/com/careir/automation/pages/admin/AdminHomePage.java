package com.careir.automation.pages.admin;

import com.careir.automation.base.BasePage;
import org.openqa.selenium.By;

/**
 * Admin module — role-based navigation checks (Maker vs Checker capabilities).
 */
public class AdminHomePage extends BasePage {

    private final By makerNav = By.cssSelector("[data-testid='ir-nav-maker']");
    private final By checkerNav = By.cssSelector("[data-testid='ir-nav-checker']");

    public AdminHomePage openAdmin() {
        navigateTo("admin");
        return this;
    }

    public boolean isMakerSectionPresent() {
        return !driver.findElements(makerNav).isEmpty();
    }

    public boolean isCheckerSectionPresent() {
        return !driver.findElements(checkerNav).isEmpty();
    }
}
