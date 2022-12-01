package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RightDoubleClick extends BasePage{

    @FindBy(id = "rightClickBtn")
    private WebElement rightClickBtn;

    @FindBy(id = "doubleClickBtn")
    private WebElement doubleClickBtn;

    @FindBy(id = "9L3KU")
    private WebElement clickMe;

    public RightDoubleClick(){
        PageFactory.initElements(driver, this);
    }

    public RightDoubleClick rightDoubleClick(){
        driver.get("https://demoqa.com/buttons");

        Actions actions = new Actions(driver);
        actions.contextClick(rightClickBtn).perform();
        actions.doubleClick(doubleClickBtn).perform();


        System.out.println(driver.findElement(By.id("rightClickMessage")).getText());
        System.out.println(driver.findElement(By.id("doubleClickMessage")).getText());
        return this;
    }

}
