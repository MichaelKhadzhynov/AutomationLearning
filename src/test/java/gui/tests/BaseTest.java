package gui.tests;

import gui.enums.BrowsersType;
import io.cucumber.java.StepDefinitionAnnotation;
import io.cucumber.java.StepDefinitionAnnotations;
import io.cucumber.java.en.Given;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeMethod;
import gui.categories.BasePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import gui.utils.DriverNavigation;

import java.time.Duration;

public abstract class BaseTest {

    private static String URL = "https://demoqa.com/login";
    private WebDriver driver;
    public BaseTest() {
    }

    @BeforeTest
    public void beforeTest() {
        setDriver(BrowsersType.CHROME);
        setDriverBasePage();
        DriverNavigation.getInstance().setDriver(driver);
        setWindowManage();
        setImplicitlyWait(20);
    }

    @BeforeMethod
    public void beforeMethod(){
        open(URL);
    }

    @AfterTest
    public void afterTest() {
        driverQuit();
    }


    public WebDriver getDriver() {
        return driver;
    }

    public void open(String url) {
        driver.get(url);
    }


    public WebDriver setDriver(BrowsersType driverType) {
        driver = null;
        switch (driverType) {

            case CHROME:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                return driver;

            case SAFARI:
                WebDriverManager.safaridriver().setup();
                driver = new SafariDriver();
                return driver;

            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                return driver;
        }
        return null;
    }

    private void driverQuit(){
        driver.quit();
    }

    private BasePage setDriverBasePage(){
        return new BasePage(driver);
    }

    private void setWindowManage(){
        driver.manage().window().maximize();
    }

    private void setImplicitlyWait(int seconds){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
    }

}
