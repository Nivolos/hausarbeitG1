package de.nordakademie.iaa.library.web;

import de.nordakademie.iaa.library.domain.Loan;
import de.nordakademie.iaa.library.service.LoanService;
import de.nordakademie.iaa.library.web.dto.CreateLoanRequest;
import de.nordakademie.iaa.library.web.dto.LoanDto;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<LoanDto> borrow(@Valid @RequestBody CreateLoanRequest request) {
        Loan loan = loanService.borrow(request.getPublicationId(), request.getBorrowerId());
        LoanDto body = LoanMapper.toDto(loan);
        return ResponseEntity.created(URI.create("/api/loans/" + body.getId())).body(body);
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<LoanDto> returnLoan(@PathVariable Long id) {
        Loan loan = loanService.returnLoan(id);
        LoanDto body = LoanMapper.toDto(loan);
        return ResponseEntity.ok(body);
    }
}
