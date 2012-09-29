package com.chinarewards.elt.domain.gift;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;


/**
 * 员工导入的原始数据的分析代码
 * 
 * @author 
 * @since 1.0.0 2010-09-19
 */
@Entity
public class ImportGiftRawCode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 516172615449476230L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	/**
	 * 导入员工操作的一条员工记录
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private ImportGiftRaw importGiftRaw;

	/**
	 * 分析代码
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private ImportGiftCode importCode;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the importGiftRaw
	 */
	public ImportGiftRaw getImportGiftRaw() {
		return importGiftRaw;
	}

	/**
	 * @param importGiftRaw the importGiftRaw to set
	 */
	public void setImportGiftRaw(ImportGiftRaw importGiftRaw) {
		this.importGiftRaw = importGiftRaw;
	}

	/**
	 * @return the importCode
	 */
	public ImportGiftCode getImportCode() {
		return importCode;
	}

	/**
	 * @param importCode the importCode to set
	 */
	public void setImportCode(ImportGiftCode importCode) {
		this.importCode = importCode;
	}

	@Override
	public String toString() {
		return "ImportGiftRawCode [id=" + id + ", importGiftRaw="
				+ importGiftRaw + ", importCode=" + importCode + "]";
	}

}
