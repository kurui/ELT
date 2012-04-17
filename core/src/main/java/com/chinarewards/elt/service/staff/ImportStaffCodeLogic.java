package com.chinarewards.elt.service.staff;

import java.util.List;

import com.chinarewards.elt.domain.org.ImportStaffCode;

/**
 * The interface of business logic bean for import code operation
 * 
 * @author sunhongliang
 * @since 1.0.0 2010-09-21
 */
public interface ImportStaffCodeLogic {

	/**
	 * Get all import codes.
	 * 
	 * @param key
	 * @return
	 */
	public List<ImportStaffCode> getAll();
	
	/**
	 * Retrieve import code by code number
	 * 
	 * @param code
	 * @return
	 */
	public ImportStaffCode findByCode(Long code);

	/**
	 * create a new import code
	 * @param code
	 * @return
	 */
	public ImportStaffCode insertImportCode(ImportStaffCode code);
	
}
