package com.orangehrm.actiondriver;

import com.orangehrm.base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ActionDriver {

    private WebDriver driver;
    private WebDriverWait wait;

    // constructor
    public ActionDriver(WebDriver driver) {
        this.driver = driver;
        // object for wait first
        int explicitWait = Integer.parseInt(BaseClass.getProp().getProperty("explicitWait"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
    }

    // method to click an element
    public void click(By by) {
        try {
            waitForElementToBeClickable(by);
            driver.findElement(by).click();
        } catch (Exception e) {
            System.out.println("Unable to click the element: "+e.getMessage());
        }
    }

    // method to enter text into an input field
    // Avoid Code Duplication fix the multiple
    public void enterText(By by, String value) {
        try {
            waitForElementToBeVisible(by);
            //driver.findElement(by).clear();
            //driver.findElement(by).sendKeys(value);
            WebElement element = driver.findElement(by);
            element.clear();
            element.sendKeys(value);
        } catch (Exception e) {
            System.out.println("Unable to enter the value: "+e.getMessage());
        }
    }

    // Method to get text from an input field
    public String getText(By by) {
        try {
            waitForElementToBeVisible(by);
            return driver.findElement(by).getText();
        } catch (Exception e) {
            System.out.println("Unable to get the text: "+e.getMessage());
            return "";
        }
    }

    // Method to compare Two text
    public void compareText(By by, String expectedText) {
        try {
            waitForElementToBeVisible(by);
            String actualText = driver.findElement(by).getText();
            if(expectedText.equals(actualText)) {
                System.out.println("The text is equal to the expected: "+actualText+ "equals" +expectedText);
            } else {
                System.out.println("The text are not Matching: "+actualText+ "not equals" +expectedText);
            }
        } catch  (Exception e) {
            System.out.println("Unable to compare the text: "+e.getMessage());
        }
    }

    // Method to check if an element is displayed
    /* public boolean isDisplayed(By by) {
        try {
            waitForElementToBeVisible(by);
            boolean isDisplayed = driver.findElement(by).isDisplayed();
            if(isDisplayed) {
                System.out.println("The element is displayed");
                return isDisplayed;
            }
            else {
                System.out.println("The element is not displayed");
                return isDisplayed;
            }
        } catch (Exception e) {
            System.out.println("Unable to display the element: "+e.getMessage());
            return false;
        }
    } */

    // Simplified the method and remove redundant conditions
    public boolean isDisplayed(By by) {
        try {
            waitForElementToBeVisible(by);
            return driver.findElement(by).isDisplayed();
        } catch (Exception e) {
            System.out.println("Unable to display the element: "+e.getMessage());
            return false;
        }
    }

    // wait for the page to load
    public void waitForPageLoad(int timeOutSec) {
        try {
            wait.withTimeout(Duration.ofSeconds(timeOutSec)).until(WebDriver -> (JavascriptExecutor) WebDriver)
                    .executeScript("return document.readyState").equals("complete");
            System.out.println("The page is loaded successfully");
        } catch (Exception e) {
            System.out.println("Unable to wait for the page load: "+timeOutSec+ " seconds. Exeception: " +e.getMessage());
        }
    }

    // Scroll to an Element
    public void scrollToElement(By by) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement element = driver.findElement(by);
            js.executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception e) {
            System.out.println("Unable to scroll to the element: "+e.getMessage());
        }
    }

    // Wait for element to be clickable
    public void waitForElementToBeClickable(By by) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (Exception e) {
            System.out.println("Element is not clickable: "+e.getMessage());
        }
    }

    // Wait for Element to be Visible
    public void waitForElementToBeVisible(By by) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception e) {
            System.out.println("Element is not visible: "+e.getMessage());
        }
    }

}
