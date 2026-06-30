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

    private By pimTab = By.xpath("//span[text()='PIM']");
    private By employeeSearch =  By.xpath("//label[text()='Employee Name']/parent::div/following-sibling::div/div/div/input");
    private By searchButton = By.xpath("//button[@type='submit']");
    private By emplFirstAndMiddleName = By.xpath("//div[@class='oxd-table-card']/div/div[3]");
    private By emplLastName = By.xpath("//div[@class='oxd-table-card']/div/div[4]");

    // Method to verify is admin is visible
    public boolean isAdminYabVisible() {
        return actionDriver.isDisplayed(adminTab);
    }

    public boolean verifyOrangeHtmlLogo() {
        return actionDriver.isDisplayed(orangeHRMLogo);
    }

    // Method to Navigate to PIM tab
    public void clickOnPimTab() {
        actionDriver.click(pimTab);
    }

    // Employee Search
    public void clickOnEmployeeSearch(String value) {
        actionDriver.enterText(employeeSearch, value);
        actionDriver.click(searchButton);
        actionDriver.scrollToElement(emplFirstAndMiddleName);
    }

    // Verify employee first and middle name
    public boolean verifyEmployeeFirstAndMiddleName(String emplFirstAndMiddleNameFromDB) {
        return actionDriver.compareText(emplFirstAndMiddleName,  emplFirstAndMiddleNameFromDB);
    }

    // Verify employee first and last name
    public boolean verifyEmployeeLastName(String emplLastFromDB) {
        return actionDriver.compareText(emplLastName,  emplLastFromDB);
    }

    // Method to perform logout operation
    public void logout() {
        actionDriver.click(userIDButon);
        actionDriver.click(logoutButton);
    }

}
