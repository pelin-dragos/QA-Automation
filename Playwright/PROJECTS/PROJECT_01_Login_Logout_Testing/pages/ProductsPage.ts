import { Page, Locator, expect } from '@playwright/test';
import { LoginPage } from './LoginPage';

/**
 * Page Object Pattern - Products Page (page after login)
 * Represents the page shown after successful login
 */
export class ProductsPage {
  readonly page: Page;
  readonly inventoryContainer: Locator;
  readonly menuButton: Locator;
  readonly logoutLink: Locator;
  readonly shoppingCart: Locator;
  readonly pageTitle: Locator;

  constructor(page: Page) {
    this.page = page;

    // Locators
    this.inventoryContainer = page.locator('#inventory_container');
    this.menuButton = page.locator('#react-burger-menu-btn');
    this.logoutLink = page.locator('#logout_sidebar_link');
    this.shoppingCart = page.locator('.shopping_cart_link');
    this.pageTitle = page.locator('.title');
  }

  /**
   * Check if the products page is loaded
   *
   * @returns true if page is loaded
   */
  async isLoaded(): Promise<boolean> {
    try {
      // Wait for URL to contain 'inventory' (products page)
      await this.page.waitForURL(/.*inventory.*/, { timeout: 15000 });
      
      // Wait for container element to be present and visible
      await expect(this.inventoryContainer).toBeVisible({ timeout: 10000 });
      
      return true;
    } catch (error) {
      // If first check fails, try alternative check
      try {
        const currentUrl = this.getCurrentUrl();
        if (currentUrl.includes('inventory')) {
          // If URL is correct, wait for menu which is always present
          await expect(this.menuButton).toBeVisible({ timeout: 5000 });
          return true;
        }
      } catch {
        return false;
      }
      return false;
    }
  }

  /**
   * Return the page title
   */
  async getPageTitle(): Promise<string | null> {
    try {
      return await this.pageTitle.textContent();
    } catch {
      return null;
    }
  }

  /**
   * Return the current URL
   */
  getCurrentUrl(): string {
    return this.page.url();
  }

  /**
   * Open the hamburger menu
   */
  async openMenu(): Promise<void> {
    await this.menuButton.click();
    // Wait for menu to open
    await expect(this.logoutLink).toBeVisible();
  }

  /**
   * Log out from the application
   *
   * @returns instance of login page
   */
  async logout(): Promise<LoginPage> {
    await this.openMenu();
    await this.logoutLink.click();
    
    // Wait for redirect to login page
    await this.page.waitForURL(/.*saucedemo\.com.*/, { timeout: 5000 });
    
    return new LoginPage(this.page);
  }

  /**
   * Check if the user is logged in
   *
   * @returns true if user is logged in (products page is visible)
   */
  async isLoggedIn(): Promise<boolean> {
    const isLoaded = await this.isLoaded();
    const currentUrl = this.getCurrentUrl();
    return isLoaded && currentUrl.includes('inventory');
  }
}

