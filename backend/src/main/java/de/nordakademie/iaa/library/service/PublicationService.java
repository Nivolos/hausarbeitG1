package de.nordakademie.iaa.library.service;

import de.nordakademie.iaa.library.domain.Publication;
import de.nordakademie.iaa.library.repository.PublicationRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PublicationService {

    private final PublicationRepository publicationRepository;

    public PublicationService(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
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
        publicationRepository.deleteById(id);
    }
}
