import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Borrower } from '../models/borrower';

@Injectable({ providedIn: 'root' })
export class BorrowerService {
  private readonly baseUrl = `${environment.apiBaseUrl}/borrowers`;

  constructor(private readonly http: HttpClient) {}

  getAll(): Observable<Borrower[]> {
    return this.http.get<Borrower[]>(this.baseUrl);
  }
}
