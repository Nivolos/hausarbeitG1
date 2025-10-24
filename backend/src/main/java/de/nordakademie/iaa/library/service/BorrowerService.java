package de.nordakademie.iaa.library.service;

import de.nordakademie.iaa.library.domain.Borrower;
import de.nordakademie.iaa.library.repository.BorrowerRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BorrowerService {

    private final BorrowerRepository borrowerRepository;

    public BorrowerService(BorrowerRepository borrowerRepository) {
        this.borrowerRepository = borrowerRepository;
    }

    public List<Borrower> findAll() {
        return borrowerRepository.findAll();
    }
}
