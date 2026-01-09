package Utilities;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class Utility {
    private static final String ScreenShots_PATH = "test-outputs/ScreenShots/";

    //making function used for clicking, but it mixed with webdriver wait until it present and be able to e clickable.
    public static void clickingOnElement(WebDriver driver, By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(locator));
        driver.findElement(locator).click();
    }

    //making function used for sending data, but it mixed with webdriver wait until it present and visible.
    public static void sendData(WebDriver driver, By locator, String data) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.findElement(locator).sendKeys(data);
    }

    //making function used for getting data, but it mixed with webdriver wait until it present and visible.
    public static String getText(WebDriver driver, By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElement(locator).getText();
    }

    //making function used for any general wait.
    public static WebDriverWait generalWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    //making function to convert By to WebElement
    public static WebElement findWebElement(WebDriver driver, By locator) {
        return driver.findElement(locator);
    }

    //making function to select item from dropdown list
    public static void selectingFromDropDown(WebDriver driver, By locator, String option) {
        new Select(findWebElement(driver, locator)).selectByVisibleText(option);
    }

    //making function used for Scrolling.
    public static void scrolling(WebDriver driver, By locator) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", findWebElement(driver, locator));
    }

    //get time stamp function
    public static String getTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd-h-m-ssa").format(new Date());
    }

    //ScreenShot function
    public static void takeScreenShot(WebDriver driver, String screenshotName) {
        try {
            //Capture ScreenShot
            File screenshotSrc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            //Save ScreenShot to a file if needed
            File screenshotFile = new File(ScreenShots_PATH + screenshotName + "-" + getTimeStamp() + ".png");
            FileUtils.copyFile(screenshotSrc, screenshotFile);
            //Attach the screenShot to allure
            Allure.addAttachment(screenshotName, Files.newInputStream(Path.of(screenshotFile.getPath())));
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
        }
    }

    public static void takeFullScreenShot(WebDriver driver, By locator) {
        try {
            Shutterbug.shootPage(driver, Capture.FULL_SCROLL).highlight(findWebElement(driver, locator)).save(ScreenShots_PATH);
        } catch (Exception e) {
            LogsUtils.error(e.getMessage());
        }
    }

    public static int generateRandomNumbers(int upperBound) {
        return new Random().nextInt(upperBound) + 1;
    }

    public static Set<Integer> generateRandomUniqueNumbers(int numberNeeded, int totalNumber) {
        Set<Integer> generatedNumbers = new HashSet<>();
        while (generatedNumbers.size() < numberNeeded) {
            int randomNumber = generateRandomNumbers(totalNumber);
            generatedNumbers.add(randomNumber);
        }
        return generatedNumbers;
    }

    public static boolean verifyURL(WebDriver driver, String expectedURL) {
        try {
            Utility.generalWait(driver).until(ExpectedConditions.urlToBe(expectedURL));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static File getLatestFile(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        assert files != null;
        if (files.length == 0)
            return null;
        Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
        return files[0];
    }

}
