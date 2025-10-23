package de.nordakademie.iaa.roommgmt.model;

import java.util.Objects;

public class Room {

    private String building;
    private int roomNumber;
    private int seats;
    private boolean projectorPresent;

    public Room() {
        // Do nothing
    }

    public Room(String building, int roomNumber, int seats, boolean projectorPresent) {
        this.building = building;
        this.roomNumber = roomNumber;
        this.seats = seats;
        this.projectorPresent = projectorPresent;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public boolean isProjectorPresent() {
        return projectorPresent;
    }

    public void setProjectorPresent(boolean projectorPresent) {
        this.projectorPresent = projectorPresent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomNumber == room.roomNumber &&
                building.equals(room.building);
    }

    @Override
    public int hashCode() {
        return Objects.hash(building, roomNumber);
    }
}
