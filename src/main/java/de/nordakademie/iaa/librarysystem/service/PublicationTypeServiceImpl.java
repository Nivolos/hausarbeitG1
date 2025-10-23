package de.nordakademie.iaa.librarysystem.service;

import de.nordakademie.iaa.librarysystem.dao.PublicationDAO;
import de.nordakademie.iaa.librarysystem.dao.exception.ModelNotFoundException;
import de.nordakademie.iaa.librarysystem.dao.PublicationTypeDAO;
import de.nordakademie.iaa.librarysystem.dao.exception.PersistException;
import de.nordakademie.iaa.librarysystem.model.Publication;
import de.nordakademie.iaa.librarysystem.model.PublicationType;
import de.nordakademie.iaa.librarysystem.service.exception.ModelUsedException;
import de.nordakademie.iaa.librarysystem.service.exception.PublicationTypeNameExistsException;
import de.nordakademie.iaa.librarysystem.service.exception.ServiceException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
/**
 * Die Klasse PublicationTypeServiceImpl ist der Kommunikator zwischen der PublicationTypeAction und der PublicationTypeDao.
 * Sie ermöglicht die CRUD Operationen der PublicationType und das finden von PublicationTypes.
 * @author Fabian Rudof, Jasmin Elvers
 * @version 1.0
 */
@Service
public class PublicationTypeServiceImpl  implements PublicationTypeService  {
    private final PublicationTypeDAO publicationTypeDAO;

    private final PublicationService publicationService;

    @Inject
    public PublicationTypeServiceImpl(PublicationTypeDAO publicationTypeDAO, PublicationService publicationService) {
        this.publicationTypeDAO = publicationTypeDAO;
        this.publicationService =publicationService;
    }

    @Override
    public void createPublicationType(PublicationType publicationType) throws ServiceException, PublicationTypeNameExistsException {
        if (publicationType.getId() != null) {
            throw new ServiceException("The publication type cannot be created because a publication type with the given ID already exists.");
        }
        try{
            checkPublicationTypeExists(publicationType);
            publicationTypeDAO.create(publicationType);
        } catch (DataIntegrityViolationException e) {
            throw new PublicationTypeNameExistsException();
        } catch (PersistException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public PublicationType readPublicationType(Long id) throws ServiceException {
        try{
            return publicationTypeDAO.load(id).orElseThrow(() -> new ServiceException(String.format("A publication type with ID %s does not exist.", id)));
        } catch (ModelNotFoundException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public PublicationType updatePublicationType(PublicationType publicationType) throws ServiceException {
        if (publicationType.getId() == null) {
            throw new ServiceException("The publication type cannot be updated, because it was not found.");
        }
        try{
            checkPublicationTypeExists(publicationType);
            return publicationTypeDAO.update(publicationType);
        } catch (PersistException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<PublicationType> findAllPublicationTypes() {
        return publicationTypeDAO.findAll();
    }

    @Override
    public void deletePublicationType(Long id) throws ServiceException, ModelUsedException {
        try {
            List<Publication> writtenPublications = publicationService.findByPublicationType(id);
            if(!writtenPublications.isEmpty()){
                throw new ModelUsedException();
            }
            publicationTypeDAO.delete(id);
        } catch (ModelNotFoundException exception) {
            throw new ServiceException(exception);
        }
    }

    private void checkPublicationTypeExists(PublicationType publicationType) {
        Boolean exists = findAllPublicationTypes().stream().anyMatch(a -> a.equals(publicationType));
        if (exists) {
            throw new ServiceException("PublicationType exists already.");
        }
    }

}