/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import DetailsUpdate from './details-update.vue';
import DetailsService from './details.service';
import AlertService from '@/shared/alert/alert.service';

type DetailsUpdateComponentType = InstanceType<typeof DetailsUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const detailsSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<DetailsUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Details Management Update Component', () => {
    let comp: DetailsUpdateComponentType;
    let detailsServiceStub: SinonStubbedInstance<DetailsService>;

    beforeEach(() => {
      route = {};
      detailsServiceStub = sinon.createStubInstance<DetailsService>(DetailsService);
      detailsServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          detailsService: () => detailsServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(DetailsUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.details = detailsSample;
        detailsServiceStub.update.resolves(detailsSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(detailsServiceStub.update.calledWith(detailsSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        detailsServiceStub.create.resolves(entity);
        const wrapper = shallowMount(DetailsUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.details = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(detailsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        detailsServiceStub.find.resolves(detailsSample);
        detailsServiceStub.retrieve.resolves([detailsSample]);

        // WHEN
        route = {
          params: {
            detailsId: '' + detailsSample.id,
          },
        };
        const wrapper = shallowMount(DetailsUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.details).toMatchObject(detailsSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        detailsServiceStub.find.resolves(detailsSample);
        const wrapper = shallowMount(DetailsUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
