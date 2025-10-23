import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { Publication } from '../models/publication';
import { PublicationService } from '../services/publication.service';

@Component({
  selector: 'app-publication-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './publication-form.component.html',
  styleUrls: ['./publication-form.component.css']
})
export class PublicationFormComponent {
  model: Publication = { title: '', authors: '', publisher: '', stock: 1 };
  submitting = false;
  errorMessage = '';

  constructor(private readonly publicationService: PublicationService, private readonly router: Router) {}

  save(form: NgForm): void {
    if (form.invalid) {
      form.control.markAllAsTouched();
      return;
    }
    this.submitting = true;
    this.errorMessage = '';
    this.publicationService.create(this.model).subscribe({
      next: () => {
        this.submitting = false;
        form.resetForm({ title: '', authors: '', publisher: '', stock: 1 });
        this.router.navigate(['/publications']);
      },
      error: (error) => {
        console.error('[PublicationForm] save failed', error);
        this.errorMessage = 'Speichern fehlgeschlagen.';
        this.submitting = false;
      }
    });
  }
}
