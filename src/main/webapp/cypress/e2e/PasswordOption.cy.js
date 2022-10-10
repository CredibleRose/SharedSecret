describe('Create a secret with password option and try to read it', () => {
  it('Visit home page', () => {
    cy.visit('http://localhost:8080');
  });

  it('Enter some data into fields, press button and copy generated link', () => {
    cy.get('[id="subject"]').type('Some text for test').should('have.value', 'Some text for test');
    cy.get('[id="usePassCheck"]').click();
    cy.get('[id="userPass"]').type('Some password for test').should('have.value', 'Some password for test');
    cy.wait(1000);
    cy.get('div').contains('Зашифровать').click();
    cy.wait(500);
    cy.get('[alt="copy"]').click();
  });
});

let copyUrl

describe('Open generated link and enter password', () => {
  it('Read link in buffer', () => {
    cy.wait(500);
    cy.window().then((win) => {
      win.navigator.clipboard.readText().then((clipText)=> {
        copyUrl = clipText;
      });
    });
  });

  it('Visit password confirm page', () => {
    cy.wait(1000);
    cy.visit(copyUrl);
  });

  it('Try to input incorrect password', () => {
    cy.wait(1000);
    cy.get('[id="password-input"]').type('Incorrect password').should('have.value', 'Incorrect password');
    cy.wait(1000);
    cy.get('button').should('have.class', 'passneed_btn').click();
    cy.wait(1000);
    cy.on('window:alert', (text) => {
      expect(text).to.equal(`Wrong password! Try again!`);
    });
    cy.wait(1000);
    cy.on('window:confirm', () => true)
    cy.wait(1000);
    cy.get('[id="password-input"]').clear().type('Some password for test').should('have.value', 'Some password for test')
    cy.wait(1000);
    cy.get('button').should('have.class', 'passneed_btn').click()
  });
});