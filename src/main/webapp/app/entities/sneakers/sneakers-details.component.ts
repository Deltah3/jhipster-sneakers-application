import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import SneakersService from './sneakers.service';
import { type ISneakers } from '@/shared/model/sneakers.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'SneakersDetails',
  setup() {
    const sneakersService = inject('sneakersService', () => new SneakersService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const sneakers: Ref<ISneakers> = ref({});

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

    return {
      alertService,
      sneakers,

      previousState,
      t$: useI18n().t,
    };
  },
});
