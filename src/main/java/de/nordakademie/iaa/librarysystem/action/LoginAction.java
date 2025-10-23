package de.nordakademie.iaa.librarysystem.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import de.nordakademie.iaa.librarysystem.model.LibraryUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;

/**
 * Die Klasse LoginAction ermöglicht die Anmeldung in die Bibliothek,
 * welche durch das Framework Apache Shiro abgedeckt wird.
 * @author Lena Bunge, Felix Groer
 * @version 1.0
 */
public class LoginAction extends ActionSupport implements Preparable {

    private static final long serialVersionUID = 1L;
    private LibraryUser libraryUser;
    private Subject shiroUser;

    @Override
    public void prepare() throws Exception
    {
        shiroUser = SecurityUtils.getSubject();
    }

    @Override
    public String execute()
    {
        String result = INPUT;

        // let's login the current user so we can check against roles and permissions:
        if (shiroUser != null && ! shiroUser.isAuthenticated())
        {
            UsernamePasswordToken token = new UsernamePasswordToken(libraryUser.getUserName(), libraryUser.getPassword());
            token.setRememberMe(true);
            try
            {
                shiroUser.login(token);
                result = SUCCESS;
            }
            catch (UnknownAccountException uae)
            {
                addActionError(getText("error.wrongUsername") + token.getPrincipal());
            }
            catch (IncorrectCredentialsException ice)
            {
                addActionError(getText("error.wrongPassword.beginning" + "'" ) + token.getPrincipal() + (getText("error.wrongPassword.end")));
            }
            catch (LockedAccountException lae)
            {
                addActionError(getText("error.locked.beginning") + token.getPrincipal() + (getText("error.locked.end")));
            }
            // ... catch more exceptions here (maybe custom ones specific to your application?
            catch (AuthenticationException ae)
            {
                addActionError(getText("error.authentication") + token.getPrincipal());
            }
        }
        else if (shiroUser.isAuthenticated())
        {
            result = SUCCESS;
        }

        return result;
    }

    public Subject getShiroUser()
    {
        return shiroUser;
    }

    public void setShiroUser(Subject shiroUser)
    {
        this.shiroUser = shiroUser;
    }

    public LibraryUser getLibraryUser() {
        return libraryUser;
    }

    public void setLibraryUser(LibraryUser libraryUser) {
        this.libraryUser = libraryUser;
    }
}