package de.nordakademie.iaa.librarysystem.model;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.List;
import java.util.Objects;
/**
 * Die Klasse Publication repräsentiert eine Publikation und enthält alle zugehörigen Exemplarvariablen.
 * @author Lena Bunge, Felix Groer
 * @version 1.0
 */
@Entity
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NaturalId(mutable = true)
    @Column(nullable = false)
    private String internalId;

    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(nullable = false)
    private PublicationType publicationType;

    @Column(nullable = false)
    private String title;

    @Min(0)
    private Integer availableStock;

    @Min(0)
    private Integer lostAmount;

    @Temporal(TemporalType.DATE)
    private Date publicationDate;

    private String publisher;

    @Min(0)
    private Integer totalStock;

    private String isbn;

    @ManyToMany(cascade = CascadeType.MERGE)
    private List<Keyword> keywords;

    @ManyToMany(cascade = CascadeType.MERGE)
    private List<Author> author;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    public PublicationType getPublicationType() {
        return publicationType;
    }

    public void setPublicationType(PublicationType publicationType) {
        this.publicationType = publicationType;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(Integer totalStock) {
        this.totalStock = totalStock;
    }

    public Integer getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(Integer availableAmount) {
        this.availableStock = availableAmount;
    }

    public Integer getLostAmount() {
        return lostAmount;
    }

    public void setLostAmount(Integer lostAmount) {
        this.lostAmount = lostAmount;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public List<Author> getAuthor() {
        return author;
    }

    public void setAuthor(List<Author> author) {
        this.author = author;
    }

}