package com.yourcompany.framework.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.yourcompany.framework.core.DriverFactory;

public class LoginPage {
    private WebDriver driver;

    @FindBy(xpath = "//input[@class='_2IX_2- VJZDxU']")
    private WebElement username;

    @FindBy(xpath = "//input[@type='password']")
    private WebElement password;

    @FindBy(xpath = "//button[contains(.,'Login')]")
    private WebElement loginBtn;

    public LoginPage() {
        this.driver = DriverFactory.getDriver();
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get(com.yourcompany.framework.core.ConfigReader.get("base.url"));
    }

    public void login(String user, String pass) {
        username.sendKeys(user);
        password.sendKeys(pass);
        loginBtn.click();
    }
}
