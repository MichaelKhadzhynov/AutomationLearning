package pages;

import components.LeftPannel;
import enums.Categories;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MainPage extends BasePage {

    @FindBy(xpath = "//input[@id = \"userName\"]")
    private WebElement logIn;

    @FindBy(xpath = "//input[@id = \"password\"]")
    private WebElement password;

    @FindBy(xpath = "//button[@id = \"login\"]")
    private WebElement loginButton;

    @FindBy(xpath =".//div[@class=\"header-wrapper\"]")
    private List<WebElement> categoriesGroupList;

    public MainPage clickCategory(Categories categories){
        categoriesGroupList.get(categories.getCategory()).click();
        return this;
    }

    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public MainPage typeLogIn(String login){
        logIn.sendKeys(login);
        return this;
    }

    public MainPage typePassword(String pass){
        password.sendKeys(pass);
        return this;
    }

    public MainPage clickLogInButton(){
        loginButton.submit();
        return this;
    }

    public MainPage logInMainPage(String login, String password) {
        typeLogIn(login);
        typePassword(password);
        clickLogInButton();
        return this;
    }

}
