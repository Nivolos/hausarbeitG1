package de.nordakademie.iaa.roommanagement.dao;

/**
 * Exception that indicates a duplicate room.
 */
public class RoomAlreadyExistsException extends RuntimeException {

	/** the serial version uid. */
	private static final long serialVersionUID = -6080027662783731790L;

	/**
	 * Constructor for {@link RoomAlreadyExistsException}.
	 */
	public RoomAlreadyExistsException() {
		super("In dem Gebäude ist bereits ein Raum mit der Nummer vorhanden!");
	}

}
