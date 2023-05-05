package cucumber_tests;

import gui.categories.BasePage;
import gui.categories.LogIn;
import gui.utils.DriverNavigation;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.runner.RunWith;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.Reader;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class LogInFormDef extends BasePage {

    private final LogIn logIn = new LogIn(driver);

    @When("log in {string}, {string}")
    public void logIn(String nickName, String password) {
        logIn.logIn(nickName, password);
    }

    @When("click logIn button")
    public void clickLogInButton() {
        logIn.clickLogInButton();
    }

    @Then("check error massage is visible")
    public void checkSuccessfulLogIn() {
        Assert.assertTrue(logIn.getErrorLogInMassage().isDisplayed(), "Error massage wasn't displayed");
    }

    @And("massage have text - {string}")
    public void massageHaveText(String text) {
        Assert.assertEquals(logIn.getErrorLogInMassage().getText(), text, "Text in error massage is not equal");
    }

    @Then("check logIn field validation")
    public void checkLogInFieldValidation() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.attributeToBe(logIn.getLogIn(),"border-color", "rgb(220, 53, 69)" ));

        String fieldColor = logIn.getLogIn().getCssValue("border-color");

        Assert.assertTrue(logIn.getLogIn().getAttribute("class").contains("is-invalid") ||
                fieldColor.equals("rgb(220, 53, 69)"), "LogIn field wasn't validated");
    }

    @Then("check password field validation")
    public void checkPasswordFieldValidation() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.attributeToBe(logIn.getPassword(),"border-color", "rgb(220, 53, 69)" ));

        String fieldColor = logIn.getPassword().getCssValue("border-color");

        Assert.assertTrue(logIn.getPassword().getAttribute("class").contains("is-invalid") ||
                        fieldColor.equals("rgb(220, 53, 69)"), "Password field wasn't validated");
    }

    @And("refresh page")
    public void refreshPage() {
        DriverNavigation.getInstance().navigationRefresh();
    }

    @Given("log in {int}:")
    public void logIn(Integer id, DataTable dataTable) {
        List<Map<String,String>> mapList = dataTable.asMaps();
        logIn.logIn(mapList.get(id).get("username"), mapList.get(id).get("password"));
        System.out.println(mapList);
    }

    @Given("registration form")
    public void getRegistrationForm(){
        logIn.clickNewUserButton();
    }
}
