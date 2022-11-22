package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PracticeFormPage extends BasePage {

    @FindBy(xpath = "//input[@id = \"firstName\"]")
    private WebElement name;

    @FindBy(xpath = "//input[@id = \"lastName\"]")
    private WebElement lastName;

    @FindBy(xpath = "//input[@id = \"userEmail\"]")
    private WebElement userEmail;

    @FindBy(xpath = "//div/input[@id = \"gender-radio-1\"]")
    private WebElement Gender;

    @FindBy(xpath = "//input[@id = \"userNumber\"]")
    private WebElement userNumber;

//    @FindBy(xpath = "//input[@id=\"dateOfBirthInput\"]")
//    private WebElement dateOfBirthInput;
//
//    @FindBy(xpath = "//select[@class = \"react-datepicker__year-select\"]")
//    private WebElement  dateOfBirthInputyear;

    @FindBy(xpath = "//div[@class = \"subjects-auto-complete__control css-yk16xz-control\"]")
    private WebElement subject;

    @FindBy(xpath = "//label[text()='Sports']")
    private WebElement hoobiesBook;

    @FindBy(xpath = "//textarea[@id = \"currentAddress\"]")
    private WebElement address;

    @FindBy(xpath = "//div[@id=\"state\"]")
    private WebElement state;

    @FindBy(xpath = "//div[@id= \"city\"]")
    private WebElement city;

    @FindBy(xpath = "//button[@id = \"submit\"]")
    private WebElement submit;

    public PracticeFormPage() {
        driver.get("https://demoqa.com/automation-practice-form");
        PageFactory.initElements(driver, this);
    }

    public PracticeFormPage fillFields() {
        name.sendKeys("Michael");
        lastName.sendKeys("Khad");
        userEmail.sendKeys("mk@test.com");
        Gender.click();
        userNumber.sendKeys("+380000000");

        subject.sendKeys("English");
        hoobiesBook.click();
        address.sendKeys("Test av. 1/1");
        state.click();
        city.click();
        submit.click();
        return this;
    }
}
