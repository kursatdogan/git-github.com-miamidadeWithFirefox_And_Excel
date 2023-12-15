package scott.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {

    private WaitUtils() {
    }
    public static WebElement waitForElementToBeClickable(By locator) {
        WebDriver driver = Driver.get();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Set your desired timeout
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

}
