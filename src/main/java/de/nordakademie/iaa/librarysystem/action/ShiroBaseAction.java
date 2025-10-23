/**
 * 
 */
package de.nordakademie.iaa.librarysystem.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.shiro.subject.Subject;

/**
 * Die Klasse ShiroBaseAction ist die Action Klasse für die Authentifizierung.
 * @author Fabian Rudolf, Jasmin Elvers
 * @version 1.0
 */
public class ShiroBaseAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;
	
	private Subject shiroUser;

	public boolean isAuthenticated() 
	{
		return shiroUser != null && shiroUser.isAuthenticated();
	}

	public Subject getShiroUser() {
		return shiroUser;
	}

	public void setShiroUser(Subject shiroUser) {
		this.shiroUser = shiroUser;
	}
}