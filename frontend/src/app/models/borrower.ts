export interface Borrower {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
}

export function borrowerDisplayName(borrower: Borrower): string {
  return `${borrower.firstName} ${borrower.lastName}`.trim();
}
