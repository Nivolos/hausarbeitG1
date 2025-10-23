package de.nordakademie.iaa.roommanagement.application;

import java.util.List;

import de.nordakademie.iaa.roommanagement.action.Action;
import de.nordakademie.iaa.roommanagement.action.DatabaseAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The application controller.
 */
public class ApplicationController {

    /*
     * The logger instance to be used for logging to the console
     */
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationController.class);

    /**
     * The list of actions.
     */
    private List<Action> actions;

    /**
     * Prints out the menu to the standard output.
     */
    public void showMenu() {
        assert actions != null;
        MenuController menuController = new MenuController(actions);
        // noinspection InfiniteLoopStatement
        while (true) {
            // Print out all available actions
            menuController.printMenu();
            // Read user input
            Action action = menuController.readMenuChoice();
            if (action instanceof DatabaseAction) {
                executeDatabaseAction(action);
            } else {
                executeAction(action);
            }
        }
    }

    private void executeAction(Action action) {
        try {
            action.execute();
        } catch (Exception exception) {
            LOG.error("Fehler aufgetreten: "
                    + exception.getMessage());
        }
    }

    private void executeDatabaseAction(Action action) {
        // EntityManager and TX support required
        HibernateUtil.createEntityManager();
        HibernateUtil.getCurrentEntityManager().getTransaction().begin();

        try {
            action.execute();
            HibernateUtil.getCurrentEntityManager().getTransaction().commit();
        } catch (Exception exception) {
            if (HibernateUtil.getCurrentEntityManager().getTransaction().isActive()) {
                HibernateUtil.getCurrentEntityManager().getTransaction().rollback();
            }
            LOG.error("Fehler aufgetreten: "
                    + exception.getMessage());
        } finally {
            // close entity manager no matter what result we got
            HibernateUtil.closeCurrentEntityManager();
        }
    }

    /**
     * Returns the list of actions.
     *
     * @return a List of Action elements.
     */
    public List<Action> getActions() {
        return actions;
    }

    /**
     * Sets the list of actions.
     *
     * @param actions The list of Action elements to set.
     */
    public void setActions(List<Action> actions) {
        this.actions = actions;
    }
}
