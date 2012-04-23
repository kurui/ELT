package com.chinarewards.elt.domain.gift;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.chinarewards.elt.domain.org.Corporation;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.gift.dataexchange.ImportGiftHavingTitle;

/**
 * 导入员工操作的批次及导入相关信息.
 * 
 * @author 
 * @since 1.0.0 2010-09-19
 */
@Entity
public class ImportGiftBatch implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -249965879223299948L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Corporation corporation;
	
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
	@Enumerated(EnumType.STRING)
	private ImportGiftHavingTitle havingTitle;
	
	/**
	 * 生日日期格式
	 */
	private String dobFormat;


	/**
	 * 成功导入编号
	 */
	private Long importBatchNo;
	

	/**
	 * 预估成功导入员工数
	 */
	private Long estimateSuccessNum;

	/**
	 * 最终成功导入员工数
	 */
	private Long finalSuccessNum;

	/**
	 * 创建者
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private SysUser createBy;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 最后修改者
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	private SysUser lastUpdateBy;

	/**
	 * 最后修改时间
	 */
	private Date lastUpdateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Corporation getCorporation() {
		return corporation;
	}

	public void setCorporation(Corporation corporation) {
		this.corporation = corporation;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public ImportGiftHavingTitle getHavingTitle() {
		return havingTitle;
	}

	public void setHavingTitle(ImportGiftHavingTitle havingTitle) {
		this.havingTitle = havingTitle;
	}

	public String getDobFormat() {
		return dobFormat;
	}

	public void setDobFormat(String dobFormat) {
		this.dobFormat = dobFormat;
	}

	public Long getImportBatchNo() {
		return importBatchNo;
	}

	public void setImportBatchNo(Long importBatchNo) {
		this.importBatchNo = importBatchNo;
	}

	public Long getEstimateSuccessNum() {
		return estimateSuccessNum;
	}

	public void setEstimateSuccessNum(Long estimateSuccessNum) {
		this.estimateSuccessNum = estimateSuccessNum;
	}

	public Long getFinalSuccessNum() {
		return finalSuccessNum;
	}

	public void setFinalSuccessNum(Long finalSuccessNum) {
		this.finalSuccessNum = finalSuccessNum;
	}

	public SysUser getCreateBy() {
		return createBy;
	}

	public void setCreateBy(SysUser createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public SysUser getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(SysUser lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
}
