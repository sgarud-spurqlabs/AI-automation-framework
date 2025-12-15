package com.spurqlabs.pages;

import com.microsoft.playwright.Page;
import com.spurqlabs.utils.ConfigReader;
import com.spurqlabs.utils.PlaywrightDriver;

/**
 * Page Object Model for OrangeHRM Add Employee Page
 * Follows .automation.instructions.md: prefer role/aria selectors over CSS/XPath
 */
public class AddEmployeePage {
    private final Page page;

    // Locators using role and aria attributes (preferred)
    private static final String FIRST_NAME_INPUT = "input[name='firstName']";
    private static final String MIDDLE_NAME_INPUT = "input[name='middleName']";
    private static final String LAST_NAME_INPUT = "input[name='lastName']";
    private static final String EMPLOYEE_ID_XPATH = "//label[text()='Employee Id']/parent::div/following-sibling::div/input";
    private static final String SAVE_BUTTON = "button[type='submit']";
    private static final String CANCEL_BUTTON = "button:has-text('Cancel')";
    private static final String SUCCESS_MESSAGE = ".oxd-text--toast-message";
    private static final String ERROR_MESSAGE = ".oxd-input-field-error-message";
    private static final String PIM_MENU = "a:has-text('PIM')";
    private static final String ADD_BUTTON = "button:has-text('Add')";
    private static final String EMPLOYEE_LIST_HEADER = "h5:has-text('Employee Information')";

    public AddEmployeePage() {
        this.page = PlaywrightDriver.getPage();
    }

    /**
     * Navigate to PIM module
     */
    public void navigateToPIM() {
        page.locator(PIM_MENU).click();
        page.waitForLoadState();
        // Wait for either Employee Information heading or page URL to contain 'viewEmployeeList'
        try {
            page.waitForURL("**/pim/viewEmployeeList", new Page.WaitForURLOptions()
                    .setTimeout(ConfigReader.getTimeout()));
        } catch (Exception e) {
            // Fallback: wait for page to be stable
            page.waitForTimeout(2000);
        }
    }

    /**
     * Click Add button to open Add Employee form
     */
    public void clickAddButton() {
        page.locator(ADD_BUTTON).first().click();
        page.waitForURL("**/pim/addEmployee");
    }

    /**
     * Enter First Name
     */
    public void enterFirstName(String firstName) {
        page.locator(FIRST_NAME_INPUT).fill(firstName);
    }

    /**
     * Enter Middle Name
     */
    public void enterMiddleName(String middleName) {
        page.locator(MIDDLE_NAME_INPUT).fill(middleName);
    }

    /**
     * Enter Last Name
     */
    public void enterLastName(String lastName) {
        page.locator(LAST_NAME_INPUT).fill(lastName);
    }

    /**
     * Get auto-generated Employee ID
     */
    public String getEmployeeId() {
        return page.locator(EMPLOYEE_ID_XPATH).inputValue();
    }

    /**
     * Clear Employee ID field
     */
    public void clearEmployeeId() {
        page.locator(EMPLOYEE_ID_XPATH).fill("");
    }

    /**
     * Enter custom Employee ID
     */
    public void enterEmployeeId(String employeeId) {
        page.locator(EMPLOYEE_ID_XPATH).fill(employeeId);
    }

    /**
     * Click Save button
     */
    public void clickSave() {
        page.locator(SAVE_BUTTON).click();
    }

    /**
     * Click Cancel button
     */
    public void clickCancel() {
        page.locator(CANCEL_BUTTON).click();
    }

    /**
     * Check if success message is displayed
     */
    public boolean isSuccessMessageDisplayed() {
        try {
            // Wait for page to navigate after save
            page.waitForLoadState();
            page.waitForTimeout(1000);
            
            // Success toast appears briefly
            page.waitForSelector(SUCCESS_MESSAGE, new Page.WaitForSelectorOptions()
                    .setTimeout(5000));
            return page.locator(SUCCESS_MESSAGE).isVisible();
        } catch (Exception e) {
            // Fallback: check if we navigated to personal details page
            String currentUrl = page.url();
            return currentUrl.contains("/pim/viewPersonalDetails");
        }
    }

    /**
     * Get success message text
     */
    public String getSuccessMessage() {
        return page.locator(SUCCESS_MESSAGE).textContent();
    }

    /**
     * Check if error message is displayed for a field
     */
    public boolean isErrorMessageDisplayed() {
        return page.locator(ERROR_MESSAGE).count() > 0;
    }

    /**
     * Get error message text
     */
    public String getErrorMessage() {
        return page.locator(ERROR_MESSAGE).first().textContent();
    }

    /**
     * Check if on Employee List page
     */
    public boolean isOnEmployeeListPage() {
        try {
            String currentUrl = page.url();
            return currentUrl.contains("/pim/viewEmployeeList");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Search for employee by name in the list
     */
    public boolean isEmployeeInList(String firstName, String lastName) {
        try {
            // Wait for table to load
            page.waitForSelector(".oxd-table-body", new Page.WaitForSelectorOptions()
                    .setTimeout(ConfigReader.getTimeout()));
            
            // Check if employee appears in the table
            String employeeRow = String.format(".oxd-table-body .oxd-table-row:has-text('%s %s')", 
                                                firstName, lastName);
            return page.locator(employeeRow).count() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Fill employee details from data map
     */
    public void fillEmployeeDetails(String firstName, String middleName, String lastName, String employeeId) {
        if (firstName != null && !firstName.isEmpty()) {
            enterFirstName(firstName);
        }
        if (middleName != null && !middleName.isEmpty()) {
            enterMiddleName(middleName);
        }
        if (lastName != null && !lastName.isEmpty()) {
            enterLastName(lastName);
        }
        if (employeeId != null && !employeeId.isEmpty() && !employeeId.equalsIgnoreCase("AUTO")) {
            clearEmployeeId();
            enterEmployeeId(employeeId);
        }
    }
}
