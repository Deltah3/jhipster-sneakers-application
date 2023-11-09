/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import CommandeService from './commande.service';
import { DATE_TIME_FORMAT } from '@/shared/composables/date-format';
import { Commande } from '@/shared/model/commande.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('Commande Service', () => {
    let service: CommandeService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new CommandeService();
      currentDate = new Date();
      elemDefault = new Commande(123, 0, currentDate, currentDate, false);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            dateCommande: dayjs(currentDate).format(DATE_TIME_FORMAT),
            dateLivraison: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault,
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a Commande', async () => {
        const returnedFromService = Object.assign(
          {
            id: 123,
            dateCommande: dayjs(currentDate).format(DATE_TIME_FORMAT),
            dateLivraison: dayjs(currentDate).format(DATE_TIME_FORMAT),
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            dateCommande: currentDate,
            dateLivraison: currentDate,
          },
          returnedFromService,
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Commande', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Commande', async () => {
        const returnedFromService = Object.assign(
          {
            quantite: 1,
            dateCommande: dayjs(currentDate).format(DATE_TIME_FORMAT),
            dateLivraison: dayjs(currentDate).format(DATE_TIME_FORMAT),
            status: true,
          },
          elemDefault,
        );

        const expected = Object.assign(
          {
            dateCommande: currentDate,
            dateLivraison: currentDate,
          },
          returnedFromService,
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Commande', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Commande', async () => {
        const patchObject = Object.assign(
          {
            quantite: 1,
            dateCommande: dayjs(currentDate).format(DATE_TIME_FORMAT),
            status: true,
          },
          new Commande(),
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dateCommande: currentDate,
            dateLivraison: currentDate,
          },
          returnedFromService,
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Commande', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Commande', async () => {
        const returnedFromService = Object.assign(
          {
            quantite: 1,
            dateCommande: dayjs(currentDate).format(DATE_TIME_FORMAT),
            dateLivraison: dayjs(currentDate).format(DATE_TIME_FORMAT),
            status: true,
          },
          elemDefault,
        );
        const expected = Object.assign(
          {
            dateCommande: currentDate,
            dateLivraison: currentDate,
          },
          returnedFromService,
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Commande', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Commande', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Commande', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
