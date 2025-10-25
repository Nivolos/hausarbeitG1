package de.nordakademie.iaa.librarysystem.service;

import de.nordakademie.iaa.librarysystem.dao.LibrarySystemSettingDAO;
import de.nordakademie.iaa.librarysystem.dao.exception.ModelNotFoundException;
import de.nordakademie.iaa.librarysystem.dao.exception.PersistException;
import de.nordakademie.iaa.librarysystem.model.LibrarySystemSetting;
import de.nordakademie.iaa.librarysystem.service.exception.ServiceException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
/**
 * Die Klasse LibrarySystemSettingServiceImpl ist der Kommunikator zwischen der
 * LibrarySystemSettingAction und der LibrarySystemSettingDao.
 * Sie ermöglicht die CRU Operationen der LibrarySystemSettings.
 * @author Fabian Rudof, Jasmin Elvers
 * @version 1.0
 */
@Service
public class LibrarySystemSettingServiceImpl implements LibrarySystemSettingService {
    private final LibrarySystemSettingDAO dao;

    @Inject
    public LibrarySystemSettingServiceImpl(LibrarySystemSettingDAO dao) {
        this.dao = dao;
    }

    @Override
    public void createSystemSetting(LibrarySystemSetting librarySystemSetting) throws ServiceException {
        if (librarySystemSetting.getId() != null) {
            throw new ServiceException("The system setting cannot be created because a keyword with the " +
                    "given ID already exists.");
        }
        try {
            dao.create(librarySystemSetting);
        } catch (PersistException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public LibrarySystemSetting readSystemSetting(String descriptor) throws ServiceException {
        try{
            return dao.load(descriptor).orElseThrow(() -> new ServiceException(String.format("A system setting with " +
                    "string descriptor %s does not exist.", descriptor)));
        } catch (ModelNotFoundException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public LibrarySystemSetting updateSystemSetting(LibrarySystemSetting librarySystemSetting) throws ServiceException {
        if (librarySystemSetting.getId() == null) {
            throw new ServiceException("The system setting cannot be updated, because it was not found.");
        }
        try {
            return dao.update(librarySystemSetting);
        } catch (PersistException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<LibrarySystemSetting> findAllSystemSettings() {
        return dao.findAll();
    }

}