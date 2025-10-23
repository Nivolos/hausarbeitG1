package de.nordakademie.iaa.librarysystem.dao;

import de.nordakademie.iaa.librarysystem.dao.exception.ModelNotFoundException;
import de.nordakademie.iaa.librarysystem.service.exception.ServiceException;
import org.springframework.dao.DataIntegrityViolationException;
import de.nordakademie.iaa.librarysystem.model.LibraryUser;
import org.hibernate.annotations.QueryHints;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;

import de.nordakademie.iaa.librarysystem.model.LibraryUser;
import org.hibernate.annotations.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
/**
 * Die Klasse LibraryUserDAOImpl ist für die Datenbankverarbeitung der Leihers zuständig.
 * Mit ihr können:
 *      - bereits angelegte Leihers abgefragt
 *      - Leihers angelegt
 *      - Leihers bearbeiten
 *      - Leihers gelöscht
 *      - alle Leihers ausgegeben werden
 * werden.
 * @author Fabian Rudolf, Jasmin Elvers
 * @version 1.0
 */

public class LibraryUserDAOImpl implements LibraryUserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<LibraryUser> load(Long id){
        LibraryUser libraryUser = entityManager.find(LibraryUser.class, id);
        return ofNullable(libraryUser);
    }

    @Override
    public void create(LibraryUser libraryUser){
        try{
            entityManager.persist(libraryUser);
        } catch(EntityExistsException
                | IllegalArgumentException
                | TransactionRequiredException
                | DataIntegrityViolationException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public LibraryUser update(LibraryUser libraryUser){
        return entityManager.merge(libraryUser);
    }

    @Override
    public void delete(Long id){
        LibraryUser libraryUser = load(id).orElseThrow(ModelNotFoundException::new);
        entityManager.remove(libraryUser);
    }

    @Override
    public List<LibraryUser> findAll(){
        return entityManager
                .createQuery("SELECT l FROM LibraryUser l", LibraryUser.class)
                .getResultList();
    }

    @Override
    public Optional<LibraryUser> loadByUserName(String userName) throws ModelNotFoundException {
        LibraryUser libraryUser = null;
        try {
            libraryUser = entityManager.createQuery(
                            "SELECT DISTINCT l " +
                                    "FROM LibraryUser l " +
                                    "LEFT JOIN FETCH l.roles r " +
                                    "WHERE l.userName=:userName", LibraryUser.class)
                    .setParameter("userName", userName)
                    .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                    .getSingleResult();

        }
        catch (NoResultException | NonUniqueResultException ignore){
            // Ignore library user not found or user name found multiple times
        }
        catch (IllegalStateException | PersistenceException e){
            throw new ModelNotFoundException();
        }
        return ofNullable(libraryUser);
    }

}