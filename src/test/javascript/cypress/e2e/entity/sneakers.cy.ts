import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Sneakers e2e test', () => {
  const sneakersPageUrl = '/sneakers';
  const sneakersPageUrlPattern = new RegExp('/sneakers(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const sneakersSample = {};

  let sneakers;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/sneakers+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/sneakers').as('postEntityRequest');
    cy.intercept('DELETE', '/api/sneakers/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (sneakers) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/sneakers/${sneakers.id}`,
      }).then(() => {
        sneakers = undefined;
      });
    }
  });

  it('Sneakers menu should load Sneakers page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('sneakers');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Sneakers').should('exist');
    cy.url().should('match', sneakersPageUrlPattern);
  });

  describe('Sneakers page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(sneakersPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Sneakers page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/sneakers/new$'));
        cy.getEntityCreateUpdateHeading('Sneakers');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', sneakersPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/sneakers',
          body: sneakersSample,
        }).then(({ body }) => {
          sneakers = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/sneakers+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [sneakers],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(sneakersPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Sneakers page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('sneakers');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', sneakersPageUrlPattern);
      });

      it('edit button click should load edit Sneakers page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Sneakers');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', sneakersPageUrlPattern);
      });

      it('edit button click should load edit Sneakers page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Sneakers');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', sneakersPageUrlPattern);
      });

      it('last delete button click should delete instance of Sneakers', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('sneakers').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', sneakersPageUrlPattern);

        sneakers = undefined;
      });
    });
  });

  describe('new Sneakers page', () => {
    beforeEach(() => {
      cy.visit(`${sneakersPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Sneakers');
    });

    it('should create an instance of Sneakers', () => {
      cy.get(`[data-cy="stock"]`).type('9988');
      cy.get(`[data-cy="stock"]`).should('have.value', '9988');

      cy.get(`[data-cy="nom"]`).type('éliminer');
      cy.get(`[data-cy="nom"]`).should('have.value', 'éliminer');

      cy.get(`[data-cy="taille"]`).type('8348');
      cy.get(`[data-cy="taille"]`).should('have.value', '8348');

      cy.get(`[data-cy="couleur"]`).type('maintenant');
      cy.get(`[data-cy="couleur"]`).should('have.value', 'maintenant');

      cy.get(`[data-cy="prix"]`).type('22599.66');
      cy.get(`[data-cy="prix"]`).should('have.value', '22599.66');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        sneakers = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', sneakersPageUrlPattern);
    });
  });
});
