package de.nordakademie.iaa.librarysystem.dao;

import de.nordakademie.iaa.librarysystem.dao.exception.ModelNotFoundException;
import de.nordakademie.iaa.librarysystem.dao.exception.PersistException;
import de.nordakademie.iaa.librarysystem.model.LibrarySystemSetting;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * Die Klasse LibrarySystemSettingDAOImpl ist für die Datenbankverarbeitung der
 * Bücherei Systemeinstellungen zuständig.
 * Mit ihr können:
 *      - die Systemeinstellungen geladen werden
 *      - Systemeinstellungen erstellt
 *      - Systemeinstellungen bearbeitet
 *      - Systemeinstellungen gelöscht
 *      - alle Systemeinstellungen geladen
 * werden.
 * @author Lena Bunge, Felix Groer
 * @version 1.0
 */
@Repository
public class LibrarySystemSettingDAOImpl implements LibrarySystemSettingDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<LibrarySystemSetting> load(String descriptor) throws ModelNotFoundException {
        TypedQuery<LibrarySystemSetting> query = entityManager.createQuery(
                "SELECT s FROM LibrarySystemSetting s WHERE s.parameterKey= :parameter", LibrarySystemSetting.class
        );
        query.setParameter("parameter", descriptor);
        LibrarySystemSetting librarySystemSetting = null;
        try{
            librarySystemSetting = query.getSingleResult();
        }
        catch (NoResultException | NonUniqueResultException ignore){
            // Ignore parameter not set or set multiple times
        } catch (IllegalStateException | PersistenceException e){
            throw new ModelNotFoundException();
        }
        return ofNullable(librarySystemSetting);
    }

    @Override
    public void create(LibrarySystemSetting librarySystemSetting) throws PersistException {
        try {
            entityManager.persist(librarySystemSetting);
        } catch(EntityExistsException
                | IllegalArgumentException
                | TransactionRequiredException
                | DataIntegrityViolationException e) {
            throw new PersistException();
        }
    }

    @Override
    public LibrarySystemSetting update(LibrarySystemSetting librarySystemSetting) throws PersistException {
        try{
            return entityManager.merge(librarySystemSetting);
        } catch (IllegalArgumentException | TransactionRequiredException e){
            throw new PersistException();
        }
    }

    @Override
    public List<LibrarySystemSetting> findAll(){
        return entityManager
                .createQuery("SELECT s FROM LibrarySystemSetting s", LibrarySystemSetting.class)
                .getResultList();
    }

}