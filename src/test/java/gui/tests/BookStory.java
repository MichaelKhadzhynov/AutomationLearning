package gui.tests;


import gui.categories.LogIn;
import org.apache.log4j.Logger;
import gui.categories.*;
import org.testng.annotations.Test;
import gui.utils.DriverNavigation;
import utils.ExcelDataProviders;

public class BookStory extends BaseTest {

    private static final Logger LOGGER = Logger.getLogger(BookStory.class);

    @Test(testName = "MainePage logIn",
            dataProvider = "usersData",
            dataProviderClass = ExcelDataProviders.class)
    public void testLogIn(String id, String name, String password) {
        LogIn logIn = new LogIn(getDriver());

        logIn.logInMainPage(name, password);

        LOGGER.info("id: " + id + "\n name: " + name + "\n password: " + password);
    }

    @Test(testName = "Navigation Commands", priority = 2)
    public void testNavigationCommands() {
        LogIn logIn = new LogIn(getDriver());

        logIn.clickNewUserButton();

        DriverNavigation.getInstance().navigationBack();
        DriverNavigation.getInstance().navigationForward();
        DriverNavigation.getInstance().navigationRefresh();

    }

}
