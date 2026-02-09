package com.qa.automation.project05.base;

import com.qa.automation.project05.config.TestConfig;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * JUnit 5 TestWatcher: captures a screenshot when a test fails.
 * Register with @RegisterExtension and pass a Supplier that returns the current WebDriver.
 */
public class ScreenshotOnFailureWatcher implements TestWatcher {

    private final Supplier<WebDriver> driverSupplier;

    public ScreenshotOnFailureWatcher(Supplier<WebDriver> driverSupplier) {
        this.driverSupplier = driverSupplier;
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        WebDriver driver = driverSupplier.get();
        if (driver != null && driver instanceof TakesScreenshot) {
            try {
                Path dir = Paths.get(TestConfig.getScreenshotOutputDir());
                Files.createDirectories(dir);
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
                String className = context.getRequiredTestClass().getSimpleName();
                String methodName = context.getRequiredTestMethod().getName();
                String fileName = String.format("%s_%s_%s.png", className, methodName, timestamp);
                Path file = dir.resolve(fileName);
                byte[] bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                Files.write(file, bytes);
                System.out.println("[Screenshot on failure] Saved: " + file.toAbsolutePath());
            } catch (IOException e) {
                System.err.println("[Screenshot on failure] Could not save screenshot: " + e.getMessage());
            }
        }
        quitDriver();
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        quitDriver();
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        quitDriver();
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        quitDriver();
    }

    private void quitDriver() {
        WebDriver d = driverSupplier.get();
        if (d != null) {
            try {
                d.quit();
            } catch (Exception ignored) {
            }
        }
    }
}
