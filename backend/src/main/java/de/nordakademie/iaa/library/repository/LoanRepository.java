package de.nordakademie.iaa.library.repository;

import de.nordakademie.iaa.library.domain.Loan;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    boolean existsByPublication_IdAndReturnedAtIsNull(Long publicationId);

    long countByPublication_IdAndReturnedAtIsNull(Long publicationId);

    List<Loan> findByPublication_IdInAndReturnedAtIsNull(Collection<Long> publicationIds);
}
