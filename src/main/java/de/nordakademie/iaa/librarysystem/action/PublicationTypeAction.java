package de.nordakademie.iaa.librarysystem.action;

import com.opensymphony.xwork2.ActionSupport;
import de.nordakademie.iaa.librarysystem.model.PublicationType;
import de.nordakademie.iaa.librarysystem.service.exception.ModelUsedException;
import de.nordakademie.iaa.librarysystem.service.exception.PublicationTypeNameExistsException;
import de.nordakademie.iaa.librarysystem.service.PublicationTypeService;
import de.nordakademie.iaa.librarysystem.service.exception.ServiceException;
/**
 * Die Klasse PublicationTypeAction ist die Action Klasse für das publicationTypeForm.jsp
 * Mit ihr können:
 *      - Publikationstypen angelegt
 *      - Publikationstypen bearbeiten
 *      - Publikationstypen gelöscht
 * werden.
 * @author Fabian Rudolf, Jasmin Elvers
 * @version 1.0
 */
public class PublicationTypeAction extends ActionSupport {

    private PublicationTypeService publicationTypeService;

    private PublicationType publicationType;

    private Long publicationTypeKey;

    @Override
    public void validate() {
        if (publicationTypeKey == null && publicationType == null) {
            addActionError(getText("error.selection.required"));
        }
    }

    public String edit() throws Exception {
        try {
            publicationType = publicationTypeService.readPublicationType(publicationTypeKey);
        } catch (ServiceException e){
            addActionError(getText("error.model.not.found"));
            addActionError(String.valueOf(publicationTypeKey));
            return INPUT;
        }
        return SUCCESS;
    }

    public String save() throws Exception{
        if (publicationType.getId() == null) {
            try {
                publicationTypeService.createPublicationType(publicationType);
                addActionMessage(getText("message.create.ok"));
            } catch (ServiceException e){
                addActionError(getText("error.object.exists"));
                return INPUT;
            }
        } else {
            try {
                publicationTypeService.updatePublicationType(publicationType);
                addActionMessage(getText("message.update.ok"));
            } catch (ServiceException e){
                addActionError(getText("error.persist.failed"));
                return INPUT;
            }
        }
        return SUCCESS;
    }

    public String delete() throws Exception {
        try {
            publicationTypeService.deletePublicationType(publicationTypeKey);
            addActionMessage(getText("message.delete.ok"));
        } catch (ServiceException e) {
            addActionError(getText("error.publicationtype.delete.failed"));
            return INPUT;
        } catch (ModelUsedException e){
            addActionError(getText("error.object.is.used"));
            return INPUT;
        }
        return SUCCESS;
    }

    public PublicationTypeService getPublicationTypeService() {
        return publicationTypeService;
    }

    public void setPublicationTypeService(PublicationTypeService publicationTypeService) {
        this.publicationTypeService = publicationTypeService;
    }

    public Long getPublicationTypeKey() {
        return publicationTypeKey;
    }

    public void setPublicationTypeKey(Long publicationTypeKey) {
        this.publicationTypeKey = publicationTypeKey;
    }

    public PublicationType getPublicationType() {
        return publicationType;
    }

    public void setPublicationType(PublicationType publicationType) {
        this.publicationType = publicationType;
    }

}