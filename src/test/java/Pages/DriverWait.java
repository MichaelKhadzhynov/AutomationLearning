package Pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static Pages.BasePage.driver;

public class DriverWait {

    @FindBy(xpath = "//button[@id=\"enableAfter\"]")
    private WebElement enableAfter;

    @FindBy(xpath = "//button[@id=\"colorChange\"]")
    private WebElement colorChange;

    @FindBy(xpath = "//button[@id=\"visibleAfter\"]")
    private WebElement visibleAfter;

    public DriverWait() {
        PageFactory.initElements(driver, this);
    }

    public DriverWait DW() {
        driver.get("https://demoqa.com/dynamic-properties");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
//                FluentWait wait = new FluentWait<>(driver)
//                        .withTimeout(Duration.ofSeconds(7))
//                        .pollingEvery(Duration.ofSeconds(2));
        wait.until(ExpectedConditions.elementToBeClickable(enableAfter));
        enableAfter.click();

        colorChange.click();

        wait.until((ExpectedConditions.elementToBeClickable(visibleAfter)));
        visibleAfter.click();

        return this;
    }


}
