package cucumber_tests;

import gui.categories.BasePage;
import gui.categories.LogIn;
import gui.categories.NewUserForm;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class NewUserFormDef extends BasePage {

    private final NewUserForm newUserForm = new NewUserForm(driver);

    @When("type first name - {string}, last name - {string}, user name - {string}, password - {string}")
    public void getRegistration(String fName, String lName, String userName, String password){
        newUserForm.getRegistration(fName,lName,userName,password);
    }

    @And("click register button")
    public void clickRegisterButton() {
        newUserForm.scrollByJs(200).clickRegisterButton();
    }


    @Then("check error reCaptcha massage {string}")
    public void checkErrorReCaptchaMassage(String massage) {
        Assert.assertEquals(newUserForm.getErrorReCaptchaMassage().getText(), massage, "Massage wasn't displayed");
    }
}
