package Pages;

import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P06_FinishingOrderPage {
    private final WebDriver driver;
    private final By thanksMsg = By.tagName("h2");

    public P06_FinishingOrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean checkVisibilityOfThanksMsg() {
        return Utility.findWebElement(driver, thanksMsg).isDisplayed();
    }
}
