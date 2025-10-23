package de.nordakademie.iaa.roommgmt.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/rest")
public class RoomManagementApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>(super.getClasses());
        classes.add(RoomResource.class);
        return classes;
    }

}
