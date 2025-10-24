package de.nordakademie.iaa.librarysystem.service;

import de.nordakademie.iaa.librarysystem.model.Author;
import de.nordakademie.iaa.librarysystem.model.LibraryUser;
import de.nordakademie.iaa.librarysystem.model.Role;
import de.nordakademie.iaa.librarysystem.service.exception.ServiceException;

import java.util.List;

public interface RoleService {

    /**
     * Load a role by name
     *
     * @return a role. Empty if none exists
     */
    Role findRolesByName(String roleName);

    /**
     * Load all roles
     *
     * @return a list of roles. Empty if no one exists
     */
    List<Role> findAllRoles();

    /**
     * Loads the role with the given id from the database.
     *
     * @param id The id to get the Role for
     * @return the Role with the given id
     * @throws ServiceException in case the given id does not exist
     */
    Role loadRole(Long id) throws ServiceException;
}
