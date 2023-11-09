<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="sneakersApplicationApp.sneakers.home.createOrEditLabel"
          data-cy="SneakersCreateUpdateHeading"
          v-text="t$('sneakersApplicationApp.sneakers.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="sneakers.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="sneakers.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sneakersApplicationApp.sneakers.nom')" for="sneakers-nom"></label>
            <input
              type="text"
              class="form-control"
              name="nom"
              id="sneakers-nom"
              data-cy="nom"
              :class="{ valid: !v$.nom.$invalid, invalid: v$.nom.$invalid }"
              v-model="v$.nom.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sneakersApplicationApp.sneakers.couleur')" for="sneakers-couleur"></label>
            <input
              type="text"
              class="form-control"
              name="couleur"
              id="sneakers-couleur"
              data-cy="couleur"
              :class="{ valid: !v$.couleur.$invalid, invalid: v$.couleur.$invalid }"
              v-model="v$.couleur.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sneakersApplicationApp.sneakers.stock')" for="sneakers-stock"></label>
            <input
              type="number"
              class="form-control"
              name="stock"
              id="sneakers-stock"
              data-cy="stock"
              :class="{ valid: !v$.stock.$invalid, invalid: v$.stock.$invalid }"
              v-model.number="v$.stock.$model"
            />
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
<script lang="ts" src="./sneakers-update.component.ts"></script>
