<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="details">
        <h2 class="jh-entity-heading" data-cy="detailsDetailsHeading">
          <span v-text="t$('sneakersApplicationApp.details.detail.title')"></span> {{ details.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('sneakersApplicationApp.details.description')"></span>
          </dt>
          <dd>
            <span>{{ details.description }}</span>
          </dd>
          <dt>
            <span v-text="t$('sneakersApplicationApp.details.reference')"></span>
          </dt>
          <dd>
            <span>{{ details.reference }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="details.id" :to="{ name: 'DetailsEdit', params: { detailsId: details.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./details-details.component.ts"></script>
