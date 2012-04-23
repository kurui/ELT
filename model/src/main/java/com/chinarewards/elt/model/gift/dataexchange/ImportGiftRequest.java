package com.chinarewards.elt.model.gift.dataexchange;

import java.util.ArrayList;
import java.util.List;

/**
 * 导入员工操作输入
 * 
 * @author yanrui
 * @since 1.5.2 
 */
public class ImportGiftRequest {

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
	private List<ImportGiftRawParameter> GiftRawList = new ArrayList<ImportGiftRawParameter>();
	
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
	 * @return the GiftRawList
	 */
	public List<ImportGiftRawParameter> getGiftRawList() {
		return GiftRawList;
	}

	/**
	 * @param GiftRawList
	 *            the GiftRawList to set
	 */
	public void setGiftRawList(List<ImportGiftRawParameter> GiftRawList) {
		this.GiftRawList = GiftRawList;
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
	 * The corporation ID which these Gift will go to.
	 * 
	 * @return the corporationId
	 */
	public String getCorporationId() {
		return corporationId;
	}

	/**
	 * The corporation ID which these Gift will go to.
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
		return "ImportGiftRequest [id=" + id + ", corporationId="
				+ corporationId + ", fileName=" + fileName + ", contentType="
				+ contentType + ", havingTitle=" + havingTitle + ", dobFormat="
				+ dobFormat + ", doeFormat=" + doeFormat + ", GiftRawList="
				+ GiftRawList + "]";
	}

}
