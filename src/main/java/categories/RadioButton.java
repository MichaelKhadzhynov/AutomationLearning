package categories;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RadioButton extends BasePage {

    private static final org.apache.log4j.Logger LOGGER = Logger.getLogger(RadioButton.class);

    @FindBy(xpath = "//label[@for=\"yesRadio\"]")
    private WebElement yesRadioButton;

    @FindBy(xpath = "//label[@for=\"impressiveRadio\"]")
    private WebElement impressiveRadioButton;

    @FindBy(xpath = "//label[@for=\"noRadio\"]")
    private WebElement noRadioButton;

    public RadioButton(WebDriver driver) {
        super(driver);
    }

    public RadioButton clickYesRadioButton(){
        yesRadioButton.click();
        return this;
    }

    public RadioButton clickImpressiveRadioButton(){
        impressiveRadioButton.click();
        return this;
    }

    public RadioButton clickNoRadioButton(){

        if (noRadioButton.isEnabled())noRadioButton.click();
        else LOGGER.error("'NO' radio button is disabled");
        return this;
    }

}
