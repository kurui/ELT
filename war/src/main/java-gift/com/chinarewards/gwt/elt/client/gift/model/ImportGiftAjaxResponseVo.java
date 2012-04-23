/**
 * 
 */
package com.chinarewards.gwt.elt.client.gift.model;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.IsSerializable;


public class ImportGiftAjaxResponseVo extends ImportGiftAjaxRequestVo implements IsSerializable {

	private String LicenseMessage;
	public String getLicenseMessage() {
		return LicenseMessage;
	}

	public void setLicenseMessage(String licenseMessage) {
		LicenseMessage = licenseMessage;
	}
	/**
	 * batch id
	 */
	private String id;
	/**
	 * the pairs of import code and message
	 */
	private Map<Long, String> allImportGiftCodeInfos;
	/**
	 * the pairs of import code and type, namely, success, warning or fatal
	 */
	private Map<Long, String> allImportGiftCodeTypes;
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
	 * 预计自动分配新卡号数量
	 */
	private Long estNewAutoCardNum;
	
	/**
	 * 成功自动分配新卡号数量
	 */
	private Long finalNewAutoCardNum;
	
	/**
	 * 预计手动分配新卡号数量
	 */
	private Long estNewAssignCardNum;
	
	/**
	 * 成功手动分配新卡号数量
	 */
	private Long finalNewAssignCardNum;
	
	/**
	 * 预计手动分配旧卡号数量
	 */
	private Long estOldAssignCardNum;

	/**
	 * 成功手动分配旧卡号数量
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

	private Long finalFailedNum;
	
	/**
	 * the parse result for staff raw, namely, import codes
	 */
	private List<List<Long>> importGiftRawCode;
	
	private String fileName;
	/**
	 * 
	 * 操作者
	 */
	private String createByLastName;
	/**
	 * 
	 *导入日期 
	 */
	private Long createTime;
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
	 * @return the allImportGiftCodeInfos
	 */
	public Map<Long, String> getAllImportGiftCodeInfos() {
		return allImportGiftCodeInfos;
	}

	/**
	 * @param allImportGiftCodeInfos the allImportGiftCodeInfos to set
	 */
	public void setAllImportGiftCodeInfos(Map<Long, String> allImportGiftCodeInfos) {
		this.allImportGiftCodeInfos = allImportGiftCodeInfos;
	}

	/**
	 * @return the allImportGiftCodeTypes
	 */
	public Map<Long, String> getAllImportGiftCodeTypes() {
		return allImportGiftCodeTypes;
	}

	/**
	 * @param allImportGiftCodeTypes the allImportGiftCodeTypes to set
	 */
	public void setAllImportGiftCodeTypes(Map<Long, String> allImportGiftCodeTypes) {
		this.allImportGiftCodeTypes = allImportGiftCodeTypes;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCreateByLastName() {
		return createByLastName;
	}

	public void setCreateBy(String createByLastName) {
		this.createByLastName = createByLastName;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
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
	 * 最终导入失败数
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
	 * @param createByLastName the createByLastName to set
	 */
	public void setCreateByLastName(String createByLastName) {
		this.createByLastName = createByLastName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ImportGiftAjaxResponseVo [id=" + id
				+ ", allImportGiftCodeInfos=" + allImportGiftCodeInfos
				+ ", allImportGiftCodeTypes=" + allImportGiftCodeTypes
				+ ", importBatchNo=" + importBatchNo
				+ ", estimateSuccessDeptNum=" + estimateSuccessDeptNum
				+ ", finalSuccessDeptNum=" + finalSuccessDeptNum
				+ ", estNewAutoCardNum=" + estNewAutoCardNum
				+ ", finalNewAutoCardNum=" + finalNewAutoCardNum
				+ ", estNewAssignCardNum=" + estNewAssignCardNum
				+ ", finalNewAssignCardNum=" + finalNewAssignCardNum
				+ ", estOldAssignCardNum=" + estOldAssignCardNum
				+ ", finalOldAssignCardNum=" + finalOldAssignCardNum
				+ ", estimateSuccessNum=" + estimateSuccessNum
				+ ", finalSuccessNum=" + finalSuccessNum
				+ ", importGiftRawCode=" + importGiftRawCode + ", fileName="
				+ fileName + ", createByLastName=" + createByLastName
				+ ", createTime=" + createTime + "]";
	}

}
