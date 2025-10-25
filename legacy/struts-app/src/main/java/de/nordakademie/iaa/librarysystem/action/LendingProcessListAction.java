package de.nordakademie.iaa.librarysystem.action;

import com.opensymphony.xwork2.Action;
import de.nordakademie.iaa.librarysystem.model.LibraryUser;
import de.nordakademie.iaa.librarysystem.model.LendingProcess;
import de.nordakademie.iaa.librarysystem.model.Publication;
import de.nordakademie.iaa.librarysystem.service.LendingProcessService;

import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse LendingProcessListAction ist die Action Klasse für lendingProcessList.jsp
 * Mit ihr kann die Ausleihprozess Liste erstellt werden.
 * @author Fabian Rudolf, Jasmin Elvers
 * @version 1.0
 */
public class LendingProcessListAction extends ShiroBaseAction implements Action {

    private LendingProcessService lendingProcessService;

    // The list of lending processes which gets loaded from the service during execution
    private List<LendingProcess> lendingProcessList = new ArrayList<>();

    private Long lendingProcessKey;

    // User search input parameters
    private LendingProcess searchLendingProcess;
    private LibraryUser searchLibraryUser;
    private Publication searchPublication;
    private Boolean includeOutstandingOverdueReminderOnly;

    private void resetUserSelection() {
        searchLendingProcess = new LendingProcess();
        searchLibraryUser = new LibraryUser();
        searchPublication = new Publication();
        includeOutstandingOverdueReminderOnly = false;
    }

    @Override
    public String execute() {
        searchLendingProcess = new LendingProcess();
        searchLibraryUser = new LibraryUser();
        searchPublication = new Publication();
        includeOutstandingOverdueReminderOnly = null;
        lendingProcessList = lendingProcessService.findAllLendingProcesses();
        return SUCCESS;
    }

    private void prepareUserInput() {
        searchLendingProcess.setLibraryUser(searchLibraryUser);
        searchLendingProcess.setPublication(searchPublication);
        /* Only filter if boolean checkbox is set, else set to null such that service doesn't filter */
        if(searchLendingProcess.getLost().equals(Boolean.FALSE)){
            searchLendingProcess.setLost(null);
        }
        if(searchLendingProcess.getReturned().equals(Boolean.TRUE)){
            searchLendingProcess.setReturned(null);
        }
    }

    public String showResultList(){
        prepareUserInput();
        lendingProcessList = lendingProcessService.searchLendingProcess(
                searchLendingProcess,
                includeOutstandingOverdueReminderOnly
        );
        resetUserSelection();
        return SUCCESS;
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

    public Long getLendingProcessKey() {
        return lendingProcessKey;
    }

    public void setLendingProcessKey(Long lendingProcessKey) {
        this.lendingProcessKey = lendingProcessKey;
    }

    public LendingProcess getSearchLendingProcess() {
        return searchLendingProcess;
    }

    public void setSearchLendingProcess(LendingProcess searchLendingProcess) {
        this.searchLendingProcess = searchLendingProcess;
    }

    public LibraryUser getSearchLibraryUser() {
        return searchLibraryUser;
    }

    public void setSearchLibraryUser(LibraryUser searchLibraryUser) {
        this.searchLibraryUser = searchLibraryUser;
    }

    public Publication getSearchPublication() {
        return searchPublication;
    }

    public void setSearchPublication(Publication searchPublication) {
        this.searchPublication = searchPublication;
    }

    public Boolean getIncludeOutstandingOverdueReminderOnly() {
        return includeOutstandingOverdueReminderOnly;
    }

    public void setIncludeOutstandingOverdueReminderOnly(Boolean includeOutstandingOverdueReminderOnly) {
        this.includeOutstandingOverdueReminderOnly = includeOutstandingOverdueReminderOnly;
    }
}