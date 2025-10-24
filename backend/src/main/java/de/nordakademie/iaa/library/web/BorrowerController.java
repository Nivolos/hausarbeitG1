package de.nordakademie.iaa.library.web;

import de.nordakademie.iaa.library.domain.Borrower;
import de.nordakademie.iaa.library.service.BorrowerService;
import de.nordakademie.iaa.library.web.dto.BorrowerDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/borrowers")
public class BorrowerController {

    private final BorrowerService borrowerService;

    public BorrowerController(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    @GetMapping
    public List<BorrowerDto> getAll() {
        return borrowerService.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private BorrowerDto toDto(Borrower borrower) {
        return new BorrowerDto(
                borrower.getId(),
                borrower.getFirstName(),
                borrower.getLastName(),
                borrower.getEmail()
        );
    }
}
