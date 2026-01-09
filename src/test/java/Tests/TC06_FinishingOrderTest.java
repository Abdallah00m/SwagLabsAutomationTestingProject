package Tests;

import DriverFactory.DriverFactory;
import Listeners.IInvokedMethodListenerClass;
import Listeners.ITestResultListenerClass;
import Pages.P01_LoginPage;
import Pages.P06_FinishingOrderPage;
import Utilities.DataUtils;
import Utilities.LogsUtils;
import Utilities.Utility;
import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

@Listeners({IInvokedMethodListenerClass.class, ITestResultListenerClass.class})
public class TC06_FinishingOrderTest {

    private final String USERNAME = DataUtils.getJsondata("validLogin", "username");
    private final String PASSWORD = DataUtils.getJsondata("validLogin", "password");
    private final String FIRSTNAME = DataUtils.getJsondata("information", "firstname") + "-" + Utility.getTimeStamp();
    private final String LASTNAME = DataUtils.getJsondata("information", "lastname") + "-" + Utility.getTimeStamp();
    private final String ZIPCODE = new Faker().number().digits(5);

    @BeforeMethod
    public void setup() throws IOException {
        DriverFactory.setupDriver(DataUtils.getPropertyData("environment", "Browser"));
        LogsUtils.info("Edge driver is opened");
        DriverFactory.getDriver().get(DataUtils.getPropertyData("environment", "Login_URL"));
        LogsUtils.info("SwagLabs login page is opened Successfully");
        DriverFactory.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void finishOrderTC() throws IOException {
        new P01_LoginPage(DriverFactory.getDriver())
                .enterUsername(USERNAME).enterPassword(PASSWORD)
                .clickOnLoginButton().
                addAllProductsToCart().
                clickOnCartIcon().clickOnCheckoutButton()
                .fillingInformationCheckout(FIRSTNAME, LASTNAME, ZIPCODE).clickOnContinueButton().clickOnFinishButton();
        LogsUtils.info(FIRSTNAME + "||" + LASTNAME + "||" + ZIPCODE);
        Assert.assertTrue(new P06_FinishingOrderPage(DriverFactory.getDriver()).checkVisibilityOfThanksMsg());
    }

    @AfterMethod
    public void quit() {
        DriverFactory.quitDriver();
    }
}
