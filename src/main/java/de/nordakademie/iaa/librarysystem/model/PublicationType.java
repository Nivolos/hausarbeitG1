package de.nordakademie.iaa.librarysystem.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;
/**
 * Die Klasse PublicationType repräsentiert eine Publikationsart und enthält alle zugehörigen Exemplarvariablen.
 * Es wird verhindert, dass eine Publikationsart doppelt angelegt werden kann.
 * @author Lena Bunge, Felix Groer
 * @version 1.0
 */
@Entity
public class PublicationType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String publicationTypeNameDe;

    private String publicationTypeNameEn;

    private Boolean isAuthorRequired;

    private Boolean isPublicationDateRequired;

    private Boolean isPublisherRequired;

    private Boolean isIsbnRequired;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublicationTypeNameDe() {
        return publicationTypeNameDe;
    }

    public void setPublicationTypeNameDe(String publicationTypeNameDe) {
        this.publicationTypeNameDe = publicationTypeNameDe;
    }

    public String getPublicationTypeNameEn() {
        return publicationTypeNameEn;
    }

    public void setPublicationTypeNameEn(String publicationTypeNameEn) {
        this.publicationTypeNameEn = publicationTypeNameEn;
    }

    public Boolean getAuthorRequired() {
        return isAuthorRequired;
    }

    public void setAuthorRequired(Boolean authorRequired) {
        isAuthorRequired = authorRequired;
    }

    public Boolean getPublicationDateRequired() {
        return isPublicationDateRequired;
    }

    public void setPublicationDateRequired(Boolean publicationDateRequired) {
        isPublicationDateRequired = publicationDateRequired;
    }

    public Boolean getPublisherRequired() {
        return isPublisherRequired;
    }

    public void setPublisherRequired(Boolean publisherRequired) {
        isPublisherRequired = publisherRequired;
    }

    public Boolean getIsbnRequired() {
        return isIsbnRequired;
    }

    public void setIsbnRequired(Boolean isbnRequired) {
        isIsbnRequired = isbnRequired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PublicationType that = (PublicationType) o;
        return Objects.equals(getPublicationTypeNameDe(), that.getPublicationTypeNameDe()) && Objects.equals(getPublicationTypeNameEn(), that.getPublicationTypeNameEn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPublicationTypeNameDe(), getPublicationTypeNameEn());
    }
}