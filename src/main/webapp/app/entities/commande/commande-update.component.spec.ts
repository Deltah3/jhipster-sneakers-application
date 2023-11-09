/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import CommandeUpdate from './commande-update.vue';
import CommandeService from './commande.service';
import { DATE_TIME_LONG_FORMAT } from '@/shared/composables/date-format';
import AlertService from '@/shared/alert/alert.service';

import SneakersService from '@/entities/sneakers/sneakers.service';
import ClientService from '@/entities/client/client.service';

type CommandeUpdateComponentType = InstanceType<typeof CommandeUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const commandeSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<CommandeUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Commande Management Update Component', () => {
    let comp: CommandeUpdateComponentType;
    let commandeServiceStub: SinonStubbedInstance<CommandeService>;

    beforeEach(() => {
      route = {};
      commandeServiceStub = sinon.createStubInstance<CommandeService>(CommandeService);
      commandeServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          commandeService: () => commandeServiceStub,
          sneakersService: () =>
            sinon.createStubInstance<SneakersService>(SneakersService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          clientService: () =>
            sinon.createStubInstance<ClientService>(ClientService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('load', () => {
      beforeEach(() => {
        const wrapper = shallowMount(CommandeUpdate, { global: mountOptions });
        comp = wrapper.vm;
      });
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(CommandeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.commande = commandeSample;
        commandeServiceStub.update.resolves(commandeSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(commandeServiceStub.update.calledWith(commandeSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        commandeServiceStub.create.resolves(entity);
        const wrapper = shallowMount(CommandeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.commande = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(commandeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        commandeServiceStub.find.resolves(commandeSample);
        commandeServiceStub.retrieve.resolves([commandeSample]);

        // WHEN
        route = {
          params: {
            commandeId: '' + commandeSample.id,
          },
        };
        const wrapper = shallowMount(CommandeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.commande).toMatchObject(commandeSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        commandeServiceStub.find.resolves(commandeSample);
        const wrapper = shallowMount(CommandeUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
