package de.nordakademie.iaa.roommanagement.action;

import com.opensymphony.xwork2.ActionSupport;
import de.nordakademie.iaa.roommanagement.model.Room;
import de.nordakademie.iaa.roommanagement.service.RoomService;
import org.apache.struts2.interceptor.validation.SkipValidation;

import java.util.Scanner;

public class RoomAction extends ActionSupport {

    private RoomService roomService;
    private String selectedKey;
    private Room room;

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    @SkipValidation
    public String load() throws Exception {
        if(selectedKey == null || selectedKey.isBlank()){
            return INPUT;
        }
        Scanner scanner = new Scanner(selectedKey);
        scanner.useDelimiter("\\|");
        room = roomService.findRoomByBuildingAndRoomNumber(scanner.next(),
                Integer.parseInt(scanner.next()));
        return SUCCESS;
    }

    public String save() throws Exception {
        roomService.saveRoom(room);
        return SUCCESS;
    }

    @SkipValidation
    public String delete() throws Exception {
        load();
        roomService.deleteRoom(room.getId());
        return SUCCESS;
    }

    @Override
    public void validate() {
        if(!roomService.isValidBuilding(room)){
            addFieldError("room.building", "Ungültiges Gebäude");
        }
    }

    public String getSelectedKey() {
        return selectedKey;
    }

    public void setSelectedKey(String selectedKey) {
        this.selectedKey = selectedKey;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }
}
