import { test, expect } from '@playwright/test';
import { LoginPage } from '../pages/LoginPage';
import { ProductsPage } from '../pages/ProductsPage';

/**
 * Test Suite: Logout Scenarios
 * Tests for logout functionality
 */
test.describe('Logout Tests', () => {
  
  test('should logout after successful login', async ({ page }) => {
    /**
     * Test: Full flow login → logout
     *
     * Steps:
     * 1. Login with valid credentials
     * 2. Verify we are on the products page
     * 3. Log out
     * 4. Verify we are returned to the login page
     */
    const loginPage = new LoginPage(page);
    
    // Step 1: Login with valid credentials
    await loginPage.navigateTo();
    await loginPage.login('standard_user', 'secret_sauce');
    
    const productsPage = new ProductsPage(page);
    expect(await productsPage.isLoaded()).toBeTruthy();
    expect(await productsPage.isLoggedIn()).toBeTruthy();
    
    // Step 2: Log out
    const logoutLoginPage = await productsPage.logout();
    
    // Step 3: Verify logout succeeded
    // Verify we are on the login page
    expect(await logoutLoginPage.isLoaded()).toBeTruthy();
    
    // Verify URL is the login page
    const currentUrl = page.url();
    expect(currentUrl).toContain('saucedemo.com');
    expect(currentUrl).not.toContain('inventory');
    
    // Verify login elements are visible
    await expect(logoutLoginPage.usernameField).toBeVisible();
    await expect(logoutLoginPage.passwordField).toBeVisible();
    await expect(logoutLoginPage.loginButton).toBeVisible();
  });

  test('should clear session after logout', async ({ page }) => {
    /**
     * Test: Verify session is cleared after logout
     * (cannot access products page directly)
     */
    const loginPage = new LoginPage(page);
    
    // Step 1: Login then logout
    await loginPage.navigateTo();
    await loginPage.login('standard_user', 'secret_sauce');
    
    const productsPage = new ProductsPage(page);
    await productsPage.logout();
    
    // Step 2: Try direct access to products page
    await page.goto('https://www.saucedemo.com/inventory.html');
    
    // Step 3: Verify we are redirected to login
    await page.waitForTimeout(2000);
    const currentUrl = page.url();
    
    // Verify we are not on inventory or we are redirected
    const isOnLoginPage = await loginPage.isLoaded();
    expect(
      !currentUrl.includes('inventory.html') || isOnLoginPage
    ).toBeTruthy();
    
    // Verify login elements are present
    expect(await loginPage.isLoaded()).toBeTruthy();
  });

  test('should allow relogin after logout', async ({ page }) => {
    /**
     * Test: Verify you can log in again after logout
     */
    const loginPage = new LoginPage(page);
    
    // Step 1: Login → Logout
    await loginPage.navigateTo();
    await loginPage.login('standard_user', 'secret_sauce');
    
    const productsPage = new ProductsPage(page);
    const logoutLoginPage = await productsPage.logout();
    
    // Step 2: Log in again with same credentials
    await logoutLoginPage.login('standard_user', 'secret_sauce');
    
    // Step 3: Verify second login succeeded (isLoaded() waits automatically)
    const newProductsPage = new ProductsPage(page);
    expect(await newProductsPage.isLoaded()).toBeTruthy();
    expect(await newProductsPage.isLoggedIn()).toBeTruthy();
  });
});

