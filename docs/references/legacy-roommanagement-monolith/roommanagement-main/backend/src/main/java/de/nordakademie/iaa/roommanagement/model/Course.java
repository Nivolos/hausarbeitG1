/**
 *
 */
package de.nordakademie.iaa.roommanagement.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The course entity.
 */
@Entity
public class Course implements Serializable {
    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = 7664217191744579056L;

    /**
     * The identifier.
     */
    private Long id;
    /**
     * The field of study.
     */
    private String fieldOfStudy;
    /**
     * The course number.
     */
    private int number;
    /**
     * The lecturer's name.
     */
    private String lecturer;
    /**
     * The course title.
     */
    private String title;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NaturalId
    @Column(name = "field_of_study")
    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    @NaturalId
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Basic
    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    @Basic
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((fieldOfStudy == null) ? 0 : fieldOfStudy.hashCode());
        result = prime * result + number;
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
        final Course other = (Course) obj;
        if (fieldOfStudy == null) {
            if (other.fieldOfStudy != null)
                return false;
        } else if (!fieldOfStudy.equals(other.fieldOfStudy))
            return false;
        return number == other.number;
    }
}
