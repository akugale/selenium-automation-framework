package com.careir.automation.pages.admin;

import com.careir.automation.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Admin module — role-based navigation checks (Maker vs Checker capabilities).
 */
public class AdminHomePage extends BasePage {

    @FindBy(css = "[data-testid='ir-nav-maker']")
    private WebElement makerNav;

    @FindBy(css = "[data-testid='ir-nav-checker']")
    private WebElement checkerNav;

    public AdminHomePage openAdmin() {
        navigateTo("admin");
        return this;
    }

    public boolean isMakerSectionPresent() {
        try {
            return makerNav.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCheckerSectionPresent() {
        try {
            return checkerNav.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
