<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="sneakersApplicationApp.commande.home.createOrEditLabel"
          data-cy="CommandeCreateUpdateHeading"
          v-text="t$('sneakersApplicationApp.commande.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="commande.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="commande.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sneakersApplicationApp.commande.quantite')" for="commande-quantite"></label>
            <input
              type="number"
              class="form-control"
              name="quantite"
              id="commande-quantite"
              data-cy="quantite"
              :class="{ valid: !v$.quantite.$invalid, invalid: v$.quantite.$invalid }"
              v-model.number="v$.quantite.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('sneakersApplicationApp.commande.dateCommande')"
              for="commande-dateCommande"
            ></label>
            <div class="d-flex">
              <input
                id="commande-dateCommande"
                data-cy="dateCommande"
                type="datetime-local"
                class="form-control"
                name="dateCommande"
                :class="{ valid: !v$.dateCommande.$invalid, invalid: v$.dateCommande.$invalid }"
                :value="convertDateTimeFromServer(v$.dateCommande.$model)"
                @change="updateInstantField('dateCommande', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('sneakersApplicationApp.commande.dateLivraison')"
              for="commande-dateLivraison"
            ></label>
            <div class="d-flex">
              <input
                id="commande-dateLivraison"
                data-cy="dateLivraison"
                type="datetime-local"
                class="form-control"
                name="dateLivraison"
                :class="{ valid: !v$.dateLivraison.$invalid, invalid: v$.dateLivraison.$invalid }"
                :value="convertDateTimeFromServer(v$.dateLivraison.$model)"
                @change="updateInstantField('dateLivraison', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sneakersApplicationApp.commande.status')" for="commande-status"></label>
            <input
              type="checkbox"
              class="form-check"
              name="status"
              id="commande-status"
              data-cy="status"
              :class="{ valid: !v$.status.$invalid, invalid: v$.status.$invalid }"
              v-model="v$.status.$model"
            />
          </div>
          <div class="form-group">
            <label v-text="t$('sneakersApplicationApp.commande.sneakerss')" for="commande-sneakerss"></label>
            <select
              class="form-control"
              id="commande-sneakersses"
              data-cy="sneakerss"
              multiple
              name="sneakerss"
              v-if="commande.sneakersses !== undefined"
              v-model="commande.sneakersses"
            >
              <option
                v-bind:value="getSelected(commande.sneakersses, sneakersOption)"
                v-for="sneakersOption in sneakers"
                :key="sneakersOption.id"
              >
                {{ sneakersOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sneakersApplicationApp.commande.client')" for="commande-client"></label>
            <select class="form-control" id="commande-client" data-cy="client" name="client" v-model="commande.client">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="commande.client && clientOption.id === commande.client.id ? commande.client : clientOption"
                v-for="clientOption in clients"
                :key="clientOption.id"
              >
                {{ clientOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./commande-update.component.ts"></script>
