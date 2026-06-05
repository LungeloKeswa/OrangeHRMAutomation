package com.orangehrm.pages;

import com.orangehrm.actiondriver.ActionDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private ActionDriver actionDriver;

    // define all the locators BY class
    private By usernameField = By.name("username");
    private By passwordField = By.cssSelector("input[type='password']");
    private By loginButton = By.xpath("//button[normalize-space()='Login']");
    private By errorMessage = By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']");

    // constructor
    // intialize the ActionDriver by passing WebDriver instance
    public LoginPage(WebDriver driver) {
        this.actionDriver = new ActionDriver(driver);
    }

    // Mehod to perfomr login
    public void login(String userName, String password) {
        actionDriver.enterText(usernameField, userName);
        actionDriver.enterText(passwordField, password);
        actionDriver.click(loginButton);
    }

    // Method to check if error message is Displayed
    public boolean isErrorMessage() {
        return actionDriver.isDisplayed(errorMessage);
    }

    // Method to get text from error message
    public String getErrorMessageText() {
        return actionDriver.getText(errorMessage);
    }

    // Verify if error is displayed correct or not
    public void verifyErrorMessage(String expectedError) {
        actionDriver.compareText(errorMessage, expectedError);
    }
}
