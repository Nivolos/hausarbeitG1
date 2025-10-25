package de.nordakademie.iaa.librarysystem.service;

import de.nordakademie.iaa.librarysystem.model.Author;
import de.nordakademie.iaa.librarysystem.service.exception.ModelUsedException;
import de.nordakademie.iaa.librarysystem.service.exception.ServiceException;

import java.util.List;

public interface AuthorService {
    /**
     * Create the given instance of the author.
     *
     * @param author The author to create
     * @throws ServiceException When the author is already persistent
     */
    void createAuthor(Author author) throws ServiceException;

    /**
     * Loads the author with the given id from the database.
     *
     * @param id The id to get the Author for
     * @return the Author with the given id
     * @throws ServiceException in case the given id does not exist
     */
    Author loadAuthor(Long id) throws ServiceException;

    /**
     * Update an existing author.
     *
     * @param author that should be updated
     * @return the updated author
     * @throws ServiceException When the author is transient
     */
    Author updateAuthor(Author author) throws ServiceException;

    /**
     * Load all authors
     *
     * @return a list of authors. Empty if no one exists
     */
    List<Author> findAllAuthors();

    /**
     * Delete an {@link Author}.
     *
     * @param id primary id
     * @throws ServiceException if there is no author with the given id
     * @throws ModelUsedException if author is still in use for a publication
     */
    void deleteAuthor(Long id) throws ServiceException, ModelUsedException;
}