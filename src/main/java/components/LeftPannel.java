package components;

import enums.Categories;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.BasePage;

import java.util.List;

public class LeftPannel extends BasePage {

    private static LeftPannel INSTANCE = new LeftPannel(driver);

    @FindBy(xpath = ".//div[@class=\"header-wrapper\"]")
    private List<WebElement> categoriesGroupList;

    @FindBy(xpath = ".//div[@class='element-list collapse show']//li")
    private List<WebElement> elementsList;

    private LeftPannel(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    private void clickCategory(Categories categories) {
        categoriesGroupList.get(categories.getCategory()).click();
    }

    public void selectCategory(Categories categories) {
        clickCategory(categories);
    }

    public void selectCategory(Categories categories, int subCategory) {
        clickCategory(categories);
        elementsList.get(subCategory).click();
    }

    public List<WebElement> getCategoriesGroupList() {
        return categoriesGroupList;
    }

    public static LeftPannel getInstance() {
        return INSTANCE;
    }

}
