package de.nordakademie.iaa.librarysystem.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import de.nordakademie.iaa.librarysystem.model.PublicationType;
import de.nordakademie.iaa.librarysystem.service.PublicationTypeService;

import java.util.List;
/**
 * Die Klasse PublicationTypeListAction ist die Action Klasse für publicationTypeList.jsp
 * Mit ihr kann die Punlikationstypen Liste erstellt werden.
 * @author Fabian Rudolf, Jasmin Elvers
 * @version 1.0
 */
public class PublicationTypeListAction implements Action {

    private PublicationTypeService publicationTypeService;

    // The list of publicationTypes which gets loaded from the service during execution
    private List<PublicationType> publicationTypeList;

    private String currentLocale;

    @Override
    public String execute() {
        currentLocale = ActionContext.getContext().getLocale().getLanguage();
        publicationTypeList = publicationTypeService.findAllPublicationTypes();
        return SUCCESS;
    }

    public List<PublicationType> getPublicationTypeList() {
        return publicationTypeList;
    }

    public void setPublicationTypeList(List<PublicationType> publicationTypeList) {
        this.publicationTypeList = publicationTypeList;
    }

    public PublicationTypeService getPublicationTypeService() {
        return publicationTypeService;
    }

    public void setPublicationTypeService(PublicationTypeService publicationTypeService) {
        this.publicationTypeService = publicationTypeService;
    }

    public String getCurrentLocale() {
        return currentLocale;
    }

    public void setCurrentLocale(String currentLocale) {
        this.currentLocale = currentLocale;
    }
}