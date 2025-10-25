package de.nordakademie.iaa.librarysystem.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Die Klasse Role repräsentiert eine Nutzerrolle und enthält alle zugehörigen Exemplarvariablen.
 * @author Fabian Rudof, Jasmin Elvers
 * @version 1.0
 */
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
