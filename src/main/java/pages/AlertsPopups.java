package pages;

import enums.AcceptVariants;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

public class AlertsPopups extends BasePage {

    @FindBy(xpath = "//button[@id='alertButton']")
    private WebElement simpleAlertButton;

    @FindBy(id = "timerAlertButton")
    private WebElement timerAlertButton;

    @FindBy(id = "confirmButton")
    private WebElement confirmButton;

    @FindBy(xpath = "//button[@id = \"promtButton\"]")
    private WebElement promtButton;

    public AlertsPopups(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    public AlertsPopups scrollTo() {
        driver.get("https://demoqa.com/alerts");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 200)");
        return this;
    }

    public AlertsPopups clickSimpleAlertButton() {
        simpleAlertButton.click();
        return this;
    }

    public AlertsPopups acceptDismissAlert(AcceptVariants acceptVariants) {
        Alert alert = driver.switchTo().alert();
        if(acceptVariants == AcceptVariants.ACCEPT)
        alert.accept();
        else alert.dismiss();
        return this;
    }

    public AlertsPopups clickTimerAlertButton() {
        timerAlertButton.click();
        return this;
    }

    public AlertsPopups waitAlertIsPresent(int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.alertIsPresent());
        return this;
    }

    public AlertsPopups clickConfirmationButton(){
        confirmButton.click();
        return this;
    }

    public AlertsPopups clickPromptButton(){
        promtButton.click();
        return this;
    }

    public AlertsPopups waitPromtButtonToBeClickable(int seconds){
        WebDriverWait promwait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        promwait.until(ExpectedConditions.elementToBeClickable(promtButton));
        return this;
    }

    public AlertsPopups typeTextInAlert(String text){
        Alert prButton = driver.switchTo().alert();
        prButton.sendKeys(text);
        return this;
    }

    public AlertsPopups testSimpleAlert(){
        scrollTo();
        clickSimpleAlertButton();
        acceptDismissAlert(AcceptVariants.ACCEPT);
        return this;
    }

    public AlertsPopups testTimerAlert(){
        clickTimerAlertButton();
        waitAlertIsPresent(10);
        acceptDismissAlert(AcceptVariants.ACCEPT);
        return this;
    }

    public AlertsPopups testConfirmationAlert(){
        clickConfirmationButton();
        acceptDismissAlert(AcceptVariants.ACCEPT);
        clickSimpleAlertButton();
        acceptDismissAlert(AcceptVariants.DISMISS);
        return this;
    }

    public AlertsPopups testPromptAlert(){
        waitPromtButtonToBeClickable(5);
        clickPromptButton();
        typeTextInAlert("Text");
        acceptDismissAlert(AcceptVariants.ACCEPT);
        clickPromptButton();
        acceptDismissAlert(AcceptVariants.DISMISS);
        return this;
    }
}
