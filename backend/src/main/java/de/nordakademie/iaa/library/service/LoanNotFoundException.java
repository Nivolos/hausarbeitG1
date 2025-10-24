package de.nordakademie.iaa.library.service;

public class LoanNotFoundException extends RuntimeException {

    private final Long loanId;

    public LoanNotFoundException(Long loanId) {
        super("Loan not found: " + loanId);
        this.loanId = loanId;
    }

    public Long getLoanId() {
        return loanId;
    }
}
