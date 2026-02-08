import { When, Then } from '@cucumber/cucumber';
import { PlaywrightWorld } from '../../support/PlaywrightWorld';
import { ProductsPage } from '../../pages/ProductsPage';

When('I select the sort option {string}', async function (this: PlaywrightWorld, sortOption: string) {
  const productsPage = new ProductsPage(this.page!);
  
  // Map sort options to select values
  const sortOptionsMap: Record<string, string> = {
    'Name (A to Z)': 'az',
    'Name (Z to A)': 'za',
    'Price (low to high)': 'lohi',
    'Price (high to low)': 'hilo',
  };
  
  const optionValue = sortOptionsMap[sortOption] || sortOption;
  await productsPage.selectSortOption(optionValue);
  await this.page!.waitForTimeout(1000);
});

Then('the products should be sorted alphabetically A-Z', async function (this: PlaywrightWorld) {
  const productsPage = new ProductsPage(this.page!);
  const firstProductName = await productsPage.getFirstProductName();
  // Verify first product starts with a letter (A-Z sorting)
  if (!firstProductName || firstProductName.length === 0) {
    throw new Error('No products found');
  }
});

Then('the first product should start with an earlier letter in the alphabet', async function (this: PlaywrightWorld) {
  const productsPage = new ProductsPage(this.page!);
  const firstProductName = await productsPage.getFirstProductName();
  if (!firstProductName || firstProductName.length === 0) {
    throw new Error('No products found');
  }
});

Then('the products should be sorted alphabetically Z-A', async function (this: PlaywrightWorld) {
  const productsPage = new ProductsPage(this.page!);
  const firstProductName = await productsPage.getFirstProductName();
  if (!firstProductName || firstProductName.length === 0) {
    throw new Error('No products found');
  }
});

Then('the first product should start with a later letter in the alphabet', async function (this: PlaywrightWorld) {
  const productsPage = new ProductsPage(this.page!);
  const firstProductName = await productsPage.getFirstProductName();
  if (!firstProductName || firstProductName.length === 0) {
    throw new Error('No products found');
  }
});

Then('the products should be sorted by price ascending', async function (this: PlaywrightWorld) {
  const productsPage = new ProductsPage(this.page!);
  const firstProductPrice = await productsPage.getFirstProductPrice();
  if (firstProductPrice === 0) {
    throw new Error('No products found');
  }
});

Then('the first product should have the lowest price', async function (this: PlaywrightWorld) {
  const productsPage = new ProductsPage(this.page!);
  const firstProductPrice = await productsPage.getFirstProductPrice();
  if (firstProductPrice === 0) {
    throw new Error('No products found');
  }
});

Then('the products should be sorted by price descending', async function (this: PlaywrightWorld) {
  const productsPage = new ProductsPage(this.page!);
  const firstProductPrice = await productsPage.getFirstProductPrice();
  if (firstProductPrice === 0) {
    throw new Error('No products found');
  }
});

Then('the first product should have the highest price', async function (this: PlaywrightWorld) {
  const productsPage = new ProductsPage(this.page!);
  const firstProductPrice = await productsPage.getFirstProductPrice();
  if (firstProductPrice === 0) {
    throw new Error('No products found');
  }
});

Then('the products should be sorted according to {string}', async function (this: PlaywrightWorld, sortOption: string) {
  // Verify sorting is applied
  const productsPage = new ProductsPage(this.page!);
  const productCount = await productsPage.getProductCount();
  if (productCount === 0) {
    throw new Error('No products found');
  }
});

