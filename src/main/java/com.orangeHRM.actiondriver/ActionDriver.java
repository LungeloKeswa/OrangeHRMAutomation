package com.orangehrm.actiondriver;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;
import org.apache.logging.log4j.Logger;
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
    public static final Logger logger = BaseClass.logger;

    // constructor
    public ActionDriver(WebDriver driver) {
        this.driver = driver;
        // object for wait first
        int explicitWait = Integer.parseInt(BaseClass.getProp().getProperty("explicitWait"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
        logger.info("WebDriver instance is created.");
    }

    // method to click an element
    public void click(By by) {

        String elementDescription = getElementDescription(by);

        try {
            applyBorder(by, "green");
            waitForElementToBeClickable(by);
            driver.findElement(by).click();
            ExtentManager.logStep("Clicked an element: " + elementDescription);
            logger.info("Element is clicked.-->"+elementDescription);
        } catch (Exception e) {
            applyBorder(by, "red");
            System.out.println("Unable to click the element: " +e.getMessage());
            ExtentManager.logFailure(BaseClass.getDriver(), "Unable to click Element", elementDescription+"Unable to click Element");
            logger.error("Unable to click the element: "+e.getMessage());
        }
    }

    // method to enter text into an input field
    // Avoid Code Duplication fix the multiple
    public void enterText(By by, String value) {
        try {
            waitForElementToBeVisible(by);
            applyBorder(by, "green");
            //driver.findElement(by).clear();
            //driver.findElement(by).sendKeys(value);
            WebElement element = driver.findElement(by);
            element.clear();
            element.sendKeys(value);
            logger.info("Enter text on: " +getElementDescription(by)+ " -->" +value);
        } catch (Exception e) {
            applyBorder(by, "red");
            logger.error("Unable to enter the value" +e.getMessage());
        }
    }

    // Method to get text from an input field
    public String getText(By by) {
        try {
            waitForElementToBeVisible(by);
            applyBorder(by, "green");
            return driver.findElement(by).getText();
        } catch (Exception e) {
            applyBorder(by, "red");
            logger.error("Unable to get the text: "+e.getMessage());
            return "";
        }
    }

    // Method to compare Two text -- change the return type
    public boolean compareText(By by, String expectedText) {
        try {
            waitForElementToBeVisible(by);
            String actualText = driver.findElement(by).getText();
            if(expectedText.equals(actualText)) {
                applyBorder(by, "green");
                logger.info("The text is equal to the expected: "+actualText+ "equals" +expectedText);
                ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Compare Text", "Text Verified Successfully" +actualText + "equals" +expectedText);
                return true;
            } else {
                applyBorder(by, "red");
                logger.error("The text are not Matching: "+actualText+ "not equals" +expectedText);
                ExtentManager.logFailure(BaseClass.getDriver(), "Text Comparison Failed!", "Text Comparison Failed" +actualText + "not equals" +expectedText);
                return false;
            }
        } catch  (Exception e) {
            applyBorder(by, "red");
            logger.error("Unable to compare the text: "+e.getMessage());
        }
        return false;
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
            applyBorder(by, "green");
            boolean displayed = driver.findElement(by).isDisplayed();
            logger.info("The element is displayed: " +getElementDescription(by));
            ExtentManager.logStep("Element is displayed: " +getElementDescription(by));
            ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Element is dsiplayed", "Element is dsiplayed"+getElementDescription(by));
           // ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Element is displayed: " +getElementDescription(by));
            return driver.findElement(by).isDisplayed();
        } catch (Exception e) {
            applyBorder(by, "red");
            logger.error("Unable to display the element: "+e.getMessage());
            ExtentManager.logFailure(BaseClass.getDriver(), "Element is not displayed: ","Element is not displayed"+getElementDescription(by));
            return false;
        }
    }

    // wait for the page to load
    public void waitForPageLoad(int timeOutSec) {
        try {
            wait.withTimeout(Duration.ofSeconds(timeOutSec)).until(WebDriver -> (JavascriptExecutor) WebDriver)
                    .executeScript("return document.readyState").equals("complete");
            logger.info("The page is loaded successfully");
        } catch (Exception e) {
            logger.error("Unable to wait for the page load: "+timeOutSec+ " seconds. Exeception: " +e.getMessage());
        }
    }

    // Scroll to an Element
    public void scrollToElement(By by) {
        try {
            applyBorder(by, "green");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement element = driver.findElement(by);
            js.executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception e) {
            applyBorder(by, "red");
            logger.error("Unable to scroll to the element: "+e.getMessage());
        }
    }

    // Wait for element to be clickable
    public void waitForElementToBeClickable(By by) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (Exception e) {
            logger.error("Element is not clickable: "+e.getMessage());
        }
    }

    // Wait for Element to be Visible
    public void waitForElementToBeVisible(By by) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception e) {
            logger.error("Element is not visible: "+e.getMessage());
        }
    }

    // Method to create the description of an element using By locator
    public String getElementDescription(By locator) {
        // Check for null driver or locator to avoid NullPointer Exception
        if (driver==null)
            return "driver is null";
        if(locator==null)
            return "locator is null";

        try {
            // find the element using the locator
            WebElement element = driver.findElement(locator);

            // Get Element Attributes
            String name = element.getDomAttribute("name");
            String id = element.getDomAttribute("id");
            String text = element.getText();
            String className = element.getDomAttribute("classs");
            String placeholder = element.getDomAttribute("placeholder");

            // Return the description based on element attributes
            if (isNotEmpty(name)) {
                return "Element with name:" +name;
            }
            else if (isNotEmpty(id)) {
                return "Element with id:" +id;
            }
            else if (isNotEmpty(text)) {
                return "Element with text:" +truncate(text,50);
            }
            else if (isNotEmpty(className)) {
                return "Element with class name:" +className;
            }
            else if (isNotEmpty(placeholder)) {
                return "Element with placeholder:" +placeholder;
            }
        } catch (Exception e) {
            logger.error("Unable to get the element description: " +e.getMessage());
        }

        return "Unable to get the element description";

    }

    // Utility method to check a string is not NULL or empty
    private boolean isNotEmpty(String value) {
        return value != null && !value.isEmpty();
    }

    // Utility Method to truncate long String
    private String truncate(String value, int maxLength) {
        if (value==null || value.length() <= maxLength) {
           return value;
        }
        return value.substring(0, maxLength)+"....";
    }

    /// Utility Method to border and element
    public void applyBorder(By by, String color) {
        try {
            WebElement element = driver.findElement(by);

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                    "arguments[0].style.border='3px solid " + color + "';",
                    element
            );

            logger.info("Applied border color: " + color + " to " + getElementDescription(by));

        } catch (Exception e) {
            logger.warn("Failed to apply border to element: " + getElementDescription(by), e);
        }
    }
   /* public void applyBorder(By by, String color) {
        try {
            // Locate the element
            WebElement element = driver.findElement(by);
            // Apply border
            String script = "arguments[0].style.border='3px solid " +color+ "'";
            // Javascript excutor
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(script, element);
            logger.info("Applied the borders of element with border color: " +color+ "to element" +getElementDescription(by));
        } catch (Exception e) {
            logger.warn("Failed to apply the border to an element" +getElementDescription(by), e.getMessage());
        }
    } */
}
