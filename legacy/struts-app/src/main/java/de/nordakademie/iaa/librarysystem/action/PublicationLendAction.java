package de.nordakademie.iaa.librarysystem.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Validateable;
import de.nordakademie.iaa.librarysystem.service.exception.PublicationNotAvailableException;
import de.nordakademie.iaa.librarysystem.model.LibraryUser;
import de.nordakademie.iaa.librarysystem.model.LendingProcess;
import de.nordakademie.iaa.librarysystem.model.Publication;
import de.nordakademie.iaa.librarysystem.service.LibraryUserService;
import de.nordakademie.iaa.librarysystem.service.LendingProcessService;
import de.nordakademie.iaa.librarysystem.service.PublicationService;
import de.nordakademie.iaa.librarysystem.service.exception.ServiceException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.struts2.interceptor.validation.SkipValidation;

import java.util.*;
/**
 * Die Klasse PublicationLendAction ist die Action Klasse für die LendingProcessCreateForm.jsp und
 * selectedLendingProcessCreateForm
 * Mit ihr können:
 *      - Publikationen ausgeliehen
 * werden.
 * @author Fabian Rudolf, Jamsin Elvers
 * @version 1.0
 */
public class PublicationLendAction extends ActionSupport {
    private LendingProcessService lendingProcessService;
    private LibraryUserService ibraryUserService;
    private PublicationService publicationService;

    /* Fill selection lists */
    private Map<Long, String> ibraryUserSelectionMap = new HashMap<>();
    private Map<Long, String> publicationSelectionMap = new HashMap<>();

    /* Read user selections */
    private Long selectedLibraryUserId;
    private Long[] selectedPublicationIds;

    // if action is called via search publication
    private Long publicationKey;

    public Boolean validateSelectedPublication(){

        try{
            int stock = publicationService.readPublication(publicationKey).getAvailableStock();
            if(stock==0){
                addActionError(getText("error.stock.is.zero"));
                return false;
            }
        }
        catch (ServiceException e){
            //ignore because it is handled
        }
        if (publicationKey == null) {
            addActionError(getText("error.selection.required"));
            return false;
        }
        return true;
    }
    @SkipValidation
    public String loadLendingProcessCreateFormForSelectedPublicationId(){
        Boolean valid = validateSelectedPublication();
        if(!valid){
            return ERROR;
        }
        fillLibraryUserSelectionMap();
        try {
            Publication publication = publicationService.readPublication(publicationKey);
            publicationSelectionMap.put(publication.getId(),
                    publication.getTitle() + " (" + publication.getInternalId() + ")");
        } catch (ServiceException e){
            addActionError(getText("error.model.not.found"));
            return INPUT;
        }
       return SUCCESS;
    }

    @SkipValidation
    public String loadLendingProcessCreateForm() {
        /* Load ibraryUsers and publications for dropdown selection */
        fillLibraryUserSelectionMap();
        publicationService.findAvailablePublications()
                .forEach(publication -> publicationSelectionMap.put(publication.getId(),
                        publication.getTitle() + " (" + publication.getInternalId() + ")"));
        return SUCCESS;
    }

    private void fillLibraryUserSelectionMap() {
        ibraryUserService.findAllLibraryUsers()
                .forEach(ibraryUser -> ibraryUserSelectionMap.put(ibraryUser.getId(),
                        (ibraryUser.getFirstName() +
                                " " +
                                ibraryUser.getLastName() +
                                ((ibraryUser.getStudentNumber() != null) ? " (" + ibraryUser.getStudentNumber() + ")" : "")
                        )));
    }


    public Boolean validateBeforeLP() {
        if (selectedLibraryUserId == null || selectedLibraryUserId == -1|| selectedPublicationIds.length==0 || selectedPublicationIds == null) {
            addActionError(getText("error.selection.required"));
            return false;
        }
        return true;
    }
    public String lendPublications() {
        Boolean valid = validateBeforeLP();
        if(!valid){
            return INPUT;
        }
        /* Prepare user input */
        selectedPublicationIds = ArrayUtils.removeElement(selectedPublicationIds, -1);
        if (selectedLibraryUserId == -1L) {
            addFieldError("selectedLibraryUserId", "error.lendingprocess.ibraryUser.none");
        }
        selectedLibraryUserId = ((selectedLibraryUserId == -1) ? null : selectedLibraryUserId);

        /* Set ibraryUser from user selections */
        LibraryUser ibraryUser = ibraryUserService.loadLibraryUser(selectedLibraryUserId);

        Boolean success = lendPublicationsHelper(ibraryUser);
        if(!success){
            return INPUT;
        }

        return SUCCESS;
    }

    /**
     * Creates lending processes for those publications that are available
     * @param ibraryUser
     * @return `true` if all publications have been successfully lended and `false` with field errors otherwise
     */
    private Boolean lendPublicationsHelper(LibraryUser ibraryUser) {
        Boolean successFlag = true;
        /* Create 1 lending process for each publication in user selection */
        for (Long publicationId : selectedPublicationIds) {
            LendingProcess lendingProcess = new LendingProcess();

            Publication selectedPublication = publicationService.readPublication(publicationId);
            lendingProcess.setPublication(selectedPublication);
            lendingProcess.setLibraryUser(ibraryUser);

            /* Create new lending process object */
            try {
                lendingProcessService.lendPublication(lendingProcess);
                lendingProcessService.sendStartLendEmail(lendingProcess);
            } catch (PublicationNotAvailableException e) {
                if(successFlag){
                    addActionError(getText("error.publication.notavailable"));
                }
                addActionError(getText(lendingProcess.getPublication().getTitle()));
                successFlag = false;
            }
        }
        return successFlag;
    }

    public LendingProcessService getLendingProcessService() {
        return lendingProcessService;
    }

    public void setLendingProcessService(LendingProcessService lendingProcessService) {
        this.lendingProcessService = lendingProcessService;
    }

    public LibraryUserService getLibraryUserService() {
        return ibraryUserService;
    }

    public void setLibraryUserService(LibraryUserService ibraryUserService) {
        this.ibraryUserService = ibraryUserService;
    }

    public PublicationService getPublicationService() {
        return publicationService;
    }

    public void setPublicationService(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    public Map<Long, String> getLibraryUserSelectionMap() {
        return ibraryUserSelectionMap;
    }

    public void setLibraryUserSelectionMap(Map<Long, String> ibraryUserSelectionMap) {
        this.ibraryUserSelectionMap = ibraryUserSelectionMap;
    }

    public Map<Long, String> getPublicationSelectionMap() {
        return publicationSelectionMap;
    }

    public void setPublicationSelectionMap(Map<Long, String> publicationSelectionMap) {
        this.publicationSelectionMap = publicationSelectionMap;
    }

    public Long getSelectedLibraryUserId() {
        return selectedLibraryUserId;
    }

    public void setSelectedLibraryUserId(Long selectedLibraryUserId) {
        this.selectedLibraryUserId = selectedLibraryUserId;
    }

    public Long[] getSelectedPublicationIds() {
        return selectedPublicationIds;
    }

    public void setSelectedPublicationIds(Long[] selectedPublicationIds) {
        this.selectedPublicationIds = selectedPublicationIds;
    }

    public Long getPublicationKey() {
        return publicationKey;
    }

    public void setPublicationKey(Long publicationKey) {
        this.publicationKey = publicationKey;
    }

}