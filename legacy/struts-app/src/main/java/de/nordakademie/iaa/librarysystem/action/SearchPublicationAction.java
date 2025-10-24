package de.nordakademie.iaa.librarysystem.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;
import de.nordakademie.iaa.librarysystem.model.Author;
import de.nordakademie.iaa.librarysystem.model.Keyword;
import de.nordakademie.iaa.librarysystem.model.Publication;
import de.nordakademie.iaa.librarysystem.model.PublicationType;
import de.nordakademie.iaa.librarysystem.service.AuthorService;
import de.nordakademie.iaa.librarysystem.service.KeywordService;
import de.nordakademie.iaa.librarysystem.service.PublicationService;
import de.nordakademie.iaa.librarysystem.service.PublicationTypeService;
import de.nordakademie.iaa.librarysystem.service.exception.ServiceException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
/**
 * Die Klasse SearchPublicationAction ist die Action Klasse für searchPublication.jsp
 * Mit ihr kann die Publikationssuche Liste erstellt werden.
 * @author Lena Bunge, Felix Groer
 * @version 1.0
 */
public class SearchPublicationAction extends ShiroBaseAction implements Preparable {

    private PublicationService publicationService;

    private AuthorService authorService;

    private KeywordService keywordService;

    private PublicationTypeService publicationTypeService;

    private HashMap<Long, String> keywordSelectionMap = new HashMap<>();

    private HashMap<Long, String> publicationTypeSelectionMap = new HashMap<>();

    private HashMap<Long, String> authorSelectionMap = new HashMap<>();
    private Publication publication;

    private List<Publication> publicationList;

    private Long publicationKey;
    private Long[] selectedKeywordIds;

    private Long[] selectedAuthorIds;
    private Long[] selectedPublicationTypeIds;

    private String currentLocale;

    private List<PublicationType> userSelectedPublicationTypes = new ArrayList<>();

    private void fillKeywordSelectionList(){
        for(Keyword keyword : keywordService.findAllKeywords()){
            if(currentLocale == "de"){
                keywordSelectionMap.put(keyword.getId(), keyword.getValueDe());
            } else{
                keywordSelectionMap.put(keyword.getId(), keyword.getValueEn());
            }
        }
    }

    private void fillAuthorSelectionList(){
        for(Author author : authorService.findAllAuthors()){
            authorSelectionMap.put(author.getId(), author.getFirstName()+ " " + author.getLastName());
        }
    }

    private void fillPublicationTypeList(){
        for(PublicationType publicationType : publicationTypeService.findAllPublicationTypes()){
            if(currentLocale == "de"){
                publicationTypeSelectionMap.put(publicationType.getId(), publicationType.getPublicationTypeNameDe());
            } else{
                publicationTypeSelectionMap.put(publicationType.getId(), publicationType.getPublicationTypeNameEn());
            }
        }
    }

    private void fillUserSelectedKeywords(){
        List<Keyword> selectedKeywords = new ArrayList<Keyword>();
        for(Long id : selectedKeywordIds){
            try {
                selectedKeywords.add(keywordService.readKeyword(id));
            } catch (ServiceException e){
                addFieldError("selectedKeywordIds", "error.model.not.found");
                addFieldError("selectedKeywordIds", "ID: " + id);
            }
        }
        publication.setKeywords(selectedKeywords);
    }

    private void fillUserSelectedAuthors(){
        List<Author> selectedAuthors = new ArrayList<>();
        for(Long id : selectedAuthorIds){
            try {
                selectedAuthors.add(authorService.loadAuthor(id));
            } catch (ServiceException e){
                addFieldError("selectedAuthorIds", "error.model.not.found");
                addFieldError("selectedAuthorIds", "ID: " + id);
            }
        }
        publication.setAuthor(selectedAuthors);
    }

    private void fillUserSelectedPublicationType(){
        userSelectedPublicationTypes = new ArrayList<>();
        for(Long id : selectedPublicationTypeIds){
            try {
                userSelectedPublicationTypes.add(publicationTypeService.readPublicationType(id));
            } catch (ServiceException e){
                addFieldError("selectedPublicationTypeIds", "error.model.not.found");
                addFieldError("selectedPublicationTypeIds", "ID: " + id);
            }
        }
    }

    private void prepareUserInput() {
        /* If no user selection, set to empty objects to avoid NPE. */
        if(selectedKeywordIds == null){
            selectedKeywordIds = new Long[0];
        }
        if(selectedAuthorIds == null){
            selectedAuthorIds = new Long[0];
        }
        if(selectedPublicationTypeIds == null){
            selectedPublicationTypeIds = new Long[0];
        }
        if(publication == null){
            publication = new Publication();
        }
    }

    private void fillResultList() {
        publicationList = publicationService.searchPublication(publication,
                Arrays.asList(selectedKeywordIds),
                Arrays.asList(selectedPublicationTypeIds),
                Arrays.asList(selectedAuthorIds));
    }

    private void resetUserSelection() {
        publication = new Publication();
        selectedKeywordIds = null;
        selectedPublicationTypeIds = null;
        selectedAuthorIds= null;
    }

    @Override
    public void prepare() {
        fillKeywordSelectionList();
        fillAuthorSelectionList();
        fillPublicationTypeList();
        currentLocale = ActionContext.getContext().getLocale().getLanguage();
    }

    public String prepareFillResult(){
        resetUserSelection();
        /* Load result without parameters by default */
        return showResultList();
    }

    public String show() {
        prepareFillResult();
        return SUCCESS;
    }

    public String showResultList() {
        prepareUserInput();
        fillResultList();
        return SUCCESS;
    }

    public PublicationService getPublicationService() {
        return publicationService;
    }

    public void setPublicationService(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    public AuthorService getAuthorService() {
        return authorService;
    }

    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }

    public KeywordService getKeywordService() {
        return keywordService;
    }

    public void setKeywordService(KeywordService keywordService) {
        this.keywordService = keywordService;
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

    public List<Publication> getPublicationList() {
        return publicationList;
    }

    public void setPublicationList(List<Publication> publicationList) {
        this.publicationList = publicationList;
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

    public Long[] getSelectedPublicationTypeIds() {
        return selectedPublicationTypeIds;
    }

    public void setSelectedPublicationTypeIds(Long[] selectedPublicationTypeIds) {
        this.selectedPublicationTypeIds = selectedPublicationTypeIds;
    }

    public List<PublicationType> getUserSelectedPublicationTypes() {
        return userSelectedPublicationTypes;
    }

    public void setUserSelectedPublicationTypes(List<PublicationType> userSelectedPublicationTypes) {
        this.userSelectedPublicationTypes = userSelectedPublicationTypes;
    }

    public String getCurrentLocale() {
        return currentLocale;
    }

    public void setCurrentLocale(String currentLocale) {
        this.currentLocale = currentLocale;
    }
}