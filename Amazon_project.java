package src.main.AmazonAutomation;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Amazon_project {

    //Open Browser
    public static void open_browser(){
        //providing location
        System.setProperty("WebDriver.chrome.driver","E:\\Selenium webDriver\\chromedriver-win64\\chromedriver.exe");
        //creating instance
        WebDriver driver = new ChromeDriver();

        //maximizing the window
        driver.manage().window().maximize();
        //go to webpage
        driver.get("https://www.amazon.in/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //calling login
        login(driver);
    }

    //Method for login
    public static void login(WebDriver driver){
        //click on login button
        driver.findElement(By.id("nav-link-accountList-nav-line-1")).click();

        String email = "er.ps9634@gmail.com";
        String password = "8960064374";
        //click on userName
        driver.findElement(By.id("ap_email_login")).sendKeys(email);

        //click on continue button
        driver.findElement(By.className("a-button-input")).click();

        //enter password
        driver.findElement(By.id("ap_password")).sendKeys(password);

        //click on sign in
        driver.findElement(By.className("a-button-input")).click();
        searchItem(driver);
    }

    //Method for search product
    public static void searchItem(WebDriver driver) {
        try {
            WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
            searchBox.sendKeys("android phone 8GB RAM 128GB storage 6000mAh battery");
            driver.findElement(By.id("nav-search-submit-button")).click();
            Thread.sleep(3000);
            // Get list of mobile results
            List<WebElement> products = driver.findElements(By.cssSelector("div.s-main-slot div[data-component-type='s-search-result']"));
            for (int i = 0; i < Math.min(5, products.size()); i++) {
                WebElement product = products.get(i);

                String title = product.findElement(By.cssSelector("h2 span")).getText();
                String price = "Price not available";
                String rating = "Rating not available";

                // Try to extract price
                try {
                    price = product.findElement(By.cssSelector(".a-price-whole")).getText();
                } catch (Exception e) {
                    // skip if not found
                }

                // Try to extract rating
                try {
                    rating = product.findElement(By.cssSelector(".a-icon-alt")).getText();
                } catch (Exception e) {
                    // skip if not found
                }

                System.out.println("Brand/Model: " + title);
                System.out.println("Price: ₹" + price);
                System.out.println("Rating: " + rating);
                System.out.println("-----------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Adding to cart and open cart
        try {
            driver.findElement(By.id("a-autoid-1-announce")).click();
            Thread.sleep(3000);
            System.out.println("Product added to cart successfully.");
        } catch (Exception e) {
            System.out.println("Failed to add product to cart.");
            e.printStackTrace();
        }
        try {
            WebElement cartButton = driver.findElement(By.id("nav-cart-count-container"));
            cartButton.click();
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //screenshot of the product
        //Full page screenshot
        //Step 1: Convert webdriver object to Takescreenshot interface
        TakesScreenshot screenshot = (TakesScreenshot) driver; //TakesScreenshot is an interface

        //Step 2: Call getScreenshot method to create image file
        File src = screenshot.getScreenshotAs(OutputType.FILE);
        File dest = new File("C:\\Users\\Pcz\\Desktop\\amazon screenshot\\AddToCart.png");

        try {
            FileUtils.copyFile(src, dest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Method to logOut
    public static void logOut(WebDriver driver){
       try {
           WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
           driver.findElement(By.cssSelector("body > div:nth-child(1) > header:nth-child(21) > div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > div:nth-child(2) > div:nth-child(2) > a:nth-child(1)")).click();
           Thread.sleep(3000);
       }catch (Exception e){
           e.printStackTrace();
       }
    }
    public static void main(String[] args) {
        open_browser();
    }
}
