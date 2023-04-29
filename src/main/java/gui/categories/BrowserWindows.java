package gui.categories;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class BrowserWindows extends BasePage {

    @FindBy(xpath = "//div[@id=\"tabButtonWrapper\"]//button")
    private WebElement newTabButton;
    @FindBy(xpath = "//button[@id = \"windowButton\"]")
    private WebElement windowButton;
    @FindBy(xpath = "//button[@id= \"messageWindowButton\"]")
    private WebElement windowMassage;

    public BrowserWindows(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void openPageInNewTab(WebElement element) {
        element.sendKeys(Keys.COMMAND, Keys.RETURN);
    }

    public BrowserWindows switchToTab(int tabNumber) {
        List<String> list = new ArrayList<>(driver.getWindowHandles());
        if (list.get(tabNumber) != null)
            driver.switchTo().window(list.get(tabNumber));
        else throw new NullPointerException("Tab not found");

        return this;
    }

    public BrowserWindows closeTab(int tabNumber) {
        List<String> list = new ArrayList<>(driver.getWindowHandles());

        if (list.get(tabNumber) != null)
            driver.switchTo().window(list.get(tabNumber)).close();
        else throw new NullPointerException("Tab not found");

        return this;
    }

    public BrowserWindows openingTabs() {
        openPageInNewTab(newTabButton);
        openPageInNewTab(windowButton);
        openPageInNewTab(windowMassage);
        return this;
    }

    public BrowserWindows switchingTabs() {
        switchToTab(1);
        switchToTab(2);
        switchToTab(3);
        return this;
    }

    public BrowserWindows closingTabs() {
        closeTab(3);
        closeTab(2);
        closeTab(1);
        return this;
    }

}
