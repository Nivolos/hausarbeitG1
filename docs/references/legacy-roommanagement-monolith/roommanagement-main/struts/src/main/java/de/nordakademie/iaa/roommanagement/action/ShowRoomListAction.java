package de.nordakademie.iaa.roommanagement.action;

import java.util.*;

import com.opensymphony.xwork2.Action;
import de.nordakademie.iaa.roommanagement.model.Room;
import de.nordakademie.iaa.roommanagement.service.RoomService;

public class ShowRoomListAction implements Action {

    private List<Room> rooms;
    private RoomService roomService;

    @Override
    public String execute() throws Exception {
        rooms = roomService.listRooms();
        return SUCCESS;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }
}
