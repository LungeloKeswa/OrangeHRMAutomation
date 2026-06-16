package com.orangeHRM.test;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;
import org.testng.SkipException;
import org.testng.annotations.Test;
import org.testng.Assert;

public class DummyTest extends BaseClass {

    @Test
    public void dummyTest() {
        //ExtentManager.startTest("Dummy Test 1"); --This has been implemented in TestListener
        String title = getDriver().getTitle();
        ExtentManager.startTest("Verify the Title");
        //assert title.equals("OrangeHRM"):"Test Failed - Title does not match";
        Assert.assertEquals(title, "OrangeHRM", "Title does not match");
        System.out.println("Test Passed Title is : " +title);
        //ExtentManager.logSkip("This case is skipped");
        throw new SkipException("Skipped the test as part of testing");
    }

}
