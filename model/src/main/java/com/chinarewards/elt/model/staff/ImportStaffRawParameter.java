package com.chinarewards.elt.model.staff;


/**
 * 员工导入的原始数据
 * 
 * @author sunhongliang
 * @since 1.0.0 2010-09-19
 */
public class ImportStaffRawParameter {

	/**
	 * 导入员工操作的导入批次id
	 */
	private String importStaffBatchId;

	/**
	 * 
	 */
	private String id;
	
	/**
	 * 行数
	 */
	private Long rowPos;
	
	/**
	 * 部门（用|分隔符分隔部门及其子部门，例如：技术部|开发部）
	 */
	private String department;

	/**
	 * 手机号码
	 */
	private String phone;

	/**
	 * 邮箱
	 */
	private String email;

	
	/**
	 * 员工编号
	 */
	private String jobNo;
	/**
	 * 员工名称
	 */
	private String name;
	/**
	 * 出生日期
	 */
	private String dob;

	
	/**
	 * 导入结果：成功、失败、未知
	 */
	private ImportStaffResultType result;


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getImportStaffBatchId() {
		return importStaffBatchId;
	}


	public void setImportStaffBatchId(String importStaffBatchId) {
		this.importStaffBatchId = importStaffBatchId;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public Long getRowPos() {
		return rowPos;
	}


	public void setRowPos(Long rowPos) {
		this.rowPos = rowPos;
	}


	public String getDepartment() {
		return department;
	}


	public void setDepartment(String department) {
		this.department = department;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getJobNo() {
		return jobNo;
	}


	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}


	public String getDob() {
		return dob;
	}


	public void setDob(String dob) {
		this.dob = dob;
	}


	public ImportStaffResultType getResult() {
		return result;
	}


	public void setResult(ImportStaffResultType result) {
		this.result = result;
	}
	

}
