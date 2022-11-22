package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class DropDown extends BasePage {

    @FindBy(xpath = "//select[@id = \"oldSelectMenu\"]")
    private WebElement selectColor;

    @FindBy(xpath = "//div[@class=\" css-1hwfws3\"]/div[contains(text(), \"Select Title\")]/..")
    private WebElement SelectTitle;

    @FindBy(xpath = "//div[@id=\"react-select-3-option-0-2\"]")
    private WebElement mrs;

    @FindBy(xpath = "//div[contains(text(), \"Select...\")]")
    private WebElement selectColorNew;

    @FindBy(xpath = "//select[@id= \"cars\"]")
    private WebElement cars;

    public DropDown() {
        PageFactory.initElements(driver, this);
    }

    public DropDown dropDown() {
        driver.get("https://demoqa.com/select-menu");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 250)");

        selectColor.click();
        Select select = new Select(selectColor);
        List<WebElement> lst = select.getOptions();
        select.selectByIndex(1);
        select.selectByIndex(3);
        select.selectByValue("5");
        select.selectByVisibleText("Purple");
        SelectTitle.click();
        mrs.click();
        selectColorNew.click();

        List<WebElement> elements = driver.findElements(By.xpath("//div[@class=\" css-11unzgr\"]/div"));
        elements.get(0).click();
        elements.get(1).click();
        elements.get(2).click();
        elements.get(3).click();
        driver.findElement(By.xpath("//div[contains(text(), \"Green\")]/../div[last()]")).click();
        Select car = new Select(cars);
        if (car.isMultiple()) {
            car.selectByIndex(0);
            car.selectByIndex(1);
            car.selectByVisibleText("Opel");
            car.selectByValue("audi");
        }

        car.deselectByIndex(0);
        car.deselectByValue("opel");


        return this;
    }
}
