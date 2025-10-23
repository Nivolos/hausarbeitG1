package de.nordakademie.iaa.roommanagement.action;


import de.nordakademie.iaa.roommanagement.application.ConsoleUtil;
import de.nordakademie.iaa.roommanagement.model.Room;

/**
 * Action to create a {@link Room}.
 */
public class CreateRoomAction extends DatabaseAction {

	/** {@inheritDoc} */
	@Override
	public String getLabel() {
		return "Einen neuen Raum anlegen!";
	}

	/** {@inheritDoc} */
	@Override
	public void execute() throws Exception {
		System.out.println("Geben Sie eine Raumnummer an!");
		int roomNumber = ConsoleUtil.readInt();
		System.out.println("Geben Sie ein Gebäude an!");
		String building = ConsoleUtil.readString();
		System.out.println("Geben Sie die Anzahl Plätze an!");
		int seats = ConsoleUtil.readInt();
		System.out.println("Hat der Raum einen Beamer?");
		boolean beamer = ConsoleUtil.readBoolean();
		// create and save the room
		Room room = new Room();
		room.setBuilding(building);
		room.setRoomNumber(roomNumber);
		room.setSeats(seats);
		room.setPresenterAvailable(beamer);
		room = getRoomDAO().saveRoom(room);
		System.out.printf("Der Raum wurde mit der ID %1$d angelegt!%n",
				room.getId());
	}

}
