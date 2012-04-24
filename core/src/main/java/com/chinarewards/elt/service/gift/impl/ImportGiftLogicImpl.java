package com.chinarewards.elt.service.gift.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinarewards.elt.common.LogicContext;
import com.chinarewards.elt.common.UserContextProvider;
import com.chinarewards.elt.dao.gift.ImportGiftBatchDao;
import com.chinarewards.elt.dao.gift.ImportGiftRawCodeDao;
import com.chinarewards.elt.dao.gift.ImportGiftRawDao;
import com.chinarewards.elt.dao.org.CorporationDao;
import com.chinarewards.elt.domain.gift.Gift;
import com.chinarewards.elt.domain.gift.ImportGiftBatch;
import com.chinarewards.elt.domain.gift.ImportGiftCode;
import com.chinarewards.elt.domain.gift.ImportGiftRaw;
import com.chinarewards.elt.domain.gift.ImportGiftRawCode;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.gift.dataexchange.ImportCodeType;
import com.chinarewards.elt.model.gift.dataexchange.ImportGiftEjbHelper;
import com.chinarewards.elt.model.gift.dataexchange.ImportGiftHavingTitle;
import com.chinarewards.elt.model.gift.dataexchange.ImportGiftParser;
import com.chinarewards.elt.model.gift.dataexchange.ImportGiftRawParameter;
import com.chinarewards.elt.model.gift.dataexchange.ImportGiftRequest;
import com.chinarewards.elt.model.gift.dataexchange.ImportGiftResponse;
import com.chinarewards.elt.model.gift.dataexchange.ImportGiftResultType;
import com.chinarewards.elt.model.gift.search.GiftStatus;
import com.chinarewards.elt.model.org.Gender;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.model.vo.ImportGiftSearchVo;
import com.chinarewards.elt.service.exception.ImportGiftNotFoundException;
import com.chinarewards.elt.service.gift.GiftLogic;
import com.chinarewards.elt.service.gift.GiftService;
import com.chinarewards.elt.service.gift.ImportGiftCodeLogic;
import com.chinarewards.elt.service.gift.ImportGiftLogic;
import com.chinarewards.elt.service.license.LicenseService;
import com.chinarewards.elt.service.org.CorporationLogic;
import com.chinarewards.elt.service.org.DepartmentLogic;
import com.chinarewards.elt.service.user.UserLogic;
import com.google.inject.Inject;

/**
 * 
 * @author yanrui
 * @since 1.5.2
 */
public class ImportGiftLogicImpl implements ImportGiftLogic {
	// @InjectLogger
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	final ImportGiftBatchDao importGiftBatchDao;
	final ImportGiftRawDao importGiftRawDao;
	final ImportGiftRawCodeDao importGiftRawCodeDao;
	final UserLogic userLogic;
	final GiftLogic giftLogic;
	final DepartmentLogic departmentLogic;
	final CorporationLogic corporationLogic;
	final CorporationDao corporationDao;
	final ImportGiftCodeLogic importCodeLogic;
	final LicenseService licenseService;
	final GiftService giftService;

	@Inject
	public ImportGiftLogicImpl(CorporationDao corporationDao,
			ImportGiftBatchDao importGiftBatchDao,
			ImportGiftRawDao importGiftRawDao,
			ImportGiftRawCodeDao importGiftRawCodeDao, UserLogic userLogic,
			GiftLogic giftLogic, DepartmentLogic departmentLogic,
			CorporationLogic corporationLogic,
			ImportGiftCodeLogic importCodeLogic, LicenseService licenseService,
			GiftService giftService) {

		this.importGiftBatchDao = importGiftBatchDao;
		this.importGiftRawDao = importGiftRawDao;
		this.importGiftRawCodeDao = importGiftRawCodeDao;
		this.userLogic = userLogic;
		this.giftLogic = giftLogic;
		this.departmentLogic = departmentLogic;
		this.corporationLogic = corporationLogic;
		this.corporationDao = corporationDao;
		this.importCodeLogic = importCodeLogic;
		this.licenseService = licenseService;
		this.giftService = giftService;

	}

