package com.careir.automation.pages.refdata;

import com.careir.automation.base.BasePage;
import org.openqa.selenium.By;

/**
 * Reference Data module — list + CRUD entry points. Tune selectors to your Angular grid/dialogs.
 */
public class ReferenceDataListPage extends BasePage {

    private final By addButton = By.cssSelector("button[data-testid='ir-refdata-add']");
    private final By searchInput = By.cssSelector("input[data-testid='ir-refdata-search']");

    public ReferenceDataListPage openModule() {
        navigateTo("reference-data");
        return this;
    }

    public void openCreateDialog() {
        waits().forClickable(addButton).click();
    }

    public void search(String text) {
        waits().forVisible(searchInput).clear();
        driver.findElement(searchInput).sendKeys(text);
    }
}
