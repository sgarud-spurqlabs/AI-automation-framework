package com.spurqlabs.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.spurqlabs.utils.PlaywrightDriver;

/**
 * Page Object Model for OrangeHRM Login Page
 */
public class LoginPage {
    private final Page page;

    // Locators using stable attributes
    private static final String USERNAME_INPUT = "input[name='username']";
    private static final String PASSWORD_INPUT = "input[name='password']";
    private static final String LOGIN_BUTTON = "button[type='submit']";
    private static final String DASHBOARD_HEADER = "h6:has-text('Dashboard')";
    private static final String ERROR_MESSAGE = ".oxd-alert-content-text";

    public LoginPage() {
        this.page = PlaywrightDriver.getPage();
    }

    /**
     * Navigate to login page
     */
    public void navigateToLoginPage(String url) {
        page.navigate(url);
        page.waitForLoadState();
    }

    /**
     * Enter username
     */
    public void enterUsername(String username) {
        page.locator(USERNAME_INPUT).fill(username);
    }

    /**
     * Enter password
     */
    public void enterPassword(String password) {
        page.locator(PASSWORD_INPUT).fill(password);
    }

    /**
     * Click login button
     */
    public void clickLoginButton() {
        page.locator(LOGIN_BUTTON).click();
    }

    /**
     * Perform complete login action
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    /**
     * Verify dashboard is displayed after successful login
     */
    public boolean isDashboardDisplayed() {
        try {
            page.waitForSelector(DASHBOARD_HEADER, new Page.WaitForSelectorOptions().setTimeout(10000));
            return page.locator(DASHBOARD_HEADER).isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get error message text
     */
    public String getErrorMessage() {
        try {
            page.waitForSelector(ERROR_MESSAGE, new Page.WaitForSelectorOptions().setTimeout(5000));
            return page.locator(ERROR_MESSAGE).textContent();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Get current page title
     */
    public String getPageTitle() {
        return page.title();
    }
}
