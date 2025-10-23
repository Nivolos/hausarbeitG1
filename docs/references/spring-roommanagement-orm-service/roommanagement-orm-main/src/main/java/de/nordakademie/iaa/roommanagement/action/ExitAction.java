package de.nordakademie.iaa.roommanagement.action;

import de.nordakademie.iaa.roommanagement.application.HibernateUtil;

public class ExitAction extends Action {

	/** {@inheritDoc} */
	@Override
	public String getLabel() {
		return "Beenden!";
	}

	/** {@inheritDoc} */
	@Override
	public void execute() throws Exception {
		HibernateUtil.shutdownJPA();
		System.out.println("Raumverwaltung beendet!");
		System.exit(0);
	}

}
