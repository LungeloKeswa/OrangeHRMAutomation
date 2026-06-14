package com.orangehrm.listeners;

import com.orangehrm.base.BaseClass;
import com.orangehrm.utilities.ExtentManager;
import org.jspecify.annotations.NonNull;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListners implements ITestListener {

    //Triggered when a test starts
    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        // Start loggin in Extent Reports
        ExtentManager.startTest(testName);
        ExtentManager.logStep("Test Started: "+testName);
    }

    // Triggered when a Test succeeds
    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        ExtentManager.logStepWithScreenshot(BaseClass.getDriver(), "Test Passed Successfully!", "Test End" + testName + " - FIND A TICK ICON HERE Test Passed");
    }

    //Triggered when a Test Fails
    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String failureMessage = result.getThrowable().getMessage();
        ExtentManager.logStep(failureMessage);
        ExtentManager.logFailure(BaseClass.getDriver(), "Test Failed", "Test End: " + testName + " - FIND A X ICON HERE Test Passed ");
    }

    // Triggered when a Test skips
    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        ExtentManager.logSkip("Test Skipped" +testName);
    }

    //Triggered when a suite starts
    @Override
    public void onStart(ITestContext context) {
        // Initialize the Extent Report
        ExtentManager.getReporter();
    }

    // Trigger when the suite ends
    @Override
    public void onFinish(ITestContext context) {
        // Flush the Extent Reports
        ExtentManager.endTest();
    }

}
