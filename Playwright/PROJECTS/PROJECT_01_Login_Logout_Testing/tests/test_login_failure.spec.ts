import { test, expect } from '@playwright/test';
import { LoginPage } from '../pages/LoginPage';

/**
 * Test Suite: Login Failure Scenarios
 * Tests for login with invalid credentials and negative cases
 */
test.describe('Login Failure Tests', () => {
  
  test('should show error message with invalid username', async ({ page }) => {
    /**
     * Test: Login with invalid username and valid password
     *
     * Expected: Error message displayed
     */
    const loginPage = new LoginPage(page);
    
    // Step 1: Navigate to login page
    await loginPage.navigateTo();
    expect(await loginPage.isLoaded()).toBeTruthy();
    
    // Step 2: Try login with invalid username
    await loginPage.login('invalid_user', 'secret_sauce');
    
    // Step 3: Verify error message appears
    const errorMessage = await loginPage.getErrorMessage();
    
    expect(errorMessage).not.toBeNull();
    expect(errorMessage?.length).toBeGreaterThan(0);
  });

  test('should show error message with invalid password', async ({ page }) => {
    /**
     * Test: Login with valid username and invalid password
     *
     * Expected: Error message displayed
     */
    const loginPage = new LoginPage(page);
    
    // Step 1: Navigate and try login with wrong password
    await loginPage.navigateTo();
    await loginPage.login('standard_user', 'wrong_password');
    
    // Step 2: Verify error message
    const errorMessage = await loginPage.getErrorMessage();
    
    expect(errorMessage).not.toBeNull();
    expect(
      errorMessage?.toLowerCase().includes('password') ||
      errorMessage?.toLowerCase().includes('credentials') ||
      errorMessage?.toLowerCase().includes('match')
    ).toBeTruthy();
  });

  test('should show validation with empty credentials', async ({ page }) => {
    /**
     * Test: Login without entering credentials
     *
     * Expected: Error message or form validation
     */
    const loginPage = new LoginPage(page);
    
    // Step 1: Navigate and try login without credentials
    await loginPage.navigateTo();
    
    // Enter nothing, just click login
    await loginPage.clickLogin();
    
    // Step 2: Verify behaviour
    // May be error message or HTML5 validation
    const errorMessage = await loginPage.getErrorMessage();
    const usernameRequired = await loginPage.usernameField.getAttribute('required');
    const passwordRequired = await loginPage.passwordField.getAttribute('required');
    
    // Verify HTML5 validation or error message
    expect(usernameRequired !== null || errorMessage !== null).toBeTruthy();
    expect(passwordRequired !== null || errorMessage !== null).toBeTruthy();
  });

  const invalidCredentials = [
    { username: 'wrong_user', password: 'wrong_pass', description: 'both wrong' },
    { username: '', password: 'secret_sauce', description: 'empty username' },
    { username: 'standard_user', password: '', description: 'empty password' },
    { username: 'test@test.com', password: 'password123', description: 'email as username' }
  ];

  for (const cred of invalidCredentials) {
    test(`should fail login with ${cred.description}`, async ({ page }) => {
      /**
       * Parameterized test: Login with different invalid credential combinations
       */
      const loginPage = new LoginPage(page);
      
      // Step 1: Test login with invalid credentials
      await loginPage.navigateTo();
      
      if (cred.username) {
        await loginPage.enterUsername(cred.username);
      }
      if (cred.password) {
        await loginPage.enterPassword(cred.password);
      }
      
      await loginPage.clickLogin();
      
      // Step 2: Verify result
      // Login should fail
      const currentUrl = page.url();
      const productsPageLoaded = currentUrl.includes('inventory');
      
      expect(productsPageLoaded).toBeFalsy();
      
      const errorMessage = await loginPage.getErrorMessage();
      // If there is an error message, verify it
      if (errorMessage) {
        expect(errorMessage.length).toBeGreaterThan(0);
      }
    });
  }
});

