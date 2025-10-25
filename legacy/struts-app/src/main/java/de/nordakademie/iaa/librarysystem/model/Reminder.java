package de.nordakademie.iaa.librarysystem.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;
/**
 * Die Klasse Reminder repräsentiert eine Mahnung und enthält alle zugehörigen Exemplarvariablen.
 * @author Fabian Rudof, Jasmin Elvers
 * @version 1.0
 */
@Entity
public class Reminder implements Comparable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lendingProcessId")
    private LendingProcess lendingProcess;

    @Temporal(TemporalType.DATE)
    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LendingProcess getLendingProcess() {
        return lendingProcess;
    }

    public void setLendingProcess(LendingProcess lendingProcess) {
        this.lendingProcess = lendingProcess;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int compareTo(Object o) {
        if (o == null || getClass() != o.getClass()){
            return 1;
        }
        Reminder other = (Reminder) o;
        return this.date.compareTo(other.date);
    }


}