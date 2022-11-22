package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.nio.file.Watchable;

import static Pages.BasePage.driver;

public class NavigationCommands extends BasePage{

    @FindBy (xpath = "//button[@id = \"newUser\"]")
    private WebElement newUser;

    public NavigationCommands(){
        PageFactory.initElements(driver, this);
    }

    public NavigationCommands navigationCommands(){

        newUser.click();
        driver.navigate().back();
        driver.navigate().forward();
        driver.navigate().back();
        driver.navigate().refresh();
        return this;
    }
}
