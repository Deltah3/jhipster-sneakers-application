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

describe('Details e2e test', () => {
  const detailsPageUrl = '/details';
  const detailsPageUrlPattern = new RegExp('/details(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const detailsSample = {};

  let details;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/details+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/details').as('postEntityRequest');
    cy.intercept('DELETE', '/api/details/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (details) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/details/${details.id}`,
      }).then(() => {
        details = undefined;
      });
    }
  });

  it('Details menu should load Details page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('details');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Details').should('exist');
    cy.url().should('match', detailsPageUrlPattern);
  });

  describe('Details page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(detailsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Details page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/details/new$'));
        cy.getEntityCreateUpdateHeading('Details');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', detailsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/details',
          body: detailsSample,
        }).then(({ body }) => {
          details = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/details+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [details],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(detailsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Details page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('details');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', detailsPageUrlPattern);
      });

      it('edit button click should load edit Details page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Details');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', detailsPageUrlPattern);
      });

      it('edit button click should load edit Details page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Details');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', detailsPageUrlPattern);
      });

      it('last delete button click should delete instance of Details', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('details').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', detailsPageUrlPattern);

        details = undefined;
      });
    });
  });

  describe('new Details page', () => {
    beforeEach(() => {
      cy.visit(`${detailsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Details');
    });

    it('should create an instance of Details', () => {
      cy.get(`[data-cy="description"]`).type('assez émérite presque');
      cy.get(`[data-cy="description"]`).should('have.value', 'assez émérite presque');

      cy.get(`[data-cy="reference"]`).type('sitôt que');
      cy.get(`[data-cy="reference"]`).should('have.value', 'sitôt que');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        details = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', detailsPageUrlPattern);
    });
  });
});
