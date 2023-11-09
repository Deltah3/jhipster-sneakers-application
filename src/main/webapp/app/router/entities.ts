import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const Client = () => import('@/entities/client/client.vue');
const ClientUpdate = () => import('@/entities/client/client-update.vue');
const ClientDetails = () => import('@/entities/client/client-details.vue');

const Commande = () => import('@/entities/commande/commande.vue');
const CommandeUpdate = () => import('@/entities/commande/commande-update.vue');
const CommandeDetails = () => import('@/entities/commande/commande-details.vue');

const Sneakers = () => import('@/entities/sneakers/sneakers.vue');
const SneakersUpdate = () => import('@/entities/sneakers/sneakers-update.vue');
const SneakersDetails = () => import('@/entities/sneakers/sneakers-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'client',
      name: 'Client',
      component: Client,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client/new',
      name: 'ClientCreate',
      component: ClientUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client/:clientId/edit',
      name: 'ClientEdit',
      component: ClientUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'client/:clientId/view',
      name: 'ClientView',
      component: ClientDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'commande',
      name: 'Commande',
      component: Commande,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'commande/new',
      name: 'CommandeCreate',
      component: CommandeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'commande/:commandeId/edit',
      name: 'CommandeEdit',
      component: CommandeUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'commande/:commandeId/view',
      name: 'CommandeView',
      component: CommandeDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'sneakers',
      name: 'Sneakers',
      component: Sneakers,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'sneakers/new',
      name: 'SneakersCreate',
      component: SneakersUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'sneakers/:sneakersId/edit',
      name: 'SneakersEdit',
      component: SneakersUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'sneakers/:sneakersId/view',
      name: 'SneakersView',
      component: SneakersDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
