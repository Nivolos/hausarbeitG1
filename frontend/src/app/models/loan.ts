export interface Loan {
  id: number;
  publicationId: number;
  borrowerId: number;
  borrowerName?: string;
  issuedAt: string;
  dueAt: string;
  returnedAt?: string | null;
  overdue: boolean;
}
