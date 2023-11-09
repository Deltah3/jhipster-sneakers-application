<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="commande">
        <h2 class="jh-entity-heading" data-cy="commandeDetailsHeading">
          <span v-text="t$('sneakersApplicationApp.commande.detail.title')"></span> {{ commande.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('sneakersApplicationApp.commande.quantite')"></span>
          </dt>
          <dd>
            <span>{{ commande.quantite }}</span>
          </dd>
          <dt>
            <span v-text="t$('sneakersApplicationApp.commande.dateCommande')"></span>
          </dt>
          <dd>
            <span v-if="commande.dateCommande">{{ formatDateLong(commande.dateCommande) }}</span>
          </dd>
          <dt>
            <span v-text="t$('sneakersApplicationApp.commande.dateLivraison')"></span>
          </dt>
          <dd>
            <span v-if="commande.dateLivraison">{{ formatDateLong(commande.dateLivraison) }}</span>
          </dd>
          <dt>
            <span v-text="t$('sneakersApplicationApp.commande.status')"></span>
          </dt>
          <dd>
            <span>{{ commande.status }}</span>
          </dd>
          <dt>
            <span v-text="t$('sneakersApplicationApp.commande.sneakerss')"></span>
          </dt>
          <dd>
            <span v-for="(sneakerss, i) in commande.sneakersses" :key="sneakerss.id"
              >{{ i > 0 ? ', ' : '' }}
              <router-link :to="{ name: 'SneakersView', params: { sneakersId: sneakerss.id } }">{{ sneakerss.id }}</router-link>
            </span>
          </dd>
          <dt>
            <span v-text="t$('sneakersApplicationApp.commande.client')"></span>
          </dt>
          <dd>
            <div v-if="commande.client">
              <router-link :to="{ name: 'ClientView', params: { clientId: commande.client.id } }">{{ commande.client.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="commande.id" :to="{ name: 'CommandeEdit', params: { commandeId: commande.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./commande-details.component.ts"></script>
