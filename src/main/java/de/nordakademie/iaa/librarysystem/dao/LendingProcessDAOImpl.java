package de.nordakademie.iaa.librarysystem.dao;

import de.nordakademie.iaa.librarysystem.dao.exception.ModelNotFoundException;
import de.nordakademie.iaa.librarysystem.model.LibraryUser;
import de.nordakademie.iaa.librarysystem.model.LendingProcess;
import de.nordakademie.iaa.librarysystem.model.Publication;
import de.nordakademie.iaa.librarysystem.service.exception.ServiceException;
import org.apache.commons.lang3.time.DateUtils;

import org.hibernate.annotations.QueryHints;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.sql.Timestamp;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

import static java.util.Optional.ofNullable;
/**
 * Die Klasse LendingProcessDAOImpl ist für die Datenbankverarbeitung der Ausleihvorgänge zuständig.
 * Mit ihr können:
 *      - bereits angelegte Ausleihvorgänge abgefragt
 *      - Ausleihvorgänge angelegt
 *      - Ausleihvorgänge bearbeiten
 *      - Ausleihvorgänge gelöscht
 *      - alle Ausleihvorgänge ausgegeben werden
 *      - die History zu einem Ausleihvorgang ausgegeben
 *      - die Ausleihvorgänge zu einem Leiher ausgegeben
 *      - die Ausleihvorgänge zu einer Pulikation ausgegeben
 *      - alle überfälligen Ausleihvorgänge
 *      - Suche mit spezifischen Eingabeparametern durchgeführt
 * werden.
 * @author Fabian Rudolf, Jasmin Elvers
 * @version 1.0
 */
