import { Page } from '@playwright/test';

/**
 * Page Object Pattern - JSON Viewer Page
 * For viewing JSON in browser (for API data verification)
 */
export class JSONViewerPage {
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
   * Get JSON from page
   */
  async getJsonFromPage(): Promise<any> {
    try {
      // Method 1: From <pre> tag
      const preElement = this.page.locator('pre').first();
      if (await preElement.isVisible().catch(() => false)) {
        const jsonText = await preElement.textContent();
        if (jsonText) {
          return JSON.parse(jsonText);
        }
      }

      // Method 2: From body (raw JSON)
      const bodyElement = this.page.locator('body');
      const bodyText = await bodyElement.textContent();
      
      if (bodyText) {
        const trimmedText = bodyText.trim();
        if (trimmedText.startsWith('{') || trimmedText.startsWith('[')) {
          return JSON.parse(trimmedText);
        }
      }

      return null;
    } catch (error) {
      console.error(`Error getting JSON from page: ${error}`);
      return null;
    }
  }

  /**
   * Check if page contains valid JSON
   */
  async isJsonValid(): Promise<boolean> {
    const jsonData = await this.getJsonFromPage();
    return jsonData !== null;
  }

  /**
   * Check if JSON contains a key (and optionally the value)
   */
  async verifyJsonContains(key: string, value?: any): Promise<boolean> {
    const jsonData = await this.getJsonFromPage();

    if (jsonData === null) {
      return false;
    }

    // Check in dict or list
    if (Array.isArray(jsonData)) {
      // Search in all list items
      for (const item of jsonData) {
        if (typeof item === 'object' && item !== null && key in item) {
          if (value === undefined) {
            return true;
          }
          if (item[key] === value) {
            return true;
          }
        }
      }
    } else if (typeof jsonData === 'object' && jsonData !== null) {
      if (key in jsonData) {
        if (value === undefined) {
          return true;
        }
        return jsonData[key] === value;
      }
    }

    return false;
  }

  /**
   * Find an item in a JSON list by field
   */
  async findItemByField(fieldName: string, fieldValue: any): Promise<any> {
    const jsonData = await this.getJsonFromPage();

    if (jsonData === null) {
      return null;
    }

    if (Array.isArray(jsonData)) {
      for (const item of jsonData) {
        if (typeof item === 'object' && item !== null && item[fieldName] === fieldValue) {
          return item;
        }
      }
    }

    return null;
  }
}

