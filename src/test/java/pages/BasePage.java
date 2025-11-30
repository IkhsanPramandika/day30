package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }

    // --- FITUR LOGGING ---
    protected void log(String action) {
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        System.out.println(String.format("[%s] [ACTION] %s", time, action));
    }

    // --- FITUR INTERAKSI ---

    protected void enterText(By locator, String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.findElement(locator).sendKeys(text);
        // Log otomatis mencatat apa yang diketik
        log("Mengetik '" + text + "' ke field: " + locator.toString());
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        wait.until(ExpectedConditions.elementToBeClickable(locator));

        WebElement element = driver.findElement(locator);
        js.executeScript("arguments[0].click();", element);

        // Log otomatis mencatat apa yang diklik
        log("Mengklik tombol: " + locator.toString());
    }

    protected String getText(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        String text = driver.findElement(locator).getText();
        log("Mengambil teks: '" + text + "'");
        return text;
    }
}