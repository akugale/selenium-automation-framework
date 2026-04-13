package com.careir.automation.tests.ci;

import com.careir.automation.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Fast, non-network checks for CI so the pipeline validates compilation and config load without VPN.
 */
public class CiSanityTest {

    @Test(groups = {"ci"})
    public void configLoads() {
        Assert.assertNotNull(ConfigReader.get("browser"));
    }
}
