package cucumber_tests.step;

import gui.categories.BasePage;
import gui.tests.BaseTest;
import io.cucumber.java.en.Given;

public class BeforeStep extends BasePage {

    @Given("open url {string}")
    public void openUrl(String url){
        driver.get(url);
    }
}
