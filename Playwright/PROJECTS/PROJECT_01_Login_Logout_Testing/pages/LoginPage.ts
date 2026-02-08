import { Page, Locator, expect } from '@playwright/test';

/**
 * Page Object Pattern - Login Page
 * Represents the login page for demo sites (SauceDemo, DemoQA, etc.)
 */
export class LoginPage {
  readonly page: Page;
  readonly usernameField: Locator;
  readonly passwordField: Locator;
  readonly loginButton: Locator;
  readonly errorMessage: Locator;
  readonly loginContainer: Locator;
  private baseUrl: string;

  constructor(page: Page, baseUrl: string = 'https://www.saucedemo.com/') {
    this.page = page;
    this.baseUrl = baseUrl;

    // Locators for page elements
    this.usernameField = page.locator('#user-name');
    this.passwordField = page.locator('#password');
    this.loginButton = page.locator('#login-button');
    this.errorMessage = page.locator('h3[data-test="error"]');
    this.loginContainer = page.locator('.login-container');
  }

  /**
   * Navigate to the login page
   */
  async navigateTo(): Promise<void> {
    await this.page.goto(this.baseUrl);
    await expect(this.loginButton).toBeVisible();
  }

  /**
   * Enter the username
   *
   * @param username - username to enter
   */
  async enterUsername(username: string): Promise<void> {
    await this.usernameField.clear();
    await this.usernameField.fill(username);
  }

  /**
   * Enter the password
   *
   * @param password - password to enter
   */
  async enterPassword(password: string): Promise<void> {
    await this.passwordField.clear();
    await this.passwordField.fill(password);
  }

  /**
   * Click the login button
   */
  async clickLogin(): Promise<void> {
    await this.loginButton.click();
  }

  /**
   * Perform full login
   *
   * @param username - username
   * @param password - password
   */
  async login(username: string, password: string): Promise<void> {
    await this.enterUsername(username);
    await this.enterPassword(password);
    await this.clickLogin();
    // Wait for navigation to products page after login
    await this.page.waitForURL(/.*inventory.*/, { timeout: 15000 }).catch(() => {
      // If we don't reach inventory, login likely failed
      // Don't throw here, let tests verify
    });
  }

  /**
   * Return the error message if present
   *
   * @returns error message or null
   */
  async getErrorMessage(): Promise<string | null> {
    try {
      const errorElement = this.errorMessage;
      if (await errorElement.isVisible()) {
        return await errorElement.textContent();
      }
      return null;
    } catch {
      return null;
    }
  }

  /**
   * Check if the login page is loaded correctly
   *
   * @returns true if page is loaded, false otherwise
   */
  async isLoaded(): Promise<boolean> {
    try {
      await expect(this.loginButton).toBeVisible({ timeout: 5000 });
      return true;
    } catch {
      return false;
    }
  }

  /**
   * Return the page title
   */
  async getPageTitle(): Promise<string> {
    return await this.page.title();
  }

  /**
   * Check if an error message is displayed
   *
   * @returns true if error message exists, false otherwise
   */
  async isErrorMessageDisplayed(): Promise<boolean> {
    const errorMessage = await this.getErrorMessage();
    return errorMessage !== null && errorMessage.length > 0;
  }
}

