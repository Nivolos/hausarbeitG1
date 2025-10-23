package de.nordakademie.iaa.roommanagement.dao;

import de.nordakademie.iaa.roommanagement.application.HibernateUtil;
import de.nordakademie.iaa.roommanagement.model.Room;
import org.hibernate.exception.ConstraintViolationException;

import jakarta.persistence.EntityManager;
import java.util.List;

/**
 * The room dao that manages data access.
 */
public class RoomDAO {

	/**
	 * List all rooms currently stored in the database.
	 * 
	 * @return a list of room entities. If no room was found an empty list is
	 *         returned.
	 */
	@SuppressWarnings("unchecked")
	public List<Room> listRooms() {
		EntityManager entityManager = HibernateUtil.getCurrentEntityManager();
		List<Room> rooms = entityManager.createQuery("from Room").getResultList();
		return rooms;
	}

	/**
	 * Creates a new room in the database.
	 * 
	 * @param room
	 * 			The room to save
	 * @throws de.nordakademie.iaa.roommanagement.dao.RoomAlreadyExistsException
	 *             if the room already exists in the database.
	 */
	public Room saveRoom(Room room) {
		try {
            HibernateUtil.getCurrentEntityManager().persist(room);
		} catch (ConstraintViolationException exception) {
			throw new RoomAlreadyExistsException();
		}
		return room;
	}

	/**
	 * Returns the room identified by the given id.
	 * 
	 * @param roomId
	 *            The identifier.
	 * @return the found entity.
	 * @throws RoomNotFoundException
	 *             if no room could be found for the given id.
	 */
	public Room loadRoom(Long roomId) {
		// get session
		Room room = (Room) HibernateUtil.getCurrentEntityManager().find(Room.class, roomId);
		if (room == null) {
			throw new RoomNotFoundException();
		}
		return room;
	}

	/**
	 * Deletes the room with the given id.
	 * 
	 * @param roomId
	 *            The identifier.
	 * @throws RoomNotFoundException
	 *             if no room could be fount for the given id.
	 */
	public void deleteRoom(Long roomId) {
		Room room = loadRoom(roomId);
        HibernateUtil.getCurrentEntityManager().remove(room);
	}
}
