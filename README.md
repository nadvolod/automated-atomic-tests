# automated-atomic-tests
Automated atomic tests. The gold standard for automated UI testing

## What is an automated atomic test (AAT)?

<img width="500" height="300" alt="AAT" src="https://media.giphy.com/media/xj7dVmF9OS11K/giphy.gif">

An automated atomic test (AAT) checks only a single feature or component. AAT has very few UI interactions and typically touches a maximum of two screens. The “typical” UI end-to-end tests break the AAT pattern.

Furthermore, AATs meet several requirements of good tests as specified by Kent Beck

✅ Isolated

✅ Composable

✅ Fast

As an aside, this concept is already well understood in unit and integration tests, but UI tests continue to lag behind.

## Your Instructor: Nikolay Advolodkin

- 🏢 I’m a Staff Developer Advocate at Sauce Labs
- 🔭 I’m the founder of [Ultimate QA](https://ultimateqa.com/)
- 💬 Ask me about environmentalism, veganism, test automation, and fitness
- 😄 Pronouns: he/him
- ⚡ Fun fact: I love to be outdoors and active. I enjoy roller skating, biking, walking. I also enjoy weight lifting.
- 📫 Follow me for testing and dev training
    - [📩Test Java like the best](https://ultimateqa.ck.page/selenium-java-tips)
    - [Test Automation Experience Show](https://youtube.com/@test-automation-experience)
    - [LinkedIn for professional connections](https://www.linkedin.com/in/nikolayadvolodkin/)
    - [X](https://twitter.com/Nikolay_A00)

### ❓Is this test atomic❓

```java

@Test
public void shouldAddItemToCart() {
        // Open the SauceDemo website
        driver.get("https://www.saucedemo.com");

        // Find the username and password input fields and enter the credentials
        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));

        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");

        // Find and click on the login button
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        // Find the Add to Cart button for the desired item and click on it
        WebElement addToCartButton = driver.findElement(By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']"));
        addToCartButton.click();

        // Verify that the item is added to the cart
        WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
        String cartCount = cartBadge.getText();

        // Use assertions to verify the result
        Assertions.assertEquals(1, getItemsInCart(), "Item count in the cart is not as expected!");
        }
```

---

### Atomic tests can come in many forms

<img width="500" height="300" alt="forms" src="https://media.giphy.com/media/3ohzdOVasfbk9eY8es/giphy.gif">

```js
//test that validates that the link is what we expect, without clicking it
it('should validate link is correct', () => {
   cy.get('.App-link').should('have.attr', 'href')
        .and('include','www.ultimateqa.com')
});
```

Or a bit more useful but complicated, a login without filling out forms

```js
    Cypress.Commands.add('loginByForm', (username, password) => {
      Cypress.log({
        name: 'loginByForm',
        message: `${username} | ${password}`,
      })

      return cy.request({
        method: 'POST',
        url: '/login',
        form: true,
        body: {
          username,
          password,
        },
      })
    })

    beforeEach(function () {
      // login before each test
      cy.loginByForm(username, password)
    })

    it('can visit /dashboard', function () {
      // after cy.request, the session cookie has been set
      // and we can visit a protected page
      cy.visit('/dashboard')
      cy.get('h1').should('contain', 'jane.lane')
    })
```

## Key

💡 this is a tip

🏋️‍♀️ this is an exercise for you to do

❓ this is a question for us to think and talk about. Try not to scroll beyond this question before we discuss

## Rules of engagement

<img src="https://media.giphy.com/media/CB26wRVi3B9T2/giphy.gif" alt="api-testing" width="500"/>

- I'm here to Serve You, your education, and your experience
- Be kind, respectful, no judgment
- Have fun

## Automated Atomic Tests

### App 1

✅ https://www.saucedemo.com/

✅ Let's explore the app

✅ Understand login with Cookie

✅ Understand how to seed data / set app state

🏋️‍♀️ Perform exercise in SauceDemoExercise

### App 2

✅ https://thinking-tester-contact-list.herokuapp.com/

✅ Let's explore the app

✅ Understand Registration with an API

✅ Understand how to set JWT

🏋️‍♀️ Perform exercise in SauceDemoExercise

## More Examples

[Cypress examples repo](https://github.com/cypress-io/cypress-example-recipes/tree/master/examples) has a bunch of useful information. We are focused on a few examples.

[HTML web form](https://github.com/cypress-io/cypress-example-recipes/tree/master/examples/logging-in__html-web-forms)

[JWT](https://github.com/cypress-io/cypress-example-recipes/blob/master/examples/logging-in__jwt)

## Bonus Resources

- [this blog post](https://snip.ly/qt49hg) with a lot more great resources to help you learn this topic.
