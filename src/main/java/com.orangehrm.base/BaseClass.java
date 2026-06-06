package com.orangehrm.base;

import com.orangehrm.actiondriver.ActionDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class BaseClass {

    // add static so it carries the same instance as the other value
    // carry the same value
    // not needed for the driver cause we are closing the driver
    protected static Properties prop;
    protected static WebDriver driver;
    private static ActionDriver actionDriver;

    @BeforeSuite
    public void loadConfig() throws IOException {
        // load the config file
        // object of the properties
        prop = new Properties();
        /// read the file
        FileInputStream fis = new FileInputStream("C:\\Users\\lunge\\eclipse-workspace\\IntellJ IDEA Projects\\OrangeHRMAutomation\\src\\main\\resources\\config.properties");
        prop.load(fis);
    }

    // method
    @BeforeMethod
    public void setup() {
        launchBrowser();
        configureBrowser();
        staticWait(2);
        System.out.println("Setting up WebDriver for: "+this.getClass().getSimpleName());

        // Initialize the action driver
        if(actionDriver == null){
            actionDriver = new ActionDriver(driver);
            System.out.println("ActionDriver Instance is created "+this.getClass().getSimpleName());
        }

    }

    private void launchBrowser() {

        // initialize the WebDriver based on browser defined in the config.properties file
        String browser = prop.getProperty("browser");

        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        }
        else if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        }
        else if (browser.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        }
        else {
            throw new IllegalArgumentException("Invalid browser"+browser);
        }

    }

    //configureBrowser settings such as implicit wait, maximize the browser and go to URL
    private void configureBrowser() {

        // initialize the WebDriver based on browser defined in the config.properties file
        String browser = prop.getProperty("browser");

        // Implicit Wait - Global Wait
        int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        // Maximize the driver
        driver.manage().window().maximize();

        // Navigate to URL
        try {
            driver.get(prop.getProperty("url"));
        } catch (Exception e) {
            System.out.println("Failed to navigate to the URL:"+e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown() {
       if(driver!=null) {
           try {
               driver.quit();
           } catch (Exception e) {
               System.out.println("Failed to quit driver:"+e.getMessage());
           }
       }
       driver = null;
       actionDriver = null;
       System.out.println("WebDriver instance is closed");
    }

    // driver and getter method so we can use this method when ever we want
    // basically use it outside the package
    /*public WebDriver getDriver() {
        return driver;
    } */

    // Driver setter method
    /*public void setDriver(WebDriver driver) {
        this.driver = driver;
    }*/

    // Getter Method for WebDriver
    public static WebDriver getDriver() {

        if (driver == null) {

            System.out.println("WebDriver is not initialized");
            throw new IllegalStateException("WebDriver is not initialized");
        }

        return driver;
    }

    // Getter method for prop
    public static Properties getProp() {
        return prop;
    }

    // Getter Method for ActionDriver
    public static ActionDriver getActionDriver() {

        if (actionDriver == null) {

            System.out.println("actionDriver is not initialized");
            throw new IllegalStateException("actionDriver is not initialized");
        }

        return actionDriver;
    }

    // static wait fOr pause
    public void staticWait(int seconds) {
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
    }

}
