package de.nordakademie.iaa.library.service;

import de.nordakademie.iaa.library.domain.Publication;
import de.nordakademie.iaa.library.repository.LoanRepository;
import de.nordakademie.iaa.library.repository.PublicationRepository;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PublicationService {

    private final PublicationRepository publicationRepository;
    private final LoanRepository loanRepository;

    public PublicationService(PublicationRepository publicationRepository, LoanRepository loanRepository) {
        this.publicationRepository = publicationRepository;
        this.loanRepository = loanRepository;
    }

    @Transactional(readOnly = true)
    public List<Publication> findAll() {
        return publicationRepository.findAll();
    }

    public Publication create(Publication publication) {
        publication.setId(null);
        return publicationRepository.save(publication);
    }

    public void delete(Long id) {
        if (loanRepository.existsByPublication_IdAndReturnedAtIsNull(id)) {
            throw new PublicationDeletionConflictException(id);
        }
        try {
            publicationRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new PublicationNotFoundException(id);
        } catch (DataIntegrityViolationException ex) {
            throw new PublicationDeletionConflictException(id);
        }
    }

    @Transactional(readOnly = true)
    public long countActiveLoansForPublication(Long publicationId) {
        if (publicationId == null) {
            return 0L;
        }
        return loanRepository.countByPublication_IdAndReturnedAtIsNull(publicationId);
    }

    @Transactional(readOnly = true)
    public Map<Long, Long> countActiveLoans(Collection<Long> publicationIds) {
        if (publicationIds == null || publicationIds.isEmpty()) {
            return Map.of();
        }
        return publicationIds.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(
                        id -> id,
                        loanRepository::countByPublication_IdAndReturnedAtIsNull,
                        (existing, replacement) -> replacement
                ));
    }
}
