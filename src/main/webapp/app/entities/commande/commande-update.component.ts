import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import CommandeService from './commande.service';
import { useValidation, useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import SneakersService from '@/entities/sneakers/sneakers.service';
import { type ISneakers } from '@/shared/model/sneakers.model';
import ClientService from '@/entities/client/client.service';
import { type IClient } from '@/shared/model/client.model';
import { type ICommande, Commande } from '@/shared/model/commande.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CommandeUpdate',
  setup() {
    const commandeService = inject('commandeService', () => new CommandeService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const commande: Ref<ICommande> = ref(new Commande());

    const sneakersService = inject('sneakersService', () => new SneakersService());

    const sneakers: Ref<ISneakers[]> = ref([]);

    const clientService = inject('clientService', () => new ClientService());

    const clients: Ref<IClient[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'fr'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveCommande = async commandeId => {
      try {
        const res = await commandeService().find(commandeId);
        res.dateCommande = new Date(res.dateCommande);
        res.dateLivraison = new Date(res.dateLivraison);
        commande.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.commandeId) {
      retrieveCommande(route.params.commandeId);
    }

    const initRelationships = () => {
      sneakersService()
        .retrieve()
        .then(res => {
          sneakers.value = res.data;
        });
      clientService()
        .retrieve()
        .then(res => {
          clients.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      quantite: {},
      dateCommande: {},
      dateLivraison: {},
      status: {},
      sneakersses: {},
      client: {},
    };
    const v$ = useVuelidate(validationRules, commande as any);
    v$.value.$validate();

    return {
      commandeService,
      alertService,
      commande,
      previousState,
      isSaving,
      currentLanguage,
      sneakers,
      clients,
      v$,
      ...useDateFormat({ entityRef: commande }),
      t$,
    };
  },
  created(): void {
    this.commande.sneakersses = [];
  },
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.commande.id) {
        this.commandeService()
          .update(this.commande)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('sneakersApplicationApp.commande.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.commandeService()
          .create(this.commande)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('sneakersApplicationApp.commande.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },

    getSelected(selectedVals, option): any {
      if (selectedVals) {
        return selectedVals.find(value => option.id === value.id) ?? option;
      }
      return option;
    },
  },
});
