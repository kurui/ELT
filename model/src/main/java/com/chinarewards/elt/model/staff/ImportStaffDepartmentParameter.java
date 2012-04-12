package com.chinarewards.elt.model.staff;


/**
 * 员工导入的部门分析结果
 * 
 * @author sunhongliang
 * @since 1.0.0 2010-09-19
 */
public class ImportStaffDepartmentParameter {

	/**
	 * 导入员工操作的导入批次id
	 */
	private String importStaffBatchId;

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
	private ImportStaffResultType result;

	/**
	 * get id from ImportStaffBatch
	 * @return
	 */
	public String getImportStaffBatchId() {
		return importStaffBatchId;
	}

	public void setImportStaffBatchId(String importStaffBatchId) {
		this.importStaffBatchId = importStaffBatchId;
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
	 * get the department raw, which populated from staff raw
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
	public ImportStaffResultType getResult() {
		return result;
	}

	public void setResult(ImportStaffResultType result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "ImportStaffDepartmentParameter [importStaffBatchId="
				+ importStaffBatchId + ", id=" + id + ", departmentRaw="
				+ departmentRaw + ", result=" + result + "]";
	}
	
}
