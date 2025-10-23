package de.nordakademie.iaa.librarysystem.dao;

import de.nordakademie.iaa.librarysystem.dao.exception.ModelNotFoundException;
import de.nordakademie.iaa.librarysystem.dao.exception.PersistException;
import de.nordakademie.iaa.librarysystem.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleDao {
    /**
     * Load @{@link Role} by id.
     *
     * @param id id as primary for the user
     * @return optionally an user if found
     * @throws ModelNotFoundException if argument is illegal
     */
    Optional<Role> load(Long id) throws ModelNotFoundException;

    /**
     * Create a new role.
     *
     * @param role new unmanaged user
     * @throws PersistException if entity exists, argument is illegal or transaction is required
     */
    void create(Role role) throws PersistException;

    /**
     * Update an existing {@link Role}.
     *
     * @param role managed entity
     * @return updated managed user
     * @throws PersistException if argument is illegal or transaction is required
     */
    Role update(Role role) throws PersistException;

    /**
     * Delete an existing employ by id
     *
     * @param id primary key
     * @throws ModelNotFoundException if there is no user with this id
     */
    void delete(Long id) throws ModelNotFoundException;

    /**
     * List all {@link Role}s and return null if no roles are found
     *
     * @return a list of persisted users or an empty list
     */
    List<Role> findAll();

    /**
     * List all {@link Role}s and return null if no roles are found
     *
     * @return a list of search result persisted users or an empty list
     */
    Optional<Role> findRoleByName(String username) throws ModelNotFoundException;

}