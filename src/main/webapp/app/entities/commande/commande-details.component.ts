import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import CommandeService from './commande.service';
import { useDateFormat } from '@/shared/composables';
import { type ICommande } from '@/shared/model/commande.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CommandeDetails',
  setup() {
    const dateFormat = useDateFormat();
    const commandeService = inject('commandeService', () => new CommandeService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const commande: Ref<ICommande> = ref({});

    const retrieveCommande = async commandeId => {
      try {
        const res = await commandeService().find(commandeId);
        commande.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.commandeId) {
      retrieveCommande(route.params.commandeId);
    }

    return {
      ...dateFormat,
      alertService,
      commande,

      previousState,
      t$: useI18n().t,
    };
  },
});
