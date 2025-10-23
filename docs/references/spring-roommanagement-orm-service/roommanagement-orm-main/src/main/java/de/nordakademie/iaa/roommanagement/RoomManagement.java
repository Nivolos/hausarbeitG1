package de.nordakademie.iaa.roommanagement;

import java.util.ArrayList;
import java.util.List;

import de.nordakademie.iaa.roommanagement.action.*;
import de.nordakademie.iaa.roommanagement.application.ApplicationController;

/**
 * Main class of the room management application.
 */
public class RoomManagement {

	/**
	 * Runs the application.
	 * 
	 * @param args
	 *            Arguments (not supported).
	 */
	public static void main(String[] args) {
		ApplicationController controller = new ApplicationController();
		// Create list of actions
		List<Action> actions = new ArrayList<Action>();
		actions.add(new ListRoomsAction());
		actions.add(new ShowRoomAction());
		actions.add(new CreateRoomAction());
		actions.add(new UpdateRoomAction());
		actions.add(new DeleteRoomAction());
		actions.add(new ExitAction());

		// configure the controller
		controller.setActions(actions);
		// Show menu
		controller.showMenu();
	}
}
