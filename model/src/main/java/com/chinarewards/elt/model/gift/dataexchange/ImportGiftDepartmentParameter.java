package com.chinarewards.elt.model.gift.dataexchange;


/**
 * 员工导入的部门分析结果
 * 
 * @author yanrui
 * @since 1.5.2 
 */
public class ImportGiftDepartmentParameter {

	/**
	 * 导入员工操作的导入批次id
	 */
	private String importGiftBatchId;

	/**
	 * 
	 */
	private String id;
	
	/**
	 * 
	 */
	private String departmentRaw;
	
	/**
	 * 
	 */
	private ImportGiftResultType result;

	/**
	 * get id from ImportGiftBatch
	 * @return
	 */
	public String getImportGiftBatchId() {
		return importGiftBatchId;
	}

	public void setImportGiftBatchId(String importGiftBatchId) {
		this.importGiftBatchId = importGiftBatchId;
	}

	/**
	 * id
	 * @return
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * get the department raw, which populated from Gift raw
	 * @return
	 */
	public String getDepartmentRaw() {
		return departmentRaw;
	}

	public void setDepartmentRaw(String departmentRaw) {
		this.departmentRaw = departmentRaw;
	}

	/**
	 * result
	 * @return
	 */
	public ImportGiftResultType getResult() {
		return result;
	}

	public void setResult(ImportGiftResultType result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "ImportGiftDepartmentParameter [importGiftBatchId="
				+ importGiftBatchId + ", id=" + id + ", departmentRaw="
				+ departmentRaw + ", result=" + result + "]";
	}
	
}