	@Override
	public ImportGiftBatch createImportGiftBatch(ImportGiftRequest request)
			throws ImportGiftNotFoundException {
		logger.debug("createImportGiftBatch method starts with request = "
				+ request);
		if (request == null || request.getGiftRawList().isEmpty()) {
			throw new ImportGiftNotFoundException("没有可导入的员工原始信息");
		}
		Date now = getNow();
		ImportGiftBatch batch;
		if (request.getId() != null) {
			removeAllInBatchById(request.getId());

		}
		SysUser nowuser = userLogic.findUserById(request.getNowUserId());
		// create import Gift batch
		batch = new ImportGiftBatch();
		batch.setCorporation(corporationLogic.findCorporationById(request
				.getCorporationId()));
		batch.setCreateBy(nowuser);
		batch.setCreateTime(now);
		batch.setFileName(request.getFileName());
		batch.setContentType(request.getContentType());
		batch.setDobFormat(request.getDobFormat());

		batch.setHavingTitle(request.isHavingTitle() ? ImportGiftHavingTitle.HAVING_TITLE
				: ImportGiftHavingTitle.NO_TITLE);
		batch.setLastUpdateBy(nowuser);
		batch.setLastUpdateTime(now);
		importGiftBatchDao.save(batch);

		// add import Gift raw
		for (ImportGiftRawParameter pGiftRaw : request.getGiftRawList()) {
			ImportGiftRaw GiftRaw = new ImportGiftRaw();
			toImportGiftRaw(GiftRaw, pGiftRaw, batch);
			GiftRaw.setResult(ImportGiftResultType.PENDING);
			GiftRaw = importGiftRawDao.save(GiftRaw);
		}

		logger.debug("createImportGiftBatch method end with batch = " + batch);
		return batch;
	}

	protected ImportGiftEjbHelper prepareEjbHelper(String corporationId,
			ImportGiftRequest request, List<ImportGiftRaw> GiftRaws) {

		ImportGiftEjbHelper ejbHelper = new ImportGiftEjbHelper();

		// retrieve date format in request
		List<String> desiredNames = new ArrayList<String>();
		List<String> desiredMobileNos = new ArrayList<String>();
		for (ImportGiftRaw GiftRaw : GiftRaws) {
			desiredNames.add(GiftRaw.getName());
			// desiredMobileNos.add(GiftRaw.getMobileTelephoneNumber());

		}
		ejbHelper.setDobFormat(request.getDobFormat());
		ejbHelper.setDesiredName(desiredNames);
		ejbHelper.setDesiredMobileNos(desiredMobileNos);

		List<Gift> existingGifts = giftLogic.findNotDeleteGift();

		List<String> existingMobileNos = new ArrayList<String>();
		List<String> existingName = new ArrayList<String>();

		for (int i = 0; i < existingGifts.size(); i++) {
			existingName.add((String) existingGifts.get(i).getName());
			// existingMobileNos.add((String) existingGifts.get(i).getPhone());

			// existingGifts.get(i).getJobNo());

		}
		ejbHelper.setExistingMobileNos(existingMobileNos);
		ejbHelper.setExistingName(existingName);

		ejbHelper.setNow(this.getNow());

		return ejbHelper;
	}

	@Override
	public ImportGiftResponse pretreatmentImportGift(ImportGiftRequest request)
			throws SecurityException, IllegalArgumentException,
			ClassNotFoundException, NoSuchFieldException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException, ImportGiftNotFoundException {
		logger.debug("pretreatmentImportGift method starts with request = "
				+ request);

		ImportGiftResponse response = calculatePretreatmentImportGift(request);
		logger.debug("pretreatmentImportGift method end with response = "
				+ response);
		return response;
	}

