package de.nordakademie.iaa.roommanagement.dao;

/**
 * The dao factory. This class cannot be instantiated but provides static
 * methods to create instances of the daos.
 * 
 * @author Stefan Reichert
 */
public final class DAOFactory {

	/**
	 * Hidden default constructor.
	 */
	private DAOFactory() {
		// Do nothing
	}

	/**
	 * Returns the {@link RoomDAO} instance.
	 * 
	 * @return an instance of the DAO.
	 */
	public static RoomDAO createRoomDAO() {
		return new RoomDAO();
	}
}
