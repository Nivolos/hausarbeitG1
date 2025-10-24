package de.nordakademie.iaa.library.web.error;

import de.nordakademie.iaa.library.service.PublicationDeletionConflictException;
import de.nordakademie.iaa.library.service.PublicationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(PublicationDeletionConflictException.class)
    public ResponseEntity<ProblemDetail> handlePublicationDeletionConflict(PublicationDeletionConflictException ex) {
        ProblemDetail body = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "Löschen nicht möglich: Es existieren aktive Ausleihen.");
        body.setTitle("Publication in use");
        body.setProperty("publicationId", ex.getPublicationId());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(PublicationNotFoundException.class)
    public ResponseEntity<ProblemDetail> handlePublicationNotFound(PublicationNotFoundException ex) {
        ProblemDetail body = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Publikation wurde nicht gefunden.");
        body.setTitle("Publication not found");
        body.setProperty("publicationId", ex.getPublicationId());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}