	/**
	 * @param request
	 * @return
	 * @throws SystemErrorException
	 * @throws PartnerHasNotRelationToChannelException
	 * @throws EnterpriseHasNotPartnerRelationException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws ClassNotFoundException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws ImportGiftNotFoundException
	 */
	private ImportGiftResponse calculatePretreatmentImportGift(
			ImportGiftRequest request) throws SecurityException,
			IllegalArgumentException, ClassNotFoundException,
			NoSuchFieldException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException,
			ImportGiftNotFoundException {

		ImportGiftBatch batch = importGiftBatchDao.findById(
				ImportGiftBatch.class, request.getId());
		batch.setHavingTitle(request.isHavingTitle() ? ImportGiftHavingTitle.HAVING_TITLE
				: ImportGiftHavingTitle.NO_TITLE);
		batch.setDobFormat(request.getDobFormat());
		batch.setLastUpdateTime(getNow());

		// retrieve all uploaded Gift raw
		List<ImportGiftRaw> GiftRaws = getGiftRawByBatchId(request.getId());

		if (GiftRaws == null || GiftRaws.isEmpty()) {
			throw new ImportGiftNotFoundException("没有可预处理的员工");
		}

		// 所有验证方法加入
		ImportGiftEjbHelper helper = prepareEjbHelper(
				request.getCorporationId(), request, GiftRaws);

		ImportGiftResponse response = new ImportGiftResponse(request);

		List<ImportGiftCode> codes = importCodeLogic.getAll();
		// 屏蔽验证
		//
		// EmailValidator validator = new EmailValidator("emailAddress",
		// "EMAIL");
		//
		List<List<Long>> importGiftRawCodes = new ArrayList<List<Long>>();
		//
		// long newAssignCardNum = 0;
		// long newAutoCardNum = 0;
		// long oldAssignCardNum = 0;
		long estimateSuccessNum = 0;
		// List<List<String>> validDepartmentRawList = new
		// ArrayList<List<String>>();

		for (ImportGiftRaw GiftRaw : GiftRaws) {
			ImportGiftRawParameter pGiftRaw = new ImportGiftRawParameter();
			toImportGiftRawParameter(GiftRaw, pGiftRaw);

			logger.debug("calculatePretreatmentImportGift - start parse Gift raw : "
					+ pGiftRaw);

			// this.getEntityManager()
			// .createQuery(
			// "DELETE FROM ImportGiftRawCode isrc WHERE isrc.importGiftRaw.id=:rawId")
			// .setParameter("rawId", GiftRaw.getId()).executeUpdate();

			List<Long> parseRawResult = new ArrayList<Long>();
			if (ImportGiftHavingTitle.HAVING_TITLE.equals(batch
					.getHavingTitle())
					&& GiftRaw.getRowPos().compareTo(new Long(1)) == 0) {
				// ignore title
				continue;
			}
			//
			//
			//
			// email validation on current Gift raw
			// try {
			// boolean fal= StringUtil.isValidEmail(pGiftRaw.getEmail());
			// if(fal)
			// helper.setEmailFormatInvalid(false);
			// else
			// helper.setEmailFormatInvalid(true);
			// } catch (Exception e) {
			// helper.setEmailFormatInvalid(true);
			// }

			logger.debug(
					"calculatePretreatmentImportGift - helper result = {}",
					helper);

			helper.setAllPassed(true);

			boolean isFailed = false;
			for (ImportGiftCode code : codes) {
				logger.debug("method = " + code);

				Object[] args = { pGiftRaw, helper };

				// if rule is against, result is true
				Boolean result = (Boolean) ImportGiftParser
						.getParserMethodResult(code.getParserMethod(), args);

				if (result) {
					logger.debug("calculatePretreatmentImportGift - checking Gift raw on the rule "
							+ code.getParserMethod()
							+ " with code "
							+ code.getCode() + " true");

					// parse result is true
					parseRawResult.add(code.getCode());

					ImportGiftRawCode rawCode = new ImportGiftRawCode();
					rawCode.setImportGiftRaw(GiftRaw);
					rawCode.setImportCode(code);
					importGiftRawCodeDao.save(rawCode);

					if (code.getType().equals(ImportCodeType.FATAL)) {
						if (!isFailed) {
							isFailed = true;
						}
					}
				} else {

					logger.debug("calculatePretreatmentImportGift - checking Gift raw on the rule "
							+ code.getParserMethod()
							+ " with code "
							+ code.getCode() + " false");
				}

				if (helper.isAllPassed() && result) {
					// parse failed
					helper.setAllPassed(false);
				}
			}
			//
			if (!isFailed) {
				estimateSuccessNum++;
				//
				// validDepartmentRawList.add(ImportGiftParser
				// .convertRaw2Department(pGiftRaw.getDepartment()));
				//
			}

			importGiftRawCodes.add(parseRawResult);

			// importGiftRawDao.update(GiftRaw);
		}

		// logger.debug(
		// "calculatePretreatmentImportGift - validDepartmentRawList - {}",
		// validDepartmentRawList);

		batch.setEstimateSuccessNum(estimateSuccessNum);
		batch.setFinalSuccessNum(null);
		batch.setImportBatchNo(null);
		batch = importGiftBatchDao.update(batch);
		//
		// logger.debug(
		// "calculatePretreatmentImportGift - import Gift raw codes = {}",
		// importGiftRawCodes);
		//
		//
		response.setImportGiftRawCode(importGiftRawCodes);
		//
		// response.setFinalSuccessDeptNum(null);
		// response.setEstNewAutoCardNum(newAutoCardNum);
		// response.setFinalNewAutoCardNum(null);
		// response.setEstNewAssignCardNum(newAssignCardNum);
		// response.setFinalNewAssignCardNum(null);
		// response.setEstOldAssignCardNum(oldAssignCardNum);
		// response.setFinalOldAssignCardNum(null);
		response.setEstimateSuccessNum(estimateSuccessNum);
		// response.setFinalSuccessNum(null);
		response.setImportBatchNo(importGiftBatchDao.getLastImportBatch(batch
				.getCorporation().getId()) + 1);

		return response;
	}

