package com.orangehrm.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class BaseClass {

    protected Properties prop;
    protected WebDriver driver;

    // method
    @BeforeMethod
    public void setup() throws IOException {
        // load the config file
        // object of the properties
        prop = new Properties();
        /// read the file
        FileInputStream fis = new FileInputStream("C:\\Users\\lunge\\eclipse-workspace\\IntellJ IDEA Projects\\OrangeHRMAutomation\\src\\main\\resources\\config.properties");
        prop.load(fis);

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

        // Implicit Wait - Global Wait
        int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        // Maximize the driver
        driver.manage().window().maximize();

        // Navigate to URL
        driver.get(prop.getProperty("url"));
    }

    @AfterMethod
    public void tearDown() {
       if(driver!=null) {
           driver.quit();
       }
    }


}
