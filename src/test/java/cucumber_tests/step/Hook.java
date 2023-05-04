package cucumber_tests.step;

import gui.tests.BaseTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;



public class Hook extends BaseTest {

    @Before
    public void configureDriver(){
        super.beforeTest();
    }

    @After
    public void quitDriver(){
        super.afterTest();

    }
}
