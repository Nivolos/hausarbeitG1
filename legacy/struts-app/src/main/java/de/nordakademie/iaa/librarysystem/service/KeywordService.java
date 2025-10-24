package de.nordakademie.iaa.librarysystem.service;

import de.nordakademie.iaa.librarysystem.model.Keyword;
import de.nordakademie.iaa.librarysystem.service.exception.ModelUsedException;
import de.nordakademie.iaa.librarysystem.service.exception.ServiceException;

import java.util.List;

public interface KeywordService {
    /**
     * Create the given instance of the keyword.
     *
     * @param keyword The keyword to be created
     * @throws ServiceException When the keyword is already persistent
     */
    void createKeyword(Keyword keyword) throws ServiceException;

    /**
     * Loads the keyword with the given id from the database.
     *
     * @param id The id of the keyword to be read
     * @return the keyword with the given id
     * @throws ServiceException in case the given id does not exist
     */
    Keyword readKeyword(Long id) throws ServiceException;

    /**
     * Update an existing keyword.
     *
     * @param keyword that should be updated
     * @return the updated keyword
     * @throws ServiceException When the keyword is in a transient state
     */
    Keyword updateKeyword(Keyword keyword) throws ServiceException;

    /**
     * Delete an {@link Keyword}.
     *
     * @param id primary id
     * @throws ServiceException if there is no keyword with the given id
     */
    void deleteKeyword(Long id) throws ServiceException, ModelUsedException;

    /**
     * Load all keywords
     *
     * @return a list of keywords. Empty if no one exists
     */
    List<Keyword> findAllKeywords();
}