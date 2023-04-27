package tests;


import org.apache.log4j.Logger;
import pages.*;
import org.testng.annotations.Test;
import utils.DriverNavigation;

public class BookStory extends BaseTest {

    private static final Logger LOGGER = Logger.getLogger(BookStory.class);

    @Test(testName = "MainePage logIn", priority = 1)
    public void testLogIn() {
        LogIn logIn = new LogIn(getDriver());

        logIn.logInMainPage("testuser@test.ua", "12565432");
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