	@Override
	public List<Gift> getGiftByBatchId(String batchId) {
		return importGiftBatchDao.getAllGiftInSameBatch(batchId);
	}

	@Override
	public List<ImportGiftRaw> getGiftRawByBatchId(String batchId) {
		return importGiftBatchDao.getAllGiftRawInSameBatch(batchId);
	}

	protected LogicContext getLogicContext() {
		return UserContextProvider.get();
	}

	protected Date getNow() {
		return new Date();
	}

	protected void toImportGiftRaw(ImportGiftRaw giftRaw,
			ImportGiftRawParameter pGiftRaw, ImportGiftBatch importGiftBatch) {
		giftRaw.setId(pGiftRaw.getId());
		giftRaw.setImportGiftBatch(importGiftBatch);

		giftRaw.setRowPos(pGiftRaw.getRowPos());

		giftRaw.setName(pGiftRaw.getName());
		giftRaw.setSource(pGiftRaw.getSource());
		giftRaw.setSourceText(pGiftRaw.getSourceText());
		giftRaw.setPrice(pGiftRaw.getPrice());
		giftRaw.setIntegral(pGiftRaw.getIntegral());
		giftRaw.setStock(pGiftRaw.getStock());
		giftRaw.setStatusValue(pGiftRaw.getStatusValue());
		giftRaw.setStatusText(pGiftRaw.getStatusText());

	}

	protected void toImportGiftRawParameter(ImportGiftRaw giftRaw,
			ImportGiftRawParameter pGiftRaw) {
		pGiftRaw.setId(giftRaw.getId());
		pGiftRaw.setRowPos(giftRaw.getRowPos());
		pGiftRaw.setResult(giftRaw.getResult());
		pGiftRaw.setName(giftRaw.getName());

		pGiftRaw.setSource(giftRaw.getSource());
		pGiftRaw.setSourceText(giftRaw.getSourceText());
		pGiftRaw.setPrice(giftRaw.getPrice());
		pGiftRaw.setIntegral(giftRaw.getIntegral());
		pGiftRaw.setStock(giftRaw.getStock());
		pGiftRaw.setStatusValue(giftRaw.getStatusValue());
		pGiftRaw.setStatusText(giftRaw.getStatusText());

	}

