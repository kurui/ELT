package com.chinarewards.elt.service.staff;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.chinarewards.elt.domain.org.ImportStaffBatch;
import com.chinarewards.elt.domain.org.ImportStaffRaw;
import com.chinarewards.elt.domain.org.Staff;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.staff.ImportStaffRequest;
import com.chinarewards.elt.model.staff.ImportStaffResponse;
import com.chinarewards.elt.model.vo.ImportStaffSearchVo;
import com.chinarewards.elt.service.exception.ImportStaffNotFoundException;

/**
 * @author sunhongliang
 *
 */
public interface ImportStaffLogic {

	/**
	 * 最终导入的员工文件备份目录
	 * @return
	 */
	public String getImportStaffBackupDir();
	
	/**
	 * 创建导入批次及相关信息实体 ImportStaffBatch 及导入员工原始信息 ImportStaffRaw
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
	 * 预处理员工保存导入原始信息在 ImportStaffRaw 并且更新导入批次信息 ImportStaffBatch
	 * @param request
	 * @return
	 * @throws EnterpriseHasNotPartnerRelationException 
	 * @throws PartnerHasNotRelationToChannelException 
	 * @throws SystemErrorException 
	 * @throws InvocationTargetException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchFieldException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 */
	public ImportStaffResponse pretreatmentImportStaff(ImportStaffRequest request) throws ImportStaffNotFoundException, SecurityException, IllegalArgumentException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException;

	/**
	 * 找到同一次导入的员工
	 * @param batchId
	 * @return
	 */
	public List<Staff> getStaffByBatchId(String batchId);

	/**
	 * 找到同一次导入的员工原始信息
	 * @param batchId
	 * @return
	 */
	public List<ImportStaffRaw> getStaffRawByBatchId(String batchId);
	
	/**
	 * 找到同一次导入的员工记录及相关分析结果
	 * @param batchId
	 * @return
	 */
	public ImportStaffResponse getImportStaffReport(String batchId);
	
	/**
	 * 返回当前已经导入的记录数
	 * @param batchId
	 * @return
	 */
	public Long getCurrentImportedRecord(String batchId);
	/**
	 * 
	 * 根据当前batchId删除批处理
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
	
	/**
	 * 修改预导入数据的标志
	 * @param rawId
	 * @param importfal
	 * @return
	 */
	public boolean updateImportfal(String rawId,int importfal);
	/**
	 * 查询预导入选中的count
	 * @param batchId
	 * @return
	 */
	public int findImportStaffCount(ImportStaffSearchVo searchVo);
}
