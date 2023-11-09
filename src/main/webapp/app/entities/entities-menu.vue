<template>
  <div>
    <b-dropdown-item to="/client">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.client')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/commande">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.commande')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/sneakers">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.sneakers')"></span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
