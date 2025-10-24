package de.nordakademie.iaa.librarysystem.dao;

import de.nordakademie.iaa.librarysystem.dao.exception.ModelNotFoundException;
import de.nordakademie.iaa.librarysystem.dao.exception.PersistException;
import de.nordakademie.iaa.librarysystem.model.PublicationType;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
/**
 * Die Klasse PublicationTypeDAOImpl ist für die Datenbankverarbeitung der Publikationstypen zuständig.
 * Mit ihr können:
 *      - bereits angelegte Publikationstypen abgefragt
 *      - Publikationstypen angelegt
 *      - Publikationstypen bearbeiten
 *      - Publikationstypen gelöscht
 *      - alle Publikationstypen ausgegeben werden
 * werden.
 * @author Fabian Rudolf, Jasmin Elvers
 * @version 1.0
 */
@Repository
public class PublicationTypeDAOImpl implements PublicationTypeDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<PublicationType> load(Long id) throws ModelNotFoundException{
        PublicationType publication;
        try{
            publication = entityManager.find(PublicationType.class, id);
        } catch(IllegalArgumentException e) {
            throw new ModelNotFoundException();
        }
        return ofNullable(publication);
    }

    @Override
    public void create(PublicationType publicationType) throws PersistException {
        try {
            entityManager.persist(publicationType);
        } catch(EntityExistsException
                | IllegalArgumentException
                | TransactionRequiredException
                | DataIntegrityViolationException e) {
            throw new PersistException();
        }
    }

    @Override
    public PublicationType update(PublicationType publicationType) throws PersistException {
        try{
            return entityManager.merge(publicationType);
        } catch (IllegalArgumentException | TransactionRequiredException e){
            throw new PersistException();
        }
    }

    @Override
    public void delete(Long id) throws ModelNotFoundException {
        PublicationType publication = load(id).orElseThrow(ModelNotFoundException::new);
        entityManager.remove(publication);
    }

    @Override
    public List<PublicationType> findAll(){
        return entityManager
                .createQuery("SELECT p FROM PublicationType p", PublicationType.class)
                .getResultList();
    }

}