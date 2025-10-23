package de.nordakademie.iaa.librarysystem.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import de.nordakademie.iaa.librarysystem.model.*;
import de.nordakademie.iaa.librarysystem.service.LibraryUserService;
import de.nordakademie.iaa.librarysystem.service.RoleService;
import de.nordakademie.iaa.librarysystem.service.exception.ModelUsedException;
import de.nordakademie.iaa.librarysystem.service.exception.ServiceException;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Die Klasse LibraryUserAction ist die Action Klasse für das libraryUserForm.jsp
 * Mit ihr können:
 *      - Ausleiher angelegt
 *      - Ausleiher bearbeiten
 *      - Ausleiher gelöscht
 * werden.
 * @author Fabian Rudolf, Jasmin Elvers
 * @version 1.0
 */
public class LibraryUserAction extends ActionSupport implements Preparable {

    private LibraryUserService libraryUserService;
    private RoleService roleService;

    private LibraryUser libraryUser;

    private Long libraryUserKey;

    private Long[] existingRolesIds;
    private Long selectedRolesId;

    private HashMap<Long, String> roleSelectionMap = new HashMap<>();

    @Override
    public String execute(){
        findRoleSelectionList();
        return SUCCESS;
    }

    @Override
    public void prepare() throws Exception {
        findRoleSelectionList();
    }
    private void findRoleSelectionList(){
        for(Role role : roleService.findAllRoles()){
            roleSelectionMap.put(role.getId(), role.getName());
        }
    }

    public Boolean prepareSelectionEdit() {
        try{
            libraryUser = libraryUserService.loadLibraryUser(libraryUserKey);
        } catch (ServiceException e){
            addActionError(getText("error.model.not.found"));
            return false;
        }
        existingRolesIds = libraryUser.getRoles().stream().map(x -> x.getId()).toArray(Long[]::new);
        return true;
    }

    public String edit() throws Exception {
        if(libraryUserKey != null){
            prepareSelectionEdit();
        }
        try{
            libraryUser = libraryUserService.loadLibraryUser(libraryUserKey);
        } catch (ServiceException e){
            addActionError(getText("error.model.not.found"));
            return INPUT;
        }
        return SUCCESS;
    }

    public String save() throws Exception {
        Role role = roleService.loadRole(selectedRolesId);
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role);
        libraryUser.setRoles(roles);
        if (libraryUser.getId() == null) {
            try{
                libraryUserService.createLibraryUser(libraryUser);
                addActionMessage(getText("message.create.ok"));
            } catch (ServiceException e){
                addActionError(getText("error.create.failed"));
            }
        } else {
            try{
                libraryUserService.updateLibraryUser(libraryUser);
                addActionMessage(getText("message.update.ok"));
            } catch (ServiceException e){
                addActionError(getText("error.update.failed"));
            }
        }
        return SUCCESS;
    }

    public String saveFromLogin() throws Exception {
        if (libraryUser.getId() == null) {
            Role role = roleService.findRolesByName("user");
            ArrayList<Role> roles = new ArrayList<>();
            roles.add(role);
            libraryUser.setRoles(roles);
            try{
                libraryUserService.createLibraryUser(libraryUser);
                addActionMessage(getText("message.create.ok"));
            } catch (ServiceException e){
                addActionError(getText("error.create.failed"));
            }
        } else {
            try{
                libraryUserService.updateLibraryUser(libraryUser);
                addActionMessage(getText("message.update.ok"));
            } catch (ServiceException e){
                addActionError(getText("error.update.failed"));
            }
        }
        return SUCCESS;
    }

    public String delete() throws Exception {
        try{
            libraryUserService.deleteLibraryUser(libraryUserKey);
            addActionMessage(getText("message.delete.ok"));
        } catch(ServiceException e){
            addActionError(getText("error.delete.failed"));
            return INPUT;
        }catch (ModelUsedException e){
            addActionError(getText("error.object.is.used"));
            return INPUT;
        }
        return SUCCESS;
    }

    @Override
    public void validate() {
        if (libraryUserKey == null && libraryUser == null) {
            addActionError(getText("error.selection.required"));
        }
    }

    public LibraryUserService getLibraryUserService() {
        return libraryUserService;
    }

    public void setLibraryUserService(LibraryUserService libraryUserService) {
        this.libraryUserService = libraryUserService;
    }

    public LibraryUser getLibraryUser() {
        return libraryUser;
    }

    public void setLibraryUser(LibraryUser libraryUser) {
        this.libraryUser = libraryUser;
    }

    public Long getLibraryUserKey() {
        return libraryUserKey;
    }

    public void setLibraryUserKey(Long libraryUserKey) {
        this.libraryUserKey = libraryUserKey;
    }

    public RoleService getRoleService() {
        return roleService;
    }

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }


    public HashMap<Long, String> getRoleSelectionMap() {
        return roleSelectionMap;
    }

    public void setRoleSelectionMap(HashMap<Long, String> roleSelectionMap) {
        this.roleSelectionMap = roleSelectionMap;
    }

    public Long[] getExistingRolesIds() {
        return existingRolesIds;
    }

    public void setExistingRolesIds(Long[] existingRolesIds) {
        this.existingRolesIds = existingRolesIds;
    }

    public Long getSelectedRolesId() {
        return selectedRolesId;
    }

    public void setSelectedRolesId(Long selectedRolesId) {
        this.selectedRolesId = selectedRolesId;
    }

}