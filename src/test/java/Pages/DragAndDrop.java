package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DragAndDrop extends BasePage {

    @FindBy(id = "draggable")
    private WebElement dragMe;

    @FindBy(id = "droppable")
    private WebElement dropHere;

    public DragAndDrop() {
        PageFactory.initElements(driver, this);
    }

    public DragAndDrop dragAndDrop() {
        driver.get("https://demoqa.com/droppable");

        Actions actions = new Actions(driver);
//        actions.dragAndDrop(dragMe, dropHere).perform();

        int xOffset1 = dragMe.getLocation().getX();
        int yOffset1 = dragMe.getLocation().getY();
        System.out.println("y= " + yOffset1 + " x= " + xOffset1);

        int xOffset = dropHere.getLocation().getX();
        int yOffset = dropHere.getLocation().getY();

        xOffset = (xOffset - xOffset1) + 10;
        yOffset = (yOffset - yOffset1) + 10;

        actions.dragAndDropBy(dragMe, xOffset, yOffset).perform();

        String textTo = dropHere.getText();

        if (textTo.equals("Dropped!")) {
            System.out.println("PASS: Source is dropped at location as expected");
        } else {
            System.out.println("FAIL: Source couldn't be dropped at location as expected");
        }


        return this;
    }
}
