package de.nordakademie.iaa.librarysystem.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import de.nordakademie.iaa.librarysystem.model.Publication;
import de.nordakademie.iaa.librarysystem.service.PublicationService;

import java.util.List;

public class PublicationListAction implements Action {

    private PublicationService publicationService;

    // The list of publications which gets loaded from the service during execution
    private List<Publication> publicationList;

    private String currentLocale;

    @Override
    public String execute() throws Exception {
        currentLocale = ActionContext.getContext().getLocale().getLanguage();
        return SUCCESS;
    }

    public List<Publication> getPublicationList() {
        return publicationList;
    }

    public void setPublicationList(List<Publication> publicationList) {
        this.publicationList = publicationList;
    }

    public PublicationService getPublicationService() {
        return publicationService;
    }

    public void setPublicationService(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    public String getCurrentLocale() {
        return currentLocale;
    }

    public void setCurrentLocale(String currentLocale) {
        this.currentLocale = currentLocale;
    }
}