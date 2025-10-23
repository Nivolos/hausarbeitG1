package de.nordakademie.iaa.library.web;

import de.nordakademie.iaa.library.domain.Publication;
import de.nordakademie.iaa.library.service.PublicationService;
import de.nordakademie.iaa.library.web.dto.PublicationDto;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/publications")
public class PublicationController {

    private final PublicationService publicationService;

    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @GetMapping
    public List<PublicationDto> getAll() {
        return publicationService.findAll().stream()
                .map(PublicationMapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<PublicationDto> create(@Valid @RequestBody PublicationDto publicationDto) {
        Publication toCreate = PublicationMapper.toEntity(publicationDto);
        Publication created = publicationService.create(toCreate);
        PublicationDto body = PublicationMapper.toDto(created);
        return ResponseEntity
                .created(URI.create("/api/publications/" + body.getId()))
                .body(body);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        publicationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
