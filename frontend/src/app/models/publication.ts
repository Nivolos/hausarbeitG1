import { Loan } from './loan';

export interface Publication {
  id?: number;
  title: string;
  authors?: string;
  publisher?: string;
  stock: number;
  activeLoanCount?: number;
  activeLoans?: Loan[];
}

export function availableCopies(publication: Publication): number {
  const count = publication.activeLoanCount ?? publication.activeLoans?.length ?? 0;
  return Math.max(0, (publication.stock ?? 0) - count);
}
