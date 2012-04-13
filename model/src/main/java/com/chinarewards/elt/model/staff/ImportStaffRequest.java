package com.chinarewards.elt.model.staff;

import java.util.ArrayList;
import java.util.List;

/**
 * 导入员工操作输入
 * 
 * @author sunhongliang
 * @since 2.0.0 2010-09-19
 */
public class ImportStaffRequest {

	private String id;

	/**
	 * which corporation to be import
	 */
	private String corporationId;

	/**
	 * 导入文件名
	 */
	private String fileName;

	/**
	 * 导入文件类型
	 */
	private String contentType;

	/**
	 * 有无标题
	 */
	private boolean havingTitle;

	/**
	 * 生日日期格式
	 */
	private String dobFormat;

	/**
	 * 入职日期格式
	 */
	private String doeFormat;

	/**
	 * 导入员工列表
	 */
	private List<ImportStaffRawParameter> staffRawList = new ArrayList<ImportStaffRawParameter>();
	
	private String nowUserId;

	public String getNowUserId() {
		return nowUserId;
	}

	public void setNowUserId(String nowUserId) {
		this.nowUserId = nowUserId;
	}

	/**
	 * @return
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType
	 *            the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the dobFormat
	 */
	public String getDobFormat() {
		return dobFormat;
	}

	/**
	 * @param dobFormat
	 *            the dobFormat to set
	 */
	public void setDobFormat(String dobFormat) {
		this.dobFormat = dobFormat;
	}

	/**
	 * @return the doeFormat
	 */
	public String getDoeFormat() {
		return doeFormat;
	}

	/**
	 * @param doeFormat
	 *            the doeFormat to set
	 */
	public void setDoeFormat(String doeFormat) {
		this.doeFormat = doeFormat;
	}

	/**
	 * @return the staffRawList
	 */
	public List<ImportStaffRawParameter> getStaffRawList() {
		return staffRawList;
	}

	/**
	 * @param staffRawList
	 *            the staffRawList to set
	 */
	public void setStaffRawList(List<ImportStaffRawParameter> staffRawList) {
		this.staffRawList = staffRawList;
	}

	/**
	 * @return the havingTitle
	 */
	public boolean isHavingTitle() {
		return havingTitle;
	}

	/**
	 * @param havingTitle
	 *            the havingTitle to set
	 */
	public void setHavingTitle(boolean havingTitle) {
		this.havingTitle = havingTitle;
	}

	/**
	 * The corporation ID which these staff will go to.
	 * 
	 * @return the corporationId
	 */
	public String getCorporationId() {
		return corporationId;
	}

	/**
	 * The corporation ID which these staff will go to.
	 * 
	 * @param corporationId
	 *            the corporationId to set
	 */
	public void setCorporationId(String corporationId) {
		this.corporationId = corporationId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ImportStaffRequest [id=" + id + ", corporationId="
				+ corporationId + ", fileName=" + fileName + ", contentType="
				+ contentType + ", havingTitle=" + havingTitle + ", dobFormat="
				+ dobFormat + ", doeFormat=" + doeFormat + ", staffRawList="
				+ staffRawList + "]";
	}

}
