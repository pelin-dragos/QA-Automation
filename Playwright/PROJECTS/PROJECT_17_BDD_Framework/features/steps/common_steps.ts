import { Given, When, Then } from '@cucumber/cucumber';
import { PlaywrightWorld } from '../../support/PlaywrightWorld';
import { LoginPage } from '../../pages/LoginPage';

Given('I am on the login page', async function (this: PlaywrightWorld) {
  const loginPage = new LoginPage(this.page!, this.baseUrl);
  await loginPage.navigateTo();
  await this.page!.waitForTimeout(1000);
});

Given(
  'I am logged in with username {string} and password {string}',
  async function (this: PlaywrightWorld, username: string, password: string) {
    const loginPage = new LoginPage(this.page!, this.baseUrl);
    await loginPage.navigateTo();
    await loginPage.login(username, password);
    await this.page!.waitForTimeout(1000);
  }
);

Given('I am logged in', async function (this: PlaywrightWorld) {
  const loginPage = new LoginPage(this.page!, this.baseUrl);
  await loginPage.navigateTo();
  await loginPage.login('standard_user', 'secret_sauce');
  await this.page!.waitForTimeout(1000);
});

Given('I am on the products page', async function (this: PlaywrightWorld) {
  const loginPage = new LoginPage(this.page!, this.baseUrl);
  await loginPage.navigateTo();
  await loginPage.login('standard_user', 'secret_sauce');
  await this.page!.waitForTimeout(1000);
});

Given('I am on the cart page', async function (this: PlaywrightWorld) {
  const loginPage = new LoginPage(this.page!, this.baseUrl);
  await loginPage.navigateTo();
  await loginPage.login('standard_user', 'secret_sauce');
  await this.page!.goto(`${this.baseUrl}/cart.html`);
  await this.page!.waitForTimeout(1000);
});

Given('I have added a product to the cart', async function (this: PlaywrightWorld) {
  const loginPage = new LoginPage(this.page!, this.baseUrl);
  await loginPage.navigateTo();
  await loginPage.login('standard_user', 'secret_sauce');
  const { ProductsPage } = await import('../../pages/ProductsPage');
  const productsPage = new ProductsPage(this.page!);
  await productsPage.waitForPageLoad();
  await productsPage.addProductToCart(0);
  await this.page!.waitForTimeout(1000);
});

Given('I have added products to the cart', async function (this: PlaywrightWorld) {
  const loginPage = new LoginPage(this.page!, this.baseUrl);
  await loginPage.navigateTo();
  await loginPage.login('standard_user', 'secret_sauce');
  const { ProductsPage } = await import('../../pages/ProductsPage');
  const productsPage = new ProductsPage(this.page!);
  await productsPage.waitForPageLoad();
  await productsPage.addProductToCart(0);
  await productsPage.addProductToCart(1);
  await this.page!.waitForTimeout(1000);
});

Given('I have products in the cart with total price of {string}', async function (this: PlaywrightWorld, totalPrice: string) {
  const loginPage = new LoginPage(this.page!, this.baseUrl);
  await loginPage.navigateTo();
  await loginPage.login('standard_user', 'secret_sauce');
  const { ProductsPage } = await import('../../pages/ProductsPage');
  const productsPage = new ProductsPage(this.page!);
  await productsPage.waitForPageLoad();
  await productsPage.addProductToCart(0);
  await this.page!.waitForTimeout(1000);
});

Given('I am on the checkout page', async function (this: PlaywrightWorld) {
  const loginPage = new LoginPage(this.page!, this.baseUrl);
  await loginPage.navigateTo();
  await loginPage.login('standard_user', 'secret_sauce');
  const { ProductsPage } = await import('../../pages/ProductsPage');
  const productsPage = new ProductsPage(this.page!);
  await productsPage.waitForPageLoad();
  await productsPage.addProductToCart(0);
  await productsPage.clickCartIcon();
  const { CartPage } = await import('../../pages/CartPage');
  const cartPage = new CartPage(this.page!);
  await cartPage.waitForPageLoad();
  await cartPage.clickCheckout();
  await this.page!.waitForTimeout(1000);
});

Given('I have logged out', async function (this: PlaywrightWorld) {
  const loginPage = new LoginPage(this.page!, this.baseUrl);
  await loginPage.navigateTo();
  await loginPage.login('standard_user', 'secret_sauce');
  const { ProductsPage } = await import('../../pages/ProductsPage');
  const productsPage = new ProductsPage(this.page!);
  await productsPage.waitForPageLoad();
  await productsPage.clickMenuButton();
  await this.page!.waitForTimeout(500);
  await productsPage.clickLogout();
  await this.page!.waitForTimeout(1000);
});

