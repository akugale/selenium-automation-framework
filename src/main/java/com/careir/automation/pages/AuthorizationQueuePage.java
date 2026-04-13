package com.careir.automation.pages;

import com.careir.automation.base.BasePage;
import org.openqa.selenium.By;

/**
 * Checker flow: authorization queue (pending items). Approve / reject actions.
 */
public class AuthorizationQueuePage extends BasePage {

    private final By queueTable = By.cssSelector("[data-testid='ir-auth-queue-table']");
    private final By rowByRequestId = By.cssSelector("[data-testid='ir-auth-row']");
    private final By approveButton = By.cssSelector("button[data-testid='ir-auth-approve']");
    private final By rejectButton = By.cssSelector("button[data-testid='ir-auth-reject']");

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
