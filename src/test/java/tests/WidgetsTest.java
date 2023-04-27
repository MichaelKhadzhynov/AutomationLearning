package tests;

import components.LeftPannel;
import enums.Categories;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import categories.SelectMenu;

public class WidgetsTest extends BaseTest{

    private static final Logger LOGGER = Logger.getLogger(WidgetsTest.class);
    @Test(testName = "DropDown", priority = 5)
    public void testDropDawn() {
        SelectMenu selectMenu = new SelectMenu(getDriver());

        LeftPannel.getInstance().selectCategory(Categories.WIDGETS, 8);

        selectMenu.selectColor().selectByIndex(1);
        selectMenu.selectColor().selectByIndex(3);
        selectMenu.selectColor().selectByValue("5");
        selectMenu.selectColor().selectByVisibleText("Purple");

        selectMenu.selectTitle(1);

        selectMenu.clickMultiSelect();
        selectMenu.selectMultiSelectColor("green");
        selectMenu.selectMultiSelectColor("black");
        selectMenu.selectMultiSelectColor("blue");
        selectMenu.selectMultiSelectColor("red");

        selectMenu.carSelectMenu().selectByIndex(0);
        selectMenu.carSelectMenu().selectByIndex(1);
        selectMenu.carSelectMenu().selectByVisibleText("Opel");
        selectMenu.carSelectMenu().selectByValue("audi");
    }
}
