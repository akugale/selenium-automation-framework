package com.careir.automation.pages;

import com.careir.automation.base.BasePage;
import com.careir.automation.utils.ConfigReader;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * IR application login. Align selectors with your Angular templates
 * (prefer {@code data-testid} attributes for stable selectors).
 */
public class LoginPage extends BasePage {

    /**
     * Update these selectors to match the IR login screen (Angular form controls / material inputs).
     */
    @FindBy(css =
            "input[data-testid='ir-login-username'], input[formcontrolname='username'], input[name='username']")
    private WebElement usernameInput;

    @FindBy(css =
            "input[data-testid='ir-login-password'], input[formcontrolname='password'], input[type='password']")
    private WebElement passwordInput;

    @FindBy(css =
            "button[data-testid='ir-login-submit'], button[type='submit'], button.login-btn")
    private WebElement loginButton;

    public LoginPage open() {
        driver.get(ConfigReader.get("login.url"));
        return this;
    }

    public LoginPage enterCredentials(String user, String pass) {
        waits().forVisible(usernameInput).clear();
        usernameInput.sendKeys(user);
        waits().forVisible(passwordInput).clear();
        passwordInput.sendKeys(pass);
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