	// protected GiftPersistence processImportGiftRawByInstruction(
	// ImportGiftBatch batch, ImportGiftRaw importGiftRaw,
	// com.chinarewards.hr.model.rewards.dataimport.Department department,
	// String targetCorporationId) {
	// GiftPersistence GiftPersistence = new GiftPersistence();
	// GiftPersistence.setImportGiftBatchId(batch.getId());
	// GiftPersistence.setCorporationId(targetCorporationId);
	// GiftPersistence.setDepartmentId(department.getId());
	// if (importGiftRaw.getMemberCardNumber() == null
	// || "".equals(importGiftRaw.getMemberCardNumber().trim())) {
	// GiftPersistence.setMemberCardNumber(null);
	// GiftPersistence.setAutoCreateCardNumber(true);
	// } else {
	// GiftPersistence.setMemberCardNumber(importGiftRaw
	// .getMemberCardNumber());
	// GiftPersistence.setAutoCreateCardNumber(false);
	// }
	// GiftPersistence.setMobileTelephoneNumber(importGiftRaw
	// .getMobileTelephoneNumber());
	// GiftPersistence.setEmailAddress(importGiftRaw.getEmailAddress());
	// GiftPersistence
	// .setNotificationMethodConstant(NotificationMethod.EMAIL);
	// GiftPersistence.setFirstName(importGiftRaw.getFirstName());
	// GiftPersistence.setLastName(importGiftRaw.getLastName());
	// GiftPersistence
	// .setForeignLastName(importGiftRaw.getForeignLastName());
	// GiftPersistence.setForeignFirstName(importGiftRaw
	// .getForeignFirstName());
	// GiftPersistence.setGiftNumber(importGiftRaw.getGiftNumber());
	// GiftPersistence.setDob(convertDate(importGiftRaw.getDob(),
	// batch.getDobFormat()));
	// GiftPersistence.setDateOfEmployment(convertDate(
	// importGiftRaw.getDateOfEmployment(), batch.getDoeFormat()));
	// GiftPersistence.setPosition(importGiftRaw.getGiftPosition());
	// GiftPersistence.setGender(convertGender(importGiftRaw.getGender()));
	// GiftPersistence.setNativePlace(importGiftRaw.getNativePlace());
	// GiftPersistence.setMinorityNationality(importGiftRaw
	// .getMinorityNationality());
	// GiftPersistence.setIdNo(importGiftRaw.getIdNo());
	// GiftPersistence.setLocation(importGiftRaw.getLocation());
	// return GiftPersistence;
	// }

	protected Gender convertGender(String src) {
		if (Gender.MALE.getMessage().equals(src))
			return Gender.MALE;
		if (Gender.FEMALE.getMessage().equals(src))
			return Gender.FEMALE;
		return Gender.UNEXPOSED;
	}

	protected Date convertDate(String src, String fmt) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(fmt);
			return format.parse(src);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public ImportGiftResponse getImportGiftReport(String batchId) {
		ImportGiftBatch batch = importGiftBatchDao.findById(
				ImportGiftBatch.class, batchId);
		ImportGiftResponse response = new ImportGiftResponse();
		response.setContentType(batch.getContentType());
		response.setDobFormat(batch.getDobFormat());
		response.setFileName(batch.getFileName());
		response.setHavingTitle(ImportGiftHavingTitle.HAVING_TITLE.equals(batch
				.getHavingTitle()));
		response.setId(batchId);
		response.setImportBatchNo(batch.getImportBatchNo());
		setImportGiftRawAndCodeForResponse(response, batchId, true, true);
		if (batch.getImportBatchNo() != null) {
			setImportGiftDepartmentForResponse(response, batchId, false, true);
		} else {
			setImportGiftDepartmentForResponse(response, batchId, true, false);
		}
		return response;
	}

	@Override
	public Long getCurrentImportedRecord(String batchId) {
		return importGiftBatchDao.getProcessedGiftRawInSameBatch(batchId);
	}

	@Override
	public boolean removeAllInBatchById(String batchId) {
		logger.debug("removeAllInBatchById - " + batchId);
		ImportGiftBatch batch = importGiftBatchDao.findById(
				ImportGiftBatch.class, batchId);

		importGiftBatchDao.delete(batch);
		return true;
	}

