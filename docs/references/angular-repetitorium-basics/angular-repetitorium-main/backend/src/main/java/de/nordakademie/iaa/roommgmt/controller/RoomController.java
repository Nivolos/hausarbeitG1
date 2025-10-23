package de.nordakademie.iaa.roommgmt.controller;

import de.nordakademie.iaa.roommgmt.model.Room;
import de.nordakademie.iaa.roommgmt.service.EntityAlreadyPresentException;
import de.nordakademie.iaa.roommgmt.service.EntityNotFoundException;
import de.nordakademie.iaa.roommgmt.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	private final RoomService roomService;

	public RoomController(RoomService roomService) {
		this.roomService = roomService;
	}

	/**
	 * List all rooms.
	 *
	 * @return the list of rooms.
	 */
	@GetMapping
	public List<Room> listRooms() {
		return roomService.listRooms();
	}

	/**
	 * Creates a new room entity.
	 * @param room The room entity to be persisted.
	 * @return a response entity.
	 */
	@PostMapping
	public ResponseEntity<?> createRoom(@RequestBody Room room) {
		try {
			roomService.createRoom(room);
			return ResponseEntity.status(CREATED).build();
		}
		catch (EntityAlreadyPresentException e) {
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
	@PutMapping(path = "/{id}")
	public ResponseEntity<?> updateRoom(@PathVariable("id") Long id, @RequestBody Room room) {
		try {
			roomService.updateRoom(id, room.getSeats(), room.isProjectorPresent());
			return ResponseEntity.ok().build();
		}
		catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Deletes the room with given id.
	 *
	 * @param id The id of the room to be deleted.
	 * @return a response entity.
	 */
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteRoom(@PathVariable("id") Long id) {
		try {
			roomService.deleteRoom(id);
			return ResponseEntity.ok().build();
		}
		catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
