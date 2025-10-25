package de.nordakademie.iaa.librarysystem.service;

import de.nordakademie.iaa.librarysystem.model.PublicationType;
import de.nordakademie.iaa.librarysystem.service.exception.ModelUsedException;
import de.nordakademie.iaa.librarysystem.service.exception.PublicationTypeNameExistsException;
import de.nordakademie.iaa.librarysystem.service.exception.ServiceException;

import java.util.List;

public interface PublicationTypeService {

    /**
     * Create the given instance of the publicationType.
     *
     * @param publicationType The publicationType to be created
     * @throws ServiceException When the publicationType is already persistent
     */
    void createPublicationType(PublicationType publicationType) throws PublicationTypeNameExistsException;

    /**
     * Loads the publicationType with the given id from the database.
     *
     * @param id The id of the publicationType to be read
     * @return the publicationType with the given id
     * @throws ServiceException in case the given id does not exist
     */
    PublicationType readPublicationType(Long id) throws ServiceException;

    /**
     * Update an existing publicationType.
     *
     * @param publicationType that should be updated
     * @return the updated publicationType
     * @throws ServiceException when the publication type is in a transient state
     * @throws PublicationTypeNameExistsException when a publication type with the same name as value already exists
     *         to avoid persisting duplicate publication types
     */
    PublicationType updatePublicationType(PublicationType publicationType) throws ServiceException;

    /**
     * Delete an {@link PublicationType}.
     *
     * @param id primary id
     * @throws ServiceException if there is no publicationType with the given id
     */
    void deletePublicationType(Long id) throws ServiceException, ModelUsedException;

    /**
     * Load all publicationTypes
     *
     * @return a list of publicationTypes. Empty if no one exists
     */
    List<PublicationType> findAllPublicationTypes();
}