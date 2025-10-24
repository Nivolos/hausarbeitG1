package de.nordakademie.iaa.librarysystem.dao;

import de.nordakademie.iaa.librarysystem.dao.exception.ModelNotFoundException;
import de.nordakademie.iaa.librarysystem.dao.exception.PersistException;
import de.nordakademie.iaa.librarysystem.model.Author;
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
 * Die Klasse AuthorDAOImpl ist für die Datenbankverarbeitung des Autors zuständig.
 * Mit ihr können:
 *      - bereits angelegte Autoren abgefragt
 *      - Autoren angelegt
 *      - Autoren bearbeiten
 *      - Autoren gelöscht
 *      - alle Autoren ausgegeben werden
 * werden.
 * @author Lena Bunge, Felix Groer
 * @version 1.0
 */
@Repository
public class AuthorDAOImpl implements AuthorDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Author> load(Long id) throws ModelNotFoundException{
        Author author;
        try{
            author = entityManager.find(Author.class, id);
        } catch(IllegalArgumentException e) {
            throw new ModelNotFoundException();
        }
        return ofNullable(author);
    }

    @Override
    public void create(Author author) throws PersistException {
        try {
            entityManager.persist(author);
        } catch(EntityExistsException
                | IllegalArgumentException
                | TransactionRequiredException
                | DataIntegrityViolationException e) {
            throw new PersistException();
        }
    }

    @Override
    public Author update(Author author) throws PersistException {
        try{
            return entityManager.merge(author);
        } catch (IllegalArgumentException | TransactionRequiredException e){
            throw new PersistException();
        }
    }

    @Override
    public void delete(Long id) throws ModelNotFoundException {
        Author author = load(id).orElseThrow(ModelNotFoundException::new);
        entityManager.remove(author);
    }

    @Override
    public List<Author> findAll(){
        return entityManager
                .createQuery("SELECT a FROM Author a", Author.class)
                .getResultList();
    }

}