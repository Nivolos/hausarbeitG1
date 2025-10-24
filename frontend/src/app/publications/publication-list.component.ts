import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { Publication } from '../models/publication';
import { PublicationService } from '../services/publication.service';

@Component({
  selector: 'app-publication-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './publication-list.component.html',
  styleUrls: ['./publication-list.component.css']
})
export class PublicationListComponent implements OnInit {
  publications: Publication[] = [];
  loading = false;
  errorMessage = '';

  constructor(private readonly publicationService: PublicationService) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.loading = true;
    this.errorMessage = '';
    this.publicationService.getAll().subscribe({
      next: (data) => {
        this.publications = data;
        this.loading = false;
        // console.table(this.publications);
      },
      error: (error) => {
        console.error('[PublicationList] load failed', error);
        this.errorMessage = 'Publikationen konnten nicht geladen werden.';
        this.loading = false;
      }
    });
  }

  delete(id: number | undefined): void {
    if (id == null) {
      return;
    }
    if (!confirm('Publikation wirklich löschen?')) {
      return;
    }
    this.publicationService.delete(id).subscribe({
      next: () => this.load(),
      error: (error: HttpErrorResponse) => {
        console.error('[PublicationList] delete failed', error);
        if (error.status === 409) {
          let detail = 'Löschen nicht möglich: Es existieren aktive Ausleihen für diese Publikation.';
          if (typeof error.error === 'string' && error.error.trim().length > 0) {
            detail = error.error;
          } else if (error.error && (error.error.detail || error.error.message || error.error.title)) {
            detail = error.error.detail || error.error.message || error.error.title;
          }
          this.errorMessage = detail;
          return;
        }
        this.errorMessage = 'Löschen fehlgeschlagen.';
      }
    });
  }
}
