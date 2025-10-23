import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Publication } from '../models/publication';

@Injectable({ providedIn: 'root' })
export class PublicationService {
  private readonly baseUrl = `${environment.apiBaseUrl}/publications`;

  constructor(private readonly http: HttpClient) {}

  getAll(): Observable<Publication[]> {
    // console.debug('[PublicationService] Loading publications from', this.baseUrl);
    return this.http.get<Publication[]>(this.baseUrl);
  }

  create(publication: Publication): Observable<Publication> {
    return this.http.post<Publication>(this.baseUrl, publication);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
