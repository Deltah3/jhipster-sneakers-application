import { type ICommande } from '@/shared/model/commande.model';

export interface ISneakers {
  id?: number;
  nom?: string | null;
  couleur?: string | null;
  stock?: number | null;
  commandes?: ICommande[] | null;
}

export class Sneakers implements ISneakers {
  constructor(
    public id?: number,
    public nom?: string | null,
    public couleur?: string | null,
    public stock?: number | null,
    public commandes?: ICommande[] | null,
  ) {}
}
