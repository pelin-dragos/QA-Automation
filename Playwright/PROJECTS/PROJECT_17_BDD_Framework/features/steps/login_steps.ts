import { When, Then } from '@cucumber/cucumber';
import { PlaywrightWorld } from '../../support/PlaywrightWorld';
import { LoginPage } from '../../pages/LoginPage';
import { ProductsPage } from '../../pages/ProductsPage';

When('I enter username {string}', async function (this: PlaywrightWorld, username: string) {
  const loginPage = new LoginPage(this.page!, this.baseUrl);
  await loginPage.enterUsername(username);
});

When('I enter password {string}', async function (this: PlaywrightWorld, password: string) {
  const loginPage = new LoginPage(this.page!, this.baseUrl);
  await loginPage.enterPassword(password);
});

When('I click the login button', async function (this: PlaywrightWorld) {
  const loginPage = new LoginPage(this.page!, this.baseUrl);
  await loginPage.clickLoginButton();
  await this.page!.waitForTimeout(2000);
});

When('I click the login button without entering credentials', async function (this: PlaywrightWorld) {
  const loginPage = new LoginPage(this.page!, this.baseUrl);
  await loginPage.clickLoginButton();
  await this.page!.waitForTimeout(2000);
});

Then('I should be logged in successfully', async function (this: PlaywrightWorld) {
  const loginPage = new LoginPage(this.page!, this.baseUrl);
  const isLoggedIn = await loginPage.isLoggedIn();
  if (!isLoggedIn) {
    throw new Error('Login failed - user is not on products page');
  }
});

Then('I should see the products page', async function (this: PlaywrightWorld) {
  const productsPage = new ProductsPage(this.page!);
  const isLoaded = await productsPage.isLoaded();
  if (!isLoaded) {
    throw new Error('Products page is not loaded');
  }
});

Then('I should see an error message', async function (this: PlaywrightWorld) {
  const loginPage = new LoginPage(this.page!, this.baseUrl);
  const isErrorVisible = await loginPage.isErrorVisible();
  if (!isErrorVisible) {
    throw new Error('Error message is not visible');
  }
});

Then('I should not be logged in', async function (this: PlaywrightWorld) {
  const loginPage = new LoginPage(this.page!, this.baseUrl);
  const isLoggedIn = await loginPage.isLoggedIn();
  if (isLoggedIn) {
    throw new Error('User should not be logged in');
  }
});

Then('I should see {string}', async function (this: PlaywrightWorld, expectedResult: string) {
  if (expectedResult === 'products page') {
    const productsPage = new ProductsPage(this.page!);
    const isLoaded = await productsPage.isLoaded();
    if (!isLoaded) {
      throw new Error('Products page is not loaded');
    }
  } else if (expectedResult === 'error message') {
    const loginPage = new LoginPage(this.page!, this.baseUrl);
    const isErrorVisible = await loginPage.isErrorVisible();
    if (!isErrorVisible) {
      throw new Error('Error message is not visible');
    }
  }
});

