package com.careir.automation.base;

import com.careir.automation.utils.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.time.Duration;

/**
 * Thread-local WebDriver for parallel TestNG execution.
 */
public final class DriverFactory {

    private static final Logger LOG = LogManager.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static void initDriver() {
        if (DRIVER.get() != null) {
            LOG.warn("initDriver called but driver already exists for this thread");
            return;
        }
        String browser = ConfigReader.get("browser", "chrome");
        WebDriver driver = switch (browser.toLowerCase()) {
            case "edge" -> createEdge();
            default -> createChrome();
        };
        int implicit = ConfigReader.getInt("implicit.wait", 0);
        if (implicit > 0) {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicit));
        }
        DRIVER.set(driver);
        LOG.info("WebDriver started: {}", browser);
    }

    private static WebDriver createChrome() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        if (ConfigReader.getBoolean("headless", false)) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        return new ChromeDriver(options);
    }

    private static WebDriver createEdge() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        if (ConfigReader.getBoolean("headless", false)) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--start-maximized");
        return new EdgeDriver(options);
    }

    public static WebDriver getDriver() {
        WebDriver d = DRIVER.get();
        if (d == null) {
            throw new IllegalStateException("Driver not initialized. Call initDriver() from @BeforeMethod.");
        }
        return d;
    }

    public static void quitDriver() {
        WebDriver d = DRIVER.get();
        if (d != null) {
            try {
                d.quit();
            } catch (Exception e) {
                LOG.warn("Error quitting driver: {}", e.getMessage());
            } finally {
                DRIVER.remove();
            }
        }
    }
}
