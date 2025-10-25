package de.nordakademie.iaa.librarysystem.dao;

import de.nordakademie.iaa.librarysystem.dao.exception.ModelNotFoundException;
import de.nordakademie.iaa.librarysystem.dao.exception.PersistException;
import de.nordakademie.iaa.librarysystem.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDAO {
    /**
     * Load @{@link Author} by id.
     *
     * @param id id as primary for the user
     * @return optionally an user if found
     * @throws ModelNotFoundException if argument is illegal
     */
    Optional<Author> load(Long id) throws ModelNotFoundException;

    /**
     * Create a new author.
     *
     * @param author new unmanaged user
     * @throws PersistException if entity exists, argument is illegal or transaction is required
     */
    void create(Author author) throws PersistException;

    /**
     * Update an existing {@link Author}.
     *
     * @param author managed entity
     * @return updated managed user
     * @throws PersistException if argument is illegal or transaction is required
     */
    Author update(Author author) throws PersistException;

    /**
     * Delete an existing employ by id
     *
     * @param id primary key
     * @throws ModelNotFoundException if there is no user with this id
     */
    void delete(Long id) throws ModelNotFoundException;

    /**
     * List all {@link Author}s and return null if no authors are found
     *
     * @return a list of persisted users or an empty list
     */
    List<Author> findAll();
}