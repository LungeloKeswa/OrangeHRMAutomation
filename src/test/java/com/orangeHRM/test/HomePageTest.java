package com.orangeHRM.test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.ExtentManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePageTest extends BaseClass {

    // objects
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setupPages() {
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
    }

    @Test
    public void verifyOrangeHRMLogo(){
       // ExtentManager.startTest("Home Page Verify Logo Test"); --This has been implemented in TestListener
        ExtentManager.logStep("Navigating to Login Page Entering username and password");
        loginPage.login("admin", "admin123");
        ExtentManager.logStep("Verifying Logo is visible or not");
        Assert.assertTrue(homePage.verifyOrangeHtmlLogo(), "Logo is not visible");
        ExtentManager.logStep("Validation Successful");
        ExtentManager.logStep("Logged out successfully!");
    }

}
