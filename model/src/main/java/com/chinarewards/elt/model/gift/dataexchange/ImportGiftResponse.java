package com.chinarewards.elt.model.gift.dataexchange;

import java.util.List;


/**
 * 导入员工操作输出.
 * 
  * @author yanrui
 * @since 1.5.2 
 */
public class ImportGiftResponse extends ImportGiftRequest {

	public ImportGiftResponse() {
		super();
	}

	public ImportGiftResponse(ImportGiftRequest request) {
		this.setContentType(request.getContentType());
		this.setFileName(request.getFileName());
		this.setDobFormat(request.getDobFormat());
		this.setDoeFormat(request.getDoeFormat());
		this.setId(request.getId());
		this.setHavingTitle(request.isHavingTitle());
		this.setGiftRawList(request.getGiftRawList());
	}
	private String LicenseMessage;
	public String getLicenseMessage() {
		return LicenseMessage;
	}

	public void setLicenseMessage(String licenseMessage) {
		LicenseMessage = licenseMessage;
	}
	/**
	 * 成功导入编号
	 */
	private Long importBatchNo;
	
	/**
	 * 预估创建部门数量
	 */
	private Long estimateSuccessDeptNum;
	
	/**
	 * 成功创建部门数量
	 */
	private Long finalSuccessDeptNum;
	
	/**
	 * 预计自动分配卡号数
	 */
	private Long estNewAutoCardNum;
	
	/**
	 * 成功自动分配卡号数
	 */
	private Long finalNewAutoCardNum;
	
	/**
	 * 预计手动分配新卡号数
	 */
	private Long estNewAssignCardNum;
	
	/**
	 * 成功手动分配新卡号数
	 */
	private Long finalNewAssignCardNum;
	
	/**
	 * 预计手动分配旧卡号数
	 */
	private Long estOldAssignCardNum;
	
	/**
	 * 成功手动分配旧卡号数
	 */
	private Long finalOldAssignCardNum;
	
	/**
	 * 预估成功导入员工数
	 */
	private Long estimateSuccessNum;

	/**
	 * 最终成功导入员工数
	 */
	private Long finalSuccessNum;

	/**
	 * 
	 */
	private Long finalFailedNum;
	
	/**
	 * 员工分析结果
	 */
	private List<List<Long>> importGiftRawCode;
	
	/**
	 * 部门分析结果
	 */
	private List<String> importGiftDepartment;
	
	/**
	 * 还有未处理的原始数据
	 */
	private boolean isHavingPending;
	
	/**
	 * 员工分析结果
	 * @return the importGiftRawCode
	 */
	public List<List<Long>> getImportGiftRawCode() {
		return importGiftRawCode;
	}

	/**
	 * @param importGiftRawCode the importGiftRawCode to set
	 */
	public void setImportGiftRawCode(List<List<Long>> importGiftRawCode) {
		this.importGiftRawCode = importGiftRawCode;
	}

	/**
	 * 部门分析结果
	 * @return
	 */
	public List<String> getImportGiftDepartment() {
		return importGiftDepartment;
	}

	public void setImportGiftDepartment(List<String> importGiftDepartment) {
		this.importGiftDepartment = importGiftDepartment;
	}

	/**
	 * @return the importBatchNo
	 */
	public Long getImportBatchNo() {
		return importBatchNo;
	}

	/**
	 * @param importBatchNo the importBatchNo to set
	 */
	public void setImportBatchNo(Long importBatchNo) {
		this.importBatchNo = importBatchNo;
	}

	/**
	 * @return the estimateSuccessDeptNum
	 */
	public Long getEstimateSuccessDeptNum() {
		return estimateSuccessDeptNum;
	}

	/**
	 * @param estimateSuccessDeptNum the estimateSuccessDeptNum to set
	 */
	public void setEstimateSuccessDeptNum(Long estimateSuccessDeptNum) {
		this.estimateSuccessDeptNum = estimateSuccessDeptNum;
	}

	/**
	 * @return the finalSuccessDeptNum
	 */
	public Long getFinalSuccessDeptNum() {
		return finalSuccessDeptNum;
	}

	/**
	 * @param finalSuccessDeptNum the finalSuccessDeptNum to set
	 */
	public void setFinalSuccessDeptNum(Long finalSuccessDeptNum) {
		this.finalSuccessDeptNum = finalSuccessDeptNum;
	}

	/**
	 * @return the estNewAutoCardNum
	 */
	public Long getEstNewAutoCardNum() {
		return estNewAutoCardNum;
	}

