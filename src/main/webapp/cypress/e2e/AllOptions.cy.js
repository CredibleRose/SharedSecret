describe('Create a secret with all options and try to read it', () => {
  it('Visit home page', () => {
    cy.visit('http://localhost:8080');
  });

  it('Enter some data into fields, press button and copy generated link', () => {
    cy.get('[id="subject"]').type('Some text for test').should('have.value', 'Some text for test');
    cy.get('input[name="usePass"').click();
    cy.get('[id="userPass"]').type('Some password for test').should('have.value', 'Some password for test');
    cy.get('input[name="useTime"').click();
    cy.get('[name="timeToBurn"]').select('12H')
    cy.get('[name="timeToBurn"]').should('have.value', '12H')
    cy.get('input[name="showAndDelete"').click();
    cy.get('[name="numberOfReads"]').select('4')
    cy.get('[name="numberOfReads"]').should('have.value', '4')
    cy.wait(1000);
    cy.get('div').contains('Зашифровать').click();
    cy.wait(500);
    cy.get('[alt="copy"]').click();
  });
});

let copyUrl

describe('Open generated link', () => {
  it('Read link in buffer', () => {
    cy.wait(500);
    cy.window().then((win) => {
      win.navigator.clipboard.readText().then((clipText)=> {
        copyUrl = clipText;
      });
    });
  });

  it('Visit show and burn confirm page', () => {
    cy.wait(1000);
    cy.visit(copyUrl);
  });

  it('Press show and burn "Yes"', () => {
    cy.wait(1000);
    cy.get('button[class="acceptYes_btn"').click();
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