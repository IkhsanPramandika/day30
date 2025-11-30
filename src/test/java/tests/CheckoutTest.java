package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import pages.*;

@Execution(ExecutionMode.CONCURRENT)
public class CheckoutTest extends BaseTest {

    @Test
    public void testSuccessfulCheckout() {
        System.out.println("\n=== MULAI TEST: CHECKOUT SCENARIO ===\n");

        // Init Pages
        LoginPage loginPage = new LoginPage(driver);
        InventoryPage inventoryPage = new InventoryPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        // 1. Buka Web
        System.out.println("--- STEP 1: LOGIN ---");
        driver.get("https://www.saucedemo.com/");
        loginPage.login("standard_user", "secret_sauce");

        // Handle Pop-up
        handlePopup();

        // 2. Add to Cart
        System.out.println("--- STEP 2: ADD TO CART ---");
        inventoryPage.addBackpackToCart();
        inventoryPage.goToCart();

        // 3. Checkout
        System.out.println("--- STEP 3: CHECKOUT ---");
        cartPage.clickCheckout();

        // 4. Fill Form
        System.out.println("--- STEP 4: ISI DATA ---");
        checkoutPage.fillInformation("Muhammad", "Ikhsan Pramandika", "28292");
        checkoutPage.clickContinue();

        // 5. Finish
        System.out.println("--- STEP 5: FINALISASI ---");
        checkoutPage.clickFinish();

        // 6. Verify
        System.out.println("--- STEP 6: VERIFIKASI ---");
        String actualMessage = checkoutPage.getSuccessMessage();

        Assertions.assertEquals("Thank you for your order!", actualMessage);

        System.out.println("\n[SUCCESS] Test Passed: " + actualMessage);
        System.out.println("=====================================");
    }
}