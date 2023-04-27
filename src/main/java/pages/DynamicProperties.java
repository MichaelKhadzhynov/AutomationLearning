package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class DynamicProperties extends BasePage {

    @FindBy(xpath = "//button[@id=\"enableAfter\"]")
    private WebElement enableAfter;

    @FindBy(xpath = "//button[@id=\"colorChange\"]")
    private WebElement colorChange;

    @FindBy(xpath = "//button[@id=\"visibleAfter\"]")
    private WebElement visibleAfter;

    public DynamicProperties(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public DynamicProperties clickEnableAfter(int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.elementToBeClickable(enableAfter));
        enableAfter.click();
        return this;
    }

    public DynamicProperties clickEnableAfterFluentWait(int timeout, int pollingEvery) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(pollingEvery));
        wait.until(ExpectedConditions.elementToBeClickable(enableAfter));
        enableAfter.click();
        return this;
    }

    public DynamicProperties clickColorChange() {
        colorChange.click();
        return this;
    }

    public DynamicProperties clickVisibleAfter(int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.elementToBeClickable(visibleAfter));
        visibleAfter.click();
        return this;
    }

    public WebElement getColorChange() {
        return colorChange;
    }
}
