package de.nordakademie.iaa.librarysystem.service;

import de.nordakademie.iaa.librarysystem.model.LendingProcess;
import de.nordakademie.iaa.librarysystem.model.LibraryUser;
import de.nordakademie.iaa.librarysystem.model.Publication;
import de.nordakademie.iaa.librarysystem.service.exception.*;
import de.nordakademie.iaa.librarysystem.service.exception.lendingprocess.ExtendPublicationNotPossible;
import de.nordakademie.iaa.librarysystem.service.exception.lendingprocess.LostPublicationNotPossibleException;
import de.nordakademie.iaa.librarysystem.service.exception.lendingprocess.RemindPublicationReturnNotPossible;
import de.nordakademie.iaa.librarysystem.service.exception.lendingprocess.ReturnNotPossibleException;
import org.h2.engine.User;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface LendingProcessService {
    /**
     * Set lending date and planned return date of the lending process according to system
     * parameter lending period and create it.
     *
     * @param lendingProcess the lending process to be created at today's date
     * @throws ServiceException when the lendingProcess is already persistent
     * @throws PublicationNotAvailableException when the available stock of the publication is below 1
     */
    void lendPublication(LendingProcess lendingProcess)
            throws ServiceException, PublicationNotAvailableException;

    /**
     * Set actual return date of lending process to today, is returned flag to true
     * and increase publication's available stock by 1.
     *
     * @param lendingProcess The lending process to be finalized
     * @throws ReturnNotPossibleException when the lending process can not be returned
     */
    void returnPublication(@NotNull LendingProcess lendingProcess) throws ReturnNotPossibleException;

    /**
     * Remind libraryUser to return publication for overdue lending process
     *
     * @param lendingProcess The lending process for which a reminder is created
     * @throws RemindPublicationReturnNotPossible when the reminder cannot be created
     */
    void remindPublicationReturn(LendingProcess lendingProcess) throws RemindPublicationReturnNotPossible;

    /**
     * Extend publication by system parameter extension period
     *
     * @param lendingProcess The lending process which is extended
     * @throws ExtendPublicationNotPossible when the lending process cannot be extended,
     * e.g. if extended 2 times already
     */
    void extendPublication(LendingProcess lendingProcess) throws ExtendPublicationNotPossible;

    /**
     * Mark publication as lost
     * Pre-condition: 3 reminders sent
     *
     * @param lendingProcess The lending process for which a publication is marked as lost
     * @throws LostPublicationNotPossibleException when the lending process cannot be marked as lost,
     * e.g. if less than 3 reminders are sent such that pre-condition is not met
     */
    void lostPublication(LendingProcess lendingProcess) throws LostPublicationNotPossibleException;

    /**
     * Loads the lendingProcess with the given id from the database.
     *
     * @param id The id of the lendingProcess to be read
     * @return the lendingProcess with the given id
     * @throws ServiceException in case the given id does not exist
     */
    LendingProcess readLendingProcess(Long id) throws ServiceException;

    /**
     * Update an existing lendingProcess.
     *
     * @param lendingProcess that should be updated
     * @return the updated lendingProcess
     * @throws ServiceException when the lendingProcess is in a transient state
     */
    LendingProcess updateLendingProcess(LendingProcess lendingProcess) throws ServiceException;

    /**
     * Delete an {@link LendingProcess}.
     *
     * @param id primary id
     * @throws ServiceException if there is no lendingProcess with the given id
     */
    void deleteLendingProcess(Long id) throws ServiceException;

    /**
     * Load all lendingProcesss
     *
     * @return a list of lendingProcesses. Empty if none exists
     */
    List<LendingProcess> findAllLendingProcesses();

    /**
     * Load ongoing lendingProcess selected by Publication
     *
     * @return a list of lendingProcesses where publication matches. Empty if none exists
     */
    List<LendingProcess> findCurrentLendingProcessesByPublication(Publication publication);

    /**
     * Load all lending processes by specified search criteria
     * @param searchLendingProcess with specified attributes
     * @param includeOutstandingOverdueReminderOnly as a Boolean flag
     *
     * @return a list of lendingProcesses where criteria matches or an empty list if none match
     */
    List<LendingProcess> searchLendingProcess(LendingProcess searchLendingProcess,
                                              Boolean includeOutstandingOverdueReminderOnly);

    /**
     * Find all LendingProcess by libraryUser
     * @param libraryUserId the id of the libraryUser
     * @return a list of LendingProcess or else null
     */
    List<LendingProcess> findByLibraryUser(Long libraryUserId);

    /**
     * send an email to the customer with an overdrawn borrowing
     * @param
     */
    void sendReminderEmail(LendingProcess lendingProcess);

    /**
     * send an email to the customer when the lendingProcess
     * is initialized
     * @param
     */
    void sendStartLendEmail(LendingProcess lendingProcess);

    /**
     * send an email to the customer when the lendingProcess
     * publication is returned
     * @param
     */
    void sendReturnEmail(LendingProcess lendingProcess);

    /**
     * send an email to the customer when the lendingProcess
     * publication is extended
     * @param
     */
    void sendExtendEmail(LendingProcess lendingProcess);

    /**
     * send an email to the customer when the lendingProcess
     * publication is lost
     * @param
     */
    void sendLostEmail(LendingProcess lendingProcess);
}