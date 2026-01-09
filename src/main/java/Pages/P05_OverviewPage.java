package Pages;

import Utilities.LogsUtils;
import Utilities.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class P05_OverviewPage {
    private final WebDriver driver;
    private final By subTotalPrice = By.className("summary_subtotal_label");
    private final By taxPrice = By.className("summary_tax_label");
    private final By totalPrice = By.className("summary_total_label");
    private final By finishButton = By.id("finish");

    public P05_OverviewPage(WebDriver driver) {
        this.driver = driver;
    }

    public Float getSubTotalPrice() {
        return Float.parseFloat(Utility.getText(driver, subTotalPrice).replace("Item total: $", ""));
    }

    public Float getTaxPrice() {
        return Float.parseFloat(Utility.getText(driver, taxPrice).replace("Tax: $", ""));
    }

    public Float getTotalPrice() {
        LogsUtils.info("Actual Total Price: " + (Utility.getText(driver, totalPrice).replace("Total: $", "")));
        return Float.parseFloat(Utility.getText(driver, totalPrice).replace("Total: $", ""));
    }

    public String calculateTotalPrice() {
        LogsUtils.info("Calculated Total Price: " + (getSubTotalPrice() + getTaxPrice()));
        return String.valueOf(getSubTotalPrice() + getTaxPrice());
    }

    public boolean comparingTotalPrices() {
        return calculateTotalPrice().equals(String.valueOf(getTotalPrice()));
    }

    public P06_FinishingOrderPage clickOnFinishButton() {
        Utility.clickingOnElement(driver, finishButton);
        return new P06_FinishingOrderPage(driver);
    }
}
