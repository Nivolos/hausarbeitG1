package de.nordakademie.iaa.librarysystem.service;

import de.nordakademie.iaa.librarysystem.dao.LibraryUserDAO;
import de.nordakademie.iaa.librarysystem.dao.exception.ModelNotFoundException;
import de.nordakademie.iaa.librarysystem.dao.exception.PersistException;
import de.nordakademie.iaa.librarysystem.model.LibraryUser;
import de.nordakademie.iaa.librarysystem.model.LendingProcess;
import de.nordakademie.iaa.librarysystem.model.Role;
import de.nordakademie.iaa.librarysystem.service.exception.ModelUsedException;
import de.nordakademie.iaa.librarysystem.service.exception.ServiceException;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse LibraryUserServiceImpl ist der Kommunikator zwischen der LibraryUserAction und der LibraryUserDao.
 * Sie ermöglicht die CRUD Operationen des LibraryUsers, das Prüfen, ob ein LibraryUser bereits existiert und alle
 * LibraryUser zu finden.
 * @author Lena Bunge, Felix Groer
 * @version 1.0
 */

public class LibraryUserServiceImpl implements LibraryUserService {

    private LibraryUserDAO libraryUserDAO;
    private final LendingProcessService lendingProcessService;
    private final RoleService roleService;


    @Inject
    public LibraryUserServiceImpl(LibraryUserDAO libraryUserDAO, LendingProcessService lendingProcessService,
                                  RoleService roleService)
    {
        this.libraryUserDAO = libraryUserDAO;
        this.lendingProcessService = lendingProcessService;
        this.roleService = roleService;
    }

    @Override
    public void createLibraryUser(LibraryUser libraryUser) throws ServiceException {
        if (libraryUser.getId() != null) {
            throw new ServiceException("Cannot create an existing libraryUser.");
        }
        try {
            checkLibraryUserExits(libraryUser);
            hashUserPassword(libraryUser);
            libraryUserDAO.create(libraryUser);
        } catch (PersistException e) {
            throw new ServiceException(e);
        }
    }
    private void hashUserPassword(LibraryUser libraryUser){
        String password = libraryUser.getPassword();
        String hashedPassword = new Sha256Hash(password).toString();
        libraryUser.setPassword(hashedPassword);
    }

    private void loadLazy(LibraryUser libraryUser){
        try{
            Hibernate.initialize(libraryUser.getRoles());
        } catch(HibernateException e){
            throw new ServiceException("Lazy load of roles failed");
        }
    }

    @Override
    public LibraryUser loadLibraryUser(Long id) throws ServiceException {
        try{
            LibraryUser libraryUser = libraryUserDAO.load(id).orElseThrow(() ->
                    new ServiceException(String.format("There is no user with id: %s.", id)));
            loadLazy(libraryUser);
            return libraryUser;
        } catch (ModelNotFoundException | HibernateException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public LibraryUser updateLibraryUser(LibraryUser libraryUser) throws ServiceException {
        if (libraryUser.getId() == null) {
            throw new ServiceException("Cannot update non existing libraryUser.");
        }
        try {
            hashUserPassword(libraryUser);
            return libraryUserDAO.update(libraryUser);
        } catch (PersistException e){
            throw new ServiceException(e);
        }
    }

    private List<Role> getUserRoles(LibraryUser libraryUser) {
        List<Role> roleList = new ArrayList<>();
        roleList  = lookupAllRolesForUser(libraryUser.getUserName());
        return roleList;
    }

    @Override
    public List<LibraryUser> findAllLibraryUsers() {
        return libraryUserDAO.findAll();
    }

    @Override
    public void deleteLibraryUser(Long id) throws ServiceException, ModelUsedException {
        try {
            List<LendingProcess> inLendingProcess = lendingProcessService.findByLibraryUser(id);
            if(!inLendingProcess.isEmpty()){
                throw new ModelUsedException();
            }
            libraryUserDAO.delete(id);
        } catch (ModelNotFoundException exception) {
            throw new ServiceException(exception);
        }
    }

    @Override
    public LibraryUser getUser(String userName) throws ServiceException {
        return libraryUserDAO.loadByUserName(userName).orElseThrow(() -> new ServiceException(String.format("A library employee with user name %s does not " +
                "exist.", userName)));
    }

    @Override
    public boolean isUserValid(String username, String password) {
        String hashedPassword = new Sha256Hash(password).toString();
        LibraryUser persistedLibraryUser = this.getUser(username);
        if (hashedPassword.matches(persistedLibraryUser.getPassword())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Role> lookupAllRolesForUser(String username) {
        LibraryUser libraryUser = libraryUserDAO.loadByUserName(username).orElseThrow(() -> new ServiceException(String.format("A library employee with user name %s does not " +
                "exist.", username)));
        List<Role> roles = libraryUser.getRoles();
        return roles;
    }

    private void checkLibraryUserExits(LibraryUser libraryUser) {
        Boolean exists = findAllLibraryUsers().stream().anyMatch(a -> a.equals(libraryUser));
        if (exists) {
            throw new ServiceException("LibraryUser exists already.");
        }
    }

    public LibraryUserDAO getLibraryUserDAO() {
        return libraryUserDAO;
    }
    public void setDao(LibraryUserDAO dao) {
        this.libraryUserDAO = dao;
    }
}