import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import SneakersService from './sneakers.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type ISneakers, Sneakers } from '@/shared/model/sneakers.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'SneakersUpdate',
  setup() {
    const sneakersService = inject('sneakersService', () => new SneakersService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const sneakers: Ref<ISneakers> = ref(new Sneakers());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'fr'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveSneakers = async sneakersId => {
      try {
        const res = await sneakersService().find(sneakersId);
        sneakers.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.sneakersId) {
      retrieveSneakers(route.params.sneakersId);
    }

    const initRelationships = () => {};

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      nom: {},
      couleur: {},
      stock: {},
      commandes: {},
    };
    const v$ = useVuelidate(validationRules, sneakers as any);
    v$.value.$validate();

    return {
      sneakersService,
      alertService,
      sneakers,
      previousState,
      isSaving,
      currentLanguage,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.sneakers.id) {
        this.sneakersService()
          .update(this.sneakers)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('sneakersApplicationApp.sneakers.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.sneakersService()
          .create(this.sneakers)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('sneakersApplicationApp.sneakers.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
