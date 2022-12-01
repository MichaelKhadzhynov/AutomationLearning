package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

public class MainPage extends BasePage {

    private WebElement login = driver.findElement(By.xpath("//input[@id = \"userName\"]"));
    private WebElement password = driver.findElement(By.xpath("//input[@id = \"password\"]"));
    private WebElement loginButton = driver.findElement(By.xpath("//button[@id = \"login\"]"));

    public static MainPage LoginMainPage() {

        MainPage mainPage = new MainPage();
        mainPage.login.sendKeys("testuser");
        mainPage.password.sendKeys("Password@123");
        mainPage.loginButton.submit();

//        Actions actions = new Actions(driver);
//        actions.keyDown(mainPage.login, Keys.SHIFT).sendKeys("testuser").keyUp(Keys.SHIFT);
//        Action action = actions.build();
//        action.perform();

        return mainPage;

    }
}
