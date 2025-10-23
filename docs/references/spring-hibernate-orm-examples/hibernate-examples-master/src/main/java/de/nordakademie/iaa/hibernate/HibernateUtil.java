package de.nordakademie.iaa.hibernate;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * HibernateUtil creates entity manager factory
 *
 * @author Lennart Gamradt
 */
public class HibernateUtil {

    private static final EntityManagerFactory EMF = Persistence.createEntityManagerFactory("NAK_DB");

    private HibernateUtil(){
        // private constructor to avoid instantiation
    }

    public static EntityManagerFactory getEntityManagerFactory(){
        return EMF;
    }
}
