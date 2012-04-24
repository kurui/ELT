package com.chinarewards.elt.domain.gift;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.chinarewards.elt.model.gift.dataexchange.ImportGiftResultType;

/**
 * 员工导入的原始数据
 * 
 * @author yanrui
 * @since 1.5.2
 */
@Entity
public class ImportGiftRaw implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	/**
	 * 导入员工操作的批次导入信息
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private ImportGiftBatch importGiftBatch;

	/**
	 * 行数
	 */
	private Long rowPos;

	/**
	 * 导入结果
	 */
	@Enumerated(EnumType.STRING)
	private ImportGiftResultType result;

	/**
	 * 预导入标志-默认0,--修改1为pass掉
	 */
	private Integer importfal;

	private String name;
	private String source;
	private String sourceText;
	private String price;
	private String integral;
	private String stock;
	private String statusValue;
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


	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
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

	public ImportGiftBatch getImportGiftBatch() {
		return importGiftBatch;
	}

	public void setImportGiftBatch(ImportGiftBatch importGiftBatch) {
		this.importGiftBatch = importGiftBatch;
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
