package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class LoginPage {

    private final Page page;

    private static final String LOGIN_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";

    private static final String LOGO            = ".orangehrm-login-logo";
    private static final String USERNAME_INPUT   = "//input[@name='username']";
    private static final String PASSWORD_INPUT   = "//input[@name='password']";
    private static final String LOGIN_BUTTON     = "//button[@type='submit']";
    private static final String ERROR_ALERT      = "//div[contains(@class,'oxd-alert-content')]//p";
    private static final String USERNAME_ERROR   = "(//span[contains(@class,'oxd-input-field-error-message')])[1]";
    private static final String PASSWORD_ERROR   = "(//span[contains(@class,'oxd-input-field-error-message')])[2]";
    private static final String DASHBOARD_HEADER = "//h6[contains(@class,'oxd-topbar-header-breadcrumb')]";
    private static final String FORGOT_PASSWORD  = "//p[contains(@class,'orangehrm-login-forgot')]";
    private static final String RESET_HEADING    = "//h6[text()='Reset Password']";
    private static final String USER_DROPDOWN    = "//span[contains(@class,'oxd-userdropdown-tab')]";
    private static final String LOGOUT_OPTION    = "//a[text()='Logout']";

    public LoginPage(Page page) {
        this.page = page;
    }


    public void navigate() {
        page.navigate(LOGIN_URL);
        page.waitForLoadState();
    }

    public void enterUsername(String username) {
        page.fill(USERNAME_INPUT, username);
    }

    public void enterPassword(String password) {
        page.fill(PASSWORD_INPUT, password);
    }

    public void clearUsername() {
        page.locator(USERNAME_INPUT).clear();
    }

    public void clearPassword() {
        page.locator(PASSWORD_INPUT).clear();
    }

    public void clickLogin() {
        page.click(LOGIN_BUTTON);
    }

    public void clickForgotPassword() {
        page.click(FORGOT_PASSWORD);
    }

    public void clickUserDropdown() {
        page.click(USER_DROPDOWN);
    }

    public void clickLogout() {
        page.click(LOGOUT_OPTION);
    }

    public boolean isLogoVisible() {
//        return page.isVisible(LOGO);
        return page.locator(".orangehrm-login-logo").isVisible();
    }

    public boolean isLoginFormVisible() {
        return page.isVisible(USERNAME_INPUT) && page.isVisible(PASSWORD_INPUT);
    }

    public String getErrorMessage() {
        page.waitForSelector(ERROR_ALERT);
        return page.textContent(ERROR_ALERT).trim();
    }

    public String getUsernameFieldError() {
        page.waitForSelector(USERNAME_ERROR);
        return page.textContent(USERNAME_ERROR).trim();
    }

    public String getPasswordFieldError() {
        page.waitForSelector(PASSWORD_ERROR);
        return page.textContent(PASSWORD_ERROR).trim();
    }

    public String getDashboardHeader() {
        page.waitForSelector(DASHBOARD_HEADER);
        return page.textContent(DASHBOARD_HEADER).trim();
    }

    public String getCurrentUrl() {
        return page.url();
    }

    public boolean isResetHeadingVisible() {
        page.waitForSelector(RESET_HEADING);
        return page.isVisible(RESET_HEADING);
    }

    public String getPasswordFieldType() {
        return page.getAttribute(PASSWORD_INPUT, "type");
    }
}