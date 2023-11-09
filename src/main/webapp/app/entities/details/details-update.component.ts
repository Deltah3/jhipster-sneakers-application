import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import DetailsService from './details.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IDetails, Details } from '@/shared/model/details.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'DetailsUpdate',
  setup() {
    const detailsService = inject('detailsService', () => new DetailsService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const details: Ref<IDetails> = ref(new Details());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'fr'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveDetails = async detailsId => {
      try {
        const res = await detailsService().find(detailsId);
        details.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.detailsId) {
      retrieveDetails(route.params.detailsId);
    }

    const initRelationships = () => {};

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      description: {},
      reference: {},
      sneakers: {},
    };
    const v$ = useVuelidate(validationRules, details as any);
    v$.value.$validate();

    return {
      detailsService,
      alertService,
      details,
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
      if (this.details.id) {
        this.detailsService()
          .update(this.details)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('sneakersApplicationApp.details.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.detailsService()
          .create(this.details)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('sneakersApplicationApp.details.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
