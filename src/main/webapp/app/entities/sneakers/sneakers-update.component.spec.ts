/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import SneakersUpdate from './sneakers-update.vue';
import SneakersService from './sneakers.service';
import AlertService from '@/shared/alert/alert.service';

type SneakersUpdateComponentType = InstanceType<typeof SneakersUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const sneakersSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<SneakersUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Sneakers Management Update Component', () => {
    let comp: SneakersUpdateComponentType;
    let sneakersServiceStub: SinonStubbedInstance<SneakersService>;

    beforeEach(() => {
      route = {};
      sneakersServiceStub = sinon.createStubInstance<SneakersService>(SneakersService);
      sneakersServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          sneakersService: () => sneakersServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(SneakersUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.sneakers = sneakersSample;
        sneakersServiceStub.update.resolves(sneakersSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(sneakersServiceStub.update.calledWith(sneakersSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        sneakersServiceStub.create.resolves(entity);
        const wrapper = shallowMount(SneakersUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.sneakers = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(sneakersServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        sneakersServiceStub.find.resolves(sneakersSample);
        sneakersServiceStub.retrieve.resolves([sneakersSample]);

        // WHEN
        route = {
          params: {
            sneakersId: '' + sneakersSample.id,
          },
        };
        const wrapper = shallowMount(SneakersUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.sneakers).toMatchObject(sneakersSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        sneakersServiceStub.find.resolves(sneakersSample);
        const wrapper = shallowMount(SneakersUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
