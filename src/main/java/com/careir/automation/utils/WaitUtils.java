package com.careir.automation.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Explicit wait helpers (preferred over implicit waits for Angular apps).
 */
public class WaitUtils {

    private final WebDriver driver;
    private final int defaultSeconds;

    public WaitUtils(WebDriver driver, int defaultSeconds) {
        this.driver = driver;
        this.defaultSeconds = defaultSeconds;
    }

    public WebDriverWait wait(int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds));
    }

    public WebElement forVisible(By locator) {
        return wait(defaultSeconds).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement forClickable(By locator) {
        return wait(defaultSeconds).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public boolean forInvisible(By locator) {
        return wait(defaultSeconds).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public List<WebElement> forAllVisible(By locator) {
        return wait(defaultSeconds).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    /** Static convenience for one-off calls outside {@link com.careir.automation.base.BasePage}. */
    public static WebElement waitForVisibility(WebDriver driver, By locator, int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
