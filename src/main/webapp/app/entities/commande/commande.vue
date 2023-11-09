<template>
  <div>
    <h2 id="page-heading" data-cy="CommandeHeading">
      <span v-text="t$('sneakersApplicationApp.commande.home.title')" id="commande-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('sneakersApplicationApp.commande.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'CommandeCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-commande"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('sneakersApplicationApp.commande.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && commandes && commandes.length === 0">
      <span v-text="t$('sneakersApplicationApp.commande.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="commandes && commandes.length > 0">
      <table class="table table-striped" aria-describedby="commandes">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('sneakersApplicationApp.commande.quantite')"></span></th>
            <th scope="row"><span v-text="t$('sneakersApplicationApp.commande.dateCommande')"></span></th>
            <th scope="row"><span v-text="t$('sneakersApplicationApp.commande.dateLivraison')"></span></th>
            <th scope="row"><span v-text="t$('sneakersApplicationApp.commande.status')"></span></th>
            <th scope="row"><span v-text="t$('sneakersApplicationApp.commande.sneakerss')"></span></th>
            <th scope="row"><span v-text="t$('sneakersApplicationApp.commande.client')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="commande in commandes" :key="commande.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CommandeView', params: { commandeId: commande.id } }">{{ commande.id }}</router-link>
            </td>
            <td>{{ commande.quantite }}</td>
            <td>{{ formatDateShort(commande.dateCommande) || '' }}</td>
            <td>{{ formatDateShort(commande.dateLivraison) || '' }}</td>
            <td>{{ commande.status }}</td>
            <td>
              <span v-for="(sneakerss, i) in commande.sneakersses" :key="sneakerss.id"
                >{{ i > 0 ? ', ' : '' }}
                <router-link class="form-control-static" :to="{ name: 'SneakersView', params: { sneakersId: sneakerss.id } }">{{
                  sneakerss.id
                }}</router-link>
              </span>
            </td>
            <td>
              <div v-if="commande.client">
                <router-link :to="{ name: 'ClientView', params: { clientId: commande.client.id } }">{{ commande.client.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CommandeView', params: { commandeId: commande.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CommandeEdit', params: { commandeId: commande.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(commande)"
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
          id="sneakersApplicationApp.commande.delete.question"
          data-cy="commandeDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-commande-heading" v-text="t$('sneakersApplicationApp.commande.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-commande"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeCommande()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./commande.component.ts"></script>
