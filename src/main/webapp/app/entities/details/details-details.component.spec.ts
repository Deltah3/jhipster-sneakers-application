/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import DetailsDetails from './details-details.vue';
import DetailsService from './details.service';
import AlertService from '@/shared/alert/alert.service';

type DetailsDetailsComponentType = InstanceType<typeof DetailsDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const detailsSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Details Management Detail Component', () => {
    let detailsServiceStub: SinonStubbedInstance<DetailsService>;
    let mountOptions: MountingOptions<DetailsDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      detailsServiceStub = sinon.createStubInstance<DetailsService>(DetailsService);

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
          detailsService: () => detailsServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        detailsServiceStub.find.resolves(detailsSample);
        route = {
          params: {
            detailsId: '' + 123,
          },
        };
        const wrapper = shallowMount(DetailsDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.details).toMatchObject(detailsSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        detailsServiceStub.find.resolves(detailsSample);
        const wrapper = shallowMount(DetailsDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
