package com.chinarewards.elt.service.gift;

import java.util.List;

import com.chinarewards.elt.domain.gift.ImportGiftCode;

/**
 * 
 * @author yanrui
 * @since 1.5.2
 * */
public interface ImportGiftCodeLogic {

	/**
	 * Get all import codes.
	 * 
	 * @param key
	 * @return
	 */
	public List<ImportGiftCode> getAll();
	
	/**
	 * Retrieve import code by code number
	 * 
	 * @param code
	 * @return
	 */
	public ImportGiftCode findByCode(Long code);

	/**
	 * create a new import code
	 * @param code
	 * @return
	 */
	public ImportGiftCode insertImportCode(ImportGiftCode code);
	
}
