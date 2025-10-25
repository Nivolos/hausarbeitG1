package de.nordakademie.iaa.library.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class PublicationDto {

    private Long id;

    @NotBlank
    private String title;

    private String authors;

    private String publisher;

    @Min(0)
    private int stock;

    @Min(0)
    private long activeLoanCount;

    private List<LoanDto> activeLoans = new ArrayList<>();

    public PublicationDto() {
        // Jackson
    }

    public PublicationDto(
            Long id,
            String title,
            String authors,
            String publisher,
            int stock,
            long activeLoanCount,
            List<LoanDto> activeLoans
    ) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.stock = stock;
        this.activeLoanCount = activeLoanCount;
        if (activeLoans != null) {
            this.activeLoans = new ArrayList<>(activeLoans);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public long getActiveLoanCount() {
        return activeLoanCount;
    }

    public void setActiveLoanCount(long activeLoanCount) {
        this.activeLoanCount = activeLoanCount;
    }

    public List<LoanDto> getActiveLoans() {
        return activeLoans;
    }

    public void setActiveLoans(List<LoanDto> activeLoans) {
        this.activeLoans = activeLoans == null ? new ArrayList<>() : new ArrayList<>(activeLoans);
    }
}
