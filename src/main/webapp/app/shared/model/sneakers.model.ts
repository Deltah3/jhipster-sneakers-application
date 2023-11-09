import { type IDetails } from '@/shared/model/details.model';
import { type ICommande } from '@/shared/model/commande.model';

export interface ISneakers {
  id?: number;
  stock?: number | null;
  nom?: string | null;
  taille?: number | null;
  couleur?: string | null;
  prix?: number | null;
  produits?: IDetails | null;
  commandes?: ICommande[] | null;
}

export class Sneakers implements ISneakers {
  constructor(
    public id?: number,
    public stock?: number | null,
    public nom?: string | null,
    public taille?: number | null,
    public couleur?: string | null,
    public prix?: number | null,
    public produits?: IDetails | null,
    public commandes?: ICommande[] | null,
  ) {}
}
