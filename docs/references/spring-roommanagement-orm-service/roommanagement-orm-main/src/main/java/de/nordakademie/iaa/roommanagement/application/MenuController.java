package de.nordakademie.iaa.roommanagement.application;


import de.nordakademie.iaa.roommanagement.action.Action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Util class for reading and writing to the system console.
 */
class MenuController {
	/**
	 * The list of menu actions.
	 */
	private final List<Action> menuActions;

	/**
	 * Constructor with menu actions.
	 * 
	 * @param menuActions
	 *            The list of menu actions.
	 */
	MenuController(List<Action> menuActions) {
		this.menuActions = menuActions;
	}

	/**
	 * Prints out the menu options to the console.
	 */
	public void printMenu() {
		// Print out the menu options
		System.out.println("**********************************************");
		System.out.println("Was wollen Sie tun?");
		int index = 0;
		for (Action action : menuActions) {
			System.out.printf("(%1$d) %2$s %n", index++, action.getLabel());
		}
		System.out.println("**********************************************");
	}

	/**
	 * Reads the user's menu choice from the console.
	 * 
	 * @return the chosen de.nordakademie.roommgmt.action.
	 */
	public Action readMenuChoice() {
		// Get the console
		// Read user input
		int index = -1;
		while (index == -1) {
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(System.in));
				String line = reader.readLine();
				index = Integer.valueOf(line);
				if (index < 0 || index >= menuActions.size()) {
					System.out
							.println("Bitte geben Sie eine gültige Auswahl ein:");
					index = -1;
				}
			} catch (Exception e) {
				// No number entered by user
				System.out.println("Bitte geben Sie eine gültige Auswahl ein:");
			}
		}
		return menuActions.get(index);
	}
}
