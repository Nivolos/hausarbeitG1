package de.nordakademie.iaa.librarysystem.dao;

import de.nordakademie.iaa.librarysystem.dao.exception.ModelNotFoundException;
import de.nordakademie.iaa.librarysystem.dao.exception.PersistException;
import de.nordakademie.iaa.librarysystem.model.Author;
import de.nordakademie.iaa.librarysystem.model.Publication;

import java.util.List;
import java.util.Optional;

public interface PublicationDAO {
    /**
     * Load @{@link Publication} by id.
     *
     * @param id id as primary for the publication
     * @return optionally an publication if found
     * @throws ModelNotFoundException if argument is illegal
     */
    Optional<Publication> load(Long id) throws ModelNotFoundException;

    /**
     * Create a new publication.
     *
     * @param publication new unmanaged publication
     * @throws PersistException if entity exists, argument is illegal or transaction is required
     */
    void create(Publication publication) throws PersistException;

    /**
     * Update an existing {@link Publication}.
     *
     * @param publication managed entity
     * @return updated managed publication
     * @throws PersistException if argument is illegal or transaction is required
     */
    Publication update(Publication publication) throws PersistException;

    /**
     * Delete an existing publication by id
     *
     * @param id primary key
     * @throws ModelNotFoundException if there is no publication with this id
     */
    void delete(Long id) throws ModelNotFoundException;

    /**
     * List all {@link Publication}
     *
     * @return a list of persisted publications or an empty list
     */
    List<Publication> findAll();

    /**
     * Search an existing publication by publication
     *
     * @param publication
     * @return a list of publications or an empty list
     */
    List<Publication> search(Publication publication, List<Long> keywordIdList,
                             List<Long> publicationTypeIdList, List<Long> authorTypeIdList);

    /**
     * Find all available publications
     * @return a list of available publications or an empty list
     */
    List<Publication> findAvailable();

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