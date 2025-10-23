package de.nordakademie.iaa.roommgmt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.NaturalId;

import java.io.Serializable;

/**
 * Room entity.
 *
 * @author Stephan Anft
 */
@Entity
public class Room implements Serializable {

    private static final long serialVersionUID = 6925248180274039273L;

    private Long id;
    private String building;
    private Integer roomNumber;
    private Integer seats;
    private boolean projectorPresent;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    @NaturalId
    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    @Column(name = "ROOM_NUMBER", nullable = false)
    @NaturalId
    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Column(nullable = false)
    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    @Column(name = "PROJECTOR_PRESENT", nullable = false)
    public boolean isProjectorPresent() {
        return projectorPresent;
    }

    public void setProjectorPresent(boolean projectorPresent) {
        this.projectorPresent = projectorPresent;
    }
}
