import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import DetailsService from './details.service';
import { type IDetails } from '@/shared/model/details.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'DetailsDetails',
  setup() {
    const detailsService = inject('detailsService', () => new DetailsService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const details: Ref<IDetails> = ref({});

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

    return {
      alertService,
      details,

      previousState,
      t$: useI18n().t,
    };
  },
});
