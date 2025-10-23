package de.nordakademie.iaa.roommanagement.application;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Provides access to the JPA EntityManager via an EntityManagerFactory
 */
public class HibernateUtil {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("NAK_DB");
    private static EntityManager CURRENT_ENTITY_MANAGER;

    private HibernateUtil() {
        // private constructor to avoid instantiation
    }

    public static void shutdownJPA(){
        ENTITY_MANAGER_FACTORY.close();
    }

    public static void createEntityManager() {
        if (CURRENT_ENTITY_MANAGER != null) {
            throw new IllegalStateException("there already exists an entity manager");
        }
        CURRENT_ENTITY_MANAGER = ENTITY_MANAGER_FACTORY.createEntityManager();

    }

    public static EntityManager getCurrentEntityManager() {
        if (CURRENT_ENTITY_MANAGER == null) {
            throw new IllegalStateException("no entity manager available");
        }
        return CURRENT_ENTITY_MANAGER;
    }

    public static void closeCurrentEntityManager() {
        getCurrentEntityManager().close();
        CURRENT_ENTITY_MANAGER = null;
    }
}
