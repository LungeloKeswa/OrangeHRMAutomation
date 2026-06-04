package com.orangeHRM.test;

import com.orangehrm.base.BaseClass;
import org.testng.annotations.Test;

public class DummyTest2 extends BaseClass {

    @Test
    public void dummyTest2() {

        String title = driver.getTitle();
        assert title.equals("OrangeHRM"):"Test Failed - Title does not match";
        System.out.println("Test Passed Title is : " +title);
    }

}
