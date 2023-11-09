import { defineComponent, provide } from 'vue';

import ClientService from './client/client.service';
import DetailsService from './details/details.service';
import CommandeService from './commande/commande.service';
import SneakersService from './sneakers/sneakers.service';
import UserService from '@/entities/user/user.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entities',
  setup() {
    provide('userService', () => new UserService());
    provide('clientService', () => new ClientService());
    provide('detailsService', () => new DetailsService());
    provide('commandeService', () => new CommandeService());
    provide('sneakersService', () => new SneakersService());
    // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
  },
});
