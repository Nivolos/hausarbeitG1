package de.nordakademie.iaa.librarysystem.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
/**
 * Die Klasse LibraryUser repräsentiert einen Ausleiher der Bibliothek.
 * Es wird verhindert, dass Ausleiher doppelt angelegt werden können.
 * @author Fabian Rudof, Jasmin Elvers
 * @version 1.0
 */
@Entity
public class LibraryUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private Integer studentNumber;

    private String password;

    @ManyToMany
    private List<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LibraryUser libraryUser = (LibraryUser) o;
        return Objects.equals(getEmail(), libraryUser.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail());
    }


}
