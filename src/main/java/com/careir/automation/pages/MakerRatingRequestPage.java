package com.careir.automation.pages;

import com.careir.automation.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Maker flow: create/edit rating request before authorization.
 * Routes and field locators must match your Angular IR module.
 */
public class MakerRatingRequestPage extends BasePage {

    @FindBy(css = "button[data-testid='ir-maker-new-request']")
    private WebElement newRequestButton;

    @FindBy(css = "input[data-testid='ir-rating-customer-id']")
    private WebElement customerField;

    @FindBy(css = "textarea[data-testid='ir-rating-notes']")
    private WebElement notesField;

    @FindBy(css = "button[data-testid='ir-rating-save-draft']")
    private WebElement saveDraftButton;

    @FindBy(css = "button[data-testid='ir-rating-submit-auth']")
    private WebElement submitForAuthButton;

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
        customerField.sendKeys(customerId);
        waits().forVisible(notesField).clear();
        notesField.sendKeys(notes);
        return this;
    }

    public void saveDraft() {
        waits().forClickable(saveDraftButton).click();
    }

    public void submitForAuthorization() {
        waits().forClickable(submitForAuthButton).click();
    }
}
