package de.nordakademie.iaa.library.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class PublicationDto {

    private Long id;

    @NotBlank
    private String title;

    private String authors;

    private String publisher;

    @Min(0)
    private int stock;

    @Min(0)
    private long loanCount;

    public PublicationDto() {
        // Jackson
    }

    public PublicationDto(Long id, String title, String authors, String publisher, int stock, long loanCount) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.stock = stock;
        this.loanCount = loanCount;
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

    public long getLoanCount() {
        return loanCount;
    }

    public void setLoanCount(long loanCount) {
        this.loanCount = loanCount;
    }
}
