package de.nordakademie.iaa.library.web.error;

import de.nordakademie.iaa.library.service.PublicationDeletionConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(PublicationDeletionConflictException.class)
    public ResponseEntity<ProblemDetail> handlePublicationDeletionConflict(PublicationDeletionConflictException ex) {
        ProblemDetail body = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "Die Publikation kann nicht gelöscht werden, weil Ausleihen bestehen.");
        body.setTitle("Publication in use");
        body.setProperty("publicationId", ex.getPublicationId());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }
}
