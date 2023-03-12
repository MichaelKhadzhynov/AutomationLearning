package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.openqa.selenium.Keys.ENTER;

public class BrowserWindows extends BasePage {


    private WebElement NewTab = driver.findElement(By.xpath("//button[@id = \"tabButton\"]"));
    private WebElement windowButton = driver.findElement(By.xpath("//button[@id = \"windowButton\"]"));
    private WebElement windowMassage = driver.findElement(By.xpath("//button[@id= \"messageWindowButton\"]"));

    public BrowserWindows newTab() throws InterruptedException {
        NewTab.sendKeys(Keys.COMMAND, ENTER);
        windowButton.sendKeys(Keys.COMMAND, ENTER);
        windowMassage.sendKeys(Keys.COMMAND, ENTER);

        List<String> list = new ArrayList<>(driver.getWindowHandles());

        driver.switchTo().window(list.get(1));

        driver.switchTo().window(list.get(2));

        driver.switchTo().window(list.get(3));

        driver.switchTo().window(list.get(3)).close();

        driver.switchTo().window(list.get(2)).close();

        driver.switchTo().window(list.get(1));




        return null;
    }


}
