package de.nordakademie.iaa.roommanagement.service;

import de.nordakademie.iaa.roommanagement.dao.RoomAlreadyExistsException;
import de.nordakademie.iaa.roommanagement.dao.RoomDAO;
import de.nordakademie.iaa.roommanagement.dao.RoomNotFoundException;
import de.nordakademie.iaa.roommanagement.model.Room;

import javax.inject.Inject;
import java.util.List;

/**
 * The room service that manages all business functionality.
 */
public class RoomServiceImpl implements RoomService {

    /**
     * The {@link RoomDAO} to use for db access.
     */
    private RoomDAO roomDAO;

    @Inject
    public void setRoomDAO(RoomDAO roomDAO) {
        this.roomDAO = roomDAO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Room> listRooms() {
        return roomDAO.listRooms();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Room loadRoom(long roomId) {
        try {
            return roomDAO.loadRoom(roomId);
        } catch (RoomNotFoundException exception) {
            throw new ServiceException(exception.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteRoom(long roomId) {
        try {
            roomDAO.deleteRoom(roomId);
        } catch (RoomNotFoundException exception) {
            throw new ServiceException(exception.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Room saveRoom(Room room) {
        try {
            return roomDAO.saveRoom(room);
        } catch (RoomAlreadyExistsException exception) {
            throw new ServiceException(exception.getMessage());
        }
    }

    @Override
    public Room findRoomByBuildingAndRoomNumber(String building, int roomNumber) {
        return roomDAO.findRoomByBuildingAndRoomNumber(building, roomNumber);
    }

    @Override
    public boolean isValidBuilding(Room room) {
        return room.getBuilding().length() == 1
                && Character.isLetter(room.getBuilding().charAt(0))
                && Character.isUpperCase(room.getBuilding().charAt(0));
    }

}
