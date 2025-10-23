import { Routes } from '@angular/router';
import { PublicationListComponent } from './publications/publication-list.component';
import { PublicationFormComponent } from './publications/publication-form.component';

export const routes: Routes = [
  { path: '', redirectTo: 'publications', pathMatch: 'full' },
  { path: 'publications', component: PublicationListComponent },
  { path: 'publications/new', component: PublicationFormComponent },
  { path: '**', redirectTo: 'publications' }
];
