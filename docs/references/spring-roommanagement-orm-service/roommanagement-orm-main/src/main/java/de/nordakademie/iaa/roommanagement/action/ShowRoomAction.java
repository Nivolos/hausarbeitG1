package de.nordakademie.iaa.roommanagement.action;

import de.nordakademie.iaa.roommanagement.application.ConsoleUtil;
import de.nordakademie.iaa.roommanagement.model.Room;

/**
 * Action to show a single {@link Room}.
 */
public class ShowRoomAction extends DatabaseAction {
	
	/** {@inheritDoc} */
	@Override
	public String getLabel() {
		return "Einen Raum anzeigen!";
	}

	/** {@inheritDoc} */
	@Override
	public void execute() throws Exception {
		System.out.println("Geben Sie die Raum ID an!");
		long roomId = ConsoleUtil.readLong();
		Room room = getRoomDAO().loadRoom(roomId);
		System.out.printf("Raum mit ID %1$d:%n", room.getId());
		System.out
				.printf(" - Gebäude: %1$s, Raumnummer: %2$d, Plätze: %3$d, Beamer [%4$b]%n",
						room.getBuilding(), room.getRoomNumber(),
						room.getSeats(), room.isPresenterAvailable());
	}

}
