/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Details from './details.vue';
import DetailsService from './details.service';
import AlertService from '@/shared/alert/alert.service';

type DetailsComponentType = InstanceType<typeof Details>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Details Management Component', () => {
    let detailsServiceStub: SinonStubbedInstance<DetailsService>;
    let mountOptions: MountingOptions<DetailsComponentType>['global'];

    beforeEach(() => {
      detailsServiceStub = sinon.createStubInstance<DetailsService>(DetailsService);
      detailsServiceStub.retrieve.resolves({ headers: {} });

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
          detailsService: () => detailsServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        detailsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Details, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(detailsServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.details[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: DetailsComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Details, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        detailsServiceStub.retrieve.reset();
        detailsServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        detailsServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeDetails();
        await comp.$nextTick(); // clear components

        // THEN
        expect(detailsServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(detailsServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
