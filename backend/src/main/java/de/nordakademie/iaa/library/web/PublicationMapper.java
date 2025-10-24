package de.nordakademie.iaa.library.web;

import de.nordakademie.iaa.library.domain.Loan;
import de.nordakademie.iaa.library.domain.Publication;
import de.nordakademie.iaa.library.web.dto.LoanDto;
import de.nordakademie.iaa.library.web.dto.PublicationDto;
import java.util.List;
import java.util.stream.Collectors;

public final class PublicationMapper {

    private PublicationMapper() {
    }

    public static PublicationDto toDto(Publication publication) {
        return toDto(publication, 0L, List.of());
    }

    public static PublicationDto toDto(Publication publication, long activeLoanCount, List<Loan> activeLoans) {
        List<LoanDto> loanDtos = activeLoans == null
                ? List.of()
                : activeLoans.stream().map(LoanMapper::toDto).collect(Collectors.toList());
        return new PublicationDto(
                publication.getId(),
                publication.getTitle(),
                publication.getAuthors(),
                publication.getPublisher(),
                publication.getStock(),
                activeLoanCount,
                loanDtos
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
