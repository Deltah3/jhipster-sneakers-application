/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Commande from './commande.vue';
import CommandeService from './commande.service';
import AlertService from '@/shared/alert/alert.service';

type CommandeComponentType = InstanceType<typeof Commande>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Commande Management Component', () => {
    let commandeServiceStub: SinonStubbedInstance<CommandeService>;
    let mountOptions: MountingOptions<CommandeComponentType>['global'];

    beforeEach(() => {
      commandeServiceStub = sinon.createStubInstance<CommandeService>(CommandeService);
      commandeServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          commandeService: () => commandeServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        commandeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Commande, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(commandeServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.commandes[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: CommandeComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Commande, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        commandeServiceStub.retrieve.reset();
        commandeServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        commandeServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeCommande();
        await comp.$nextTick(); // clear components

        // THEN
        expect(commandeServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(commandeServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