	@Override
	public ImportGiftResponse finalImportOneGift(ImportGiftRequest request) {
		logger.debug("finalImportOneGift method starts with request = "
				+ request);
		ImportGiftBatch batch = importGiftBatchDao.findById(
				ImportGiftBatch.class, request.getId());

		long finalSuccessNum = 0;
		// long newAutoCardNum = 0;
		// long newAssignCardNum = 0;
		// long oldAssignCardNum = 0;
		// long finalSuccessDeptNum = 0;
		List<ImportGiftRaw> rawList = this.importGiftBatchDao
				.getOutstandingGiftRawInSameBatch(request.getId());

		logger.debug("outstanding Gift raw record number = " + rawList.size());

		// create Gift one by one, exit if one was finished
		for (ImportGiftRaw raw : rawList) {

			if (raw.getRowPos().compareTo(new Long(1)) == 0
					&& batch.getHavingTitle().equals(
							ImportGiftHavingTitle.HAVING_TITLE)) {
				raw.setResult(ImportGiftResultType.SUCCESS);
				break;
			}

			logger.debug("start prepare Gift raw = " + raw);
			List<ImportGiftRawCode> rawCodes = importGiftRawCodeDao
					.getImportGiftRawCodeByRawId(raw.getId());

			boolean isIgnore = false;
			for (ImportGiftRawCode rawCode : rawCodes) {
				// any raw code is fatal, ignore import current record
				if (rawCode.getImportCode().getType()
						.compareTo(ImportCodeType.FATAL) == 0) {
					isIgnore = true;
					break;
				}
			}

			if (isIgnore) {
				raw.setResult(ImportGiftResultType.FAILURE);
				importGiftRawDao.update(raw);
			} else {
				boolean isSuccess = false;
				try {
					UserContext context = new UserContext();
					context.setCorporationId(request.getCorporationId());
					context.setUserId(request.getNowUserId());

					SysUser caller=userLogic.findUserById(request.getNowUserId());
					
					Gift gift = assembleGift(raw);
					String giftId = "";
					 gift=giftLogic.save(caller, gift);
					 giftId = gift.getId();

					// 加员工--------------------------------------------------
					logger.debug("prepare to create Gift ");
					if (giftId != null && !"ERROR".equals(giftId)) {
						isSuccess = true;
						finalSuccessNum++;
					}

				} catch (Exception e) {
					logger.warn("import Gift failed due to system error!", e);
				}
				if (isSuccess) {
					raw.setResult(ImportGiftResultType.SUCCESS);
				} else {
					try {
						raw.setResult(ImportGiftResultType.FAILURE);
					} catch (Exception e) {
						logger.warn(
								"failed to roll back department due to send email failed after Gift created",
								e);
						raw.setResult(ImportGiftResultType.FAILURE_ON_SENDING_EMAIL);
					}
				}
				importGiftRawDao.update(raw);
			}

			logger.debug("jump out import Gift raw loop");
			break;
		}

		batch.setImportBatchNo(batch.getImportBatchNo() == null ? importGiftBatchDao
				.getLastImportBatch(batch.getCorporation().getId()) + 1 : batch
				.getImportBatchNo());
		logger.debug("before batch.setFinalSuccessNum - batch.getFinalSuccessNum()="
				+ batch.getFinalSuccessNum()
				+ "; finalSuccessNum="
				+ finalSuccessNum);
		batch.setFinalSuccessNum(batch.getFinalSuccessNum() == null ? finalSuccessNum
				: batch.getFinalSuccessNum() + finalSuccessNum);

		batch = importGiftBatchDao.update(batch);

		ImportGiftResponse response = new ImportGiftResponse(request);
		response.setImportBatchNo(batch.getImportBatchNo());
		response.setHavingTitle(ImportGiftHavingTitle.HAVING_TITLE.equals(batch
				.getHavingTitle()));
		response.setFinalSuccessNum(batch.getFinalSuccessNum());
		response.setEstimateSuccessNum(batch.getEstimateSuccessNum());

		response.setId(batch.getId());
		if (rawList.size() > 1) {
			response.setHavingPending(true);
		} else {
			response.setHavingPending(false);

			Long finalFailedNum = new Long(0);

			response.setFinalFailedNum(finalFailedNum);
			setImportGiftRawAndCodeForResponse(response, batch.getId(), false,
					true);
			setImportGiftDepartmentForResponse(response, batch.getId(), false,
					true);
		}
		logger.debug("finalImportOneGift method end with response = "
				+ response);
		return response;
	}

