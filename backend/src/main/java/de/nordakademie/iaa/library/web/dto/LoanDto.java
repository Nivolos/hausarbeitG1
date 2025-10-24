package de.nordakademie.iaa.library.web.dto;

import java.time.LocalDate;

public class LoanDto {

    private Long id;
    private Long publicationId;
    private Long borrowerId;
    private String borrowerName;
    private LocalDate issuedAt;
    private LocalDate dueAt;
    private LocalDate returnedAt;
    private boolean overdue;

    public LoanDto() {
        // Jackson
    }

    public LoanDto(
            Long id,
            Long publicationId,
            Long borrowerId,
            String borrowerName,
            LocalDate issuedAt,
            LocalDate dueAt,
            LocalDate returnedAt,
            boolean overdue
    ) {
        this.id = id;
        this.publicationId = publicationId;
        this.borrowerId = borrowerId;
        this.borrowerName = borrowerName;
        this.issuedAt = issuedAt;
        this.dueAt = dueAt;
        this.returnedAt = returnedAt;
        this.overdue = overdue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public LocalDate getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDate issuedAt) {
        this.issuedAt = issuedAt;
    }

    public LocalDate getDueAt() {
        return dueAt;
    }

    public void setDueAt(LocalDate dueAt) {
        this.dueAt = dueAt;
    }

    public LocalDate getReturnedAt() {
        return returnedAt;
    }

    public void setReturnedAt(LocalDate returnedAt) {
        this.returnedAt = returnedAt;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }
}
