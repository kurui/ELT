/**
 * 
 */
package com.chinarewards.elt.service.org;

import java.util.List;

/**
 * @author Cream
 * @since 0.2.0
 */
public interface DepartmentManagerLogic {

	/**
	 * 
	 * @return
	 */
	public List<String> findDepartmentIdsManagedByLoginUser();
}
