package de.nordakademie.iaa.librarysystem.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Die Klasse LendingProcess enthält alle Exemplarvariablen für den Ausleihprozess.
 * @author Fabian Rudof, Jasmin Elvers
 * @version 1.0
 */
@Entity
public class LendingProcess {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne( cascade = CascadeType.MERGE)
    private LibraryUser libraryUser;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Publication publication;

    @OneToMany(mappedBy = "id", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Reminder> reminders = new ArrayList<>();

    @Temporal(TemporalType.DATE)
    private Date lendingDate;

    @Temporal(TemporalType.DATE)
    private Date plannedReturnDate;

    @Temporal(TemporalType.DATE)
    private Date actualReturnDate;

    @Column(nullable = false)
    private Boolean isReturned = false;

    @Column(nullable = false)
    private Boolean isLost = false;

    @Column(nullable = false)
    private Integer extensionCount = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LibraryUser getLibraryUser() {
        return libraryUser;
    }

    public void setLibraryUser(LibraryUser libraryUser) {
        this.libraryUser = libraryUser;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public Date getLendingDate() {
        return lendingDate;
    }

    public void setLendingDate(Date lendingDate) {
        this.lendingDate = lendingDate;
    }

    public Date getPlannedReturnDate() {
        return plannedReturnDate;
    }

    public void setPlannedReturnDate(Date plannedReturnDate) {
        this.plannedReturnDate = plannedReturnDate;
    }

    public Date getActualReturnDate() {
        return actualReturnDate;
    }

    public void setActualReturnDate(Date actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    public Boolean getReturned() {
        return isReturned;
    }

    public void setReturned(Boolean returned) {
        isReturned = returned;
    }

    public Integer getExtensionCount() {
        return extensionCount;
    }

    public void setExtensionCount(Integer extensionCount) {
        this.extensionCount = extensionCount;
    }

    public Boolean getLost() {
        return isLost;
    }

    public void setLost(Boolean isLost) {
        this.isLost = isLost;
    }

    public List<Reminder> getReminders() {
        return reminders;
    }

    public Reminder getLatestReminder(){
        return getReminders().stream().max(Reminder::compareTo).get();
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }
}