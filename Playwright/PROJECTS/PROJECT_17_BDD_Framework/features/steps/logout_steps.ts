import { When, Then } from '@cucumber/cucumber';
import { PlaywrightWorld } from '../../support/PlaywrightWorld';
import { LoginPage } from '../../pages/LoginPage';
import { ProductsPage } from '../../pages/ProductsPage';

When('I click the logout button', async function (this: PlaywrightWorld) {
  const productsPage = new ProductsPage(this.page!);
  await productsPage.clickMenuButton();
  await this.page!.waitForTimeout(500);
  await productsPage.clickLogout();
  await this.page!.waitForTimeout(1000);
});

When('I try to access the products page directly', async function (this: PlaywrightWorld) {
  await this.page!.goto(`${this.baseUrl}/inventory.html`);
  await this.page!.waitForTimeout(1000);
});

When('I open the menu', async function (this: PlaywrightWorld) {
  const productsPage = new ProductsPage(this.page!);
  await productsPage.clickMenuButton();
  await this.page!.waitForTimeout(500);
});

When('I click the {string} option', async function (this: PlaywrightWorld, option: string) {
  if (option === 'Logout') {
    const productsPage = new ProductsPage(this.page!);
    await productsPage.clickLogout();
    await this.page!.waitForTimeout(1000);
  }
});

Then('I should be logged out', async function (this: PlaywrightWorld) {
  const loginPage = new LoginPage(this.page!, this.baseUrl);
  const isOnLoginPage = await loginPage.isOnLoginPage();
  if (!isOnLoginPage) {
    throw new Error('User is not logged out');
  }
});

Then('I should be redirected to the login page', async function (this: PlaywrightWorld) {
  const loginPage = new LoginPage(this.page!, this.baseUrl);
  const isOnLoginPage = await loginPage.isOnLoginPage();
  if (!isOnLoginPage) {
    throw new Error('User is not redirected to login page');
  }
});

Then('I should not be able to access the products page without login', async function (this: PlaywrightWorld) {
  const loginPage = new LoginPage(this.page!, this.baseUrl);
  const isOnLoginPage = await loginPage.isOnLoginPage();
  if (!isOnLoginPage) {
    throw new Error('User can access products page without login');
  }
});

Then('I should be logged out successfully', async function (this: PlaywrightWorld) {
  const loginPage = new LoginPage(this.page!, this.baseUrl);
  const isOnLoginPage = await loginPage.isOnLoginPage();
  if (!isOnLoginPage) {
    throw new Error('Logout was not successful');
  }
});

