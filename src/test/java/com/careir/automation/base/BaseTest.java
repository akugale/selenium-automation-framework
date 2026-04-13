package com.careir.automation.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * Shared lifecycle for UI tests. Extend per module or use directly for simple suites.
 */
public abstract class BaseTest {

    protected final Logger log = LogManager.getLogger(getClass());

    @BeforeMethod(alwaysRun = true)
    public void baseSetup() {
        DriverFactory.initDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void baseTeardown() {
        DriverFactory.quitDriver();
    }
}
