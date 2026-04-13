package com.careir.automation.pages.refdata;

import com.careir.automation.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Reference Data module — list + CRUD entry points. Tune selectors to your Angular grid/dialogs.
 */
public class ReferenceDataListPage extends BasePage {

    @FindBy(css = "button[data-testid='ir-refdata-add']")
    private WebElement addButton;

    @FindBy(css = "input[data-testid='ir-refdata-search']")
    private WebElement searchInput;

    public ReferenceDataListPage openModule() {
        navigateTo("reference-data");
        return this;
    }

    public void openCreateDialog() {
        waits().forClickable(addButton).click();
    }

    public void search(String text) {
        waits().forVisible(searchInput).clear();
        searchInput.sendKeys(text);
    }
}
