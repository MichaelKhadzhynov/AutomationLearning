package tests;

import components.LeftPannel;
import enums.Categories;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.Alerts;
import pages.BrowserWindows;
import pages.Frames;

public class AlertFrameWindowTest extends BaseTest {
    private static final Logger LOGGER = Logger.getLogger(AlertFrameWindowTest.class);
    @Test(testName = "AlertTest", priority = 7)
    public void testAlerts() {
        Alerts alerts = new Alerts(getDriver());

        LeftPannel.getInstance().selectCategory(Categories.ALERT_FRAME_WINDOW, 1);

        alerts.testSimpleAlert();
        alerts.testTimerAlert();
        alerts.testConfirmationAlert();
        alerts.testPromptAlert();
    }

    @Test(testName = "Frames", priority = 8)
    public void testFrames() {
        Frames frames = new Frames(getDriver());

        LeftPannel.getInstance().selectCategory(Categories.ALERT_FRAME_WINDOW, 2);

        frames.switchToFrame1();
        Assert.assertEquals(frames.getSampleHeading().getText(), "This is a sample page");
        LOGGER.info(frames.getSampleHeading().getText());

        frames.switchToDefaultContent();
        frames.switchToFrame2();
        Assert.assertEquals(frames.getSampleHeading().getText(), "This is a sample page");

        LOGGER.error(frames.getSampleHeading().getText());

    }

    @Test(testName = "BrowserWindows", priority = 20)
    public void testBrowserWindow() {
        BrowserWindows browserWindows = new BrowserWindows(getDriver());

        LeftPannel.getInstance().selectCategory(Categories.ALERT_FRAME_WINDOW, 0);

        browserWindows.openingTabs().switchingTabs().closingTabs();
    }
}
