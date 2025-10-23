package de.nordakademie.iaa.library.repository;

import de.nordakademie.iaa.library.domain.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicationRepository extends JpaRepository<Publication, Long> {
}
