package de.nordakademie.iaa.librarysystem.dao;

import de.nordakademie.iaa.librarysystem.dao.exception.ModelNotFoundException;
import de.nordakademie.iaa.librarysystem.dao.exception.PersistException;
import de.nordakademie.iaa.librarysystem.model.LibrarySystemSetting;

import java.util.List;
import java.util.Optional;

public interface LibrarySystemSettingDAO {
    /**
     * Load @{@link LibrarySystemSetting} by descriptor String as key parameter.
     *
     * @param descriptor String as key parameter for the system setting
     * @return optionally a system setting if exactly 1 result for the descriptor String is found
     * @throws ModelNotFoundException if database state illegal or persistence exception
     */
    Optional<LibrarySystemSetting> load(String descriptor) throws ModelNotFoundException;

    /**
     * Create a new librarySystemSetting.
     *
     * @param librarySystemSetting new unmanaged system setting
     * @throws PersistException if entity exists,
     *         argument is illegal or transaction is required
     */
    void create(LibrarySystemSetting librarySystemSetting) throws PersistException;

    /**
     * Update an existing {@link LibrarySystemSetting}.
     *
     * @param librarySystemSetting managed entity
     * @return updated managed system setting
     * @throws PersistException if argument is illegal or transaction is required
     */
    LibrarySystemSetting update(LibrarySystemSetting librarySystemSetting) throws PersistException;

    /**
     * List all {@link LibrarySystemSetting}s
     *
     * @return a list of persisted system settings or empty list if none exist
     */
    List<LibrarySystemSetting> findAll();
}