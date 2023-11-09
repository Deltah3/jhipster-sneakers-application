/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import CommandeDetails from './commande-details.vue';
import CommandeService from './commande.service';
import AlertService from '@/shared/alert/alert.service';

type CommandeDetailsComponentType = InstanceType<typeof CommandeDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const commandeSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Commande Management Detail Component', () => {
    let commandeServiceStub: SinonStubbedInstance<CommandeService>;
    let mountOptions: MountingOptions<CommandeDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      commandeServiceStub = sinon.createStubInstance<CommandeService>(CommandeService);

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
          commandeService: () => commandeServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        commandeServiceStub.find.resolves(commandeSample);
        route = {
          params: {
            commandeId: '' + 123,
          },
        };
        const wrapper = shallowMount(CommandeDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.commande).toMatchObject(commandeSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        commandeServiceStub.find.resolves(commandeSample);
        const wrapper = shallowMount(CommandeDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
