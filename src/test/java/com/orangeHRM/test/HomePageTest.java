package com.orangeHRM.test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DataProviders;
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

    @Test(dataProvider ="validLoginData", dataProviderClass = DataProviders.class)
    public void verifyOrangeHRMLogo(String username, String password){
       // ExtentManager.startTest("Home Page Verify Logo Test"); --This has been implemented in TestListener
        ExtentManager.logStep("Navigating to Login Page Entering username and password");
        loginPage.login(username, password);
        ExtentManager.logStep("Verifying Logo is visible or not");
        Assert.assertTrue(homePage.verifyOrangeHtmlLogo(), "Logo is not visible");
        ExtentManager.logStep("Validation Successful");
        ExtentManager.logStep("Logged out successfully!");
    }

}
