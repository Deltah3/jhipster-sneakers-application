/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Sneakers from './sneakers.vue';
import SneakersService from './sneakers.service';
import AlertService from '@/shared/alert/alert.service';

type SneakersComponentType = InstanceType<typeof Sneakers>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Sneakers Management Component', () => {
    let sneakersServiceStub: SinonStubbedInstance<SneakersService>;
    let mountOptions: MountingOptions<SneakersComponentType>['global'];

    beforeEach(() => {
      sneakersServiceStub = sinon.createStubInstance<SneakersService>(SneakersService);
      sneakersServiceStub.retrieve.resolves({ headers: {} });

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
          sneakersService: () => sneakersServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        sneakersServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Sneakers, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(sneakersServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.sneakers[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: SneakersComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Sneakers, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        sneakersServiceStub.retrieve.reset();
        sneakersServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        sneakersServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeSneakers();
        await comp.$nextTick(); // clear components

        // THEN
        expect(sneakersServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(sneakersServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
