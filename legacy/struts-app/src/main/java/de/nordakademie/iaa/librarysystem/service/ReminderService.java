package de.nordakademie.iaa.librarysystem.service;

import de.nordakademie.iaa.librarysystem.model.Reminder;
import de.nordakademie.iaa.librarysystem.service.exception.ModelUsedException;
import de.nordakademie.iaa.librarysystem.service.exception.ServiceException;

import java.util.List;

public interface ReminderService {
    /**
     * Create the given instance of the reminder.
     *
     * @param reminder The reminder to be created
     * @throws ServiceException When the reminder is already persistent
     */
    void createReminder(Reminder reminder) throws ServiceException;

    /**
     * Loads the reminder with the given id from the database.
     *
     * @param id The id of the reminder to be read
     * @return the reminder with the given id
     * @throws ServiceException in case the given id does not exist
     */
    Reminder readReminder(Long id) throws ServiceException;

    /**
     * Update an existing reminder.
     *
     * @param reminder that should be updated
     * @return the updated reminder
     * @throws ServiceException When the reminder is in a transient state
     */
    Reminder updateReminder(Reminder reminder) throws ServiceException;

    /**
     * Find all reminders connected to lending process id
     *
     * @param lendingProcessId
     * @return a list of reminders. Empty if no one exists
     */
    List<Reminder> findAllRemindersByLendingProcessId(Long lendingProcessId);

    /**
     * Delete an {@link Reminder}.
     *
     * @param id primary id
     * @throws ServiceException if there is no reminder with the given id
     */
    void deleteReminder(Long id) throws ServiceException, ModelUsedException;

    /**
     * Load all reminders
     *
     * @return a list of reminders. Empty if no one exists
     */
    List<Reminder> findAllReminders();
}