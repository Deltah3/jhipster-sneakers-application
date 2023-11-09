<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="sneakers">
        <h2 class="jh-entity-heading" data-cy="sneakersDetailsHeading">
          <span v-text="t$('sneakersApplicationApp.sneakers.detail.title')"></span> {{ sneakers.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('sneakersApplicationApp.sneakers.nom')"></span>
          </dt>
          <dd>
            <span>{{ sneakers.nom }}</span>
          </dd>
          <dt>
            <span v-text="t$('sneakersApplicationApp.sneakers.couleur')"></span>
          </dt>
          <dd>
            <span>{{ sneakers.couleur }}</span>
          </dd>
          <dt>
            <span v-text="t$('sneakersApplicationApp.sneakers.stock')"></span>
          </dt>
          <dd>
            <span>{{ sneakers.stock }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="sneakers.id" :to="{ name: 'SneakersEdit', params: { sneakersId: sneakers.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./sneakers-details.component.ts"></script>
