import { test, expect } from '@playwright/test';
import { LoginPage } from '../pages/LoginPage';
import { ProductsPage } from '../pages/ProductsPage';

/**
 * Test Suite: Login Success Scenarios
 * Tests for login with valid credentials
 */
test.describe('Login Success Tests', () => {
  
  test('should login with valid credentials - standard_user', async ({ page }) => {
    /**
     * Test: Login with standard_user (valid credentials)
     *
     * Steps:
     * 1. Navigate to login page
     * 2. Enter valid credentials (standard_user/secret_sauce)
     * 3. Click login button
     * 4. Verify login succeeded (products page is loaded)
     * 5. Verify URL contains 'inventory'
     */
    const loginPage = new LoginPage(page);
    
    // Step 1: Navigate to login page
    await loginPage.navigateTo();
    expect(await loginPage.isLoaded()).toBeTruthy();
    
    // Step 2: Enter valid credentials
    await loginPage.login('standard_user', 'secret_sauce');
    
    // Step 3: Verify login succeeded (isLoaded() waits automatically)
    const productsPage = new ProductsPage(page);
    
    expect(await productsPage.isLoaded()).toBeTruthy();
    expect(productsPage.getCurrentUrl()).toContain('inventory');
    
    const pageTitle = await productsPage.getPageTitle();
    expect(pageTitle).toBe('Products');
  });

  const validUsers = [
    { username: 'problem_user', password: 'secret_sauce' },
    { username: 'performance_glitch_user', password: 'secret_sauce' }
  ];

  for (const user of validUsers) {
    test(`should login with valid credentials - ${user.username}`, async ({ page }) => {
      /**
       * Test: Login with different valid user types
       */
      const loginPage = new LoginPage(page);
      
      // Step 1: Navigate and login
      await loginPage.navigateTo();
      await loginPage.login(user.username, user.password);
      
      // Step 2: Verify login succeeded (isLoaded() waits automatically)
      const productsPage = new ProductsPage(page);
      
      expect(await productsPage.isLoaded()).toBeTruthy();
      expect(productsPage.getCurrentUrl()).toContain('inventory');
    });
  }

  test('should verify elements after successful login', async ({ page }) => {
    /**
     * Test: Verify all important elements are present after login
     */
    const loginPage = new LoginPage(page);
    
    // Step 1: Log in
    await loginPage.navigateTo();
    await loginPage.login('standard_user', 'secret_sauce');
    
    // Step 2: Verify important elements are present (isLoaded() waits automatically)
    const productsPage = new ProductsPage(page);
    expect(await productsPage.isLoaded()).toBeTruthy();
    
    // Verify menu is present
    await expect(productsPage.menuButton).toBeVisible();
    
    // Verify shopping cart is present
    await expect(productsPage.shoppingCart).toBeVisible();
  });
});

