package com.orangeHRM.test;

import com.orangehrm.base.BaseClass;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DummyTest extends BaseClass {

    @Test
    public void dummyTest() {

        String title = driver.getTitle();
        assert title.equals("OrangeHRM"):"Test Failed - Title does not match";
        System.out.println("Test Passed Title is : " +title);
    }

}
