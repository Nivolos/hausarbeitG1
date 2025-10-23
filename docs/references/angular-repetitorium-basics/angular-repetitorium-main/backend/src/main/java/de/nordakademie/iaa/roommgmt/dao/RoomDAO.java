package de.nordakademie.iaa.roommgmt.dao;

import de.nordakademie.iaa.roommgmt.model.Room;
import jakarta.persistence.NoResultException;
import org.hibernate.exception.ConstraintViolationException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

/**
 * The room DAO that manages all persistence functionality.
 *
 * @author Stephan Anft
 */
public class RoomDAO {

    /**
     * The current entity manager.
     */
    private EntityManager entityManager;

    public Optional<Room> findRoomBy(String building, Integer roomNumber) {
        var query = entityManager.createQuery("select room from Room room where room.building = :building and room.roomNumber = :roomNumber", Room.class)
                .setParameter("building", building)
                .setParameter("roomNumber", roomNumber);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    /**
     * Takes the room and stores it in the database.
     *
     * @param room The room to be persisted.
     */
    public void persistRoom(Room room) {
        entityManager.persist(room);
    }

    /**
     * List all rooms currently stored in the database.
     *
     * @return a list of Room entities. If no room was found an empty list is
     * returned.
     */
    public List<Room> listRooms() {
        return entityManager.createQuery("select room from Room room", Room.class).getResultList();
    }

    /**
     * Returns the room identified by the given id.
     *
     * @param id The identifier.
     * @return the optional room found with given identifier.
     */
    public Optional<Room> loadRoom(Long id) {
        return Optional.ofNullable(entityManager.find(Room.class, id));
    }

    /**
     * Deletes the room.
     *
     * @param room The room to be deleted.
     */
    public void deleteRoom(Room room) {
        entityManager.remove(room);
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
