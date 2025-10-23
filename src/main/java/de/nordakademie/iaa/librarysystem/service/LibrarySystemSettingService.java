package de.nordakademie.iaa.librarysystem.service;

import de.nordakademie.iaa.librarysystem.model.LibrarySystemSetting;
import de.nordakademie.iaa.librarysystem.service.exception.ServiceException;

import java.util.List;

public interface LibrarySystemSettingService {

    /**
     * Create the given instance of the librarySystemSetting.
     *
     * @param librarySystemSetting The system setting to be created
     * @throws ServiceException When the system setting is already persistent
     */
    void createSystemSetting(LibrarySystemSetting librarySystemSetting);

    /**
     * Loads the system setting with the given natural id String descriptor from the database.
     *
     * @param descriptor The String descriptor of the system setting to be read
     * @return the system setting with the given id String descriptor
     * @throws ServiceException in case the given id does not exist
     */
    LibrarySystemSetting readSystemSetting(String descriptor);

    /**
     * Update an existing librarySystemSetting.
     *
     * @param librarySystemSetting that should be updated
     * @return the updated librarySystemSetting
     * @throws ServiceException When the librarySystemSetting is in a transient state
     */
    LibrarySystemSetting updateSystemSetting(LibrarySystemSetting librarySystemSetting);

//    /**
//     * Delete an {@link LibrarySystemSetting}.
//     *
//     * @param id primary id
//     * @throws ServiceException if there is no system setting with the given id
//     */
//    void deleteSystemSetting(Long id);

    /**
     * Load all systemSettings
     *
     * @return a list of system settings. Empty if no one exists
     */
    List<LibrarySystemSetting> findAllSystemSettings();
}