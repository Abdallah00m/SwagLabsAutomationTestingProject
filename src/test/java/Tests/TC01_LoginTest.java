package Tests;

import DriverFactory.DriverFactory;
import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
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
public class TC01_LoginTest {

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
    public void validLoginTC() throws IOException {
        new P01_LoginPage(DriverFactory.getDriver())
                .enterUsername(USERNAME).enterPassword(PASSWORD)
                .clickOnLoginButton();
        Assert.assertTrue(new P01_LoginPage(DriverFactory.getDriver()).
                assertLoginTC(DataUtils.getPropertyData("environment", "Home_URL")));
    }

    @AfterMethod
    public void quit() {
        DriverFactory.quitDriver();
    }
}
