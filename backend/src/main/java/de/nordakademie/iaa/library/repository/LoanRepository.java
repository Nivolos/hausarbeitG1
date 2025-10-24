package de.nordakademie.iaa.library.repository;

import de.nordakademie.iaa.library.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    boolean existsByPublication_IdAndReturnedAtIsNull(Long publicationId);

    long countByPublication_IdAndReturnedAtIsNull(Long publicationId);
}
