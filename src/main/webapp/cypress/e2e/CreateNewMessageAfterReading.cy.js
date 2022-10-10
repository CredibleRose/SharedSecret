describe('Create a secret with no options and read it', () => {
  it('Visit home page', () => {
    cy.visit('http://localhost:8080')
  })

  it('Enter some data into fields, press button and copy generated link', () => {
    cy.get('[id="subject"]').type('Some text for test').should('have.value', 'Some text for test')
    cy.wait(1000);
    cy.get('div').contains('Зашифровать').click()
    cy.wait(500);
    cy.get('[alt="copy"]').click()
  })
})

let copyUrl

describe('Open and read secret', () => {
  it('Read link in buffer', () => {
    cy.window().then((win) => {
      win.navigator.clipboard.readText().then((clipText)=> {
        copyUrl = clipText;
      });
    });
  });

  it('Visit answer page', () => {
    cy.wait(1000);
    cy.visit(copyUrl)
  });
});

describe('Create new message', () => {
  it('Press button "Create new message"', () => {
    cy.wait(1000);
    cy.get('button').should('have.class', 'responseBtn').click();
  });
})