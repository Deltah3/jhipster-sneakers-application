<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="sneakersApplicationApp.details.home.createOrEditLabel"
          data-cy="DetailsCreateUpdateHeading"
          v-text="t$('sneakersApplicationApp.details.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="details.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="details.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sneakersApplicationApp.details.description')" for="details-description"></label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="details-description"
              data-cy="description"
              :class="{ valid: !v$.description.$invalid, invalid: v$.description.$invalid }"
              v-model="v$.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('sneakersApplicationApp.details.reference')" for="details-reference"></label>
            <input
              type="text"
              class="form-control"
              name="reference"
              id="details-reference"
              data-cy="reference"
              :class="{ valid: !v$.reference.$invalid, invalid: v$.reference.$invalid }"
              v-model="v$.reference.$model"
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
<script lang="ts" src="./details-update.component.ts"></script>
