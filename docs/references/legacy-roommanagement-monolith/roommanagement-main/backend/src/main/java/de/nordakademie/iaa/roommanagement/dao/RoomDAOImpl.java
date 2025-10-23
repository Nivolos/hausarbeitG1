package de.nordakademie.iaa.roommanagement.dao;

import de.nordakademie.iaa.roommanagement.model.Room;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * The room dao that manages data access.
 */
public class RoomDAOImpl implements RoomDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Room> listRooms() {
        List<Room> rooms = entityManager.createQuery("from Room", Room.class).getResultList();
        return rooms;
    }

    @Override
    public Room saveRoom(Room room) {
        if (room.getId() != null) {
            // update the room
            return entityManager.merge(room);
        }
        if (room.getId() == null && existsRoomByBuildingAndRoomNumber(room.getBuilding(), room.getRoomNumber())) {
            throw new RoomAlreadyExistsException();
        }
        // create the room
        entityManager.persist(room);
        return room;
    }

    @Override
    public Room loadRoom(Long roomId) {
        // get session
        Room room = (Room) entityManager.find(Room.class, roomId);
        if (room == null) {
            throw new RoomNotFoundException();
        }
        return room;
    }

    @Override
    public void deleteRoom(Long roomId) {
        Room room = loadRoom(roomId);
        entityManager.remove(room);
    }

    @Override
    public Room findRoomByBuildingAndRoomNumber(String building, int roomNumber) {
        TypedQuery<Room> query = entityManager.createQuery("from Room as room where room.building = :building and room.roomNumber = :roomNumber", Room.class);
        query.setParameter("building", building);
        query.setParameter("roomNumber", roomNumber);
        List<Room> rooms = query.getResultList();
        if (rooms.isEmpty()) {
            throw new RoomNotFoundException();
        }
        return rooms.get(0);
    }

    private boolean existsRoomByBuildingAndRoomNumber(String building, int roomNumber) {
        TypedQuery<Long> query = entityManager.createQuery("select count(room) from Room as room where room.building = :building and room.roomNumber = :roomNumber", Long.class);
        query.setParameter("building", building);
        query.setParameter("roomNumber", roomNumber);
        return query.getSingleResult() > 0;
    }

    @Override
    public List<Room> findRoomsByMinimumSize(int minSeats) {
        TypedQuery<Room> query = entityManager.createQuery("from Room as room where room.seats >= :minSeats", Room.class);
        query.setParameter("minSeats", minSeats);
        return query.getResultList();
    }
}
