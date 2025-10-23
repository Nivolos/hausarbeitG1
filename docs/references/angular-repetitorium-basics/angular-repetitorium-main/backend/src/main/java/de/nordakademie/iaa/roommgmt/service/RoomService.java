package de.nordakademie.iaa.roommgmt.service;

import de.nordakademie.iaa.roommgmt.dao.RoomDAO;
import de.nordakademie.iaa.roommgmt.model.Room;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class RoomService {

    private RoomDAO roomDAO;

    /**
     * Takes the new room and stores it in the database.
     *
     * @param room The room to be persisted.
     * @throws EntityAlreadyPresentException if a room with the same building/room number
     *                                       combination is already present in the database.
     */
    public void createRoom(Room room) throws EntityAlreadyPresentException {
        var duplicate = roomDAO.findRoomBy(room.getBuilding(), room.getRoomNumber());
        if (duplicate.isPresent()) {
            throw new EntityAlreadyPresentException();
        }
        roomDAO.persistRoom(room);
    }

    /**
     * List all rooms currently stored in the database.
     *
     * @return a list of Room entities. If no room was found an empty list is
     * returned.
     */
    public List<Room> listRooms() {
        return roomDAO.listRooms();
    }

    /**
     * Returns the room identified by the given id.
     *
     * @param id The identifier.
     * @return the optional entity found with given identifier.
     */
    public Optional<Room> loadRoom(Long id) {
        return roomDAO.loadRoom(id);
    }

    /**
     * Updates a room entity and stores the changes into the database.
     *
     * @param id        The identifier.
     * @param seats     The number of seats.
     * @param projector The projector flag.
     * @throws EntityNotFoundException if no room could be found for the given id.
     */
    public void updateRoom(Long id, int seats, boolean projector) throws EntityNotFoundException {
        var room = loadRoom(id).orElseThrow(EntityNotFoundException::new);
        room.setSeats(seats);
        room.setProjectorPresent(projector);
    }

    /**
     * Deletes the room with the given id.
     *
     * @param id The identifier.
     * @throws EntityNotFoundException if no room could be fount for the given id.
     */
    public void deleteRoom(Long id) throws EntityNotFoundException {
        var room = loadRoom(id).orElseThrow(EntityNotFoundException::new);
        roomDAO.deleteRoom(room);
    }

    @Autowired
    public void setRoomDAO(RoomDAO roomDAO) {
        this.roomDAO = roomDAO;
    }
}
