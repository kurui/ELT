package com.chinarewards.gwt.elt.sevlet;

import com.chinarewards.elt.service.staff.ImportStaffService;
import com.chinarewards.gwt.elt.server.InitServlet;

/**
 * Utility class to provide a centralized method to obtain an session bean
 * interface of <code>ServiceLocator</code>.
 * 
 * @author Cream copy from tiger/cyril
 * @since 0.1.0 2010-06-08
 */
public abstract class ServiceLocatorUtil {

	/**
	 * Returns an instance of ServiceLocator.
	 * 
	 * @return
	 */
	public static ImportStaffService getServiceLocator() {
		return InitServlet.getServiceLocator();
	}

}
