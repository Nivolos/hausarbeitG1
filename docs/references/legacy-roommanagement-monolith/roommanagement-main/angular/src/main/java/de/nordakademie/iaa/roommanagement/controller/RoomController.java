package de.nordakademie.iaa.roommanagement.controller;

import de.nordakademie.iaa.roommanagement.dao.RoomAlreadyExistsException;
import de.nordakademie.iaa.roommanagement.dao.RoomNotFoundException;
import de.nordakademie.iaa.roommanagement.model.Room;
import de.nordakademie.iaa.roommanagement.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * REST controller for the room entity.
 *
 * @author Stephan Anft
 */
@RestController
@RequestMapping(path = "/rooms")
public class RoomController {

    private RoomService roomService;

    /**
     * List all rooms.
     *
     * @return the list of rooms.
     */
    @RequestMapping(method = GET)
    public List<Room> listRooms() {
        return roomService.listRooms();
    }

    /**
     * Creates a new room entity.
     *
     * @param room The room entity to be persisted.
     * @return a response entity.
     */
    @RequestMapping(method = POST)
    public ResponseEntity<?> createRoom(@RequestBody Room room) {
        try {
            roomService.saveRoom(room);
            return ResponseEntity.status(CREATED).build();
        } catch (RoomAlreadyExistsException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    /**
     * Updates the given room.
     *
     * @param id   Id of the room to be updated.
     * @param room The updated room.
     * @return a response entity.
     */
    @RequestMapping(path = "/{id}", method = PUT)
    public ResponseEntity<?> updateRoom(@PathVariable("id") Long id, @RequestBody Room room) {
        try {
            room.setId(id);
            roomService.saveRoom(room);
            return ResponseEntity.ok().build();
        } catch (RoomAlreadyExistsException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes the room with given id.
     *
     * @param id The id of the room to be deleted.
     * @return a response entity.
     */
    @RequestMapping(path = "/{id}", method = DELETE)
    public ResponseEntity<?> deleteRoom(@PathVariable Long id) {
        try {
            roomService.deleteRoom(id);
            return ResponseEntity.ok().build();
        } catch (RoomNotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @Inject
    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }
}
