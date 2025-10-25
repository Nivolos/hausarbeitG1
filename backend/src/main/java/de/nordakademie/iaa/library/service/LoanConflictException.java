package de.nordakademie.iaa.library.service;

public class LoanConflictException extends RuntimeException {

    private final Long publicationId;
    private final Long loanId;

    public LoanConflictException(String message, Long publicationId, Long loanId) {
        super(message);
        this.publicationId = publicationId;
        this.loanId = loanId;
    }

    public Long getPublicationId() {
        return publicationId;
    }

    public Long getLoanId() {
        return loanId;
    }
}
