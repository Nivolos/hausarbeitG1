package de.nordakademie.iaa.librarysystem.dao;

import de.nordakademie.iaa.librarysystem.dao.exception.ModelNotFoundException;
import de.nordakademie.iaa.librarysystem.dao.exception.PersistException;
import de.nordakademie.iaa.librarysystem.model.LibraryUser;

import java.util.List;
import java.util.Optional;

public interface LibraryUserDAO {
    /**
     * Load @{@link LibraryUser} by id.
     *
     * @param id id as primary for the libraryUser
     * @return optionally an libraryUser if found
     * @throws ModelNotFoundException if argument is illegal
     */
    Optional<LibraryUser> load(Long id) throws ModelNotFoundException;

    /**
     * Create a new libraryUser.
     *
     * @param libraryUser new unmanaged libraryUser
     * @throws PersistException if entity exists, argument is illegal or transaction is required
     */
    void create(LibraryUser libraryUser) throws PersistException;

    /**
     * Update an existing {@link LibraryUser}.
     *
     * @param libraryUser managed entity
     * @return updated managed libraryUser
     * @throws PersistException if entity exists, argument is illegal or transaction is required
     */
    LibraryUser update(LibraryUser libraryUser) throws PersistException;

    /**
     * Delete an existing employ by id
     *
     * @param id primary key
     * @throws ModelNotFoundException if there is no libraryUser with this id
     * @throws ModelNotFoundException if there is no libraryUser with this id
     */
    void delete(Long id);

    /**
     * List all {@link LibraryUser}s
     *
     * @return a list of persisted libraryUsers or an empty list if none exist
     */
    List<LibraryUser> findAll();

    /**
     * Load @{@link LibraryUser} by id.
     *
     * @param userName id as primary for the libraryUser
     * @return optionally a libraryUser if found
     * @throws ModelNotFoundException if argument is illegal
     */
    Optional<LibraryUser> loadByUserName(String userName) throws ModelNotFoundException;
}