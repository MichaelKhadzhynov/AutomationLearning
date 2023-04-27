package tests;

import components.LeftPannel;
import enums.Categories;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import categories.Droppable;

public class InteractionsTest extends BaseTest{

    private static final Logger LOGGER = Logger.getLogger(InteractionsTest.class);
    @Test(testName = "DragAndDrop", priority = 9)
    public void testDragAndDrop() {
        Droppable droppable = new Droppable(getDriver());

        LeftPannel.getInstance()
                .scrollPageByJS(500)
                .selectCategory(Categories.INTERACTION, 3);

        droppable.dragAndDropByXY();

        Assert.assertTrue(droppable.isDropped());
    }
}
