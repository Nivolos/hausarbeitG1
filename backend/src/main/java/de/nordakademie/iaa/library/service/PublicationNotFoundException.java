package de.nordakademie.iaa.library.service;

public class PublicationNotFoundException extends RuntimeException {

    private final Long publicationId;

    public PublicationNotFoundException(Long publicationId) {
        super("Publication " + publicationId + " not found");
        this.publicationId = publicationId;
    }

    public Long getPublicationId() {
        return publicationId;
    }
}
