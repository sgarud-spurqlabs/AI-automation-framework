package com.spurqlabs.steps;

import com.google.gson.JsonObject;
import com.spurqlabs.pages.LoginPage;
import com.spurqlabs.utils.ConfigReader;
import com.spurqlabs.utils.PlaywrightDriver;
import com.spurqlabs.utils.TestDataReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.testng.Assert;

/**
 * Step definitions for Login feature
 */
public class LoginSteps {
    private LoginPage loginPage;
    private JsonObject validCredentials;
    private JsonObject invalidCredentials;

    @Before
    public void setUp() {
        PlaywrightDriver.initDriver();
        loginPage = new LoginPage();
        validCredentials = TestDataReader.getValidUserCredentials();
        invalidCredentials = TestDataReader.getInvalidUserCredentials();
    }

    @After
    public void tearDown() {
        PlaywrightDriver.quitDriver();
    }

    @Given("I navigate to the OrangeHRM login page")
    public void iNavigateToTheOrangeHRMLoginPage() {
        String loginUrl = ConfigReader.getBaseUrl() + "/web/index.php/auth/login";
        loginPage.navigateToLoginPage(loginUrl);
    }

    @When("I enter valid username and password")
    public void iEnterValidUsernameAndPassword() {
        String username = validCredentials.get("username").getAsString();
        String password = validCredentials.get("password").getAsString();
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @When("I enter invalid username and password")
    public void iEnterInvalidUsernameAndPassword() {
        String username = invalidCredentials.get("username").getAsString();
        String password = invalidCredentials.get("password").getAsString();
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @When("I enter username {string}")
    public void iEnterUsername(String username) {
        loginPage.enterUsername(username);
    }

    @When("I enter password {string}")
    public void iEnterPassword(String password) {
        loginPage.enterPassword(password);
    }

    @And("I click on the login button")
    public void iClickOnTheLoginButton() {
        loginPage.clickLoginButton();
    }

    @Then("I should be redirected to the dashboard")
    public void iShouldBeRedirectedToTheDashboard() {
        Assert.assertTrue(loginPage.isDashboardDisplayed(), 
            "Dashboard is not displayed after login");
    }

    @And("I should see the Dashboard heading")
    public void iShouldSeeTheDashboardHeading() {
        Assert.assertTrue(loginPage.isDashboardDisplayed(), 
            "Dashboard heading is not visible");
    }

    @Then("I should see an error message")
    public void iShouldSeeAnErrorMessage() {
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertFalse(errorMessage.isEmpty(), 
            "Error message should be displayed for invalid credentials");
    }

    @And("I should remain on the login page")
    public void iShouldRemainOnTheLoginPage() {
        String pageTitle = loginPage.getPageTitle();
        Assert.assertEquals(pageTitle, "OrangeHRM", 
            "Should remain on login page after failed login");
    }
}
