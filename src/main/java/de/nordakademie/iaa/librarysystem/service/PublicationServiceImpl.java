package de.nordakademie.iaa.librarysystem.service;

import de.nordakademie.iaa.librarysystem.dao.AuthorDAO;
import de.nordakademie.iaa.librarysystem.dao.KeywordDAO;
import de.nordakademie.iaa.librarysystem.dao.PublicationDAO;
import de.nordakademie.iaa.librarysystem.dao.exception.ModelNotFoundException;
import de.nordakademie.iaa.librarysystem.dao.exception.PersistException;
import de.nordakademie.iaa.librarysystem.model.Author;
import de.nordakademie.iaa.librarysystem.model.Keyword;
import de.nordakademie.iaa.librarysystem.model.Publication;
import de.nordakademie.iaa.librarysystem.service.exception.ServiceException;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
/**
 * Die Klasse PublicationServiceImpl ist der Kommunikator zwischen der PublicationAction und der PublicationDao.
 * Sie ermöglicht die CRUD Operationen der Publication und das finden von Publikationen nach bestimmten Kriterien.
 * @author Lena Bunge, Felix Groer
 * @version 1.0
 */
@Service
public class PublicationServiceImpl implements PublicationService {

    private final PublicationDAO publicationDao;



    @Inject
    public PublicationServiceImpl(PublicationDAO publicationDao) {
        this.publicationDao = publicationDao;

    }

    @Transactional
    @Override
    public void createPublication(Publication publication) throws ServiceException {
        if (publication.getId() != null) {
            throw new ServiceException("The publication cannot be created because a publication " +
                    "with the given ID already exists.");
        }
        try {
            publicationDao.create(publication);
        } catch (PersistException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Publication readPublication(Long id) {
        try{
            Publication publication = publicationDao.load(id).orElseThrow(() -> new ServiceException(String.format("A publication with " +
                    "ID %s does not exist.", id)));
            loadLazy(publication);
            return publication;
        } catch (ModelNotFoundException | HibernateException e){
            throw new ServiceException(e);
        }
    }

    private void loadLazy(Publication publication){
        try{
            Hibernate.initialize(publication.getAuthor());
            Hibernate.initialize(publication.getKeywords());
        } catch(HibernateException e){
            throw new ServiceException("Lazy load of publication authors / keywords failed");
        }
    }

    @Transactional
    @Override
    public Publication updatePublication(Publication publication) throws ServiceException {
        if (publication.getId() == null) {
            throw new ServiceException("The publication cannot be updated, because it was not found.");
        }
        try {
            return publicationDao.update(publication);
        } catch (PersistException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public List<Publication> findAllPublications() {
        return publicationDao.findAll();
    }

    @Override
    public void deletePublication(Long id) throws ServiceException {
        try {
            publicationDao.delete(id);
        } catch (ModelNotFoundException exception) {
            throw new ServiceException(exception);
        }
    }

    @Override
    public List<Publication> searchPublication(Publication publication,
                                               List<Long> keywordIdList,
                                               List<Long> publicationTypeIdList,
                                               List<Long> authorIdList) {
        return publicationDao.search(publication, keywordIdList, publicationTypeIdList, authorIdList);
    }

    @Override
    public List<Publication> findAvailablePublications() {
        return publicationDao.findAvailable();
    }

    @Override
    public List<Publication> findByAuthor(Long authorId) {
        return publicationDao.findByAuthor(authorId);
    }

    @Override
    public List<Publication> findByKeyword(Long keywordId) {
        return publicationDao.findByKeyword(keywordId);
    }

    @Override
    public List<Publication> findByPublicationType(Long publicationTypeId) {
        return publicationDao.findByPublicationType(publicationTypeId);
    }
}