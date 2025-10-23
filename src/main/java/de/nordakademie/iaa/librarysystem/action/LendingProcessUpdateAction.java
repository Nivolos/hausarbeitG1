package de.nordakademie.iaa.librarysystem.action;

import com.opensymphony.xwork2.ActionSupport;
import de.nordakademie.iaa.librarysystem.model.LendingProcess;
import de.nordakademie.iaa.librarysystem.service.*;
import de.nordakademie.iaa.librarysystem.service.exception.*;
import de.nordakademie.iaa.librarysystem.service.exception.lendingprocess.ExtendPublicationNotPossible;
import de.nordakademie.iaa.librarysystem.service.exception.lendingprocess.LostPublicationNotPossibleException;
import de.nordakademie.iaa.librarysystem.service.exception.lendingprocess.RemindPublicationReturnNotPossible;
import de.nordakademie.iaa.librarysystem.service.exception.lendingprocess.ReturnNotPossibleException;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Die Klasse LendingProcessUpdateAction ist die Action Klasse für das LendingProcessCreateForm.jsp
 * Mit ihr können:
 *      - Ausleihprozesse erstellt
 *      - Ausleihprozesse verlängert
 *      - Ausleihprozesse gemahnt
 *      - Ausleihprozesse beendet
 *      - Ausleihprozesse als verloren gemeldet
 * werden.
 * @author Fabian Rudolf, Jasmin Elvers
 * @version 1.0
 */
public class LendingProcessUpdateAction extends ActionSupport {
    private LendingProcessService lendingProcessService;

    /* Read user selections */
    private Long[] selectedLendingProcessIds;

    List<LendingProcess> lendingProcesses = new ArrayList<>();

    Boolean successFlag;

    @Override
    public void validate() {
        if(selectedLendingProcessIds == null || selectedLendingProcessIds.length == 0){
            addActionError(getText("error.lendingprocess.update.missingselection"));
        }
    }

    private void addUserSelectedLendingProcessOrAddActionError(Long id){
        try{
            lendingProcesses.add(lendingProcessService.readLendingProcess(id));
        } catch (ServiceException e){
            addActionError(getText("error.lendingprocess.multiselect.not.found"));
            addActionError(String.valueOf(id));
        }
    }

    public void prepareInput() {
        successFlag = true;
        /* Prepare user input */
        selectedLendingProcessIds = ArrayUtils.removeElement(selectedLendingProcessIds, -1);

        Arrays.stream(selectedLendingProcessIds)
                .forEach(x -> addUserSelectedLendingProcessOrAddActionError(x));
    }

    public String returnPublications() {
        prepareInput();
        updateLendingProcessHelper(lendingProcesses, publication -> returnPublication(publication));
        if(!this.successFlag){
            return INPUT;
        }
        return SUCCESS;
    }

    public String remindPublications() {
        prepareInput();
        updateLendingProcessHelper(lendingProcesses, publication -> remindPublicationReturn(publication));
        if(!this.successFlag){
            return INPUT;
        }
        return SUCCESS;
    }

    public String lostPublications() {
        prepareInput();
        updateLendingProcessHelper(lendingProcesses, publication -> lostPublication(publication));
        if(!this.successFlag){
            return INPUT;
        }
        return SUCCESS;
    }

    public String extendPublications() {
        prepareInput();
        updateLendingProcessHelper(lendingProcesses, publication -> extendPublication(publication));
        if(!this.successFlag){
            return INPUT;
        }
        return SUCCESS;
    }

    /**
     * Update multiple lending processes via consuming class (e.g. return / remind / extend).
     * Set `this.successFlag` to `false` if any update fails to return to input action with add field errors
     * @param lendingProcesses
     */
    private void updateLendingProcessHelper(List<LendingProcess> lendingProcesses,
                                            Consumer<LendingProcess> updateLendingProcessFunction) {
        for (LendingProcess lendingProcess : lendingProcesses) {
            if(lendingProcess != null){
                updateLendingProcessFunction.accept(lendingProcess);
            }
        }
    }

    private void returnPublication(LendingProcess lendingProcess) {
        try {
           lendingProcessService.returnPublication(lendingProcess);
           lendingProcessService.sendReturnEmail(lendingProcess);
       } catch (ReturnNotPossibleException e) {
           if(this.successFlag){
               addActionError(getText("error.publication.return.failed"));
               this.successFlag = false;
           }
            addLendingProcessToActionError(lendingProcess);
       }
    }

    private void lostPublication(LendingProcess lendingProcess) {
        try {
            lendingProcessService.lostPublication(lendingProcess);
            lendingProcessService.sendLostEmail(lendingProcess);
        } catch (LostPublicationNotPossibleException e) {
            if(this.successFlag){
                addActionError(getText("error.publication.lost.failed"));
                this.successFlag = false;
            }
            addLendingProcessToActionError(lendingProcess);
        }
    }

    private void remindPublicationReturn(LendingProcess lendingProcess) {
        try {
            lendingProcessService.remindPublicationReturn(lendingProcess);
            lendingProcessService.sendReminderEmail(lendingProcess);
        } catch (RemindPublicationReturnNotPossible e) {
            if(this.successFlag){
                addActionError(getText("error.lendingprocess.reminder.failed"));
                this.successFlag = false;
            }
            addLendingProcessToActionError(lendingProcess);
        }
    }

    private void extendPublication(LendingProcess lendingProcess) {
        try {
            lendingProcessService.extendPublication(lendingProcess);
            lendingProcessService.sendExtendEmail(lendingProcess);
        } catch (ExtendPublicationNotPossible e) {
            if(this.successFlag){
                addActionError(getText("error.lendingprocess.extend.failed"));
                this.successFlag = false;
            }
            addLendingProcessToActionError(lendingProcess);
        }
    }

    private void addLendingProcessToActionError(LendingProcess lendingProcess) {
        addActionError(
lendingProcess.getLibraryUser().getFirstName() +
            " " +
            lendingProcess.getLibraryUser().getLastName() +
            ": " + lendingProcess.getPublication().getTitle()
        );
    }

    public LendingProcessService getLendingProcessService() {
        return lendingProcessService;
    }

    public void setLendingProcessService(LendingProcessService lendingProcessService) {
        this.lendingProcessService = lendingProcessService;
    }

    public Long[] getSelectedLendingProcessIds() {
        return selectedLendingProcessIds;
    }

    public void setSelectedLendingProcessIds(Long[] selectedLendingProcessIds) {
        this.selectedLendingProcessIds = selectedLendingProcessIds;
    }

    public List<LendingProcess> getLendingProcesses() {
        return lendingProcesses;
    }

    public void setLendingProcesses(List<LendingProcess> lendingProcesses) {
        this.lendingProcesses = lendingProcesses;
    }

    public Boolean getSuccessFlag() {
        return successFlag;
    }

    public void setSuccessFlag(Boolean successFlag) {
        this.successFlag = successFlag;
    }
}