package Pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckBoxes extends BasePage {
    @FindBy(xpath = "//label[@for=\"hobbies-checkbox-1\"]")
    private WebElement sport;

    @FindBy(xpath = "//label[@for=\"hobbies-checkbox-2\"]")
    private WebElement reading;

    @FindBy(xpath = "//label[@for=\"hobbies-checkbox-3\"]")
    private WebElement music;

    public CheckBoxes() {
        PageFactory.initElements(driver, this);
    }

    public CheckBoxes checkBox() {
        driver.get("https://demoqa.com/automation-practice-form");
        /**
         * Прокрутка страницы с помощью JavaScript
         */
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 500)");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        wait.until(ExpectedConditions.elementToBeClickable(sport));
        if (sport.isEnabled()) sport.click();

        wait.until(ExpectedConditions.elementToBeClickable(reading));
        if (reading.isEnabled()) reading.click();

        wait.until(ExpectedConditions.elementToBeClickable(music));
        if (music.isEnabled()) music.click();

        wait.until(ExpectedConditions.elementToBeClickable(music));
        if (music.isEnabled()) music.click();

        wait.until(ExpectedConditions.elementToBeClickable(reading));
        if (reading.isEnabled()) reading.click();

        wait.until(ExpectedConditions.elementToBeClickable(sport));
        if (sport.isEnabled()) sport.click();
        return this;
    }
}
