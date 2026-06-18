package com.orangeHRM.test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DataProviders;
import com.orangehrm.utilities.ExtentManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginPageTest  extends BaseClass {

    // objects
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setupPages() {
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
    }

    @Test(dataProvider ="validLoginData", dataProviderClass = DataProviders.class)
    public void verifyValidLoginTest(String username, String password) {
        // ExtentManager.startTest("Valid login test"); --This has been implemented in TestListener
        System.out.println("Running test Method 1 on thread: "+Thread.currentThread().getName());
        ExtentManager.logStep("Navigating to Login Page entering username and password");
        loginPage.login(username, password);
        ExtentManager.logStep("Verifying Admin tab is visible or not");
        Assert.assertTrue(homePage.isAdminYabVisible(), "Admin tab should be visible after login");
        ExtentManager.logStep("Validation successful");
        homePage.logout();
        ExtentManager.logStep("Logged out successfully!");
        staticWait(2);
    }

    @Test(dataProvider ="invalidLoginData", dataProviderClass = DataProviders.class)
    public void invalidLoginTest(String username, String password) {
        ///ExtentManager.startTest("Invalid login test"); --This has been implemented in TestListener
        System.out.println("Running test Method 1 on thread: "+Thread.currentThread().getName());
        ExtentManager.logStep("Navigating to Login Page entering username and password");
        loginPage.login(username, password);
        String expectedErrorMessage = "Invalid credentials";
        Assert.assertTrue(loginPage.verifyErrorMessage(expectedErrorMessage), "Test Failed: invalid error message");
        ExtentManager.logStep("Validation successful");
        ExtentManager.logStep("Logged out successfully!");
    }

}
