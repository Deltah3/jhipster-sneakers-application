import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import SneakersService from './sneakers.service';
import { type ISneakers } from '@/shared/model/sneakers.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Sneakers',
  setup() {
    const { t: t$ } = useI18n();
    const sneakersService = inject('sneakersService', () => new SneakersService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const sneakers: Ref<ISneakers[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveSneakerss = async () => {
      isFetching.value = true;
      try {
        const res = await sneakersService().retrieve();
        sneakers.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveSneakerss();
    };

    onMounted(async () => {
      await retrieveSneakerss();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: ISneakers) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeSneakers = async () => {
      try {
        await sneakersService().delete(removeId.value);
        const message = t$('sneakersApplicationApp.sneakers.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveSneakerss();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      sneakers,
      handleSyncList,
      isFetching,
      retrieveSneakerss,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeSneakers,
      t$,
    };
  },
});
