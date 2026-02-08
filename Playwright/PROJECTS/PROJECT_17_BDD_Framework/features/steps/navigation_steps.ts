import { When, Then } from '@cucumber/cucumber';
import { PlaywrightWorld } from '../../support/PlaywrightWorld';
import { ProductsPage } from '../../pages/ProductsPage';
import { CartPage } from '../../pages/CartPage';

When('I navigate to the products page', async function (this: PlaywrightWorld) {
  await this.page!.goto(`${this.baseUrl}/inventory.html`);
  await this.page!.waitForTimeout(1000);
});

When('I click the cart icon', async function (this: PlaywrightWorld) {
  const productsPage = new ProductsPage(this.page!);
  await productsPage.clickCartIcon();
  await this.page!.waitForTimeout(1000);
});

When('I click the {string} button in navigation', async function (this: PlaywrightWorld, buttonText: string) {
  if (buttonText === 'Continue Shopping') {
    const cartPage = new CartPage(this.page!);
    await cartPage.clickContinueShopping();
  }
  await this.page!.waitForTimeout(1000);
});

When('I navigate through the application', async function (this: PlaywrightWorld) {
  // Navigate through different pages
  await this.page!.goto(`${this.baseUrl}/inventory.html`);
  await this.page!.waitForTimeout(500);
  const productsPage = new ProductsPage(this.page!);
  await productsPage.clickCartIcon();
  await this.page!.waitForTimeout(1000);
});

Then('I should see the product list', async function (this: PlaywrightWorld) {
  const productsPage = new ProductsPage(this.page!);
  const isLoaded = await productsPage.isLoaded();
  if (!isLoaded) {
    throw new Error('Products list is not visible');
  }
});

Then('I should see at least one product', async function (this: PlaywrightWorld) {
  const productsPage = new ProductsPage(this.page!);
  const productCount = await productsPage.getProductCount();
  if (productCount === 0) {
    throw new Error('No products found');
  }
});

Then('I should be redirected to the cart page', async function (this: PlaywrightWorld) {
  const cartPage = new CartPage(this.page!);
  const isLoaded = await cartPage.isLoaded();
  if (!isLoaded) {
    throw new Error('Cart page is not loaded');
  }
});

Then('the URL should contain {string}', async function (this: PlaywrightWorld, urlPart: string) {
  const currentUrl = this.page!.url();
  if (!currentUrl.includes(urlPart)) {
    throw new Error(`URL does not contain ${urlPart}. Current URL: ${currentUrl}`);
  }
});

Then('I should return to the products page', async function (this: PlaywrightWorld) {
  const productsPage = new ProductsPage(this.page!);
  const isLoaded = await productsPage.isLoaded();
  if (!isLoaded) {
    throw new Error('Products page is not loaded');
  }
});

Then('all menu links should be accessible', async function (this: PlaywrightWorld) {
  // Verify menu links are accessible
  const productsPage = new ProductsPage(this.page!);
  await productsPage.clickMenuButton();
  await this.page!.waitForTimeout(500);
  const menuVisible = await this.page!.locator('.bm-menu').isVisible();
  if (!menuVisible) {
    throw new Error('Menu is not accessible');
  }
});

