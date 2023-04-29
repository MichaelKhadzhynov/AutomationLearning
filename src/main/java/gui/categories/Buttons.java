package gui.categories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class Buttons extends BasePage{

    @FindBy(id = "rightClickBtn")
    private WebElement rightClickButton;

    @FindBy(xpath = "//button[text()='Double Click Me']")
    private WebElement doubleClickButton;

    @FindBy(xpath = "//button[text()='Click Me']")
    private WebElement dynamicClickMeButton;

    @FindBy(xpath = "//p[@id='doubleClickMessage']")
    private WebElement doubleClickMassage;

    @FindBy(xpath = "//p[@id='rightClickMessage']")
    private WebElement rightClickMessage;

    @FindBy(xpath = "//p[@id=\"dynamicClickMessage\"]")
    private WebElement dynamicClickMessage;


    public Buttons(WebDriver driver) {
        super(driver);
    }

    public Buttons rightClickRCButton(){
        Actions actions = new Actions(driver);
        actions.contextClick(rightClickButton).perform();
        return this;
    }

    public Buttons doubleClickDCButton(){
        Actions actions = new Actions(driver);
        actions.doubleClick(doubleClickButton).perform();
        return this;
    }

    public Buttons clickClickMeButton(){
        dynamicClickMeButton.click();
        return this;
    }

    public WebElement getDoubleClickMassage() {
        return doubleClickMassage;
    }

    public WebElement getRightClickMessage() {
        return rightClickMessage;
    }

    public WebElement getDynamicClickMessage() {
        return dynamicClickMessage;
    }
}
