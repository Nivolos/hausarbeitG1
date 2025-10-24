import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Loan } from '../models/loan';

interface CreateLoanRequest {
  publicationId: number;
  borrowerId: number;
}

@Injectable({ providedIn: 'root' })
export class LoanService {
  private readonly baseUrl = `${environment.apiBaseUrl}/loans`;

  constructor(private readonly http: HttpClient) {}

  borrow(publicationId: number, borrowerId: number): Observable<Loan> {
    const payload: CreateLoanRequest = { publicationId, borrowerId };
    return this.http.post<Loan>(this.baseUrl, payload);
  }

  returnLoan(loanId: number): Observable<Loan> {
    return this.http.post<Loan>(`${this.baseUrl}/${loanId}/return`, {});
  }
}
