package com.careir.automation.pages;

import com.careir.automation.base.BasePage;
import org.openqa.selenium.By;

/**
 * Maker flow: create/edit rating request before authorization.
 * Routes and field locators must match your Angular IR module.
 */
public class MakerRatingRequestPage extends BasePage {

    private final By newRequestButton = By.cssSelector("button[data-testid='ir-maker-new-request']");
    private final By customerField = By.cssSelector("input[data-testid='ir-rating-customer-id']");
    private final By notesField = By.cssSelector("textarea[data-testid='ir-rating-notes']");
    private final By saveDraftButton = By.cssSelector("button[data-testid='ir-rating-save-draft']");
    private final By submitForAuthButton = By.cssSelector("button[data-testid='ir-rating-submit-auth']");

    public MakerRatingRequestPage openMakerWorkspace() {
        navigateTo("rating/maker");
        return this;
    }

    public MakerRatingRequestPage startNewRequest() {
        waits().forClickable(newRequestButton).click();
        return this;
    }

    public MakerRatingRequestPage fillBasicDetails(String customerId, String notes) {
        waits().forVisible(customerField).clear();
        driver.findElement(customerField).sendKeys(customerId);
        waits().forVisible(notesField).clear();
        driver.findElement(notesField).sendKeys(notes);
        return this;
    }

    public void saveDraft() {
        waits().forClickable(saveDraftButton).click();
    }

    public void submitForAuthorization() {
        waits().forClickable(submitForAuthButton).click();
    }
}
