package de.nordakademie.iaa.librarysystem.dao;

import de.nordakademie.iaa.librarysystem.dao.exception.ModelNotFoundException;
import de.nordakademie.iaa.librarysystem.dao.exception.PersistException;
import de.nordakademie.iaa.librarysystem.model.LendingProcess;
import de.nordakademie.iaa.librarysystem.model.Publication;
import de.nordakademie.iaa.librarysystem.model.Reminder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * Die Klasse ReminderDAOImpl ist für die Datenbankverarbeitung der Erinnerungen zuständig.
 * Mit ihr können:
 *      - bereits angelegte Erinnerungen abgefragt
 *      - Erinnerungen angelegt
 *      - Erinnerungen bearbeiten
 *      - Erinnerungen gelöscht
 *      - alle Erinnerungen ausgegeben werden
 *      - alle Erinnerungen zu einem Ausleihvorgang abgefragt
 *      - alle Erinnerungen zu einer Ausleihvorgang Id abgefragt
 * werden.
 * @author Lena Bunge, Felix Groer
 * @version 1.0
 */
@Repository
public class ReminderDAOImpl implements ReminderDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Reminder> load(Long id) throws ModelNotFoundException {
        Reminder reminder;
        try{
            reminder = entityManager.find(Reminder.class, id);
        }
        catch (IllegalArgumentException e){
            throw new ModelNotFoundException();
        }
        return ofNullable(reminder);
    }

    @Override
    public void create(Reminder reminder) throws PersistException {
        try {
            entityManager.persist(reminder);
        } catch(EntityExistsException
                | IllegalArgumentException
                | TransactionRequiredException
                | DataIntegrityViolationException e) {
            throw new PersistException();
        }
    }

    @Override
    public Reminder update(Reminder reminder) throws PersistException{
        try{
            return entityManager.merge(reminder);
        } catch (IllegalArgumentException | TransactionRequiredException e){
            throw new PersistException();
        }
    }

    @Override
    public void delete(Long id) throws ModelNotFoundException{
        Reminder reminder = load(id).orElseThrow(ModelNotFoundException::new);
        entityManager.remove(reminder);
    }

    @Override
    public List<Reminder> findAll(){
        return entityManager
                .createQuery("SELECT r FROM Reminder r", Reminder.class)
                .getResultList();
    }

    @Override
    public List<Reminder> findAllRemindersByLendingProcessId(Long lendingProcessId) {
        TypedQuery<Reminder> query = entityManager.createQuery(
                "SELECT r FROM Reminder r " +
                "WHERE r.lendingProcess.id = :lendingProcessId",
                Reminder.class);
        List<Reminder> reminders = query
                .setParameter("lendingProcessId", lendingProcessId)
                .getResultList();
        return reminders;
    }

    @Override
    public List<Reminder> findAllRemindersByLendingProcesses(List<LendingProcess> lendingProcesses){
        List<Reminder> result = new ArrayList<>();
        for(LendingProcess lp : lendingProcesses){
            if(lp != null){
                result.addAll(findAllRemindersByLendingProcessId(lp.getId()));
            }
        }
        return result;
    }

}