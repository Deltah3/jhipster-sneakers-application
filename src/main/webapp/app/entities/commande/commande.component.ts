import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import CommandeService from './commande.service';
import { type ICommande } from '@/shared/model/commande.model';
import { useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Commande',
  setup() {
    const { t: t$ } = useI18n();
    const dateFormat = useDateFormat();
    const commandeService = inject('commandeService', () => new CommandeService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const commandes: Ref<ICommande[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveCommandes = async () => {
      isFetching.value = true;
      try {
        const res = await commandeService().retrieve();
        commandes.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveCommandes();
    };

    onMounted(async () => {
      await retrieveCommandes();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: ICommande) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeCommande = async () => {
      try {
        await commandeService().delete(removeId.value);
        const message = t$('sneakersApplicationApp.commande.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveCommandes();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      commandes,
      handleSyncList,
      isFetching,
      retrieveCommandes,
      clear,
      ...dateFormat,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeCommande,
      t$,
    };
  },
});
