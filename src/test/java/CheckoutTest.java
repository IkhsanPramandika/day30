import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class CheckoutTest {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    // Helper untuk membuat Log yang rapi dengan Timestamp
    private void log(String type, String message) {
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        System.out.println(String.format("[%s] [%-7s] %s", time, type, message));
    }

    @BeforeEach
    public void setUp() {
        log("SETUP", "Menginisialisasi WebDriver & Chrome Options...");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;

        log("SETUP", "Browser berhasil dibuka.");
    }

    @Test
    public void testSuccessfulCheckout() throws Exception {
        System.out.println("\n=================================================");
        System.out.println("       MULAI TEST: SCENARIO SUCCESS CHECKOUT       ");
        System.out.println("=================================================\n");

        // --- STEP 1 ---
        log("STEP 1", "Membuka halaman SauceDemo");
        driver.get("https://www.saucedemo.com/");

        // --- STEP 2 ---
        log("STEP 2", "Melakukan Login sebagai 'standard_user'");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // --- HANDLING POPUP ---
        log("ACTION", "Menunggu potensi Pop-up Password muncul...");
        Thread.sleep(2000);
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            log("ROBOT", "Tombol ENTER ditekan untuk menutup Pop-up Browser");
        } catch (Exception e) {
            log("ERROR", "Robot gagal: " + e.getMessage());
        }

        // --- STEP 3 ---
        log("STEP 3", "Memilih produk 'Backpack' (Add to Cart)");
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

        // --- STEP 4 ---
        log("STEP 4", "Masuk ke halaman Keranjang");
        WebElement cartIcon = driver.findElement(By.cssSelector("[data-test='shopping-cart-link']"));
        js.executeScript("arguments[0].click();", cartIcon); // JS Click

        // --- STEP 5 ---
        log("STEP 5", "Klik tombol Checkout");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkout")));
        WebElement checkoutBtn = driver.findElement(By.id("checkout"));
        js.executeScript("arguments[0].click();", checkoutBtn); // JS Click

        // --- STEP 6 ---
        log("STEP 6", "Mengisi Form Informasi Pengiriman");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")));
        driver.findElement(By.id("first-name")).sendKeys("Muhammad Ikhsan");
        driver.findElement(By.id("last-name")).sendKeys("Pramandika");
        driver.findElement(By.id("postal-code")).sendKeys("28292");
        log("INFO", "Data input: Muhammad Ikhsan / Pramandika / 28292");

        // --- STEP 7 ---
        log("STEP 7", "Klik Continue");
        WebElement continueBtn = driver.findElement(By.id("continue"));
        js.executeScript("arguments[0].click();", continueBtn); // JS Click

        // --- STEP 8 ---
        log("STEP 8", "Klik Finish");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("finish")));
        WebElement finishBtn = driver.findElement(By.id("finish"));
        js.executeScript("arguments[0].click();", finishBtn); // JS Click

        // --- STEP 9 ---
        log("STEP 9", "Verifikasi Pesan Sukses");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("complete-header")));
        WebElement headerElement = driver.findElement(By.className("complete-header"));
        String actualText = headerElement.getText();

        Assertions.assertEquals("Thank you for your order!", actualText);

        System.out.println("\n=================================================");
        log("PASSED", "Pesan validasi sesuai: " + actualText);
        System.out.println("=================================================");
    }

    @AfterEach
    public void tearDown() {
        log("TEARDOWN", "Menutup browser...");
        if (driver != null) {
            driver.quit();
        }
        log("TEARDOWN", "Test Selesai.");
    }
}
