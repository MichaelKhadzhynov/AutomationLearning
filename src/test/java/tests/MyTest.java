package tests;


import components.LeftPannel;
import enums.Categories;
import pages.*;
import org.testng.annotations.Test;

public class MyTest extends BaseTest {

    @Test(testName = "MainePage logIn", priority = 1)
    public void testLogIn() {
        MainPage mainPage = new MainPage(getDriver());

        mainPage.logInMainPage("testuser@test.ua", "12565432");
    }

//    @Test(testName = "Test №3 Navigation Commands", priority = 2)
//    public void navigationCommands(){
//        getDriver().get("https://demoqa.com/login");
//        NavigationCommands navigationCommands = new NavigationCommands();
//        navigationCommands.navigationCommands();
//
//    }
//
//    @Test(testName = "Test №4 CheckBoxes", priority = 3)
//    public void CheckBox(){
//
//        CheckBoxes checkBox = new CheckBoxes();
//        checkBox.checkBox();
//    }
//
//    @Test(testName = "Test №5 RadioButton", priority = 4 /*invocationCount = 5*/)
//    public void RadioButton(){
//        RadioButton radioBut = new RadioButton();
//        radioBut.radioButton();
//    }
//
//    @Test(testName = "Test №6 DropDown", priority = 5)
//    public void DropDwn(){
//        DropDown dropDown = new DropDown();
//        dropDown.dropDown();
//    }
//
//    @Test(testName = "Тест №6 WDWait", priority = 6)
//    public void WebdriverWait(){
//        DriverWait driverWait = new DriverWait(getDriver());
//        driverWait.DW();
//    }

    @Test(testName = "AlertTest", priority = 7)
    public void testAlerts()  {
        AlertsPopups alertsPopups = new AlertsPopups(getDriver());

        LeftPannel.getInstance().selectCategory(Categories.ALERT_FRAME_WINDOW, 1);

        alertsPopups.testSimpleAlert();
        alertsPopups.testTimerAlert();
        alertsPopups.testConfirmationAlert();
        alertsPopups.testPromptAlert();
    }

//    @Test(testName = "Тест №8 Frames", priority = 8)
//    public void Frames(){
//        Frames fr = new Frames();
//        fr.frames();
//        fr.nestedFrames();
//    }
//
//    @Test(testName = "Тест №9 RightDoubleClick", priority = 9)
//    public void RightDoubleClick(){
//        RightDoubleClick rdc = new RightDoubleClick();
//        rdc.rightDoubleClick();
//    }
//
//    @Test(testName = "Тест №10 DragAndDrop", priority = 9)
//    public void DragAndDrop(){
//        DragAndDrop dd = new DragAndDrop();
//        dd.dragAndDrop();
//    }
//
//
    @Test(testName = "BrowserWindows", priority = 20)
    public void testBrowserWindow() {
        BrowserWindows browserWindows = new BrowserWindows(getDriver());

        LeftPannel.getInstance().selectCategory(Categories.ALERT_FRAME_WINDOW, 0);

        browserWindows.openingTabs().switchingTabs().closingTabs();
    }
//
//
//    @Test(testName = "Тест №2 Browser", priority = 20)
//    public void browser() throws InterruptedException {
//        System.out.println("Tests "); System.out.println("conflict1111");
//    }

}
