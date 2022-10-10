describe('Create a secret with show and burn option and try to read it', () => {
  it('Visit home page', () => {
    cy.visit('http://localhost:8080');
  });

  it('Enter some data into fields, press button and copy generated link', () => {
    cy.get('[id="subject"]').type('Some text for test').should('have.value', 'Some text for test');
    cy.get('input[name="showAndDelete"').click();
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
});