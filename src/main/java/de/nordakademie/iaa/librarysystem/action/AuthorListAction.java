package de.nordakademie.iaa.librarysystem.action;

import com.opensymphony.xwork2.Action;
import de.nordakademie.iaa.librarysystem.model.Author;
import de.nordakademie.iaa.librarysystem.service.AuthorService;

import java.util.List;
/**
 * Die Klasse AuthorListAction ist die Action Klasse für authorList.jsp
 * Mit ihr kann die Authoren Liste erstellt werden.
 * @author Lena Bunge, Felix Groer
 * @version 1.0
 */
public class AuthorListAction implements Action {

    private AuthorService authorService;

    // List of users that needs to be loaded from the services
    private List<Author> authorList;

    @Override
    public String execute() throws Exception {
        authorList = authorService.findAllAuthors();
        return SUCCESS;
    }

    public List<Author> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }

    public AuthorService getUserService() {
        return authorService;
    }

    public void setUserService(AuthorService authorService) {
        this.authorService = authorService;
    }

}