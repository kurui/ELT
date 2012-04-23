/**
 * 
 */
package com.chinarewards.gwt.elt.client.gift.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author sunhongliang
 * @since 0.2.0 2011-01-05
 */
public class ImportGiftAjaxRequestVo implements IsSerializable {

	private static final long serialVersionUID = 1329978070568965425L;

	/**
	 * current request is do pretreatment or do final import?
	 */
	private String action;
	/**
	 * batch id
	 */
	private String id;
	/**
	 * corporation to be import
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
	 * 是否有标题
	 */
	private boolean isHavingTitle;

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
	private List<ImportGiftRawVo> staffRawList = new ArrayList<ImportGiftRawVo>();

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the corporationId
	 */
	public String getCorporationId() {
		return corporationId;
	}

	/**
	 * @param corporationId the corporationId to set
	 */
	public void setCorporationId(String corporationId) {
		this.corporationId = corporationId;
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
	 * @return the staffRawList
	 */
	public List<ImportGiftRawVo> getGiftRawList() {
		return staffRawList;
	}

	/**
	 * @param staffRawList
	 *            the staffRawList to set
	 */
	public void setGiftRawList(List<ImportGiftRawVo> staffRawList) {
		this.staffRawList = staffRawList;
	}

	/**
	 * @return the isHavingTitle
	 */
	public boolean isHavingTitle() {
		return isHavingTitle;
	}

	/**
	 * @param isHavingTitle
	 *            the isHavingTitle to set
	 */
	public void setHavingTitle(boolean isHavingTitle) {
		this.isHavingTitle = isHavingTitle;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ImportGiftAjaxRequestVo [action=" + action + ", id=" + id
				+ ", corporationId=" + corporationId + ", fileName=" + fileName
				+ ", contentType=" + contentType + ", isHavingTitle="
				+ isHavingTitle + ", dobFormat=" + dobFormat + ", doeFormat="
				+ doeFormat + ", staffRawList=" + staffRawList + "]";
	}

}
