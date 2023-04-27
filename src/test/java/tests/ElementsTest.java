package tests;

import components.LeftPannel;
import enums.Categories;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.Buttons;
import pages.CheckBox;
import pages.DynamicProperties;
import pages.RadioButton;

public class ElementsTest extends BaseTest{
    private static final Logger LOGGER = Logger.getLogger(ElementsTest.class);

    @Test(testName = "CheckBoxes", priority = 3)
    public void testCheckBox() {
        CheckBox checkBox = new CheckBox(getDriver());
        LeftPannel.getInstance().selectCategory(Categories.ELEMENTS, 1);

        checkBox.clickToggleButton();

        checkBox.clickDesktopCheckbox();
        checkBox.clickDownloadsCheckbox();
        checkBox.clickDocumentsCheckbox();

    }

    @Test(testName = "RadioButton", priority = 4 /*invocationCount = 5*/)
    public void testRadioButton(){
        RadioButton radioButton = new RadioButton(getDriver());

        LeftPannel.getInstance().selectCategory(Categories.ELEMENTS, 2);

        radioButton.clickImpressiveRadioButton();
        radioButton.clickYesRadioButton();
        radioButton.clickNoRadioButton();
    }

    @Test(testName = "DriverWait", priority = 6)
    public void testWebdriverWait() {
        DynamicProperties dynamicProperties = new DynamicProperties(getDriver());

        LeftPannel.getInstance().selectCategory(Categories.ELEMENTS, 8);

        dynamicProperties.clickEnableAfter(6);
        dynamicProperties.clickColorChange();

        Assert.assertEquals(dynamicProperties.getColorChange().getCssValue("color"),
                "rgba(220, 53, 69, 1)");

        dynamicProperties.clickVisibleAfter(6);
    }

    @Test(testName = "RightDoubleClick", priority = 9)
    public void testRightDoubleClick(){
        Buttons buttons = new Buttons(getDriver());

        LeftPannel.getInstance().selectCategory(Categories.ELEMENTS, 4);

        buttons.clickClickMeButton();
        Assert.assertTrue(buttons.getDynamicClickMessage().isDisplayed());

        buttons.rightClickRCButton();
        Assert.assertTrue(buttons.getRightClickMessage().isDisplayed());

        buttons.doubleClickDCButton();
        Assert.assertTrue(buttons.getDoubleClickMassage().isDisplayed());


    }
}
