package gui.categories;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class NewUserForm extends BasePage {

    public NewUserForm(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//input[@id='firstname']")
    private WebElement firstNameField;

    @FindBy(xpath = "//input[@id='lastname']")
    private WebElement lastNameField;

    @FindBy(xpath = "//input[@id='userName']")
    private WebElement userNameField;

    @FindBy(xpath = "//input[@id='password']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@id='register']")
    private WebElement registerButton;

    @FindBy(xpath = "//p[@id='name']")
    private WebElement errorReCaptchaMassage;

    public NewUserForm typeFirstName(String firstName) {
        firstNameField.sendKeys(firstName);
        return this;
    }

    public NewUserForm typeLastName(String lastName) {
        lastNameField.sendKeys(lastName);
        return this;
    }

    public NewUserForm typeUserName(String userName) {
        userNameField.sendKeys(userName);
        return this;
    }

    public NewUserForm typePassword(String password) {
        passwordField.sendKeys(password);
        return this;
    }

    public NewUserForm clickRegisterButton() {
        registerButton.click();
        return this;
    }

    public NewUserForm getRegistration(String fName, String lName, String userName, String password) {
        typeFirstName(fName);
        typeLastName(lName);
        typeUserName(userName);
        typePassword(password);
        return this;
    }

    public NewUserForm scrollByJs(int pixels) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("window.scrollBy(0, " + pixels + ")");
        return this;
    }

    public WebElement getFirstNameField() {
        return firstNameField;
    }

    public WebElement getLastNameField() {
        return lastNameField;
    }

    public WebElement getUserNameField() {
        return userNameField;
    }

    public WebElement getPasswordField() {
        return passwordField;
    }

    public WebElement getRegisterButton() {
        return registerButton;
    }

    public WebElement getErrorReCaptchaMassage() {
        return errorReCaptchaMassage;
    }
}
