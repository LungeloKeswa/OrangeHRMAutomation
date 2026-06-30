package com.orangeHRM.test;

import com.orangehrm.utilities.ApiUtility;
import io.restassured.response.Response;
import com.orangehrm.utilities.ExtentManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ApiTest {

    @Test
    public void verifyGetUserAPI() {

        SoftAssert softAssert = new SoftAssert();

        // Step: Define API EndPoint
        String endPoint = "end point link here";
        ExtentManager.logStep("API Endpoint: "+endPoint);

        // Step2: Send Get Request
        ExtentManager.logStep("Sending GET Request to teh API");
        Response response = ApiUtility.sendGetRequest(endPoint);

        // Step3: validate status code
        ExtentManager.logStep("Validating API Response staus code");
        boolean isStatusCodeValid = ApiUtility.validateStatusCode(response, 200);

        Assert.assertTrue(isStatusCodeValid, "Status code is not as Expected");

        if(isStatusCodeValid) {
            ExtentManager.logStepValidationForAPI("Status Code validation Passed");
        }
        else {
            ExtentManager.logFailureAPI("Status Code validation Failed!!");
        }

        // Step4: validate username
        ExtentManager.logStep("Validating response body for username");
        String username = ApiUtility.getJsonValue(response, "username");
        boolean isUserNameValid = "Bret".equals(username);
        softAssert.assertTrue(isUserNameValid, "Username is not as Expected");
        if(isUserNameValid) {
            ExtentManager.logStepValidationForAPI("Username validation Passed");
        }
        else {
            ExtentManager.logStepValidationForAPI("Username validation Failed");
        }

        // Step5: validate email
        ExtentManager.logStep("Validating response body for email");
        String userEmail = ApiUtility.getJsonValue(response, "email");
        boolean isEMailValid = "Sincere@april.biz".equals(userEmail);
        softAssert.assertTrue(isEMailValid, "Email is not valid");
        if (isEMailValid) {
            ExtentManager.logStepValidationForAPI("Email validation Passed");
        } else {
            ExtentManager.logStepValidationForAPI("Email validation Failed");
        }

        softAssert.assertAll();

    }
}

