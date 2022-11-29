package Pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AlertsPopups extends BasePage {

    @FindBy(id = "alertButton")
    private WebElement alertButton;

    @FindBy(id = "timerAlertButton")
    private WebElement timerAlertButton;

    @FindBy(id = "confirmButton")
    private WebElement confirmButton;

    @FindBy(xpath = "//button[@id = \"promtButton\"]")
    private WebElement promtButton;

    public AlertsPopups() {
        PageFactory.initElements(driver, this);
    }

    public AlertsPopups alertsPopups() throws InterruptedException {
        driver.get("https://demoqa.com/alerts");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 200)");

        alertButton.click();
        Alert alButton = driver.switchTo().alert();
        alButton.accept();
        try {
            timerAlertButton.click();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.alertIsPresent());
            Alert timerAlert = driver.switchTo().alert();
            timerAlert.accept();
        } catch (Exception e) {
            System.out.println("unexpected alert not present");
        }


        confirmButton.click();
        Alert confButton = driver.switchTo().alert();
        Thread.sleep(2000);
        confButton.accept();
        confirmButton.click();
        Thread.sleep(2000);
        confButton.dismiss();


        WebDriverWait promwait = new WebDriverWait(driver, Duration.ofSeconds(5));
        promwait.until(ExpectedConditions.elementToBeClickable(promtButton));
        promtButton.click();
        Alert prButton = driver.switchTo().alert();
        prButton.sendKeys("Nik");
        prButton.accept();
        promwait.until(ExpectedConditions.elementToBeClickable(promtButton));
        promtButton.click();
        Thread.sleep(2000);
        System.out.println(prButton.getText());
        prButton.dismiss();


        return this;
    }

}