	/**
	 * Convert from GiftVo to GeneratorGiftModel.
	 */
	public static Gift assembleGift(ImportGiftRaw raw) {
		Gift gift = new Gift();
		gift.setName(raw.getName());

		String source = "";
		if ("内部直接提供".equals(raw.getSourceText())) {
			source = "inner";
		} else if ("外部货品公司提供".equals(raw.getSourceText())) {
			source = "outter";
		}
		gift.setSource(source);

		gift.setIntegral(Integer.parseInt(raw.getIntegral()));
		gift.setPrice(raw.getPrice());
		gift.setStock(Integer.parseInt(raw.getStock()));

		GiftStatus updateStatus = null;
		if ("上架".equals(raw.getStatusText())) {
			updateStatus = GiftStatus.SHELVES;
		} else if ("下架".equals(raw.getStatusText())) {
			updateStatus = GiftStatus.SHELF;
		}
		if (updateStatus != null) {
			gift.setStatus(updateStatus);
		}
		
		// gift.setIndate(getIndate());//有效截止期
		gift.setDeleted(false);

		return gift;
	}

	protected void setImportGiftDepartmentForResponse(
			ImportGiftResponse response, String batchId,
			boolean isHavingPending, boolean isHavingSuccess) {
		List<String> importGiftDepartment = new ArrayList<String>();

		response.setImportGiftDepartment(importGiftDepartment);
	}

	protected void setImportGiftRawAndCodeForResponse(
			ImportGiftResponse response, String batchId,
			boolean isHavingGiftRaw, boolean isHavingGiftRawCode) {
		logger.debug("retrieveImportGiftRawAndCodeForResponse - isHavingGiftRaw = "
				+ isHavingGiftRaw
				+ "; isHavingGiftRawCode="
				+ isHavingGiftRawCode);
		List<ImportGiftRawParameter> pGiftRaws = new ArrayList<ImportGiftRawParameter>();
		List<List<Long>> pRawCodes = new ArrayList<List<Long>>();
		List<ImportGiftRaw> raws = importGiftBatchDao
				.getAllGiftRawInSameBatch(batchId);
		for (ImportGiftRaw raw : raws) {
			if (response.isHavingTitle()
					&& raw.getRowPos().compareTo(new Long(1)) == 0) {
				continue;
			}
			ImportGiftRawParameter pGiftRaw = new ImportGiftRawParameter();
			this.toImportGiftRawParameter(raw, pGiftRaw);
			pGiftRaws.add(pGiftRaw);
			if (isHavingGiftRawCode) {
				List<ImportGiftRawCode> rawCodes = importGiftRawCodeDao
						.getImportGiftRawCodeByRawId(raw.getId());
				List<Long> pRawCode = new ArrayList<Long>();
				for (ImportGiftRawCode rawCode : rawCodes) {
					pRawCode.add(rawCode.getImportCode().getCode());
				}
				pRawCodes.add(pRawCode);
			}
		}
		if (isHavingGiftRaw) {
			response.setGiftRawList(pGiftRaws);
		}
		if (isHavingGiftRawCode) {
			response.setImportGiftRawCode(pRawCodes);
		}
		logger.debug("retrieveImportGiftRawAndCodeForResponse - response = {}",
				response);
	}

	@Override
	public String getImportGiftBackupDir() {
		return null;
	}

	@Override
	public PageStore<ImportGiftRaw> findImportGiftList(
			ImportGiftSearchVo searchVo) {
		return importGiftRawDao.queryImportGiftPageAction(searchVo);
	}

	@Override
	public boolean updateImportfal(String rawId, int importfal) {
		try {
			ImportGiftRaw raw = importGiftRawDao.findById(ImportGiftRaw.class,
					rawId);
			raw.setImportfal(importfal);
			importGiftRawDao.update(raw);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public int findImportGiftCount(ImportGiftSearchVo searchVo) {
		return importGiftRawDao.getAllGiftRawInSameBatchCount(searchVo);
	}
}
