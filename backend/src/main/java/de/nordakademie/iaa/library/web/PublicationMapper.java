package de.nordakademie.iaa.library.web;

import de.nordakademie.iaa.library.domain.Publication;
import de.nordakademie.iaa.library.web.dto.PublicationDto;

public final class PublicationMapper {

    private PublicationMapper() {
    }

    public static PublicationDto toDto(Publication publication) {
        return toDto(publication, 0L);
    }

    public static PublicationDto toDto(Publication publication, long loanCount) {
        return new PublicationDto(
                publication.getId(),
                publication.getTitle(),
                publication.getAuthors(),
                publication.getPublisher(),
                publication.getStock(),
                loanCount
        );
    }

    public static Publication toEntity(PublicationDto dto) {
        Publication publication = new Publication();
        publication.setId(dto.getId());
        publication.setTitle(dto.getTitle());
        publication.setAuthors(dto.getAuthors());
        publication.setPublisher(dto.getPublisher());
        publication.setStock(dto.getStock());
        return publication;
    }
}
