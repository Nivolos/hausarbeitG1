package de.nordakademie.iaa.library.repository;

import de.nordakademie.iaa.library.domain.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
}
