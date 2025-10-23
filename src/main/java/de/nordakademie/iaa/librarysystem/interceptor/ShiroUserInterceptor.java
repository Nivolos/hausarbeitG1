/**
 * 
 */
package de.nordakademie.iaa.librarysystem.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * adapted from:
 * @author tkofford
 *
 */

/**
 * Inserts the current Shiro user into the value stack so that it can be
 * injected into Struts 2 actions should they have a JavaBeans setter
 * <code>setShiroUser(org.apache.shiro.subject.Subject shiroUser)</code>.
 *
 * @version $Revision: $ $Date: $
 */
public class ShiroUserInterceptor implements Interceptor {

	private static final long serialVersionUID = 1L;

	@Override
	public void destroy() 
	{}

	@Override
	public void init() 
	{}

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception 
	{
		if (actionInvocation.getAction() instanceof de.nordakademie.iaa.librarysystem.action.ShiroBaseAction)
		{
	        Subject shiroUser = SecurityUtils.getSubject();
	        actionInvocation.getStack().setValue("shiroUser", shiroUser);
		}
	        
		return actionInvocation.invoke();
	}

}