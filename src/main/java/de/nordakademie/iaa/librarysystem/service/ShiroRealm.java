package de.nordakademie.iaa.librarysystem.service;

import de.nordakademie.iaa.librarysystem.model.LibraryUser;
import de.nordakademie.iaa.librarysystem.model.Role;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The application's configured Apache Shiro Realm.
 *
 */
@Service
public class ShiroRealm extends AuthorizingRealm 
{

	private LibraryUserService libraryUserService;

	
    /**
     * Retrieves all the roles for the given user, "guest" role, or null
     * 
     * @param user
     * @return a list of roles for the given user, or "guest" role if user is not in the system, 
     *         or null if given user is null.
     */
    private List<String> retrieveRolesForUser(LibraryUser user)
    {
        List<String> roles = null;
        
        if (user != null)
        {
            roles = new ArrayList<>();
            // Only try to lookup roles if user is valid  user, otherwise assume default guest role
            if (libraryUserService.isUserValid(user.getUserName(), user.getPassword()))
            {
                List<Role> roleList = libraryUserService.lookupAllRolesForUser(user.getUserName());
                // Need role name only to add to list
                for (Role role : roleList)
                {
                    roles.add(role.getName());
                }
            }
            else
            {
                roles.add("guest");
            }            
        }
        
        return roles;
    }
    
    /**
     * Retrieves all the permissions for the given user,  or null.
     * NOTE - This method can/should return all permissions associated directly with a role (Implicit Roles)
     *        or it can/should return all permissions associated directly with a user (Explicit Roles)
     *        depending on the implementation. This implementation uses Implicit Roles
     * 
     * @param libraryUser
     * @return a list of permissions for the given user, or null if given user is null.
     */
    private Set<String> retrievePermissionsForUser(LibraryUser libraryUser)
    {
        //not in use, can be implmeneted if permission is needed
        return null;
    }
    
	/**
	 * @param authcToken Has user name and password
	 * @return return the SimpleAuthenticationInfo object, otherwise return null.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
	{
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		SimpleAuthenticationInfo authcInfo = null;
        
        try 
        {
			LibraryUser libraryUser = null;

        	if (libraryUserService.isUserValid(token.getUsername(), String.valueOf(token.getPassword())))
        	{                
            	libraryUser = new LibraryUser();
				libraryUser.setUserName(token.getUsername());
				libraryUser.setPassword(String.valueOf(token.getPassword()));
            	authcInfo = new SimpleAuthenticationInfo(libraryUser, token.getPassword(), getName());
        	}
        	else 
        	{
        		throw new AuthenticationException("Invalid username and/or password. User cannot be found.");
        	}
        } 
        catch (Exception e) 
        {
		}
       
        return authcInfo;
	}

	/**
	 * @param principals Has user data object.
	 * @return If the user has roles for this application,
	 * return the AuthorizationInfo object. Otherwise return null. 
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) 
	{
		SimpleAuthorizationInfo authzInfo = null;
		
		try 
		{			
			if (principals != null)
			{
				LibraryUser libraryUser = (LibraryUser) principals.fromRealm(getName()).iterator().next();
	            authzInfo = new SimpleAuthorizationInfo();
	            
	            List<String> roles = retrieveRolesForUser(libraryUser);
	            
	            // Add roles to shiro subject.
	            authzInfo.addRoles(roles);	            
	            
	            Set<String> permissions = retrievePermissionsForUser(libraryUser);
	            
                // Add permissions to shiro subject.
	            authzInfo.setStringPermissions(permissions);
			}
			else
			{
				throw new AuthorizationException("Cannot authorize with no principals.");
			}

			
		} 
		catch (Exception e) 
		{
		}
		
		return authzInfo;
	}

	public void setLibraryUserService(LibraryUserService libraryUserService) {
		this.libraryUserService = libraryUserService;
	}

	public LibraryUserService getLibraryUserService() {
		return libraryUserService;
	}

}
