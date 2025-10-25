package de.nordakademie.iaa.librarysystem.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import de.nordakademie.iaa.librarysystem.model.LendingProcess;
import de.nordakademie.iaa.librarysystem.model.Publication;
import de.nordakademie.iaa.librarysystem.service.LendingProcessService;
import de.nordakademie.iaa.librarysystem.service.PublicationService;
import de.nordakademie.iaa.librarysystem.service.exception.ServiceException;

import java.util.List;
/**
 * Die Klasse DetailPublicationAction ist die Action Klasse für das publicationDetail.jsp
 * Mit ihr kann die Detailsicht der Publikation eingesehen werden.
 * werden.
 * @author Fabian Rudolf, Jasmin Elvers
 * @version 1.0
 */
public class DetailPublicationAction extends ShiroBaseAction {

    private Publication publication;
    private PublicationService publicationService;
    private Long publicationKey;
    private LendingProcessService lendingProcessService;
    private List<LendingProcess> lendingProcessList;
    private String currentLocale;

    public String showDetails() throws Exception{
        currentLocale = ActionContext.getContext().getLocale().getLanguage();
        Boolean valid = validateInput();
        if(!valid){
            return ERROR;
        }
        try{
            publication = publicationService.readPublication(publicationKey);
            lendingProcessList = lendingProcessService
                    .findCurrentLendingProcessesByPublication(publication);
        } catch(ServiceException e){
            addActionError(getText("error.model.not.found"));
            return INPUT;
        }
        return SUCCESS;
    }

    public Boolean validateInput(){
        if (publicationKey == null || publication == null) {
            addActionError(getText("error.selection.required"));
            return false;
        }
        return true;
    }

    public PublicationService getPublicationService() {
        return publicationService;
    }

    public void setPublicationService(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    public Long getPublicationKey() {
        return publicationKey;
    }

    public void setPublicationKey(Long publicationKey) {
        this.publicationKey = publicationKey;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public LendingProcessService getLendingProcessService() {
        return lendingProcessService;
    }

    public void setLendingProcessService(LendingProcessService lendingProcessService) {
        this.lendingProcessService = lendingProcessService;
    }

    public List<LendingProcess> getLendingProcessList() {
        return lendingProcessList;
    }

    public void setLendingProcessList(List<LendingProcess> lendingProcessList) {
        this.lendingProcessList = lendingProcessList;
    }
    public String getCurrentLocale() {
        return currentLocale;
    }

    public void setCurrentLocale(String currentLocale) {
        this.currentLocale = currentLocale;
    }

}