package com.chinarewards.elt.model.gift.dataexchange;


/**
 * 员工导入的原始数据
 * 
 * @author yanrui
 * @since 1.5.2 
 */
public class ImportGiftRawParameter {

	/**
	 * 导入员工操作的导入批次id
	 */
	private String importGiftBatchId;


	/**
	 * 行数
	 */
	private Long rowPos;
	
	/**
	 * 导入结果：成功、失败、未知
	 */
	private ImportGiftResultType result;
	
	private String id;
	private String name;
	private String source;
	private String sourceText;
	private String price;
	private String integral;
	private String stock;
	private String status;
	private String statusText;


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


	public String getImportGiftBatchId() {
		return importGiftBatchId;
	}


	public void setImportGiftBatchId(String importGiftBatchId) {
		this.importGiftBatchId = importGiftBatchId;
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


	public ImportGiftResultType getResult() {
		return result;
	}


	public void setResult(ImportGiftResultType result) {
		this.result = result;
	}
	

}
