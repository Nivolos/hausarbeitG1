package de.nordakademie.iaa.librarysystem.action;

import com.opensymphony.xwork2.ActionSupport;
import de.nordakademie.iaa.librarysystem.model.Author;
import de.nordakademie.iaa.librarysystem.service.AuthorService;
import de.nordakademie.iaa.librarysystem.service.exception.ServiceException;
import de.nordakademie.iaa.librarysystem.service.exception.ModelUsedException;
/**
 * Die Klasse AuthorAction ist die Action Klasse für das authorForm.jsp
 * Mit ihr können:
 *      - Autoren angelegt
 *      - Autoren bearbeiten
 *      - Autoren gelöscht
 * werden.
 * @author Lena Bunge, Felix Groer
 * @version 1.0
 */
public class AuthorAction extends ActionSupport {

    private AuthorService authorService;

    private Author author;

    private Long authorKey;

    public String edit() {
        try {
            author = authorService.loadAuthor(authorKey);
        } catch(ServiceException e){
            addActionError(getText("error.model.not.found"));
            return INPUT;
        }
        return SUCCESS;
    }

    public String save() {
        if (author.getId() == null) {
            try{
                authorService.createAuthor(author);
            } catch(ServiceException e){
                addActionError(getText("error.object.exists"));
                return INPUT;
            }
        } else {
            try{
                authorService.updateAuthor(author);
            } catch(ServiceException e){
                addActionError(getText("error.persist.failed"));
                return INPUT;
            }
        }
        return SUCCESS;
    }

    public String delete() throws Exception {
        try {
            authorService.deleteAuthor(authorKey);
        } catch (ServiceException e) {
            addActionError(getText("error.model.not.found"));
            return INPUT;
        } catch (ModelUsedException e){
            addActionError(getText("error.object.is.used"));
            return INPUT;
        }
        return SUCCESS;
    }

    @Override
    public void validate() {
        if (authorKey == null && author == null) {
            addActionError(getText("error.selection.required"));
        }
    }

    public AuthorService getAuthorService() {
        return authorService;
    }

    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    public Long getAuthorKey() {
        return authorKey;
    }

    public void setAuthorKey(Long authorKey) {
        this.authorKey = authorKey;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

}