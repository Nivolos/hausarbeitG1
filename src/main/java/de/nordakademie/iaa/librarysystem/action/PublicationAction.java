package de.nordakademie.iaa.librarysystem.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.Validateable;
import de.nordakademie.iaa.librarysystem.model.*;
import de.nordakademie.iaa.librarysystem.service.*;
import de.nordakademie.iaa.librarysystem.service.exception.ServiceException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.struts2.interceptor.validation.SkipValidation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Die Klasse PublicationAction ist die Action Klasse für das publicationForm.jsp
 * Mit ihr können:
 *      - Publikationen angelegt
 *      - Publikationen bearbeiten
 *      - Publikationen gelöscht
 * werden.
 * @author Fabian Rudolf, Jasmin Elvers
 * @version 1.0
 */
public class PublicationAction extends ActionSupport implements Preparable {
    private PublicationService publicationService;

    private KeywordService keywordService;

    private AuthorService authorService;

    private LendingProcessService lendingProcessService;

    private PublicationTypeService publicationTypeService;

    private HashMap<Long, String> keywordSelectionMap = new HashMap<>();

    private HashMap<Long, String> publicationTypeSelectionMap = new HashMap<>();

    private HashMap<Long, String> authorSelectionMap = new HashMap<>();

    private Publication publication;

    private Long publicationKey;
    private Long[] selectedKeywordIds;

    private Long[] selectedAuthorIds;

    private Long selectedPublicationTypeId;

    private Long existingPublicationTypeId;
    private Long[] existingKeywordIds;
    private Long[] existingAuthorIds;

    private String currentLocale;

    private void findKeywordSelectionList(){
        for(Keyword keyword : keywordService.findAllKeywords()){
            if (currentLocale == "de"){
                keywordSelectionMap.put(keyword.getId(), keyword.getValueDe());
            } else{
                keywordSelectionMap.put(keyword.getId(), keyword.getValueEn());
            }
        }
    }

    private void findPublicationTypeSelectionList(){
        for(PublicationType publicationType : publicationTypeService.findAllPublicationTypes()){
            if (currentLocale == "de"){
                publicationTypeSelectionMap.put(publicationType.getId(), publicationType.getPublicationTypeNameDe());
            } else{
                publicationTypeSelectionMap.put(publicationType.getId(), publicationType.getPublicationTypeNameEn());
            }
        }
    }

    private void findAuthorSelectionList(){
        for(Author author : authorService.findAllAuthors()){
            authorSelectionMap.put(author.getId(), author.getFirstName() + " " + (author.getLastName()));
        }
    }

    @Override
    public void validate(){}

    @Override
    public void prepare() {
        findKeywordSelectionList();
        findPublicationTypeSelectionList();
        findAuthorSelectionList();
        if(publicationKey != null){
            prepareSelectionEdit();
        }
        currentLocale = ActionContext.getContext().getLocale().getLanguage();
    }

    public Boolean prepareSelectionEdit() {
        try{
            publication = publicationService.readPublication(publicationKey);
        } catch (ServiceException e){
            addActionError(getText("error.model.not.found"));
            return false;
        }
        existingPublicationTypeId = publication.getPublicationType().getId();
        existingKeywordIds = publication.getKeywords().stream().map(x -> x.getId()).toArray(Long[]::new);
        existingAuthorIds = publication.getAuthor().stream().map(x -> x.getId()).toArray(Long[]::new);
        return true;
    }

    @SkipValidation
    public String create() throws Exception{
        return SUCCESS;
    }

    private Boolean validateBeforeEditing() {
        if (publicationKey == null) {
            addActionError(getText("error.selection.required"));
            return false;
        }
        return true;
    }

    public String edit() throws Exception {
        Boolean valid = validateBeforeEditing();
        if(!valid){
            return ERROR;
        }
        if(!prepareSelectionEdit()){
            return ERROR;
        }
        return SUCCESS;
    }

    public Boolean validateBeforeSaving() {
        Boolean valid = true;
        if (publication == null) {
            addActionError(getText("error.selection.required"));
            valid = false;
        }
        /* Else check required field validation by publication type */
        else if(publication.getPublicationType() == null) {
            addFieldError("selectedPublicationTypeId", getText("error.publication.form.publicationType.required"));
            valid = false;
        } else {
            if (publication.getPublicationType().getAuthorRequired()
                    && ((publication.getAuthor() == null) || (publication.getAuthor().isEmpty()))
            ) {
                addFieldError("selectedAuthorIds", getText("error.publication.form.author.required"));
                valid = false;
            }
            if(publication.getPublicationType().getPublicationDateRequired()
                    && publication.getPublicationDate() == null){
                addFieldError("publication.publicationDate", getText("error.publication.form.publicationDate.required"));
                valid = false;
            }
            if(publication.getPublicationType().getPublisherRequired()
                    && ((publication.getPublisher() == null) || (publication.getPublisher().isEmpty()))
            ) {
                addFieldError("publication.publisher", getText("error.publication.form.publisher.required"));
                valid = false;
            }
            if(publication.getPublicationType().getIsbnRequired()
                    && ((publication.getIsbn() == null) || (publication.getIsbn().isEmpty()))){
                addFieldError("publication.isbn", getText("error.publication.form.isbn.required"));
                valid = false;
            }
        }
        return valid;
    }

