package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RadioButton extends BasePage {

    @FindBy(xpath = "//label[@for=\"yesRadio\"]")
    private WebElement yesRadio;

    @FindBy(xpath = "//label[@for=\"impressiveRadio\"]")
    private WebElement impressiveRadio;

    @FindBy(xpath = "//label[@for=\"noRadio\"]")
    private WebElement noRadio;

    public RadioButton(){
        PageFactory.initElements(driver,this);
    }

    public RadioButton radioButton(){
        driver.get("https://demoqa.com/radio-button");
        if (yesRadio.isEnabled())yesRadio.click();
        if (impressiveRadio.isEnabled())impressiveRadio.click();
        if (noRadio.isEnabled())noRadio.click();
        return this;
    }
}
