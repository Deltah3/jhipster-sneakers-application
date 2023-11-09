import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import DetailsService from './details.service';
import { type IDetails } from '@/shared/model/details.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Details',
  setup() {
    const { t: t$ } = useI18n();
    const detailsService = inject('detailsService', () => new DetailsService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const details: Ref<IDetails[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveDetailss = async () => {
      isFetching.value = true;
      try {
        const res = await detailsService().retrieve();
        details.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveDetailss();
    };

    onMounted(async () => {
      await retrieveDetailss();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IDetails) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeDetails = async () => {
      try {
        await detailsService().delete(removeId.value);
        const message = t$('sneakersApplicationApp.details.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveDetailss();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      details,
      handleSyncList,
      isFetching,
      retrieveDetailss,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeDetails,
      t$,
    };
  },
});
