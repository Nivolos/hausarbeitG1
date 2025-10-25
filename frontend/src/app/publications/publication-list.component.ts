import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { availableCopies, Publication } from '../models/publication';
import { PublicationService } from '../services/publication.service';
import { Borrower, borrowerDisplayName } from '../models/borrower';
import { BorrowerService } from '../services/borrower.service';
import { Loan } from '../models/loan';
import { LoanService } from '../services/loan.service';

@Component({
  selector: 'app-publication-list',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './publication-list.component.html',
  styleUrls: ['./publication-list.component.css']
})
export class PublicationListComponent implements OnInit {
  publications: Publication[] = [];
  borrowers: Borrower[] = [];
  selectedBorrowers: Record<number, number | undefined> = {};
  loading = false;
  errorMessage = '';
  infoMessage = '';
  processingPublicationIds = new Set<number>();
  processingLoanIds = new Set<number>();

  constructor(
    private readonly publicationService: PublicationService,
    private readonly borrowerService: BorrowerService,
    private readonly loanService: LoanService
  ) {}

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll(): void {
    this.loadPublications();
    this.loadBorrowers();
  }

  loadPublications(): void {
    this.loading = true;
    this.errorMessage = '';
    this.publicationService.getAll().subscribe({
      next: (data) => {
        this.publications = data;
        this.loading = false;
      },
      error: (error) => {
        console.error('[PublicationList] load failed', error);
        this.errorMessage = 'Publikationen konnten nicht geladen werden.';
        this.loading = false;
      }
    });
  }

  loadBorrowers(): void {
    this.borrowerService.getAll().subscribe({
      next: (borrowers) => {
        this.borrowers = borrowers;
      },
      error: (error) => {
        console.error('[PublicationList] borrower load failed', error);
        this.errorMessage = 'Ausleiher konnten nicht geladen werden.';
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
      next: () => this.loadPublications(),
      error: (error: HttpErrorResponse) => {
        console.error('[PublicationList] delete failed', error);
        if (error.status === 409) {
          this.errorMessage = this.resolveProblemDetail(error, 'Löschen nicht möglich: Es existieren aktive Ausleihen für diese Publikation.');
          return;
        }
        this.errorMessage = 'Löschen fehlgeschlagen.';
      }
    });
  }

  borrow(publication: Publication): void {
    const publicationId = publication.id;
    if (publicationId == null) {
      return;
    }
    const borrowerId = this.selectedBorrowers[publicationId];
    if (!borrowerId) {
      this.errorMessage = 'Bitte zuerst einen Ausleiher auswählen.';
      return;
    }
    this.processingPublicationIds.add(publicationId);
    this.errorMessage = '';
    this.infoMessage = '';
    this.loanService.borrow(publicationId, borrowerId).subscribe({
      next: () => {
        this.infoMessage = 'Ausleihe gespeichert.';
        this.processingPublicationIds.delete(publicationId);
        this.loadPublications();
      },
      error: (error: HttpErrorResponse) => {
        console.error('[PublicationList] borrow failed', error);
        this.processingPublicationIds.delete(publicationId);
        if (error.status === 409) {
          this.errorMessage = this.resolveProblemDetail(error, 'Ausleihe nicht möglich: Kein Bestand verfügbar.');
          return;
        }
        if (error.status === 404 || error.status === 422) {
          this.errorMessage = this.resolveProblemDetail(error, 'Ausleihe fehlgeschlagen.');
          return;
        }
        this.errorMessage = 'Ausleihe fehlgeschlagen.';
      }
    });
  }

  returnLoan(loan: Loan): void {
    const loanId = loan?.id;
    if (loanId == null) {
      return;
    }
    this.processingLoanIds.add(loanId);
    this.errorMessage = '';
    this.infoMessage = '';
    this.loanService.returnLoan(loanId).subscribe({
      next: () => {
        this.infoMessage = 'Ausleihe wurde zurückgegeben.';
        this.processingLoanIds.delete(loanId);
        this.loadPublications();
      },
      error: (error: HttpErrorResponse) => {
        console.error('[PublicationList] return failed', error);
        this.processingLoanIds.delete(loanId);
        if (error.status === 409) {
          this.errorMessage = this.resolveProblemDetail(error, 'Rückgabe nicht möglich.');
          return;
        }
        if (error.status === 404 || error.status === 422) {
          this.errorMessage = this.resolveProblemDetail(error, 'Ausleihe wurde nicht gefunden oder ist bereits zurückgegeben.');
          return;
        }
        this.errorMessage = 'Rückgabe fehlgeschlagen.';
      }
    });
  }

  canBorrow(publication: Publication): boolean {
    const publicationId = publication?.id;
    if (publicationId == null) {
      return false;
    }
    if (this.processingPublicationIds.has(publicationId)) {
      return false;
    }
    const borrowerId = this.selectedBorrowers[publicationId];
    return availableCopies(publication) > 0 && !!borrowerId;
  }

  isReturnDisabled(loan: Loan | undefined): boolean {
    const loanId = loan?.id;
    return loanId == null || this.processingLoanIds.has(loanId);
  }

  borrowerName(borrower: Borrower): string {
    return borrowerDisplayName(borrower);
  }

  loanIsOverdue(loan: Loan): boolean {
    return !!loan?.overdue;
  }

  availableCount(publication: Publication): number {
    return availableCopies(publication);
  }

  private resolveProblemDetail(error: HttpErrorResponse, fallback: string): string {
    if (typeof error.error === 'string' && error.error.trim().length > 0) {
      return error.error;
    }
    if (error.error && (error.error.detail || error.error.message || error.error.title)) {
      return error.error.detail || error.error.message || error.error.title;
    }
    return fallback;
  }
}
