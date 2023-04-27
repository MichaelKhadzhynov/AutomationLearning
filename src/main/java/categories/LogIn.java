package categories;

import enums.Categories;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class LogIn extends BasePage {

    @FindBy(xpath = "//input[@id = \"userName\"]")
    private WebElement logIn;

    @FindBy(xpath = "//input[@id = \"password\"]")
    private WebElement password;

    @FindBy(xpath = "//button[@id = \"login\"]")
    private WebElement loginButton;

    @FindBy (xpath = "//button[@id = \"newUser\"]")
    private WebElement newUserButton;

    @FindBy(xpath =".//div[@class=\"header-wrapper\"]")
    private List<WebElement> categoriesGroupList;

    public LogIn clickCategory(Categories categories){
        categoriesGroupList.get(categories.getCategory()).click();
        return this;
    }

    public LogIn(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public LogIn typeLogIn(String login){
        logIn.sendKeys(login);
        return this;
    }

    public LogIn typePassword(String pass){
        password.sendKeys(pass);
        return this;
    }

    public LogIn clickLogInButton(){
        loginButton.submit();
        return this;
    }

    public LogIn logInMainPage(String login, String password) {
        typeLogIn(login);
        typePassword(password);
        clickLogInButton();
        return this;
    }

    public LogIn clickNewUserButton(){
        newUserButton.click();
        return this;
    }

}
