package youtube_automation.Wrappers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Wrappers {
    public static void click(WebElement elementToClick, WebDriver driver) {
        if (elementToClick.isDisplayed()) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("var rect = arguments[0].getBoundingClientRect();" +
                    "window.scrollTo({ top: rect.top + window.pageYOffset - (window.innerHeight / 2), behavior: 'smooth' });",
                    elementToClick);
            elementToClick.click();
        }
    }

    public static void sendKeys(WebDriver driver, WebElement inputBox, String keysToSend) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("var rect = arguments[0].getBoundingClientRect();" +
                "window.scrollTo({ top: rect.top + window.pageYOffset - (window.innerHeight / 2), behavior: 'smooth' });",
                inputBox);
        inputBox.clear();
        inputBox.sendKeys(keysToSend);
    }

    public static void navigate(WebDriver driver, String url) {
        if (!driver.getCurrentUrl().equals(url)) {
            driver.get(url);
        }
    }
    
    public static void moveToDesiredSection(WebDriver driver, String desiredSection){
        WebElement moviesButton = driver
                                .findElement(By.xpath("//yt-formatted-string[text()='"+desiredSection+"']"));
        Wrappers.click(moviesButton, driver);
    }

    public static void nextButtonClick(WebDriver driver, WebElement nextButton){
        Boolean flag = true;
                while (flag) {
                        if (!nextButton.isDisplayed()) {
                                flag = false; // Exit the loop when no more "next" button is visible
                        }
                        else{
                              Wrappers.click(nextButton, driver);
                        }   
                }
    }

    public static int sumOfLikes(List<WebElement> newsTilesLikes){
        int limit = 3;
        int sumOfLikes = 0;
        for(WebElement element : newsTilesLikes){
            if(limit>0){
                    String likesCount = element.getText().replaceAll("[^\\d]", "");
                    try{
                            sumOfLikes += Integer.parseInt(likesCount);
                            System.out.println(likesCount);
                    }
                    catch(Exception e){
                            sumOfLikes += 0;
                            System.out.println(0);
                    }
                    limit--;
            }
        }
        return sumOfLikes;

    }

    public static WebElement findElementWithRetry(WebDriver driver, By by, int retryCount) {
        return driver.findElement(by);
    }

    public static String capture(WebDriver driver) throws IOException {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File(
                System.getProperty("user.dir") + File.separator + "reports" + System.currentTimeMillis() + ".png");
        String errflPath = dest.getAbsolutePath();
        FileUtils.copyFile(srcFile, dest);
        return errflPath;
    }
}