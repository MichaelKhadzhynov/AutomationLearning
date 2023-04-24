package tests;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeMethod;
import pages.BasePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

enum Browsers {
    SAFARI, CHROME, FIREFOX;
}

public abstract class BaseTest {

    private static String URL = "https://demoqa.com/login";
    private WebDriver driver;
    public BaseTest() {
    }

    @BeforeTest
    public void beforeTest() {

        driver = setDriver(Browsers.CHROME);

        BasePage basePage = new BasePage();
        basePage.setDriver(driver);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

    }

    @BeforeMethod
    public void beforeMethod(){
        open(URL);
    }

    @AfterTest
    public void quitDriver() {
        driver.quit();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void open(String url) {
        driver.get(url);
    }

    private WebDriver setDriver(Browsers driverType) {
        switch (driverType) {

            case CHROME:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();

            case SAFARI:
                WebDriverManager.safaridriver().setup();
                return new SafariDriver();

            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
        }
        return null;
    }
}
