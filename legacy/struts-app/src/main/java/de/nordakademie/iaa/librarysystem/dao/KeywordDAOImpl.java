package de.nordakademie.iaa.librarysystem.dao;

import de.nordakademie.iaa.librarysystem.dao.exception.ModelNotFoundException;
import de.nordakademie.iaa.librarysystem.dao.exception.PersistException;
import de.nordakademie.iaa.librarysystem.model.Keyword;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * Die Klasse KeywordDAOImpl ist für die Datenbankverarbeitung der Schlüsselwörter zuständig.
 * Mit ihr können:
 *      - bereits angelegte Schlüsselwörter abgefragt
 *      - Schlüsselwörter angelegt
 *      - Schlüsselwörter bearbeiten
 *      - Schlüsselwörter gelöscht
 *      - alle Schlüsselwörter ausgegeben werden
 * werden.
 * @author Fabian Rudolf, Jasmin Elvers
 * @version 1.0
 */
@Repository
public class KeywordDAOImpl implements KeywordDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Keyword> load(Long id) throws ModelNotFoundException{
        Keyword keyword;
        try{
            keyword = entityManager.find(Keyword.class, id);
        }
        catch (IllegalArgumentException e){
            throw new ModelNotFoundException();
        }
        return ofNullable(keyword);
    }

    @Override
    public void create(Keyword keyword) throws PersistException {
        try {
            entityManager.persist(keyword);
        } catch(EntityExistsException
                | IllegalArgumentException
                | TransactionRequiredException
                | DataIntegrityViolationException e) {
            throw new PersistException();
        }
    }

    @Override
    public Keyword update(Keyword keyword) throws PersistException{
        try{
            return entityManager.merge(keyword);
        } catch (IllegalArgumentException | TransactionRequiredException e){
            throw new PersistException();
        }
    }

    @Override
    public void delete(Long id) throws ModelNotFoundException{
        Keyword keyword = load(id).orElseThrow(ModelNotFoundException::new);
        entityManager.remove(keyword);
    }

    @Override
    public List<Keyword> findAll(){
        return entityManager
                .createQuery("SELECT k FROM Keyword k", Keyword.class)
                .getResultList();
    }

}