@Repository
public class LendingProcessDAOImpl implements LendingProcessDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<LendingProcess> load(Long id){
        LendingProcess lendingProcess = entityManager.find(LendingProcess.class, id);
        return ofNullable(lendingProcess);
    }

    @Override
    public void create(LendingProcess lendingProcess){
        try{
            entityManager.persist(lendingProcess);
        } catch(EntityExistsException
                    | IllegalArgumentException
                    | TransactionRequiredException
                    | DataIntegrityViolationException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @Transactional
    public LendingProcess update(LendingProcess lendingProcess){
        return entityManager.merge(lendingProcess);
    }

    @Override
    public void delete(Long id){
        LendingProcess lendingProcess = load(id).orElseThrow(ModelNotFoundException::new);
        entityManager.remove(lendingProcess);
    }

    @Override
    public List<LendingProcess> findAll(){
        return entityManager
                .createQuery("SELECT lp FROM LendingProcess lp", LendingProcess.class)
                .getResultList();
    }

    private List<LendingProcess> findByLibraryUser(LibraryUser libraryUser, Boolean isReturned) {
        TypedQuery<LendingProcess> query = entityManager.createQuery(
                "SELECT l FROM LendingProcess l " +
                        "WHERE l.libraryUser = :libraryUser " +
                        "AND l.isReturned = :isReturned" , LendingProcess.class);
        List<LendingProcess> result = query
                .setParameter("libraryUser", libraryUser)
                .setParameter("isReturned", isReturned)
                .getResultList();
        return result;
    }

    @Override
    public List<LendingProcess> findHistoryByLibraryUser(LibraryUser libraryUser) {
        return findByLibraryUser(libraryUser, true);
    }


    @Override
    public List<LendingProcess> findCurrentByLibraryUser(LibraryUser libraryUser) {
        return findByLibraryUser(libraryUser, false);
    }

    @Override
    public List<LendingProcess> findCurrentByPublication(Publication publication) {
        TypedQuery<LendingProcess> query = entityManager.createQuery(
                "SELECT l FROM LendingProcess l " +
                    "WHERE l.publication = :publication " +
                    "AND l.isReturned = false" , LendingProcess.class);
        List<LendingProcess> result = query
                .setParameter("publication", publication)
                .getResultList();
        return result;
    }

    @Override
    public List<LendingProcess> findAllOverdue() {
        Date today = Calendar.getInstance().getTime();
        TypedQuery<LendingProcess> query = entityManager.createQuery(
                "SELECT l FROM LendingProcess l " +
                        "WHERE l.plannedReturnDate < :today " +
                        "AND l.isReturned = false" , LendingProcess.class);
        List<LendingProcess> result = query
                .setParameter("today", today)
                .getResultList();
        return result;
    }


    @Override
    public List<LendingProcess> search(LendingProcess lendingProcess, String title, String firstName,
                                       String lastName) {
        Boolean lostFlag = true;
        Boolean returnedFlag = true;
        Boolean isReturned = null;
        Boolean lost = null;
        if(lendingProcess != null){
            isReturned = lendingProcess.getReturned();
            lost = lendingProcess.getLost();
        }
        if(title == null || title.isEmpty()){
            title = "";
        }
        if(firstName == null || firstName.isEmpty()){
            firstName = "";
        }
        if(lastName == null || lastName.isEmpty()){
            lastName = "";
        }
        if(lost == null){
            lostFlag = false;
        }
        if(isReturned == null){
            returnedFlag = false;
        }
        return searchResults(title, firstName, lastName, lost, isReturned,
                lostFlag, returnedFlag);
    }

    /**
     * Inclusively search for lending processes (combines all non-null search criteria via AND condition)
     * @param title
     * @param firstName
     * @param lastName
     * @param lost
     * @param isReturned
     * @param filterLostFlag
     * @param filterReturnedFlag
     * @return a list of matching lending processes
     */
    public List<LendingProcess> searchResults(String title, String firstName, String lastName, Boolean lost,
                                              Boolean isReturned, Boolean filterLostFlag, Boolean filterReturnedFlag) {
        /* Filter by publication title and lending process attributes */
        /* If checked, only include lost publications */
        /* If checked, only include pending returns */
        List<LendingProcess> lendingProcessList = entityManager.createQuery(
                                """
                                SELECT DISTINCT lp FROM LendingProcess lp 
                                        LEFT JOIN FETCH lp.publication p 
                                        WHERE UPPER(p.title) LIKE CONCAT('%',UPPER(:publicationTitle),'%') 
                                        AND (:filterLostFlag IS FALSE OR lp.isLost = :lendingProcessLost) 
                                        AND (:filterReturnedFlag IS FALSE OR lp.isReturned = :lendingProcessIsReturned)
                                """
                        , LendingProcess.class)
                .setParameter("publicationTitle", title)
                .setParameter("lendingProcessLost", lost)
                .setParameter("filterLostFlag", filterLostFlag)
                .setParameter("lendingProcessIsReturned", isReturned)
                .setParameter("filterReturnedFlag", filterReturnedFlag)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList();

        /* Then, filter by libraryUser entity (match first and last names) */
        lendingProcessList = entityManager.createQuery(
                        """
                         SELECT DISTINCT lp FROM LendingProcess lp 
                             LEFT JOIN FETCH lp.libraryUser l 
                             WHERE UPPER(l.firstName) LIKE CONCAT('%',UPPER(:libraryUserFirstName),'%') 
                             AND UPPER(l.lastName) LIKE CONCAT('%',UPPER(:libraryUserLastName),'%') 
                             AND lp in :lendingProcessList
                        """

                        , LendingProcess.class)
                .setParameter("libraryUserFirstName", firstName)
                .setParameter("libraryUserLastName", lastName)
                .setParameter("lendingProcessList", lendingProcessList)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList();

        /* Then, lazy load all reminders connected to the lending processes */
        lendingProcessList = entityManager.createQuery(
                   """
                            SELECT DISTINCT lp FROM LendingProcess lp 
                                LEFT JOIN FETCH lp.reminders r 
                                WHERE lp in :lendingProcessList 
                          """
                        , LendingProcess.class)
                .setParameter("lendingProcessList", lendingProcessList)
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList();

        return lendingProcessList;

    }

    @Override
    public List<LendingProcess> findByLibraryUser(Long libraryUserId) {
        TypedQuery<LendingProcess> query = entityManager.createQuery(
                """
                SELECT lp FROM LendingProcess lp 
                INNER JOIN FETCH lp.libraryUser l 
                WHERE l.id = :libraryUserId
                """,
                LendingProcess.class);

        List<LendingProcess> result = query.setParameter("libraryUserId", libraryUserId)
                .getResultList();
        return result;
    }
}