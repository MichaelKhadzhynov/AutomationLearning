package categories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Droppable extends BasePage {

    @FindBy(id = "draggable")
    private WebElement dragMe;

    @FindBy(id = "droppable")
    private WebElement dropHere;

    private final Actions actions = new Actions(driver);

    public Droppable(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public Droppable dragAndDropByTarget() {
        actions.dragAndDrop(dragMe, dropHere).perform();
        return this;
    }

    public Droppable dragAndDropByXY() {
        int xOffset1 = dragMe.getLocation().getX();
        int yOffset1 = dragMe.getLocation().getY();

        int xOffset = dropHere.getLocation().getX();
        int yOffset = dropHere.getLocation().getY();

        xOffset = (xOffset - xOffset1) + 10;
        yOffset = (yOffset - yOffset1) + 10;

        actions.dragAndDropBy(dragMe, xOffset, yOffset).perform();

        return this;
    }

    public boolean isDropped(){
        if (dropHere.getText().equals("Dropped!")) {
            System.out.println("PASS: Source is dropped at location as expected");
            return true;
        } else {
            System.out.println("FAIL: Source couldn't be dropped at location as expected");
            return false;
        }
    }

}
