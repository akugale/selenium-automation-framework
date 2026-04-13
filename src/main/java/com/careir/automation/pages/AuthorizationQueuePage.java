package com.careir.automation.pages;

import com.careir.automation.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Checker flow: authorization queue (pending items). Approve / reject actions.
 */
public class AuthorizationQueuePage extends BasePage {

    @FindBy(css = "[data-testid='ir-auth-queue-table']")
    private WebElement queueTable;

    @FindBy(css = "[data-testid='ir-auth-row']")
    private WebElement rowByRequestId;

    @FindBy(css = "button[data-testid='ir-auth-approve']")
    private WebElement approveButton;

    @FindBy(css = "button[data-testid='ir-auth-reject']")
    private WebElement rejectButton;

    public AuthorizationQueuePage openQueue() {
        navigateTo("authorization/queue");
        return this;
    }

    public boolean isQueueVisible() {
        try {
            waits().forVisible(queueTable);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public AuthorizationQueuePage openFirstPendingRow() {
        waits().forClickable(rowByRequestId).click();
        return this;
    }

    public void approve() {
        waits().forClickable(approveButton).click();
    }

    public void reject() {
        waits().forClickable(rejectButton).click();
    }
}
