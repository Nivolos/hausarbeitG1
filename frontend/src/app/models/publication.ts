export interface Publication {
  id?: number;
  title: string;
  authors?: string;
  publisher?: string;
  stock: number;
  activeLoanCount?: number;
}
