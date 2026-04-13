package com.careir.automation.tests.workflow;

import com.careir.automation.base.BaseTest;
import com.careir.automation.base.DriverFactory;
import com.careir.automation.pages.AuthorizationQueuePage;
import com.careir.automation.pages.LoginPage;
import com.careir.automation.pages.MakerRatingRequestPage;
import com.careir.automation.utils.ConfigReader;
import com.careir.automation.utils.JsonDataReader;
import com.google.gson.JsonObject;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

/**
 * End-to-end Maker → Checker sample. Enable only when IR env and Angular {@code data-testid} hooks match.
 */
public class MakerCheckerWorkflowTest extends BaseTest {

    private JsonObject data;

    @BeforeClass(alwaysRun = true)
    public void loadData() {
        data = JsonDataReader.readObject("testdata/maker_checker_flow.json");
    }

    @Test(groups = {"regression", "workflow"})
    public void makerCreatesRequest_checkerSeesInQueue() {
        if (!ConfigReader.getBoolean("workflow.e2e.enabled", false)) {
            throw new SkipException("Set workflow.e2e.enabled=true when the IR app is reachable and locators are aligned.");
        }
        JsonObject maker = data.getAsJsonObject("maker");
        JsonObject rating = data.getAsJsonObject("ratingRequest");

        String makerUser = maker.get("username").getAsString();
        String makerPass = maker.get("password").getAsString();

        new LoginPage().open().login(makerUser, makerPass);

        MakerRatingRequestPage makerPage = new MakerRatingRequestPage();
        makerPage.openMakerWorkspace();
        makerPage.startNewRequest();
        makerPage.fillBasicDetails(
                rating.get("customerId").getAsString(),
                rating.get("notes").getAsString());
        makerPage.submitForAuthorization();

        DriverFactory.quitDriver();
        DriverFactory.initDriver();

        JsonObject checker = data.getAsJsonObject("checker");
        new LoginPage().open().login(
                checker.get("username").getAsString(),
                checker.get("password").getAsString());

        AuthorizationQueuePage queue = new AuthorizationQueuePage();
        queue.openQueue();
        SoftAssert soft = new SoftAssert();
        soft.assertTrue(queue.isQueueVisible(), "Authorization queue should load for Checker");
        queue.openFirstPendingRow();
        queue.approve();
        soft.assertAll();
    }

    @Test(groups = {"regression", "rbac"})
    public void makerCannotOpenCheckerOnlyRoute() {
        if (!ConfigReader.getBoolean("workflow.e2e.enabled", false)) {
            throw new SkipException("RBAC negative checks require workflow.e2e.enabled=true");
        }
        JsonObject maker = data.getAsJsonObject("maker");
        new LoginPage().open().login(maker.get("username").getAsString(), maker.get("password").getAsString());
        WebDriver d = DriverFactory.getDriver();
        d.get(ConfigReader.get("app.base.url") + "authorization/queue");
        SoftAssert soft = new SoftAssert();
        String body = d.getPageSource().toLowerCase();
        soft.assertTrue(
                body.contains("forbidden")
                        || body.contains("access denied")
                        || d.getCurrentUrl().contains("unauthorized"),
                "Maker should not access Checker authorization UI (adjust assertion to your Angular guard behavior)");
        soft.assertAll();
    }
}
