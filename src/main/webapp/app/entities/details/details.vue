<template>
  <div>
    <h2 id="page-heading" data-cy="DetailsHeading">
      <span v-text="t$('sneakersApplicationApp.details.home.title')" id="details-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('sneakersApplicationApp.details.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'DetailsCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-details"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('sneakersApplicationApp.details.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && details && details.length === 0">
      <span v-text="t$('sneakersApplicationApp.details.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="details && details.length > 0">
      <table class="table table-striped" aria-describedby="details">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('sneakersApplicationApp.details.description')"></span></th>
            <th scope="row"><span v-text="t$('sneakersApplicationApp.details.reference')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="details in details" :key="details.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'DetailsView', params: { detailsId: details.id } }">{{ details.id }}</router-link>
            </td>
            <td>{{ details.description }}</td>
            <td>{{ details.reference }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'DetailsView', params: { detailsId: details.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'DetailsEdit', params: { detailsId: details.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(details)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span
          id="sneakersApplicationApp.details.delete.question"
          data-cy="detailsDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-details-heading" v-text="t$('sneakersApplicationApp.details.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-details"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeDetails()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./details.component.ts"></script>
