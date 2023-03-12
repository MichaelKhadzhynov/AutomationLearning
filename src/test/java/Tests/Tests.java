package Tests;


import Pages.*;
import org.testng.annotations.Test;

public class Tests extends BaseTest {


    @Test(testName = "Тест №1 MainePage logine", priority = 1)
    public void fieldsFill() {
        driver.get("https://demoqa.com/login");
        MainPage mainPage = new MainPage();
        mainPage.LoginMainPage();

    }

    @Test(testName = "Test №3 Navigation Commands", priority = 2)
    public void navigationCommands(){
        driver.get("https://demoqa.com/login");
        NavigationCommands navigationCommands = new NavigationCommands();
        navigationCommands.navigationCommands();

    }

    @Test(testName = "Test №4 CheckBoxes", priority = 3)
    public void CheckBox(){

        CheckBoxes checkBox = new CheckBoxes();
        checkBox.checkBox();
    }

    @Test(testName = "Test №5 RadioButton", priority = 4 /*invocationCount = 5*/)
    public void RadioButton(){
        RadioButton radioBut = new RadioButton();
        radioBut.radioButton();
    }

    @Test(testName = "Test №6 DropDown", priority = 5)
    public void DropDwn(){
        DropDown dropDown = new DropDown();
        dropDown.dropDown();
    }

    @Test(testName = "Тест №6 WDWait", priority = 6)
    public void WebdriverWait(){
        DriverWait driverWait = new DriverWait();
        driverWait.DW();
    }

    @Test(testName = "Тест №7 AlertTest", priority = 7)
    public void AlertTest() throws InterruptedException {
        AlertsPopups ap = new AlertsPopups();
        ap.alertsPopups();
    }

    @Test(testName = "Тест №8 Frames", priority = 8)
    public void Frames(){
        Frames fr = new Frames();
        fr.frames();
        fr.nestedFrames();
    }

    @Test(testName = "Тест №9 RightDoubleClick", priority = 9)
    public void RightDoubleClick(){
        RightDoubleClick rdc = new RightDoubleClick();
        rdc.rightDoubleClick();
    }

    @Test(testName = "Тест №10 DragAndDrop", priority = 9)
    public void DragAndDrop(){
        DragAndDrop dd = new DragAndDrop();
        dd.dragAndDrop();
    }


    @Test(testName = "Тест №2 BrowserWindows", priority = 20)
    public void browserwindow() throws InterruptedException {
        driver.navigate().to("https://demoqa.com/browser-windows/");
        BrowserWindows browserWindows = new BrowserWindows();
        browserWindows.newTab();
    }


    @Test(testName = "Тест №2 Browser", priority = 20)
    public void browser() throws InterruptedException {
        System.out.println("Tests conflict");
    }

}
