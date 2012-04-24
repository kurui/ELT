
package com.chinarewards.gwt.elt.client.gift.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author yanrui
 * @since 1.5.2
 */
public class ImportGiftRawVo implements IsSerializable {



	/**
	 * 导入员工操作的导入批次id
	 */
	private String importGiftBatchId;

	/**
	 * 
	 */
	private String id;
	
	private String name;
	private String source;
	private String sourceText;
	private String price;
	private String integral;
	private String stock;
	private String status;
	private String statusText;

	/**
	 * @return the importGiftBatchId
	 */
	public String getImportGiftBatchId() {
		return importGiftBatchId;
	}

	/**
	 * @param importGiftBatchId the importGiftBatchId to set
	 */
	public void setImportGiftBatchId(String importGiftBatchId) {
		this.importGiftBatchId = importGiftBatchId;
	}

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

	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceText() {
		return sourceText;
	}

	public void setSourceText(String sourceText) {
		this.sourceText = sourceText;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getIntegral() {
		return integral;
	}

	public void setIntegral(String integral) {
		this.integral = integral;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	

}
