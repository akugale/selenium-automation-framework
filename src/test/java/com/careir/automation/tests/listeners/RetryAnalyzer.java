package com.careir.automation.tests.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Retries failed tests up to {@link #MAX_RETRY} times (use for known flaky UI steps only).
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private static final int MAX_RETRY = 2;
    private static final String ATTR = "irRetryCount";

    @Override
    public boolean retry(ITestResult result) {
        Object val = result.getAttribute(ATTR);
        int count = val == null ? 0 : (Integer) val;
        if (count < MAX_RETRY) {
            result.setAttribute(ATTR, count + 1);
            return true;
        }
        return false;
    }
}
