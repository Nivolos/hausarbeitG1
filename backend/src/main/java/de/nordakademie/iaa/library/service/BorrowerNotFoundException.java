package de.nordakademie.iaa.library.service;

public class BorrowerNotFoundException extends RuntimeException {

    private final Long borrowerId;

    public BorrowerNotFoundException(Long borrowerId) {
        super("Borrower not found: " + borrowerId);
        this.borrowerId = borrowerId;
    }

    public Long getBorrowerId() {
        return borrowerId;
    }
}
