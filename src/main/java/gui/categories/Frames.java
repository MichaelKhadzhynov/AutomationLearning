package gui.categories;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Frames extends BasePage {
    @FindBy(xpath = "//iframe[@id='frame1']")
    private WebElement frame1;

    @FindBy(id = "sampleHeading")
    private WebElement sampleHeading;

    @FindBy(xpath = "//iframe[@id='frame1']")
    private WebElement frame2;

    public Frames(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public Frames switchToFrame(WebElement frame) {
        driver.switchTo().frame(frame);
        return this;
    }

    public Frames switchToDefaultContent(){
        driver.switchTo().defaultContent();
        return this;
    }

    public Frames switchToFrame1(){
        switchToFrame(frame1);
        return this;
    }

    public Frames switchToFrame2(){
        switchToFrame(frame2);
        return this;
    }

    public WebElement getSampleHeading() {
        return sampleHeading;
    }

}
