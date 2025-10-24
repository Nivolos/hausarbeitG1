package de.nordakademie.iaa.library.web.dto;

import jakarta.validation.constraints.NotNull;

public class CreateLoanRequest {

    @NotNull
    private Long publicationId;

    @NotNull
    private Long borrowerId;

    public CreateLoanRequest() {
        // Jackson
    }

    public Long getPublicationId() {
        return publicationId;
    }

    public void setPublicationId(Long publicationId) {
        this.publicationId = publicationId;
    }

    public Long getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }
}
