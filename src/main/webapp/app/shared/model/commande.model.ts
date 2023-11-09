import { type ISneakers } from '@/shared/model/sneakers.model';
import { type IClient } from '@/shared/model/client.model';

export interface ICommande {
  id?: number;
  quantite?: number | null;
  dateCommande?: Date | null;
  dateLivraison?: Date | null;
  status?: boolean | null;
  sneakersses?: ISneakers[] | null;
  client?: IClient | null;
}

export class Commande implements ICommande {
  constructor(
    public id?: number,
    public quantite?: number | null,
    public dateCommande?: Date | null,
    public dateLivraison?: Date | null,
    public status?: boolean | null,
    public sneakersses?: ISneakers[] | null,
    public client?: IClient | null,
  ) {
    this.status = this.status ?? false;
  }
}
