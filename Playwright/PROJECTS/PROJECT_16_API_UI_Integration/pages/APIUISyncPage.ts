import { Page } from '@playwright/test';

/**
 * Page Object Pattern - API UI Sync Page
 * For verifying synchronization between API and UI
 * Uses a site that displays data from API
 */
export class APIUISyncPage {
  private page: Page;

  constructor(page: Page) {
    this.page = page;
  }

  /**
   * Navigate to a URL
   */
  async navigateTo(url: string): Promise<void> {
    await this.page.goto(url);
  }

  /**
   * Wait for page to load
   */
  async waitForPageLoad(): Promise<void> {
    await this.page.waitForLoadState('networkidle');
    await this.page.waitForTimeout(1000);
  }

  /**
   * Check if page is loaded
   */
  async isLoaded(): Promise<boolean> {
    try {
      const body = this.page.locator('body');
      return await body.isVisible();
    } catch {
      return false;
    }
  }

  /**
   * Check if an item with the specified ID is present in UI
   */
  async verifyItemPresent(itemId: number | string): Promise<boolean> {
    const pageContent = await this.page.content();
    return pageContent.includes(String(itemId));
  }

  /**
   * Check if item data in UI matches expected data
   */
  async verifyItemData(itemId: number | string, expectedData: Record<string, any>): Promise<boolean> {
    const pageContent = await this.page.content();

    for (const [key, value] of Object.entries(expectedData)) {
      // Verify value is present on page
      if (!pageContent.includes(String(value))) {
        return false;
      }
    }

    return true;
  }

  /**
   * Return the number of items displayed in UI
   */
  async getAllItemsCount(): Promise<number> {
    try {
      // For JSONPlaceholder, we can search for simple patterns
      const pageContent = await this.page.content();
      
      // Search for ID patterns in JSON
      const idMatches = pageContent.match(/"id"\s*:\s*\d+/g);
      return idMatches ? idMatches.length : 0;
    } catch {
      return 0;
    }
  }
}

