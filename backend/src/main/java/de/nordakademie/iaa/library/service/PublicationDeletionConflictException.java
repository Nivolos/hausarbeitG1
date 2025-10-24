package de.nordakademie.iaa.library.service;

public class PublicationDeletionConflictException extends RuntimeException {

    private final Long publicationId;

    public PublicationDeletionConflictException(Long publicationId) {
        super("Publication " + publicationId + " cannot be deleted because active loans exist");
        this.publicationId = publicationId;
    }

    public Long getPublicationId() {
        return publicationId;
    }
}
