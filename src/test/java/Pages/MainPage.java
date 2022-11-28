package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class MainPage extends BasePage {

    private WebElement login = driver.findElement(By.xpath("//input[@id = \"userName\"]"));
    private WebElement password = driver.findElement(By.xpath("//input[@id = \"password\"]"));
    private WebElement loginButton = driver.findElement(By.xpath("//button[@id = \"login\"]"));

    public static MainPage LoginMainPage() {

        MainPage mainPage = new MainPage();
        mainPage.login.sendKeys("testuser");
        mainPage.password.sendKeys("Password@123");
        mainPage.loginButton.submit();
        return mainPage;

    }
}
