package de.nordakademie.iaa.librarysystem.service;

import de.nordakademie.iaa.librarysystem.dao.RoleDao;
import de.nordakademie.iaa.librarysystem.dao.exception.ModelNotFoundException;
import de.nordakademie.iaa.librarysystem.model.LibraryUser;
import de.nordakademie.iaa.librarysystem.model.Role;
import de.nordakademie.iaa.librarysystem.service.exception.ServiceException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Die Klasse RoleServiceImpl ist der Kommunikator zwischen der Action und der RoleDao.
 * Sie ermöglicht die CRUD Operationen der Rolle und das finden von Rolle nach bestimmten Kriterien.
 * @author Lena Bunge, Felix Groer
 * @version 1.0
 */
@Service
public class RoleServiceImpl implements RoleService{

    private final RoleDao roleDao;

    @Inject
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Role findRolesByName(String roleName) {
        Role role = roleDao.findRoleByName(roleName).orElseThrow(()
                -> new ServiceException(String.format("A role with name %s does not exist.", roleName)));
        return role;
    }

    @Override
    public List<Role> findAllRoles() {
        return roleDao.findAll();
    }

    @Override
    public Role loadRole(Long id) throws ServiceException {
        try{
            return roleDao.load(id).orElseThrow(()
                    -> new ServiceException(String.format("There is no role with id: %s.", id)));
        } catch (ModelNotFoundException e){
            throw new ServiceException(e);
        }
    }


}
