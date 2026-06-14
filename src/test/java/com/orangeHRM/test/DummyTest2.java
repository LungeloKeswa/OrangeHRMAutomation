package com.orangeHRM.test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;
import org.testng.annotations.Test;

public class DummyTest2 extends BaseClass {

    @Test
    public void dummyTest2() {
       // ExtentManager.startTest("Dummy Test 2"); --This has been implemented in TestListener
        String title = getDriver().getTitle();
        ExtentManager.startTest("Verifying the title");
        assert title.equals("OrangeHRM"):"Test Failed - Title does not match";
        System.out.println("Test Passed Title is : " +title);
        ExtentManager.logStep("Validation successful");
    }

}
