// import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
// import { TestBed } from '@angular/core/testing';
// import { PublicationService } from './publication.service';
//
// describe('PublicationService', () => {
//   let service: PublicationService;
//   let httpMock: HttpTestingController;
//
//   beforeEach(() => {
//     TestBed.configureTestingModule({
//       imports: [HttpClientTestingModule]
//     });
//     service = TestBed.inject(PublicationService);
//     httpMock = TestBed.inject(HttpTestingController);
//   });
//
//   afterEach(() => httpMock.verify());
//
//   it('should fetch publications', () => {
//     const mockResponse = [{ id: 1, title: 'Demo', stock: 1 }];
//     service.getAll().subscribe((publications) => {
//       expect(publications.length).toBe(1);
//     });
//     httpMock.expectOne('/api/publications').flush(mockResponse);
//   });
// });
