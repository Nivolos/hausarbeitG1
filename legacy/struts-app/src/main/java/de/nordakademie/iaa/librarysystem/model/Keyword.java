package de.nordakademie.iaa.librarysystem.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Objects;
/**
 * Die Klasse Keyword enthält alle Exemplarvariablen der Schlagworte und verhindert,
 * dass Schlagworte doppelt angelegt werden können.
 * @author Fabian Rudof, Jasmin Elvers
 * @version 1.0
 */
@Entity
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String valueDe;

    private String valueEn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValueDe() {
        return valueDe;
    }

    public void setValueDe(String valueDe) {
        this.valueDe = valueDe;
    }

    public String getValueEn() {
        return valueEn;
    }

    public void setValueEn(String valueEn) {
        this.valueEn = valueEn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Keyword keyword = (Keyword) o;
        return Objects.equals(getValueDe(), keyword.getValueDe()) && Objects.equals(getValueEn(), keyword.getValueEn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValueDe(), getValueEn());
    }
}