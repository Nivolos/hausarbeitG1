package de.nordakademie.iaa.roommanagement.model;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * The room entity.
 */
@Entity
public class Room implements Serializable {

    /** The serial version uid. */
    private static final long serialVersionUID = 7664217191744579056L;

    /** The identifier. */
    private Long id;
    /** The building. */
    private String building;
    /** The room number. */
    private int roomNumber;
    /** The seat count. */
    private int seats;
    /** The beamer information. */
    private boolean presenterAvailable;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NaturalId
    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    @NaturalId
    @Column(name = "room_number")
    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Basic
    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    @Basic
    @Column(name = "presenter_available")
    public boolean isPresenterAvailable() {
        return presenterAvailable;
    }

    public void setPresenterAvailable(boolean presenterAvailable) {
        this.presenterAvailable = presenterAvailable;
    }
}
