package de.nordakademie.iaa.librarysystem.action;

import com.opensymphony.xwork2.Action;
import de.nordakademie.iaa.librarysystem.model.LibraryUser;
import de.nordakademie.iaa.librarysystem.service.LibraryUserService;

import java.util.List;
/**
 * Die Klasse LibraryUserListAction ist die Action Klasse für libraryUserList.jsp
 * Mit ihr kann die Ausleiher Liste erstellt werden.
 * @author Fabian Rudolf, Jasmin Elvers
 * @version 1.0
 */
public class LibraryUserListAction implements Action {

    private LibraryUserService libraryUserService;

    // List of users that needs to be loaded from the services
    private List<LibraryUser> libraryUserList;

    @Override
    public String execute() throws Exception {
        libraryUserList = libraryUserService.findAllLibraryUsers();
        return SUCCESS;
    }

    public List<LibraryUser> getLibraryUserList() {
        return libraryUserList;
    }

    public void setLibraryUserList(List<LibraryUser> libraryUserList) {
        this.libraryUserList = libraryUserList;
    }

    public LibraryUserService getUserService() {
        return libraryUserService;
    }

    public void setUserService(LibraryUserService libraryUserService) {
        this.libraryUserService = libraryUserService;
    }
}
