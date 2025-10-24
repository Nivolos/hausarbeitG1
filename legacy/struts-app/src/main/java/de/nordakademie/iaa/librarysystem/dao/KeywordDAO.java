package de.nordakademie.iaa.librarysystem.dao;

import de.nordakademie.iaa.librarysystem.dao.exception.ModelNotFoundException;
import de.nordakademie.iaa.librarysystem.dao.exception.PersistException;
import de.nordakademie.iaa.librarysystem.model.Keyword;

import java.util.List;
import java.util.Optional;

public interface KeywordDAO {
    /**
     * Load @{@link Keyword} by id.
     *
     * @param id id as primary for the keyword
     * @return optionally a keyword if found
     * @throws ModelNotFoundException if argument is illegal
     */
    Optional<Keyword> load(Long id) throws ModelNotFoundException;

    /**
     * Create a new keyword.
     *
     * @param keyword new unmanaged keyword
     * @throws PersistException if entity exists, argument is illegal or transaction is required
     */
    void create(Keyword keyword) throws PersistException;

    /**
     * Update an existing {@link Keyword}.
     *
     * @param keyword managed entity
     * @return updated managed keyword
     * @throws PersistException if entity exists, argument is illegal or transaction is required
     */
    Keyword update(Keyword keyword) throws PersistException;

    /**
     * Delete an existing keyword by id
     *
     * @param id primary key
     * @throws ModelNotFoundException if there is no keyword with this id
     */
    void delete(Long id) throws ModelNotFoundException;

    /**
     * List all {@link Keyword}s
     *
     * @return a list of persisted keywords or an empty list if none exist
     */
    List<Keyword> findAll();
}