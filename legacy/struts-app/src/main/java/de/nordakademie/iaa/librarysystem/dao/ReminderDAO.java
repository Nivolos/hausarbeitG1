package de.nordakademie.iaa.librarysystem.dao;

import de.nordakademie.iaa.librarysystem.dao.exception.ModelNotFoundException;
import de.nordakademie.iaa.librarysystem.dao.exception.PersistException;
import de.nordakademie.iaa.librarysystem.model.LendingProcess;
import de.nordakademie.iaa.librarysystem.model.Reminder;

import java.util.List;
import java.util.Optional;


public interface ReminderDAO {
    /**
     * Load @{@link Reminder} by id.
     *
     * @param id id as primary for the reminder
     * @return optionally a reminder if found
     * @throws ModelNotFoundException if argument is illegal
     */
    Optional<Reminder> load(Long id) throws ModelNotFoundException;

    /**
     * Create a new reminder.
     *
     * @param reminder new unmanaged reminder
     * @throws PersistException if entity exists, argument is illegal or transaction is required
     */
    void create(Reminder reminder) throws PersistException;

    /**
     * Update an existing {@link Reminder}.
     *
     * @param reminder managed entity
     * @return updated managed reminder
     * @throws PersistException if entity exists, argument is illegal or transaction is required
     */
    Reminder update(Reminder reminder) throws PersistException;

    /**
     * Delete an existing reminder by id
     *
     * @param id primary key
     * @throws ModelNotFoundException if there is no reminder with this id
     */
    void delete(Long id) throws ModelNotFoundException;

    /**
     * List all {@link Reminder}s
     *
     * @return a list of persisted reminders or an empty list if none exist
     */
    List<Reminder> findAll();

    /**
     * List all {@link Reminder}s with the specified lending process id linked
     * @param lendingProcessId to filter for
     * @return a list of persisted reminders fitting to filter criteria or an empty list if none exist
     */
    List<Reminder> findAllRemindersByLendingProcessId(Long lendingProcessId);

    /**
     * List all {@link Reminder}s with the specified lending process id linked
     * @param lendingProcesses to filter for as a list
     * @return a list of persisted reminders fitting to any of the given lending processes or an empty list if none fit
     */
    List<Reminder> findAllRemindersByLendingProcesses(List<LendingProcess> lendingProcesses);
}