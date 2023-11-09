import { type ICommande } from '@/shared/model/commande.model';

export interface IClient {
  id?: number;
  nom?: string;
  adresse?: string;
  email?: string;
  commandes?: ICommande[] | null;
}

export class Client implements IClient {
  constructor(
    public id?: number,
    public nom?: string,
    public adresse?: string,
    public email?: string,
    public commandes?: ICommande[] | null,
  ) {}
}
