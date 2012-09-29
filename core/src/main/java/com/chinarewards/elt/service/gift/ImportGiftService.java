package com.chinarewards.elt.service.gift;

import java.lang.reflect.InvocationTargetException;

import com.chinarewards.elt.domain.gift.ImportGiftBatch;
import com.chinarewards.elt.domain.gift.ImportGiftRaw;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.gift.dataexchange.ImportGiftRequest;
import com.chinarewards.elt.model.gift.dataexchange.ImportGiftResponse;
import com.chinarewards.elt.model.vo.ImportGiftSearchVo;
import com.chinarewards.elt.service.exception.ImportGiftNotFoundException;

/**
 * 
 * @author yanrui
 * @since 1.5.2
 * */
public interface ImportGiftService {

	/**
	 * 创建导入批次及相关信息实体 ImportGiftBatch
	 * 
	 * @param request
	 * @return
	 * @throws ImportGiftNotFoundException
	 */
	public ImportGiftBatch createImportGiftBatch(ImportGiftRequest request)
			throws ImportGiftNotFoundException;

	/**
	 * 从导入批次里导入一个员工 Gift 并且更新导入批次信息 ImportGiftBatch
	 * 
	 * @param request
	 * @return
	 */
	public ImportGiftResponse finalImportOneGift(ImportGiftRequest request);

	/**
	 * 预处理员工的原始信息，返回结果
	 * 
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
	public ImportGiftResponse pretreatmentImportGift(
			ImportGiftRequest request) throws ImportGiftNotFoundException,
			SecurityException, IllegalArgumentException,
			ClassNotFoundException, NoSuchFieldException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException;

	/**
	 * 找到同一次导入的员工记录及相关分析结果
	 * 
	 * @param batchId
	 * @return
	 */
	public ImportGiftResponse getImportGiftReport(String batchId);

	/**
	 * 当前已经导入记录数
	 * 
	 * @param batchId
	 * @return
	 */
	public Long getCurrentImportedRecord(String batchId);

	/**
	 * 
	 * 删除当前batchId批处理
	 * 
	 * @param batchId
	 * @return
	 */
	public boolean removeAllInBatchById(String batchId);

	/**
	 * 查询导入的列表信息.分页显示
	 * 
	 * @param batchId
	 * @return
	 */
	public PageStore<ImportGiftRaw> findImportGiftList(
			ImportGiftSearchVo searchVo);

	/**
	 * 查询预导入选中的count
	 * 
	 * @param batchId
	 * @return
	 */
	public int findImportGiftCount(ImportGiftSearchVo searchVo);

	/**
	 * 修改预导入数据的标志
	 * 
	 * @param rawId
	 * @param importfal
	 * @return
	 */
	public boolean updateImportfal(String rawId, int importfal);
}
