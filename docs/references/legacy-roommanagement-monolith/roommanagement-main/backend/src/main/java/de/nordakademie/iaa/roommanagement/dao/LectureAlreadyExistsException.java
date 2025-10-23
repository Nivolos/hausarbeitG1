package de.nordakademie.iaa.roommanagement.dao;

/**
 * Exception that indicates a duplicate room.
 */
public class LectureAlreadyExistsException extends RuntimeException {

    /**
     * the serial version uid.
     */
    private static final long serialVersionUID = -6080027662783731790L;

    /**
     * Constructor for {@link LectureAlreadyExistsException}.
     */
    public LectureAlreadyExistsException() {
        super("Die Veranstaltung des Kurses im gewählten Raum zu dieser Uhrzeit ist bereits vorhanden!");
    }

}