    public String save() throws Exception {
        findUserSelectedKeywords();
        findUserSelectedPublicationType();
        findUserSelectedAuthors();
        Boolean valid = validateBeforeSaving();
        if(!valid){
            return INPUT;
        }
        if (publication.getId() == null) {
            try{
                publicationService.createPublication(publication);
                addActionMessage(getText("message.create.ok"));
            } catch (ServiceException e){
                addActionError(getText("error.create.failed"));
                return INPUT;
            }
        } else {
            try{
                publicationService.updatePublication(publication);
                addActionMessage(getText("message.update.ok"));
            } catch (ServiceException e){
                addActionError(getText("error.update.failed"));
                return INPUT;
            }
        }
        return SUCCESS;
    }

    public String delete() throws Exception {
        Boolean valid = validateBeforeEditing();
        List<LendingProcess> used_list = lendingProcessService.findCurrentLendingProcessesByPublication(publication);
        if(!valid || !used_list.isEmpty()){
            addActionMessage(getText("error.object.is.used"));
            return ERROR;
        }
        try{
            publicationService.deletePublication(publicationKey);
            addActionMessage(getText("message.delete.ok"));
        } catch (ServiceException e){
            addActionError(getText("error.delete.failed"));
            return INPUT;
        }
        return SUCCESS;
    }

    //Helpers to get all selected values
    private void findUserSelectedKeywords(){
        selectedKeywordIds = ArrayUtils.removeElement(selectedKeywordIds, -1);
        List<Keyword> selectedKeywords = new ArrayList<>();
        for(Long id : selectedKeywordIds){
            selectedKeywords.add(keywordService.readKeyword(id));
        }
        publication.setKeywords(selectedKeywords);
    }

    private void findUserSelectedPublicationType(){
        if (selectedPublicationTypeId != -1L) {
            publication.setPublicationType(publicationTypeService.readPublicationType(selectedPublicationTypeId));
        }
    }

    private void findUserSelectedAuthors(){
        selectedAuthorIds = ArrayUtils.removeElement(selectedAuthorIds, -1);
        List<Author> selectedAuthors = new ArrayList<Author>();
        for(Long id : selectedAuthorIds){
            selectedAuthors.add(authorService.loadAuthor(id));
        }
        publication.setAuthor(selectedAuthors);
    }

    public PublicationService getPublicationService() {
        return publicationService;
    }

    public void setPublicationService(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    public KeywordService getKeywordService() {
        return keywordService;
    }

    public void setKeywordService(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    public AuthorService getAuthorService() {
        return authorService;
    }

    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    public PublicationTypeService getPublicationTypeService() {
        return publicationTypeService;
    }

    public void setPublicationTypeService(PublicationTypeService publicationTypeService) {
        this.publicationTypeService = publicationTypeService;
    }

    public HashMap<Long, String> getKeywordSelectionMap() {
        return keywordSelectionMap;
    }

    public void setKeywordSelectionMap(HashMap<Long, String> keywordSelectionMap) {
        this.keywordSelectionMap = keywordSelectionMap;
    }

    public HashMap<Long, String> getPublicationTypeSelectionMap() {
        return publicationTypeSelectionMap;
    }

    public void setPublicationTypeSelectionMap(HashMap<Long, String> publicationTypeSelectionMap) {
        this.publicationTypeSelectionMap = publicationTypeSelectionMap;
    }

    public HashMap<Long, String> getAuthorSelectionMap() {
        return authorSelectionMap;
    }

    public void setAuthorSelectionMap(HashMap<Long, String> authorSelectionMap) {
        this.authorSelectionMap = authorSelectionMap;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public Long getPublicationKey() {
        return publicationKey;
    }

    public void setPublicationKey(Long publicationKey) {
        this.publicationKey = publicationKey;
    }

    public Long[] getSelectedKeywordIds() {
        return selectedKeywordIds;
    }

    public void setSelectedKeywordIds(Long[] selectedKeywordIds) {
        this.selectedKeywordIds = selectedKeywordIds;
    }

    public Long[] getSelectedAuthorIds() {
        return selectedAuthorIds;
    }

    public void setSelectedAuthorIds(Long[] selectedAuthorIds) {
        this.selectedAuthorIds = selectedAuthorIds;
    }

    public Long getSelectedPublicationTypeId() {
        return selectedPublicationTypeId;
    }

    public void setSelectedPublicationTypeId(Long selectedPublicationTypeId) {
        this.selectedPublicationTypeId = selectedPublicationTypeId;
    }

    public Long getExistingPublicationTypeId() {
        return existingPublicationTypeId;
    }

    public void setExistingPublicationTypeId(Long existingPublicationTypeId) {
        this.existingPublicationTypeId = existingPublicationTypeId;
    }

    public Long[] getExistingKeywordIds() {
        return existingKeywordIds;
    }

    public void setExistingKeywordIds(Long[] existingKeywordIds) {
        this.existingKeywordIds = existingKeywordIds;
    }

    public Long[] getExistingAuthorIds() {
        return existingAuthorIds;
    }

    public void setExistingAuthorIds(Long[] existingAuthorIds) {
        this.existingAuthorIds = existingAuthorIds;
    }

    public LendingProcessService getLendingProcessService() {
        return lendingProcessService;
    }

    public void setLendingProcessService(LendingProcessService lendingProcessService) {
        this.lendingProcessService = lendingProcessService;
    }
}