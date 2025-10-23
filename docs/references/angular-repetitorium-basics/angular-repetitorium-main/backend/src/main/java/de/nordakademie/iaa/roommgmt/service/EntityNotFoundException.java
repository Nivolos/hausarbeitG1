package de.nordakademie.iaa.roommgmt.service;

/**
 * Exception throws if an entity could not be found.
 *
 * @author Stephan Anft
 */
public class EntityNotFoundException extends Exception {

    /**
     * Serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public EntityNotFoundException() {
        super();
    }

    /**
     * Constructor with message.
     *
     * @param message The message.
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
