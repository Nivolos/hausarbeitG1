package de.nordakademie.iaa.librarysystem.service;

import de.nordakademie.iaa.librarysystem.dao.ReminderDAO;
import de.nordakademie.iaa.librarysystem.dao.PublicationDAO;
import de.nordakademie.iaa.librarysystem.dao.exception.ModelNotFoundException;
import de.nordakademie.iaa.librarysystem.dao.exception.PersistException;
import de.nordakademie.iaa.librarysystem.model.LendingProcess;
import de.nordakademie.iaa.librarysystem.model.Reminder;
import de.nordakademie.iaa.librarysystem.model.Publication;
import de.nordakademie.iaa.librarysystem.service.exception.ModelUsedException;
import de.nordakademie.iaa.librarysystem.service.exception.ServiceException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
/**
 * Die Klasse ReminderServiceImpl ist der Kommunikator zwischen der ReminderAction und der ReminderDao.
 * Sie ermöglicht die CRUD Operationen der Reminder und das Finden von Remindern.
 * @author Fabian Rudof, Jasmin Elvers
 * @version 1.0
 */
@Service
public class ReminderServiceImpl implements ReminderService  {
    private final ReminderDAO reminderDAO;

    @Inject
    public ReminderServiceImpl(ReminderDAO reminderDAO) {
        this.reminderDAO = reminderDAO;
    }

    @Override
    public void createReminder(Reminder reminder) throws ServiceException {
        if (reminder.getId() != null) {
            throw new ServiceException("The reminder cannot be created because a reminder with the given ID " +
                    "already exists.");
        }
        try {
            reminderDAO.create(reminder);
        } catch (PersistException e){
            throw new ServiceException("The reminder cannot be persisted.");
        }
    }

    @Override
    public Reminder readReminder(Long id) {
        try{
            return reminderDAO.load(id).orElseThrow(() -> new ServiceException(String.format("A reminder with ID %s " +
                    "does not exist.", id)));
        } catch (ModelNotFoundException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public Reminder updateReminder(Reminder reminder) throws ServiceException {
        if (reminder.getId() == null) {
            throw new ServiceException("The reminder cannot be updated, because it was not found.");
        }
        try {
            return reminderDAO.update(reminder);
        } catch (PersistException e){
            throw new ServiceException("The reminder can not be saved, because it is in a transient state.");
        }
    }

    @Override
    public List<Reminder> findAllReminders() {
        return reminderDAO.findAll();
    }

    @Override
    public List<Reminder> findAllRemindersByLendingProcessId(Long lendingProcessId){
        return reminderDAO.findAllRemindersByLendingProcessId(lendingProcessId);
    }

    @Override
    public void deleteReminder(Long id) throws ServiceException, ModelUsedException {
        try {
            reminderDAO.delete(id);
        } catch (ModelNotFoundException exception) {
            throw new ServiceException(exception);
        }
    }
}