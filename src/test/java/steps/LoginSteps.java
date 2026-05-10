package steps;

import com.microsoft.playwright.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import pages.LoginPage;

import static org.junit.Assert.*;

public class LoginSteps {

    private Playwright playwright;
    private Browser browser;
    private Page page;
    private LoginPage loginPage;

    @Before
    public void setUp() {
        playwright = Playwright.create();
        browser    = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );
        page       = browser.newPage();
        loginPage  = new LoginPage(page);
    }

    @After
    public void tearDown() {
        browser.close();
        playwright.close();
    }



    @Given("the user is on the OrangeHRM login page")
    public void userIsOnLoginPage() {
        loginPage.navigate();
    }


    @When("the user enters username {string} and password {string}")
    public void enterCredentials(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @When("the user enters username {string}")
    public void enterUsername(String username) {
        loginPage.enterUsername(username);
    }

    @When("the user enters password {string}")
    public void enterPassword(String password) {
        loginPage.enterPassword(password);
    }

    @When("the user clears the username field")
    public void clearUsername() {
        loginPage.clearUsername();
    }

    @When("the user clears the password field")
    public void clearPassword() {
        loginPage.clearPassword();
    }

    @When("the user clicks the login button")
    public void clickLogin() {
        loginPage.clickLogin();
    }

    @When("the user clicks the {string} link")
    public void clickLink(String linkText) {
        loginPage.clickForgotPassword();
    }

    @When("the user clicks on the user dropdown")
    public void clickUserDropdown() {
        loginPage.clickUserDropdown();
    }

    @When("the user clicks the logout option")
    public void clickLogout() {
        loginPage.clickLogout();
    }


    @Then("the OrangeHRM logo should be visible")
    public void logoShouldBeVisible() {
        assertTrue("Logo not visible", loginPage.isLogoVisible());
    }

    @Then("the login form should be displayed")
    public void loginFormShouldBeDisplayed() {
        assertTrue("Login form not visible", loginPage.isLoginFormVisible());
    }

    @Then("the user should be redirected to the dashboard")
    public void redirectedToDashboard() {
        assertTrue(loginPage.getCurrentUrl().contains("/dashboard"));
    }

    @Then("the dashboard header {string} should be visible")
    public void dashboardHeaderShouldBeVisible(String header) {
        assertEquals(header, loginPage.getDashboardHeader());
    }

    @Then("the error message {string} should be displayed")
    public void errorMessageShouldBeDisplayed(String expected) {
        assertEquals(expected, loginPage.getErrorMessage());
    }

    @Then("the field error {string} should be displayed under username")
    public void fieldErrorUnderUsername(String expected) {
        assertEquals(expected, loginPage.getUsernameFieldError());
    }

    @Then("the field error {string} should be displayed under password")
    public void fieldErrorUnderPassword(String expected) {
        assertEquals(expected, loginPage.getPasswordFieldError());
    }

    @Then("the password field should be of type {string}")
    public void passwordFieldType(String expectedType) {
        assertEquals(expectedType, loginPage.getPasswordFieldType());
    }

    @Then("the user should be on the forgot password page")
    public void userShouldBeOnForgotPasswordPage() {
        assertTrue(loginPage.getCurrentUrl().contains("requestPasswordResetCode"));
    }

    @Then("the heading {string} should be visible")
    public void headingShouldBeVisible(String heading) {
        assertTrue(loginPage.isResetHeadingVisible());
    }

    @Then("the user should be redirected to the login page")
    public void redirectedToLoginPage() {
        assertTrue(loginPage.getCurrentUrl().contains("/auth/login"));
    }
}