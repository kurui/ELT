package com.chinarewards.elt.service.staff;

import java.lang.reflect.InvocationTargetException;

import com.chinarewards.elt.domain.org.ImportStaffBatch;
import com.chinarewards.elt.domain.org.ImportStaffRaw;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.staff.ImportStaffRequest;
import com.chinarewards.elt.model.staff.ImportStaffResponse;
import com.chinarewards.elt.model.vo.ImportStaffSearchVo;
import com.chinarewards.elt.service.exception.ImportStaffNotFoundException;


public interface ImportStaffService {

	/**
	 * 创建导入批次及相关信息实体 ImportStaffBatch
	 * @param request
	 * @return
	 * @throws ImportStaffNotFoundException 
	 */
	public ImportStaffBatch createImportStaffBatch(ImportStaffRequest request) throws ImportStaffNotFoundException;

	/**
	 * 从导入批次里导入一个员工 Staff 并且更新导入批次信息 ImportStaffBatch
	 * @param request
	 * @return
	 */
	public ImportStaffResponse finalImportOneStaff(ImportStaffRequest request) ;

	/**
	 * 预处理员工的原始信息，返回结果
	 * @param request
	 * @return
	 * @throws InvocationTargetException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchFieldException 
	 * @throws ClassNotFoundException 
	 * @throws EnterpriseHasNotPartnerRelationException 
	 * @throws PartnerHasNotRelationToChannelException 
	 * @throws SystemErrorException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 */
	public ImportStaffResponse pretreatmentImportStaff(ImportStaffRequest request) throws ImportStaffNotFoundException, SecurityException, IllegalArgumentException,ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException;

	
	/**
	 * 找到同一次导入的员工记录及相关分析结果
	 * @param batchId
	 * @return
	 */
	public ImportStaffResponse getImportStaffReport(String batchId);
	
	/**
	 * 当前已经导入记录数
	 * @param batchId
	 * @return
	 */
	public Long getCurrentImportedRecord(String batchId);
	/**
	 * 
	 * 删除当前batchId批处理
	 * @param batchId
	 * @return
	 */
	public boolean removeAllInBatchById(String batchId);
	/**
	 * 查询导入的列表信息.分页显示
	 * @param batchId
	 * @return
	 */
	public PageStore<ImportStaffRaw> findImportStaffList(ImportStaffSearchVo searchVo);
}
