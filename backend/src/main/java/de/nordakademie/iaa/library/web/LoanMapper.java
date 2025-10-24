package de.nordakademie.iaa.library.web;

import de.nordakademie.iaa.library.domain.Loan;
import de.nordakademie.iaa.library.web.dto.LoanDto;
import java.time.LocalDate;

final class LoanMapper {

    private LoanMapper() {
    }

    static LoanDto toDto(Loan loan) {
        boolean overdue = loan.getReturnedAt() == null
                && loan.getDueAt() != null
                && LocalDate.now().isAfter(loan.getDueAt());
        String borrowerName = loan.getBorrower() == null
                ? null
                : loan.getBorrower().getFirstName() + " " + loan.getBorrower().getLastName();
        return new LoanDto(
                loan.getId(),
                loan.getPublication() != null ? loan.getPublication().getId() : null,
                loan.getBorrower() != null ? loan.getBorrower().getId() : null,
                borrowerName,
                loan.getIssuedAt(),
                loan.getDueAt(),
                loan.getReturnedAt(),
                overdue
        );
    }
}
