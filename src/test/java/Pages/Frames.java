package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Frames extends BasePage{
    @FindBy(id = "frame1")
    private WebElement frame1;

    @FindBy(id = "sampleHeading")
    private WebElement sampleHeading;

    @FindBy(id = "frame2")
    private WebElement frame2;


    public Frames(){
        PageFactory.initElements(driver, this);
    }

    public Frames frames (){
        driver.get("https://demoqa.com/frames");
        driver.switchTo().frame(frame1);
        System.out.println(sampleHeading.getText());
        driver.switchTo().defaultContent();
        driver.switchTo().frame(frame2);
        System.out.println(sampleHeading.getText());
        return this;
    }

    public Frames nestedFrames(){
        driver.get("https://demoqa.com/nestedframes");
        driver.switchTo().frame(frame1);
        String b = driver.findElement(By.tagName("body")).getText();
        System.out.println(b);
        driver.switchTo().frame(driver.findElement(By.tagName("Iframe")));
        String b1 = driver.findElement(By.tagName("body")).getText();
        System.out.println(b1);
        driver.switchTo().parentFrame();
        String b2 = driver.findElement(By.tagName("body")).getText();
        System.out.println(b2);
        String b3 = driver.switchTo().defaultContent()
                .findElement(By.xpath("//div[contains(text(), 'Sample Nested Iframe page.')]"))
                .getText();
        System.out.println(b3);
        return this;
    }
}
