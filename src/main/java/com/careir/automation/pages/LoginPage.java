package com.careir.automation.pages;

import com.careir.automation.base.BasePage;
import com.careir.automation.utils.ConfigReader;
import org.openqa.selenium.By;

/**
 * IR application login. Align {@code By} constants with your Angular templates
 * (prefer {@code data-testid} attributes for stable selectors).
 */
public class LoginPage extends BasePage {

    /**
     * Update these selectors to match the IR login screen (Angular form controls / material inputs).
     */
    private final By usernameInput = By.cssSelector(
            "input[data-testid='ir-login-username'], input[formcontrolname='username'], input[name='username']");
    private final By passwordInput = By.cssSelector(
            "input[data-testid='ir-login-password'], input[formcontrolname='password'], input[type='password']");
    private final By loginButton = By.cssSelector(
            "button[data-testid='ir-login-submit'], button[type='submit'], button.login-btn");

    public LoginPage open() {
        driver.get(ConfigReader.get("login.url"));
        return this;
    }

    public LoginPage enterCredentials(String user, String pass) {
        waits().forVisible(usernameInput).clear();
        driver.findElement(usernameInput).sendKeys(user);
        waits().forVisible(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(pass);
        return this;
    }

    public void submit() {
        waits().forClickable(loginButton).click();
    }

    public void login(String user, String pass) {
        enterCredentials(user, pass);
        submit();
    }
}
