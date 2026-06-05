package com.orangeHRM.test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginPageTest  extends BaseClass {

    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setupPages() {
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
    }

    @Test
    public void verifyValidLoginTest() {
        loginPage.login("admin", "admin123");
        Assert.assertTrue(homePage.isAdminYabVisible(), "Admin tab should be visible after login");
        homePage.logout();
        staticWait(2);
    }

}
