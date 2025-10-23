package de.nordakademie.iaa.librarysystem.dao;

import de.nordakademie.iaa.librarysystem.dao.exception.ModelNotFoundException;
import de.nordakademie.iaa.librarysystem.dao.exception.PersistException;
import de.nordakademie.iaa.librarysystem.model.Role;
import org.hibernate.annotations.QueryHints;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * Die Klasse RoleDAOImpl ist für die Datenbankverarbeitung der Rolen zuständig.
 * Mit ihr können:
 *      - bereits angelegte Rolen abgefragt
 *      - Rolen angelegt
 *      - Rolen bearbeiten
 *      - Rolen gelöscht
 *      - alle Rolen abgefragt
 * werden.
 * @author Fabian Rudolf, Jasmin Elvers
 * @version 1.0
 */
@Repository
public class RoleDAOImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Role> load(Long id) throws ModelNotFoundException{
        Role role;
        try{
            role = entityManager.find(Role.class, id);
        } catch(IllegalArgumentException e) {
            throw new ModelNotFoundException();
        }
        return ofNullable(role);
    }

    @Override
    public void create(Role role) throws PersistException {
        try {
            entityManager.persist(role);
        } catch(EntityExistsException
                | IllegalArgumentException
                | TransactionRequiredException
                | DataIntegrityViolationException e) {
            throw new PersistException();
        }
    }

    @Override
    public Role update(Role role) throws PersistException {
        try{
            return entityManager.merge(role);
        } catch (IllegalArgumentException | TransactionRequiredException e){
            throw new PersistException();
        }
    }

    @Override
    public void delete(Long id) throws ModelNotFoundException {
        Role role = load(id).orElseThrow(ModelNotFoundException::new);
        entityManager.remove(role);
    }

    @Override
    public List<Role> findAll(){
        return entityManager
                .createQuery("SELECT a FROM Role a", Role.class)
                .getResultList();
    }

    @Override
    public Optional<Role> findRoleByName(String username) throws ModelNotFoundException {
        Role role = null;
        try {
            role = entityManager
                    .createQuery("SELECT r From Role r " +
                            "WHERE r.name LIKE :username", Role.class)
                    .setParameter("username", username)
                    .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                    .getSingleResult();
        }
        catch (NoResultException | NonUniqueResultException ignore){
            // Ignore role not found or role name found multiple times
        }
        catch (IllegalStateException | PersistenceException e){
            throw new ModelNotFoundException();
        }
        return ofNullable(role);
    }
}