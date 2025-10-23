package de.nordakademie.iaa.librarysystem.model;

import org.hibernate.annotations.NaturalId;
import javax.persistence.*;

/**
 * Die Klasse LibrarySystemSetting wird für die Einstellung aller Systemeinstellungen verwendet.
 * Hier können alle Konstanten eingestellt werden und müssen auf der DB eingesetzt werden.
 * @author Lena Bunge, Felix Groer
 * @version 1.0
 */
@Entity
public class LibrarySystemSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NaturalId
    @Column(nullable = false)
    private String parameterKey;

    private String parameterValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParameterKey() {
        return parameterKey;
    }

    public void setParameterKey(String key) {
        this.parameterKey = key;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String value) {
        this.parameterValue = value;
    }
}
