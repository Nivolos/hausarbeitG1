package de.nordakademie.iaa.librarysystem.dao;

import de.nordakademie.iaa.librarysystem.dao.exception.ModelNotFoundException;
import de.nordakademie.iaa.librarysystem.dao.exception.PersistException;
import de.nordakademie.iaa.librarysystem.model.PublicationType;
import org.h2.engine.Mode;

import java.util.List;
import java.util.Optional;

public interface PublicationTypeDAO {
    /**
     * Load @{@link PublicationType} by id.
     *
     * @param id id as primary for the publicationType
     * @return optionally an publicationType if found
     * @throws ModelNotFoundException if argument is illegal
     */
    Optional<PublicationType> load(Long id) throws ModelNotFoundException;

    /**
     * Create a new publication type.
     *
     * @param publicationType new unmanaged publicationType
     * @throws PersistException if entity exists,
     *         argument is illegal or transaction is required
     */
    void create(PublicationType publicationType) throws PersistException;

    /**
     * Update an existing {@link PublicationType}.
     *
     * @param publicationType managed entity
     * @return updated managed publication type
     * @throws PersistException if argument is illegal or transaction is required
     */
    PublicationType update(PublicationType publicationType) throws PersistException;

    /**
     * Delete an existing publication type by id
     *
     * @param id primary key
     * @throws ModelNotFoundException if there is no publicationType with this id
     */
    void delete(Long id) throws ModelNotFoundException;

    /**
     * List all {@link PublicationType}
     *
     * @return a list of persisted publication types or an empty list if none are persisted
     */
    List<PublicationType> findAll();
}