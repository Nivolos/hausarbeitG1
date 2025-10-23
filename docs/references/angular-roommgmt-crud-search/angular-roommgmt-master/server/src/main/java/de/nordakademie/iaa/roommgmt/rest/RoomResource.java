package de.nordakademie.iaa.roommgmt.rest;

import de.nordakademie.iaa.roommgmt.model.Room;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Path("/rooms")
public class RoomResource {

    /** Constant for the servlet context attribute. */
    private static final String EXAMPLES_ROOMS = "examples.rooms";

    /** The injected application context. */
    @Context
    private ServletContext servletContext;

    /**
     * Internal method that initializes the room set.
     *
     * @return the set of rooms.
     */
    @SuppressWarnings("unchecked")
    private Set<Room> getRooms() {
        Set<Room> rooms = (Set<Room>) servletContext.getAttribute(EXAMPLES_ROOMS);
        if (rooms == null) {
            rooms = initializeRooms();
        }
        return rooms;
    }

    /**
     * Initializes the set of rooms.
     *
     * @return the set.
     */
    @SuppressWarnings("unchecked")
    private synchronized Set<Room> initializeRooms() {
        Set<Room> rooms = (Set<Room>) servletContext.getAttribute(EXAMPLES_ROOMS);
        if (rooms == null) {
            rooms = Collections.synchronizedSet(new HashSet<Room>());
            rooms.add(new Room("A", 102, 42, true));
            rooms.add(new Room("A", 103, 32, true));
            rooms.add(new Room("H", 101, 24, false));
            servletContext.setAttribute(EXAMPLES_ROOMS, rooms);
        }
        return rooms;
    }

    /**
     * Returns all rooms.
     *
     * @return a set. If no room is found, an empty set is returned.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Room> listRooms() {
        return Collections.unmodifiableSet(getRooms());
    }

    /**
     * Saves a room.
     *
     * @param room The room to be saved.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveRoom(Room room) {
        Set<Room> rooms = getRooms();
        rooms.remove(room);
        getRooms().add(room);
    }

    /**
     * Deletes a room.
     * @param building The building.
     * @param roomNumber The room number.
     */
    @DELETE
    @Path("{building}-{roomNumber}")
    public void deleteRoom(@PathParam("building") String building, @PathParam("roomNumber") int roomNumber) {
        Set<Room> rooms = getRooms();
        Iterator<Room> roomIterator = rooms.iterator();
        while (roomIterator.hasNext()) {
            Room room = roomIterator.next();
            if (room.getBuilding().equals(building) && room.getRoomNumber() == (roomNumber)) {
                roomIterator.remove();
                break;
            }
        }
    }

}
