package de.nordakademie.iaa.librarysystem.service;

import de.nordakademie.iaa.librarysystem.dao.AuthorDAO;
import de.nordakademie.iaa.librarysystem.dao.PublicationDAO;
import de.nordakademie.iaa.librarysystem.dao.exception.ModelNotFoundException;
import de.nordakademie.iaa.librarysystem.dao.exception.PersistException;
import de.nordakademie.iaa.librarysystem.model.Author;
import de.nordakademie.iaa.librarysystem.model.Publication;
import de.nordakademie.iaa.librarysystem.service.exception.ModelUsedException;
import de.nordakademie.iaa.librarysystem.service.exception.ServiceException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
/**
 * Die Klasse AuthorServiceImpl ist der Kommunikator zwischen der AuthorAction und der AuthorDao.
 * Sie ermöglicht die CRUD Operationen des Autors, das Prüfen, ob ein Autor bereits existiert und alle Autoren zu finden.
 * @author Lena Bunge, Felix Groer
 * @version 1.0
 */
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDAO authorDao;

    private final PublicationService publicationService;

    @Inject
    public AuthorServiceImpl(AuthorDAO authorDao, PublicationService publicationService) {
        this.authorDao = authorDao;
        this.publicationService=publicationService;
    }

    @Override
    public void createAuthor(Author author) throws ServiceException {
        if (author.getId() != null) {
            throw new ServiceException("Cannot create an existing author.");
        }
        try {
            checkAuthorExists(author);
            authorDao.create(author);
        } catch (PersistException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Author loadAuthor(Long id) throws ServiceException {
        try{
            return authorDao.load(id).orElseThrow(() -> new ServiceException(String.format("There is no user with id: %s.", id)));
        } catch (ModelNotFoundException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public Author updateAuthor(Author author) throws ServiceException {
        if (author.getId() == null) {
            throw new ServiceException("Cannot update non existing author.");
        }
        try {
            checkAuthorExists(author);
            return authorDao.update(author);
        } catch (PersistException e){
            throw new ServiceException(e);
        }
    }

    private void checkAuthorExists(Author author) {
        Boolean exists = findAllAuthors().stream().anyMatch(a -> a.equals(author));
        if (exists) {
            throw new ServiceException("Author exists already.");
        }
    }

    @Override
    public List<Author> findAllAuthors() {
        return authorDao.findAll();
    }

    @Override
    public void deleteAuthor(Long id) throws ServiceException, ModelUsedException {
        try {
            List<Publication> writtenPublications = publicationService.findByAuthor(id);
            if(!writtenPublications.isEmpty()){
                throw new ModelUsedException();
            }
            authorDao.delete(id);
        } catch (ModelNotFoundException exception) {
            throw new ServiceException(exception);
        }
    }

}