	/**
	 * @param estNewAutoCardNum the estNewAutoCardNum to set
	 */
	public void setEstNewAutoCardNum(Long estNewAutoCardNum) {
		this.estNewAutoCardNum = estNewAutoCardNum;
	}

	/**
	 * @return the finalNewAutoCardNum
	 */
	public Long getFinalNewAutoCardNum() {
		return finalNewAutoCardNum;
	}

	/**
	 * @param finalNewAutoCardNum the finalNewAutoCardNum to set
	 */
	public void setFinalNewAutoCardNum(Long finalNewAutoCardNum) {
		this.finalNewAutoCardNum = finalNewAutoCardNum;
	}

	/**
	 * @return the estNewAssignCardNum
	 */
	public Long getEstNewAssignCardNum() {
		return estNewAssignCardNum;
	}

	/**
	 * @param estNewAssignCardNum the estNewAssignCardNum to set
	 */
	public void setEstNewAssignCardNum(Long estNewAssignCardNum) {
		this.estNewAssignCardNum = estNewAssignCardNum;
	}

	/**
	 * @return the finalNewAssignCardNum
	 */
	public Long getFinalNewAssignCardNum() {
		return finalNewAssignCardNum;
	}

	/**
	 * @param finalNewAssignCardNum the finalNewAssignCardNum to set
	 */
	public void setFinalNewAssignCardNum(Long finalNewAssignCardNum) {
		this.finalNewAssignCardNum = finalNewAssignCardNum;
	}

	/**
	 * @return the estOldAssignCardNum
	 */
	public Long getEstOldAssignCardNum() {
		return estOldAssignCardNum;
	}

	/**
	 * @param estOldAssignCardNum the estOldAssignCardNum to set
	 */
	public void setEstOldAssignCardNum(Long estOldAssignCardNum) {
		this.estOldAssignCardNum = estOldAssignCardNum;
	}

	/**
	 * @return the finalOldAssignCardNum
	 */
	public Long getFinalOldAssignCardNum() {
		return finalOldAssignCardNum;
	}

	/**
	 * @param finalOldAssignCardNum the finalOldAssignCardNum to set
	 */
	public void setFinalOldAssignCardNum(Long finalOldAssignCardNum) {
		this.finalOldAssignCardNum = finalOldAssignCardNum;
	}

	/**
	 * @return the estimateSuccessNum
	 */
	public Long getEstimateSuccessNum() {
		return estimateSuccessNum;
	}

	/**
	 * @param estimateSuccessNum the estimateSuccessNum to set
	 */
	public void setEstimateSuccessNum(Long estimateSuccessNum) {
		this.estimateSuccessNum = estimateSuccessNum;
	}

	/**
	 * @return the finalSuccessNum
	 */
	public Long getFinalSuccessNum() {
		return finalSuccessNum;
	}

	/**
	 * @param finalSuccessNum the finalSuccessNum to set
	 */
	public void setFinalSuccessNum(Long finalSuccessNum) {
		this.finalSuccessNum = finalSuccessNum;
	}

	/**
	 * @return the finalFailedNum
	 */
	public Long getFinalFailedNum() {
		return finalFailedNum;
	}

	/**
	 * @param finalFailedNum the finalFailedNum to set
	 */
	public void setFinalFailedNum(Long finalFailedNum) {
		this.finalFailedNum = finalFailedNum;
	}

	/**
	 * @return the isHavingPending
	 */
	public boolean isHavingPending() {
		return isHavingPending;
	}

	/**
	 * @param isHavingPending the isHavingPending to set
	 */
	public void setHavingPending(boolean isHavingPending) {
		this.isHavingPending = isHavingPending;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ImportGiftResponse [importBatchNo=" + importBatchNo
				+ ", estimateSuccessDeptNum=" + estimateSuccessDeptNum
				+ ", finalSuccessDeptNum=" + finalSuccessDeptNum
				+ ", estNewAutoCardNum=" + estNewAutoCardNum
				+ ", finalNewAutoCardNum=" + finalNewAutoCardNum
				+ ", estNewAssignCardNum=" + estNewAssignCardNum
				+ ", finalNewAssignCardNum=" + finalNewAssignCardNum
				+ ", estOldAssignCardNum=" + estOldAssignCardNum
				+ ", finalOldAssignCardNum=" + finalOldAssignCardNum
				+ ", estimateSuccessNum=" + estimateSuccessNum
				+ ", finalSuccessNum=" + finalSuccessNum + ", finalFailedNum="
				+ finalFailedNum + ", importGiftRawCode=" + importGiftRawCode
				+ ", importGiftDepartment=" + importGiftDepartment
				+ ", isHavingPending=" + isHavingPending + "]";
	}

}
