describe('Try to read secret with incorrect link', () => {
  it('Visit incorrect link and create new secret', () => {
    cy.visit('http://localhost:8080/answer/12312312');
    cy.wait(2000);
    cy.get('button[class="createYes_btn"').click();
  })
})