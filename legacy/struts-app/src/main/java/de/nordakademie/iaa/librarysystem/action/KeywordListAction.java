package de.nordakademie.iaa.librarysystem.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import de.nordakademie.iaa.librarysystem.model.Keyword;
import de.nordakademie.iaa.librarysystem.service.KeywordService;

import java.util.List;
import java.util.Locale;
/**
 * Die Klasse KeywordListAction ist die Action Klasse für keywordList.jsp
 * Mit ihr kann die Schlagwörter Liste erstellt werden.
 * @author Lena Bunge, Felix Groer
 * @version 1.0
 */
public class KeywordListAction extends ActionSupport implements Preparable {

    private KeywordService keywordService;

    // The list of keywords which gets loaded from the service during execution
    private List<Keyword> keywordList;

    private String currentLocale;

    @Override
    public void prepare() {
        keywordList = keywordService.findAllKeywords();
        currentLocale = ActionContext.getContext().getLocale().getLanguage();
    }

    @Override
    public String execute() {
        return SUCCESS;
    }

    public List<Keyword> getKeywordList() {
        return keywordList;
    }

    public void setKeywordList(List<Keyword> keywordList) {
        this.keywordList = keywordList;
    }

    public KeywordService getKeywordService() {
        return keywordService;
    }

    public void setKeywordService(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    public String getCurrentLocale() {
        return currentLocale;
    }

    public void setCurrentLocale(String currentLocale) {
        this.currentLocale = currentLocale;
    }
}