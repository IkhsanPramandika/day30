package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    private By checkoutBtn = By.id("checkout");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void clickCheckout() {
        click(checkoutBtn);
    }
}