package gui.categories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class SelectMenu extends BasePage {

    @FindBy(xpath = "//select[@id = \"oldSelectMenu\"]")
    private WebElement selectColor;

    @FindBy(xpath = "//div[@class=\" css-1hwfws3\"]/div[contains(text(), \"Select Title\")]/..")
    private WebElement selectTitle;

    @FindBy(xpath = "//div[@class=' css-yt9ioa-option']")
    private List<WebElement> titlesList;

    @FindBy(xpath = "//div[contains(text(), \"Select...\")]")
    private WebElement multiSelect;

    @FindBy(xpath = "//div[@class=\" css-11unzgr\"]//div")
    private List<WebElement> multiSelectColorsList;

    @FindBy(xpath = "//select[@id= \"cars\"]")
    private WebElement cars;

    public SelectMenu(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public SelectMenu clickSelectColorButton() {
        selectColor.click();
        return this;
    }

    public Select selectColor() {
        clickSelectColorButton();
        return new Select(selectColor);
    }

    public SelectMenu clickSelectTitle() {
        selectTitle.click();
        return this;
    }

    public SelectMenu selectTitle(int titleNumber) {
        clickSelectTitle();
        titlesList.get(titleNumber).click();
        return this;
    }

    public SelectMenu clickMultiSelect() {
        multiSelect.click();
        return this;
    }

    public SelectMenu selectMultiSelectColor(String color) {
        multiSelectColorsList.forEach(e ->
        {
            if (e.getText().equalsIgnoreCase(color)){
                e.click();
            }
        });
        return this;
    }

    public Select carSelectMenu() {
        return new Select(cars);
    }

}
