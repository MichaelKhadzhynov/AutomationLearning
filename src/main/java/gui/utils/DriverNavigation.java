package gui.utils;

import org.openqa.selenium.WebDriver;

public class DriverNavigation {

    private WebDriver driver;

    private static final DriverNavigation INSTANCE = new DriverNavigation();

    private DriverNavigation() {
    }

    public DriverNavigation navigationBack(){
        driver.navigate().back();
        return this;
    }

    public DriverNavigation navigationForward(){
        driver.navigate().forward();
        return this;
    }

    public DriverNavigation navigationRefresh(){
        driver.navigate().refresh();
        return this;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public static DriverNavigation getInstance(){
        return INSTANCE;
    }
}
