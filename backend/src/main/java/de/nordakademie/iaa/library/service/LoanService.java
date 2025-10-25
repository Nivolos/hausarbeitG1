package de.nordakademie.iaa.library.service;

import de.nordakademie.iaa.library.domain.Borrower;
import de.nordakademie.iaa.library.domain.Loan;
import de.nordakademie.iaa.library.domain.Publication;
import de.nordakademie.iaa.library.repository.BorrowerRepository;
import de.nordakademie.iaa.library.repository.LoanRepository;
import de.nordakademie.iaa.library.repository.PublicationRepository;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoanService {

    private final LoanRepository loanRepository;
    private final PublicationRepository publicationRepository;
    private final BorrowerRepository borrowerRepository;
    private final int loanPeriodDays;

    public LoanService(
            LoanRepository loanRepository,
            PublicationRepository publicationRepository,
            BorrowerRepository borrowerRepository,
            @Value("${loan.period.days:14}") int loanPeriodDays
    ) {
        this.loanRepository = loanRepository;
        this.publicationRepository = publicationRepository;
        this.borrowerRepository = borrowerRepository;
        this.loanPeriodDays = loanPeriodDays;
    }

    public Loan borrow(Long publicationId, Long borrowerId) {
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new PublicationNotFoundException(publicationId));
        Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> new BorrowerNotFoundException(borrowerId));

        long activeLoans = loanRepository.countByPublication_IdAndReturnedAtIsNull(publicationId);
        if (activeLoans >= publication.getStock()) {
            throw new LoanConflictException("Kein Bestand verfügbar", publicationId, null);
        }

        LocalDate today = LocalDate.now();

        Loan loan = new Loan();
        loan.setPublication(publication);
        loan.setBorrower(borrower);
        loan.setIssuedAt(today);
        loan.setDueAt(today.plusDays(loanPeriodDays));
        loan.setReturnedAt(null);
        return loanRepository.save(loan);
    }

    public Loan returnLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException(loanId));
        if (loan.getReturnedAt() != null) {
            throw new LoanConflictException("Ausleihe wurde bereits zurückgegeben", loan.getPublication().getId(), loanId);
        }
        loan.setReturnedAt(LocalDate.now());
        return loanRepository.save(loan);
    }
}
