package de.nordakademie.iaa.roommanagement.action;

import de.nordakademie.iaa.roommanagement.dao.DAOFactory;
import de.nordakademie.iaa.roommanagement.dao.RoomDAO;

/**
 * Action that is provided with a transaction.
 */
public abstract class DatabaseAction extends Action {

	/**
	 * Returns the {@link RoomDAO} instance.
	 * 
	 * @return an instance of the service.
	 */
	public RoomDAO getRoomDAO() {
		return DAOFactory.createRoomDAO();
	}
}
