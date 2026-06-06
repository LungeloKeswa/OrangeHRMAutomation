package com.orangehrm.pages;

import com.orangehrm.actiondriver.ActionDriver;
import com.orangehrm.base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

    private ActionDriver actionDriver;

    // constructor
    // initialize the ActionDriver by passing WebDriver instance
    /* public HomePage(WebDriver driver) {
        this.actionDriver = new ActionDriver(driver);
    } */

    public HomePage(WebDriver driver) {
        this.actionDriver = BaseClass.getActionDriver();
    }

    // Define locators for the homepage class
    private By adminTab = By.xpath("//span[text()='Admin']");
    private By userIDButon = By.className("oxd-userdropdown-name");
    private By logoutButton = By.xpath("//a[text()='Logout']");
    private By orangeHRMLogo = By.xpath("//div[@class='oxd-brand-banner']//img");

    // Method to verify is admin is visible
    public boolean isAdminYabVisible() {
        return actionDriver.isDisplayed(adminTab);
    }

    public boolean verifyOrangeHtmlLogo() {
        return actionDriver.isDisplayed(orangeHRMLogo);
    }

    // Method to perform logout operation
    public void logout() {
        actionDriver.click(userIDButon);
        actionDriver.click(logoutButton);
    }

}
