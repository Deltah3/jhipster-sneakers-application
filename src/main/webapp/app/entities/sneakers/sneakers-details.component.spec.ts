/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import SneakersDetails from './sneakers-details.vue';
import SneakersService from './sneakers.service';
import AlertService from '@/shared/alert/alert.service';

type SneakersDetailsComponentType = InstanceType<typeof SneakersDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const sneakersSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Sneakers Management Detail Component', () => {
    let sneakersServiceStub: SinonStubbedInstance<SneakersService>;
    let mountOptions: MountingOptions<SneakersDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      sneakersServiceStub = sinon.createStubInstance<SneakersService>(SneakersService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          sneakersService: () => sneakersServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        sneakersServiceStub.find.resolves(sneakersSample);
        route = {
          params: {
            sneakersId: '' + 123,
          },
        };
        const wrapper = shallowMount(SneakersDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.sneakers).toMatchObject(sneakersSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        sneakersServiceStub.find.resolves(sneakersSample);
        const wrapper = shallowMount(SneakersDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
