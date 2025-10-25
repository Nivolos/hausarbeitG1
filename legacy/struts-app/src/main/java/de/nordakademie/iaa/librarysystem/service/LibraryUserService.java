package de.nordakademie.iaa.librarysystem.service;

import de.nordakademie.iaa.librarysystem.model.LibraryUser;
import de.nordakademie.iaa.librarysystem.model.Role;
import de.nordakademie.iaa.librarysystem.service.exception.ModelUsedException;
import de.nordakademie.iaa.librarysystem.service.exception.ServiceException;

import java.util.List;

public interface LibraryUserService {

    /**
     * Create the given instance of the libraryUser.
     *
     * @param libraryUser The libraryUser to create
     * @throws ServiceException When the libraryUser is already persistent
     */
    void createLibraryUser(LibraryUser libraryUser) throws ServiceException;

    /**
     * Loads the libraryUser with the given id from the database.
     *
     * @param id The id to get the LibraryUser for
     * @return the LibraryUser with the given id
     * @throws ServiceException in case the given id does not exist
     */
    LibraryUser loadLibraryUser(Long id) throws ServiceException;

    /**
     * Update an existing libraryUser.
     *
     * @param libraryUser that should be updated
     * @return the updated libraryUser
     * @throws ServiceException When the libraryUser is transient
     */
    LibraryUser updateLibraryUser(LibraryUser libraryUser) throws ServiceException;

    /**
     * Load all libraryUsers
     *
     * @return a list of libraryUsers. Empty if no one exists
     */
    List<LibraryUser> findAllLibraryUsers();

    /**
     * Delete an {@link LibraryUser}.
     *
     * @param id primary id
     * @throws ServiceException if there is no libraryUser with the given id
     */
    void deleteLibraryUser(Long id) throws ServiceException, ModelUsedException;

    /**
     * Load a libraryUsers by name
     *
     * @return an libraryUsers. Empty if no one exists
     */
    LibraryUser getUser(String userName) throws ServiceException;

    /**
     * Check if a libraryUsers is valid
     *
     * @return true or false
     */
    boolean isUserValid(String username, String password);

    /**
     * Load all Roles for a libraryUser
     *
     * @return a List of roles. Empty if no one exists
     */
    List<Role> lookupAllRolesForUser(String username);
}