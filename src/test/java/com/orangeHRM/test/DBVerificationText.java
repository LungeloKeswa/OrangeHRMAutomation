package com.orangeHRM.test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.pages.HomePage;
import com.orangehrm.pages.LoginPage;
import com.orangehrm.utilities.DBConnection;
import com.orangehrm.utilities.DataProviders;
import com.orangehrm.utilities.ExtentManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Map;

public class DBVerificationText extends BaseClass {

    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeMethod
    public void setupPages() {
        loginPage = new LoginPage(getDriver());
        homePage = new HomePage(getDriver());
    }

    @Test(dataProvider ="employeeVerification", dataProviderClass = DataProviders.class)
    public void verifyEmployeeNameVerificationFromDB(String empID, String empName) {

        SoftAssert softAssert = getSoftAssert();

        ExtentManager.startTest("DB Verification Test");
        ExtentManager.logStep("Loggin with Admin Credentials");

        ExtentManager.logStep("Login with Admin Credentials");
        loginPage.login(prop.getProperty("username"), prop.getProperty("password"));

        ExtentManager.logStep("Click on PIM tab");
        homePage.clickOnPimTab();

        ExtentManager.logStep("Search for Employee");
        homePage.clickOnEmployeeSearch(empName);

        ExtentManager.logStep("Get the Employee Name from DB");
        String employee_id=empID;

        // Fetch the data into a map
        // ref for Map
        Map<String,String> employeeDetails = DBConnection.getEmployeeDetails(employee_id);
        String emplFirstName = employeeDetails.get("firstName");
        String emplMiddleName = employeeDetails.get("middleName");
        String emplLastName = employeeDetails.get("lastName");

        String emplFirstAndMiddleName = (emplFirstName+ "Test" +emplMiddleName).trim();

        // Validation first and middle name
        ExtentManager.logStep("Verify the employee first and middle name");
        softAssert.assertTrue(homePage.verifyEmployeeFirstAndMiddleName(emplFirstAndMiddleName), "First and Middle name are not Matching");

        // Validation last name
        ExtentManager.logStep("Verify the employee last name");
        softAssert.assertTrue(homePage.verifyEmployeeLastName(emplLastName), "Last name are not Matching");

        ExtentManager.logStep("DB Validation Completed");

        softAssert.assertAll();

    }

}
