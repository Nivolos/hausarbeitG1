package de.nordakademie.iaa.roommanagement.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The lecture entity.
 */
@Entity
public class Lecture implements Serializable {
    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = 7664217191744579056L;

    /**
     * The identifier.
     */
    private Long id;
    /**
     * The begin date.
     */
    private Date beginDate;
    /**
     * The end date.
     */
    private Date endDate;
    /**
     * The course this lecture is attached to.
     */
    private Course course;
    /**
     * The room this lecture will be held in.
     */
    private Room room;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NaturalId
    @Column(name = "begin_date")
    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    @Column(name = "end_date")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @ManyToOne(optional = false)
    @NaturalId
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @ManyToOne(optional = false)
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((course == null) ? 0 : course.hashCode());
        result = prime * result
                + ((beginDate == null) ? 0 : beginDate.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Lecture other = (Lecture) obj;
        if (course == null) {
            if (other.course != null)
                return false;
        } else if (!course.equals(other.course))
            return false;
        if (beginDate == null) {
            return other.beginDate == null;
        } else return beginDate.equals(other.beginDate);
    }

}
