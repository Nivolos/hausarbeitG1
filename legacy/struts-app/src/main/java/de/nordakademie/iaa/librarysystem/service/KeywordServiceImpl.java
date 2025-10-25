package de.nordakademie.iaa.librarysystem.service;

import de.nordakademie.iaa.librarysystem.dao.PublicationDAO;
import de.nordakademie.iaa.librarysystem.dao.exception.ModelNotFoundException;
import de.nordakademie.iaa.librarysystem.dao.KeywordDAO;
import de.nordakademie.iaa.librarysystem.dao.exception.PersistException;
import de.nordakademie.iaa.librarysystem.model.Keyword;
import de.nordakademie.iaa.librarysystem.model.Publication;
import de.nordakademie.iaa.librarysystem.service.exception.ModelUsedException;
import de.nordakademie.iaa.librarysystem.service.exception.ServiceException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
/**
 * Die Klasse KeywordServiceImpl ist der Kommunikator zwischen der KeywordAction und der KeywordDao.
 * Sie ermöglicht die CRUD Operationen des Keywords, das Prüfen, ob ein Keyword bereits existiert und alle
 * Keywords zu finden.
 * @author Fabian Rudof, Jasmin Elvers
 * @version 1.0
 */
@Service
public class KeywordServiceImpl implements KeywordService  {
    private final KeywordDAO keywordDAO;

    private final PublicationService publicationService;


    @Inject
    public KeywordServiceImpl(KeywordDAO keywordDAO, PublicationService publicationService) {
        this.keywordDAO = keywordDAO;
        this.publicationService = publicationService;
    }

    @Override
    public void createKeyword(Keyword keyword) throws ServiceException {
        if (keyword.getId() != null) {
            throw new ServiceException("The keyword cannot be created because a keyword with the given ID " +
                    "already exists.");
        }
        try {
            checkKeywordExists(keyword);
            keywordDAO.create(keyword);
        } catch (PersistException e){
            throw new ServiceException("The keyword exists already.");
        }
    }

    @Override
    public Keyword readKeyword(Long id) {
        try{
            return keywordDAO.load(id).orElseThrow(() -> new ServiceException(String.format("A keyword with ID %s does not " +
                    "exist.", id)));
        } catch (ModelNotFoundException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public Keyword updateKeyword(Keyword keyword) throws ServiceException {
        if (keyword.getId() == null) {
            throw new ServiceException("The keyword cannot be updated, because it was not found.");
        }
        try {
            checkKeywordExists(keyword);
            return keywordDAO.update(keyword);
        } catch (PersistException e){
            throw new ServiceException("The keyword can not be saved, because it is in a transient state.");
        }
    }

    @Override
    public List<Keyword> findAllKeywords() {
        return keywordDAO.findAll();
    }

    @Override
    public void deleteKeyword(Long id) throws ServiceException, ModelUsedException {
        try {
            List<Publication> writtenPublications = publicationService.findByKeyword(id);
            if(!writtenPublications.isEmpty()){
                throw new ModelUsedException();
            }
            keywordDAO.delete(id);
        } catch (ModelNotFoundException exception) {
            throw new ServiceException(exception);
        }
    }

    private void checkKeywordExists(Keyword keyword) {
        Boolean exists = findAllKeywords().stream().anyMatch(a -> a.equals(keyword));
        if (exists) {
            throw new ServiceException("Keyword exists already.");
        }
    }

}