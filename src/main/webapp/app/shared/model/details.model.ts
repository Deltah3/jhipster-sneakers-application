import { type ISneakers } from '@/shared/model/sneakers.model';

export interface IDetails {
  id?: number;
  description?: string | null;
  reference?: string | null;
  sneakers?: ISneakers | null;
}

export class Details implements IDetails {
  constructor(
    public id?: number,
    public description?: string | null,
    public reference?: string | null,
    public sneakers?: ISneakers | null,
  ) {}
}
