package de.nordakademie.iaa.librarysystem.service;

import de.nordakademie.iaa.librarysystem.model.Publication;
import de.nordakademie.iaa.librarysystem.service.exception.ServiceException;

import java.util.List;

public interface PublicationService {

    /**
     * Create the given instance of the publication.
     *
     * @param publication The publication to be created
     * @throws ServiceException When the publication is already persistent
     */
    void createPublication(Publication publication) throws ServiceException;

    /**
     * Loads the publication with the given id from the database.
     *
     * @param id The id of the publication to be read
     * @return the publication with the given id
     * @throws ServiceException in case the given id does not exist
     */
    Publication readPublication(Long id) throws ServiceException;

    /**
     * Update an existing publication.
     *
     * @param publication that should be updated
     * @return the updated publication
     * @throws ServiceException When the publication is in a transient state
     */
    Publication updatePublication(Publication publication) throws ServiceException;

    /**
     * Delete an {@link Publication}.
     *
     * @param id primary id
     * @throws ServiceException if there is no publication with the given id
     */
    void deletePublication(Long id) throws ServiceException;

    /**
     * Load all publications
     *
     * @return a list of publications. Empty if no one exists
     */
    List<Publication> findAllPublications();

    /**
     * Search over all publications
     *
     * @return a list of publications. Empty list if no search results
     */
    List<Publication> searchPublication(Publication publication, List<Long> keywordIdList,
                                        List<Long> publicationTypeIdList, List<Long> authorIdList);

    /**
     * Finds all available publications
     * @return a list of publications with available stock greater than or equal to 1
     */
    List<Publication> findAvailablePublications();

    /**
     * Find all publications by author
     * @param authorId the id of the author
     * @return a list of publications or else null
     */
    List<Publication> findByAuthor(Long authorId);

    /**
     * Find all publications by keyword
     * @param keywordId the id of the keyword
     * @return a list of publications or else null
     */
    List<Publication> findByKeyword(Long keywordId);

    /**
     * Find all publications by publicationType
     * @param publicationTypeId the id of the publicationType
     * @return a list of publications or else null
     */
    List<Publication> findByPublicationType(Long publicationTypeId);
}