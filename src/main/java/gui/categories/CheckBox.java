package gui.categories;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckBox extends BasePage {

    @FindBy(xpath = "//button[@aria-label='Toggle']")
    private WebElement toggleButton;

    @FindBy(xpath = "//label[@for=\"tree-node-desktop\"]//span[@class='rct-checkbox']")
    private WebElement desktopCheckbox;

    @FindBy(xpath = "//label[@for=\"tree-node-documents\"]//span[@class='rct-checkbox']")
    private WebElement documentsCheckbox;

    @FindBy(xpath = "//label[@for=\"tree-node-downloads\"]//span[@class='rct-checkbox']")
    private WebElement downloadsCheckbox;


    public CheckBox(WebDriver driver) {
        super(driver);
    }

    public CheckBox clickToggleButton(){
        toggleButton.click();
        return this;
    }

    public CheckBox scrollPageByJS(String pixels) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, " + pixels + ")");
        return this;
    }

    public CheckBox clickDesktopCheckbox() {
        desktopCheckbox.click();
        return this;
    }

    public CheckBox clickDocumentsCheckbox() {
        documentsCheckbox.click();
        return this;
    }

    public CheckBox clickDownloadsCheckbox() {
        downloadsCheckbox.click();
        return this;
    }

}
