package de.nordakademie.iaa.librarysystem.dao;

import de.nordakademie.iaa.librarysystem.dao.exception.ModelNotFoundException;
import de.nordakademie.iaa.librarysystem.dao.exception.PersistException;
import de.nordakademie.iaa.librarysystem.model.LibraryUser;
import de.nordakademie.iaa.librarysystem.model.LendingProcess;
import de.nordakademie.iaa.librarysystem.model.Publication;

import java.util.List;
import java.util.Optional;

public interface LendingProcessDAO {
    /**
     * Load @{@link LendingProcess} by id.
     *
     * @param id id as primary for the lendingProcess
     * @return optionally an lendingProcess if found
     * @throws ModelNotFoundException if argument is illegal
     */
    Optional<LendingProcess> load(Long id) throws ModelNotFoundException;

    /**
     * Create a new lending process.
     *
     * @param lendingProcess consisting of 1 libraryUser lending 1 publication at current datetime timestamp
     * @throws PersistException if entity exists, argument is illegal or transaction is required
     */
    void create(LendingProcess lendingProcess) throws PersistException;

    /**
     * Update an existing {@link LendingProcess}.
     *
     * @param lendingProcess managed entity
     * @return updated managed lendingProcess
     * @throws PersistException if entity exists, argument is illegal or transaction is required
     */
    LendingProcess update(LendingProcess lendingProcess) throws PersistException;

    /**
     * Delete an existing lendingProcess by id
     *
     * @param id primary key
     * @throws ModelNotFoundException if there is no lending process with this id
     */
    void delete(Long id) throws ModelNotFoundException;

    /**
     * List all {@link LendingProcess}
     *
     * @return a list of persisted lending processes or an empty list
     */
    List<LendingProcess> findAll();

    /**
     * List all {@link LendingProcess}
     *
     * @return a list of all ongoing lending processes of a libraryUser or an empty list
     */
    List<LendingProcess> findCurrentByLibraryUser(LibraryUser libraryUser);

    /**
     * List all {@link LendingProcess}
     *
     * @return a list of all historic lending processes of a libraryUser or an empty list
     */
    List<LendingProcess> findHistoryByLibraryUser(LibraryUser libraryUser);

    /**
     * List all {@link LendingProcess}
     *
     * @return a list of ongoing lending processes of a publication or an empty list
     */
    List<LendingProcess> findCurrentByPublication(Publication publication);

    /**
     * List all {@link LendingProcess}
     *
     * @return a list of all overdue lending processes, where the publication is not returned yet or an empty list
     */
    List<LendingProcess> findAllOverdue();

    /**
     * List all {@link LendingProcess}es with specified criteria
     * @param lendingProcess
     * @param title
     * @param firstName
     * @param lastName
     * @return a list of searched lending processes or an empty list
     */
    List<LendingProcess> search(LendingProcess lendingProcess, String title, String firstName, String lastName);

    /**
     * Find all LendingProcess by libraryUser
     * @param libraryUserId the id of the libraryUser
     * @return a list of LendingProcess or else null
     */
    List<LendingProcess> findByLibraryUser(Long libraryUserId);
}