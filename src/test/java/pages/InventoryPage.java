package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage extends BasePage {

    private By backpackAddBtn = By.id("add-to-cart-sauce-labs-backpack");
    private By cartIcon = By.cssSelector("[data-test='shopping-cart-link']");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public void addBackpackToCart() {
        click(backpackAddBtn);
    }

    public void goToCart() {
        click(cartIcon);
    }
}