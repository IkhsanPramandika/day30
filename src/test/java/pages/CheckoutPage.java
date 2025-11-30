package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    private By firstNameField = By.id("first-name");
    private By lastNameField = By.id("last-name");
    private By zipCodeField = By.id("postal-code");
    private By continueBtn = By.id("continue");
    private By finishBtn = By.id("finish");
    private By completeHeader = By.className("complete-header");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void fillInformation(String first, String last, String zip) {
        enterText(firstNameField, first);
        enterText(lastNameField, last);
        enterText(zipCodeField, zip);
    }

    public void clickContinue() {
        click(continueBtn);
    }

    public void clickFinish() {
        click(finishBtn);
    }

    public String getSuccessMessage() {
        return getText(completeHeader);
    }
}