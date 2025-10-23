package de.nordakademie.iaa.roommanagement.service;

import de.nordakademie.iaa.roommanagement.model.Room;

import java.util.List;

public interface RoomService {

    /**
     * List all rooms currently stored in the database.
     *
     * @return a list of room entities. If no room was found an empty list is
     * returned.
     */
    List<Room> listRooms();

    /**
     * Saves a room in the database.
     *
     * @param room The room to save
     * @throws ServiceException if the room already exists in the database.
     */
    Room saveRoom(Room room);

    /**
     * Returns the room identified by the given id.
     *
     * @param roomId The identifier.
     * @return the found entity.
     * @throws ServiceException if no room could be found for the given id.
     */
    Room loadRoom(long roomId);

    /**
     * Deletes the room with the given id.
     *
     * @param roomId The identifier.
     * @throws ServiceException if no room could be found for the given id or if the room is
     *                          still in use by lectures.
     */
    void deleteRoom(long roomId);

    /**
     * Returns a room by it's natural id.
     *
     * @param building   The building of a room as character.
     * @param roomNumber The number of a room.
     * @return the room matching the given criteria or <code>null</code> if none exists
     */
    Room findRoomByBuildingAndRoomNumber(String building, int roomNumber);

    /**
     * Returns whether the given building is valid. A valid Room has to have a single character building letter.
     *
     * @param room The room to check.
     * @return <code>true</code> if the building is valid, <code>false</code> otherwise.
     */
    boolean isValidBuilding(Room room);
}
