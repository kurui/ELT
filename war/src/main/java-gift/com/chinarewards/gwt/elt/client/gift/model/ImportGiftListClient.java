package com.chinarewards.gwt.elt.client.gift.model;

import java.io.Serializable;
/**
 * @author yanrui
 * @since 1.5.2
 */
public class ImportGiftListClient implements Serializable,
		Comparable<ImportGiftListClient> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4934837755724342679L;
	private String id;
	private String name;
	private String source;
	private String sourceText;
	private String price;
	private String integral;
	private String stock;
	private String status;
	private String statusText;

	private Integer importfal;

	public ImportGiftListClient() {

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

	public Integer getImportfal() {
		return importfal;
	}

	public void setImportfal(Integer importfal) {
		this.importfal = importfal;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int compareTo(ImportGiftListClient o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
