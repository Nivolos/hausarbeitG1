package de.nordakademie.iaa.roommanagement.dao;

import de.nordakademie.iaa.roommanagement.model.Room;

import java.util.List;

public interface RoomDAO {
    /**
     * List all rooms currently stored in the database.
     *
     * @return a list of room entities. If no room was found an empty list is
     * returned.
     */
    List<Room> listRooms();

    /**
     * Creates a new room in the database.
     *
     * @param room The room to save
     * @throws RoomAlreadyExistsException if the room already exists in the database.
     */
    Room saveRoom(Room room);

    /**
     * Returns the room identified by the given id.
     *
     * @param roomId The identifier.
     * @return the found entity.
     * @throws RoomNotFoundException if no room could be found for the given id.
     */
    Room loadRoom(Long roomId);

    /**
     * Deletes the room with the given id.
     *
     * @param roomId The identifier.
     * @throws RoomNotFoundException if no room could be fount for the given id.
     */
    void deleteRoom(Long roomId);

    /**
     * Returns a room by it's natural id.
     *
     * @param building   The building of a room as character.
     * @param roomNumber The number of a room.
     * @return the room matching the given criteria or <code>null</code> if none exists
     */
    Room findRoomByBuildingAndRoomNumber(String building, int roomNumber);

    /**
     * Returns all rooms with a seat count equals or bigger than the given count.
     *
     * @param minSeats The minimal number of seats of a room.
     * @return the rooms matching the given criteria
     */
    List<Room> findRoomsByMinimumSize(int minSeats);
}
