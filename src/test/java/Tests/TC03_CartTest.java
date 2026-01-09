package Tests;

import DriverFactory.DriverFactory;
import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
import Pages.P02_LandingPage;
import Pages.P03_CartPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})
public class TC03_CartTest {

    private final String USERNAME = DataUtils.getJsondata("validLogin", "username");
    private final String PASSWORD = DataUtils.getJsondata("validLogin", "password");

    @BeforeMethod
    public void setup() throws IOException {
        DriverFactory.setupDriver(DataUtils.getPropertyData("environment", "Browser"));
        LogsUtils.info("Edge driver is opened");
        DriverFactory.getDriver().get(DataUtils.getPropertyData("environment", "Login_URL"));
        LogsUtils.info("SwagLabs login page is opened Successfully");
        DriverFactory.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void comparingPricesTC() throws IOException {
        String totPrice = new P01_LoginPage(DriverFactory.getDriver())
                .enterUsername(USERNAME).enterPassword(PASSWORD)
                .clickOnLoginButton().
                addRandomProducts(2, 6).
                getTotalPriceOfSelectedProducts();
        new P02_LandingPage(DriverFactory.getDriver()).clickOnCartIcon();
        Assert.assertTrue(new P03_CartPage(DriverFactory.getDriver()).comparingPrices(totPrice));
    }

    @AfterMethod
    public void quit() {
        DriverFactory.quitDriver();
    }
}