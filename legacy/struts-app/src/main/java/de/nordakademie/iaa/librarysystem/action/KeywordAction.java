package de.nordakademie.iaa.librarysystem.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import de.nordakademie.iaa.librarysystem.model.Keyword;
import de.nordakademie.iaa.librarysystem.service.KeywordService;
import de.nordakademie.iaa.librarysystem.service.exception.ServiceException;
import de.nordakademie.iaa.librarysystem.service.exception.ModelUsedException;
/**
 * Die Klasse KeywordAction ist die Action Klasse für das keywordForm.jsp
 * Mit ihr können:
 *      - Schlagwörter angelegt
 *      - Schlagwörter bearbeiten
 *      - Schlagwörter gelöscht
 * werden.
 * @author Lena Bunge, Felix Groer
 * @version 1.0
 */
public class KeywordAction extends ActionSupport {

    private KeywordService keywordService;

    private Keyword keyword;

    private Long keywordKey;

    public String edit() {
        try {
            keyword = keywordService.readKeyword(keywordKey);
        } catch(ServiceException e){
            addActionError(getText("error.model.not.found"));
            return INPUT;
        }
        return SUCCESS;
    }

    public String save() {
        if (keyword.getId() == null) {
            try {
                keywordService.createKeyword(keyword);
                addActionMessage(getText("message.create.ok"));
            } catch (ServiceException e){
                addActionError(getText("error.object.exists"));
                return INPUT;
            }
        } else {
            try {
                keywordService.updateKeyword(keyword);
                addActionMessage(getText("message.update.ok"));
            } catch (ServiceException e){
                addActionError(getText("error.persist.failed"));
                return INPUT;
            }
        }
        return SUCCESS;
    }

    public String delete() throws Exception{
        try {
            keywordService.deleteKeyword(keywordKey);
            addActionMessage(getText("message.delete.ok"));
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
        if (keywordKey == null && keyword == null) {
            addActionError(getText("error.selection.required"));
        }
    }

    public KeywordService getKeywordService() {
        return keywordService;
    }

    public void setKeywordService(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    public Long getKeywordKey() {
        return keywordKey;
    }

    public void setKeywordKey(Long keywordKey) {
        this.keywordKey = keywordKey;
    }

    public Keyword getKeyword() {
        return keyword;
    }

    public void setKeyword(Keyword keyword) {
        this.keyword = keyword;
    }

}