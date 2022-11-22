package Tests;

import Pages.BasePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.time.Duration;

public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeTest
    public void SetDriver() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        //driver.get("https://demoqa.com/login");
        String Title = driver.getTitle();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        BasePage.setDriver(driver);
        //String source = driver.getPageSource();
        System.out.println(Title);
        //System.out.println(source);
    }

    @AfterTest
    public void aftertest() {
        driver.quit();
    }
}
