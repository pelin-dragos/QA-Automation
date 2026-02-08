import { When, Then } from '@cucumber/cucumber';
import { PlaywrightWorld } from '../../support/PlaywrightWorld';
import { CheckoutPage } from '../../pages/CheckoutPage';
import { CheckoutOverviewPage } from '../../pages/CheckoutOverviewPage';
import { CheckoutCompletePage } from '../../pages/CheckoutCompletePage';

When('I click the {string} button at checkout', async function (this: PlaywrightWorld, buttonText: string) {
  if (buttonText === 'Checkout') {
    const { CartPage } = await import('../../pages/CartPage');
    const cartPage = new CartPage(this.page!);
    await cartPage.clickCheckout();
    await this.page!.waitForTimeout(1000);
  } else if (buttonText === 'Continue') {
    const checkoutPage = new CheckoutPage(this.page!);
    await checkoutPage.clickContinue();
    await this.page!.waitForTimeout(1000);
  } else if (buttonText === 'Finish') {
    const checkoutOverviewPage = new CheckoutOverviewPage(this.page!);
    await checkoutOverviewPage.clickFinish();
    await this.page!.waitForTimeout(1000);
  }
});

When(
  'I fill the form with first name {string}, last name {string} and postal code {string}',
  async function (this: PlaywrightWorld, firstName: string, lastName: string, postalCode: string) {
    const checkoutPage = new CheckoutPage(this.page!);
    await checkoutPage.fillCheckoutForm(firstName, lastName, postalCode);
    await this.page!.waitForTimeout(500);
  }
);

When('I click the {string} button without filling the form', async function (this: PlaywrightWorld, buttonText: string) {
  if (buttonText === 'Continue') {
    const checkoutPage = new CheckoutPage(this.page!);
    await checkoutPage.clickContinue();
    await this.page!.waitForTimeout(1000);
  }
});

When(
  'I fill the form with last name {string} and postal code {string} but without first name',
  async function (this: PlaywrightWorld, lastName: string, postalCode: string) {
    const checkoutPage = new CheckoutPage(this.page!);
    await checkoutPage.fillLastName(lastName);
    await checkoutPage.fillPostalCode(postalCode);
    // Intentionally skip firstName
    await this.page!.waitForTimeout(500);
  }
);

When('I access the checkout overview page', async function (this: PlaywrightWorld) {
  const checkoutOverviewPage = new CheckoutOverviewPage(this.page!);
  await checkoutOverviewPage.waitForPageLoad();
});

Then('I should be on the checkout page', async function (this: PlaywrightWorld) {
  const checkoutPage = new CheckoutPage(this.page!);
  const isLoaded = await checkoutPage.isLoaded();
  if (!isLoaded) {
    throw new Error('Checkout page is not loaded');
  }
});

Then('I should see the overview page', async function (this: PlaywrightWorld) {
  const checkoutOverviewPage = new CheckoutOverviewPage(this.page!);
  const isLoaded = await checkoutOverviewPage.isLoaded();
  if (!isLoaded) {
    throw new Error('Checkout overview page is not loaded');
  }
});

Then('I should see the product in the order list', async function (this: PlaywrightWorld) {
  const checkoutOverviewPage = new CheckoutOverviewPage(this.page!);
  const itemCount = await checkoutOverviewPage.getCartItemCount();
  if (itemCount === 0) {
    throw new Error('No items in order list');
  }
});

Then('I should see the success message {string}', async function (this: PlaywrightWorld, expectedMessage: string) {
  const checkoutCompletePage = new CheckoutCompletePage(this.page!);
  await checkoutCompletePage.waitForPageLoad();
  const isVisible = await checkoutCompletePage.isSuccessMessageVisible(expectedMessage);
  if (!isVisible) {
    throw new Error(`Success message "${expectedMessage}" is not visible`);
  }
});

Then('the order should be completed', async function (this: PlaywrightWorld) {
  const checkoutCompletePage = new CheckoutCompletePage(this.page!);
  const isLoaded = await checkoutCompletePage.isLoaded();
  if (!isLoaded) {
    throw new Error('Checkout complete page is not loaded');
  }
});

Then('I should see an error message {string} at checkout', async function (this: PlaywrightWorld, expectedError: string) {
  const checkoutPage = new CheckoutPage(this.page!);
  const isErrorVisible = await checkoutPage.isErrorVisible();
  if (!isErrorVisible) {
    throw new Error('Error message is not visible');
  }
  const errorMessage = await checkoutPage.getErrorMessage();
  if (!errorMessage.includes('First Name')) {
    throw new Error(`Error message does not match expected: ${errorMessage}`);
  }
});

Then('I should see an error message at checkout', async function (this: PlaywrightWorld) {
  const checkoutPage = new CheckoutPage(this.page!);
  const isErrorVisible = await checkoutPage.isErrorVisible();
  if (!isErrorVisible) {
    throw new Error('Error message is not visible');
  }
});

Then('the subtotal should be {string}', async function (this: PlaywrightWorld, expectedSubtotal: string) {
  const checkoutOverviewPage = new CheckoutOverviewPage(this.page!);
  const subtotal = await checkoutOverviewPage.getSubtotal();
  if (!subtotal.includes(expectedSubtotal.replace('$', ''))) {
    throw new Error(`Subtotal ${subtotal} does not match expected ${expectedSubtotal}`);
  }
});

Then('the tax should be calculated correctly', async function (this: PlaywrightWorld) {
  const checkoutOverviewPage = new CheckoutOverviewPage(this.page!);
  const tax = await checkoutOverviewPage.getTax();
  if (!tax || tax === '') {
    throw new Error('Tax is not displayed');
  }
});

Then('the final total should be correct', async function (this: PlaywrightWorld) {
  const checkoutOverviewPage = new CheckoutOverviewPage(this.page!);
  const total = await checkoutOverviewPage.getTotal();
  if (!total || total === '') {
    throw new Error('Total is not displayed');
  }
});

