package de.nordakademie.iaa.roommanagement.action;

/**
 * Abstract base type for all de.nordakademie.roommgmt.action classes
 */
public abstract class Action {
	/**
	 * Returns the label of the de.nordakademie.roommgmt.action that is shown in the menu.
	 *
	 * @return a string.
	 */
	public abstract String getLabel();

	/**
	 * Execute the de.nordakademie.roommgmt.action's process logic.
	 *
	 * @throws Exception if something went wrong during execution.
	 */
	public abstract void execute() throws Exception;
}
