package com.chinarewards.elt.service.staff.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinarewards.elt.common.LogicContext;
import com.chinarewards.elt.common.UserContextProvider;
import com.chinarewards.elt.dao.org.CorporationDao;
import com.chinarewards.elt.dao.org.ImportStaffBatchDao;
import com.chinarewards.elt.dao.org.ImportStaffRawCodeDao;
import com.chinarewards.elt.dao.org.ImportStaffRawDao;
import com.chinarewards.elt.domain.org.Department;
import com.chinarewards.elt.domain.org.ImportStaffBatch;
import com.chinarewards.elt.domain.org.ImportStaffCode;
import com.chinarewards.elt.domain.org.ImportStaffRaw;
import com.chinarewards.elt.domain.org.ImportStaffRawCode;
import com.chinarewards.elt.domain.org.Staff;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.org.Gender;
import com.chinarewards.elt.model.staff.ImportCodeType;
import com.chinarewards.elt.model.staff.ImportStaffEjbHelper;
import com.chinarewards.elt.model.staff.ImportStaffHavingTitle;
import com.chinarewards.elt.model.staff.ImportStaffRawParameter;
import com.chinarewards.elt.model.staff.ImportStaffRequest;
import com.chinarewards.elt.model.staff.ImportStaffResponse;
import com.chinarewards.elt.model.staff.ImportStaffResultType;
import com.chinarewards.elt.model.staff.StaffProcess;
import com.chinarewards.elt.model.staff.StaffStatus;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.model.user.UserRole;
import com.chinarewards.elt.model.vo.ImportStaffSearchVo;
import com.chinarewards.elt.model.vo.LicenseBo;
import com.chinarewards.elt.service.exception.ImportStaffNotFoundException;
import com.chinarewards.elt.service.license.LicenseService;
import com.chinarewards.elt.service.org.CorporationLogic;
import com.chinarewards.elt.service.org.DepartmentLogic;
import com.chinarewards.elt.service.staff.IStaffService;
import com.chinarewards.elt.service.staff.ImportStaffCodeLogic;
import com.chinarewards.elt.service.staff.ImportStaffLogic;
import com.chinarewards.elt.service.staff.StaffLogic;
import com.chinarewards.elt.service.user.UserLogic;
import com.chinarewards.elt.util.DateUtil;
import com.chinarewards.elt.util.StringUtil;
import com.google.inject.Inject;

/**
 * 
 * @author sunhongliang
 * 
 */
public class ImportStaffLogicImpl implements ImportStaffLogic {
	// @InjectLogger
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	final ImportStaffBatchDao importStaffBatchDao;
	final ImportStaffRawDao importStaffRawDao;
	final ImportStaffRawCodeDao importStaffRawCodeDao;
	final UserLogic userLogic;
	final StaffLogic staffLogic;
	final DepartmentLogic departmentLogic;
	final CorporationLogic corporationLogic;
	final CorporationDao corporationDao;
	final ImportStaffCodeLogic importCodeLogic;
	final LicenseService licenseService;
	final IStaffService staffService;
	@Inject
	public ImportStaffLogicImpl(
			CorporationDao corporationDao,
			ImportStaffBatchDao importStaffBatchDao,
			ImportStaffRawDao importStaffRawDao,
			ImportStaffRawCodeDao importStaffRawCodeDao, UserLogic userLogic,
			StaffLogic staffLogic, DepartmentLogic departmentLogic,
			CorporationLogic corporationLogic,ImportStaffCodeLogic importCodeLogic,LicenseService licenseService,IStaffService staffService) {

		this.importStaffBatchDao = importStaffBatchDao;
		this.importStaffRawDao = importStaffRawDao;
		this.importStaffRawCodeDao = importStaffRawCodeDao;
		this.userLogic = userLogic;
		this.staffLogic = staffLogic;
		this.departmentLogic = departmentLogic;
		this.corporationLogic = corporationLogic;
		this.corporationDao = corporationDao;
		this.importCodeLogic=importCodeLogic;
		this.licenseService=licenseService;
		this.staffService=staffService;

	}


	@Override
	public ImportStaffBatch createImportStaffBatch(ImportStaffRequest request)
			throws ImportStaffNotFoundException {
		logger.debug("createImportStaffBatch method starts with request = "
				+ request);
		if (request == null || request.getStaffRawList().isEmpty()) {
			throw new ImportStaffNotFoundException("没有可导入的员工原始信息");
		}
		Date now = getNow();
		ImportStaffBatch batch;
		if (request.getId() != null) {
			removeAllInBatchById(request.getId());

		}
		SysUser nowuser=userLogic.findUserById(request.getNowUserId());
		// create import staff batch
		batch = new ImportStaffBatch();
		batch.setCorporation(corporationLogic.findCorporationById(request
				.getCorporationId()));
		batch.setCreateBy(nowuser);
		batch.setCreateTime(now);
		batch.setFileName(request.getFileName());
		batch.setContentType(request.getContentType());
		batch.setDobFormat(request.getDobFormat());
		
		batch.setHavingTitle(request.isHavingTitle() ? ImportStaffHavingTitle.HAVING_TITLE
				: ImportStaffHavingTitle.NO_TITLE);
		batch.setLastUpdateBy(nowuser);
		batch.setLastUpdateTime(now);
		importStaffBatchDao.save(batch);

		// add import staff raw
		for (ImportStaffRawParameter pStaffRaw : request.getStaffRawList()) {
			ImportStaffRaw staffRaw = new ImportStaffRaw();
			toImportStaffRaw(staffRaw, pStaffRaw, batch);
			staffRaw.setResult(ImportStaffResultType.PENDING);
			staffRaw = importStaffRawDao.save(staffRaw);
		}

		logger.debug("createImportStaffBatch method end with batch = " + batch);
		return batch;
	}

	protected ImportStaffEjbHelper prepareEjbHelper(String corporationId,
			ImportStaffRequest request, List<ImportStaffRaw> staffRaws){

		ImportStaffEjbHelper ejbHelper = new ImportStaffEjbHelper();

		// retrieve date format in request
		List<String> desiredEmailAddress = new ArrayList<String>();
		List<String> desiredMobileNos = new ArrayList<String>();
		List<String> desiredStaffNumber = new ArrayList<String>();
		for (ImportStaffRaw staffRaw : staffRaws) {

			desiredEmailAddress.add(staffRaw.getEmailAddress());
			desiredMobileNos.add(staffRaw.getMobileTelephoneNumber());
			if (staffRaw.getStaffNumber() != null) {
				desiredStaffNumber.add(staffRaw.getStaffNumber());
			}
		}
		ejbHelper.setDobFormat(request.getDobFormat());
		ejbHelper.setDesiredEmailAddress(desiredEmailAddress);
		ejbHelper.setDesiredMobileNos(desiredMobileNos);

	

	
		List<Staff> existingStaffs=staffLogic.findNotDeleteStaff(corporationId);
		
		List<String> existingMobileNos = new ArrayList<String>();
		List<String> existingEmailAddress = new ArrayList<String>();
		List<String> existingCardMembers = new ArrayList<String>();
		List<String> existingStaffNumbers = new ArrayList<String>();
		for (int i = 0; i < existingStaffs.size(); i++) {
			
				existingMobileNos.add((String) existingStaffs.get(i).getPhone());
				existingEmailAddress.add((String) existingStaffs.get(i).getEmail());
				existingStaffNumbers.add((String) existingStaffs.get(i).getJobNo());
			
		}
		ejbHelper.setExistingMobileNos(existingMobileNos);
		ejbHelper.setExistingEmailAddress(existingEmailAddress);
		ejbHelper.setExistingMemberCardNumbers(existingCardMembers);
		ejbHelper.setExistingStaffNumbers(existingStaffNumbers);


		ejbHelper.setNow(this.getNow());

		return ejbHelper;
	}




	@Override
	public ImportStaffResponse pretreatmentImportStaff(
			ImportStaffRequest request) throws SecurityException,
			IllegalArgumentException, ClassNotFoundException,
			NoSuchFieldException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException,
			ImportStaffNotFoundException {
		logger.debug("pretreatmentImportStaff method starts with request = "
				+ request);

		ImportStaffResponse response = calculatePretreatmentImportStaff(request);
		logger.debug("pretreatmentImportStaff method end with response = "
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
	 * @throws ImportStaffNotFoundException
	 */
	private ImportStaffResponse calculatePretreatmentImportStaff(
			ImportStaffRequest request) throws  SecurityException,
			IllegalArgumentException, ClassNotFoundException,
			NoSuchFieldException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException,
			ImportStaffNotFoundException {

		ImportStaffBatch batch = importStaffBatchDao.findById(ImportStaffBatch.class,request.getId());
		batch.setHavingTitle(request.isHavingTitle() ? ImportStaffHavingTitle.HAVING_TITLE
				: ImportStaffHavingTitle.NO_TITLE);
		batch.setDobFormat(request.getDobFormat());
		batch.setLastUpdateTime(getNow());

		// retrieve all uploaded staff raw
		List<ImportStaffRaw> staffRaws = getStaffRawByBatchId(request.getId());

		if (staffRaws == null || staffRaws.isEmpty()) {
			throw new ImportStaffNotFoundException("没有可预处理的员工");
		}

//		ImportStaffEjbHelper helper = prepareEjbHelper(request.getCorporationId(), request, staffRaws);

		ImportStaffResponse response = new ImportStaffResponse(request);
		
		List<ImportStaffCode> codes = importCodeLogic.getAll();
//屏蔽验证
//
//		EmailValidator validator = new EmailValidator("emailAddress", "EMAIL");
//
		List<List<Long>> importStaffRawCodes = new ArrayList<List<Long>>();
//
//		long newAssignCardNum = 0;
//		long newAutoCardNum = 0;
//		long oldAssignCardNum = 0;
		long estimateSuccessNum = 0;
//		List<List<String>> validDepartmentRawList = new ArrayList<List<String>>();




		for (ImportStaffRaw staffRaw : staffRaws) {
			ImportStaffRawParameter pStaffRaw = new ImportStaffRawParameter();
			toImportStaffRawParameter(staffRaw, pStaffRaw);
			
			logger.debug("calculatePretreatmentImportStaff - start parse staff raw : "+ pStaffRaw);

//			this.getEntityManager()
//					.createQuery(
//							"DELETE FROM ImportStaffRawCode isrc WHERE isrc.importStaffRaw.id=:rawId")
//					.setParameter("rawId", staffRaw.getId()).executeUpdate();
			

			List<Long> parseRawResult = new ArrayList<Long>();
			if (ImportStaffHavingTitle.HAVING_TITLE.equals(batch.getHavingTitle())	&& staffRaw.getRowPos().compareTo(new Long(1)) == 0) {
				// ignore title
				continue;
			}
//
//		
//
//			// email validation on current staff raw
//			try {
//				validator.validate(pStaffRaw);
//				helper.setEmailFormatInvalid(false);
//			} catch (Exception e) {
//				helper.setEmailFormatInvalid(true);
//			}
//
//			logger.debug(
//					"calculatePretreatmentImportStaff - helper result = {}",
//					helper);
//
//			helper.setAllPassed(true);
			boolean isFailed = false;
			for (ImportStaffCode code : codes) {
				 logger.debug("method = " + code);
//
//				Object[] args = { pStaffRaw, helper };
//
//				// if rule is against, result is true
//				Boolean result = (Boolean) ImportStaffParser
//						.getParserMethodResult(code.getParserMethod(), args);

//				if (result) {
//					logger.debug("calculatePretreatmentImportStaff - checking staff raw on the rule "
//							+ code.getParserMethod()
//							+ " with code "
//							+ code.getCode() + " true");
//
//					// parse result is true
					parseRawResult.add(code.getCode());
//
//					ImportStaffRawCode rawCode = new ImportStaffRawCode();
//					rawCode.setImportStaffRaw(staffRaw);
//					rawCode.setImportCode(code);
//					importStaffRawCodeDao.save(rawCode);
//
//					if (code.getType().equals(ImportCodeType.FATAL)) {
//						if (!isFailed) {
//							isFailed = true;
//						}
//					}
//					// } else {
//					// logger.debug("calculatePretreatmentImportStaff - checking staff raw on the rule "
//					// + code.getParserMethod()
//					// + " with code "
//					// + code.getCode() + " false");
//				}
//
//				if (helper.isAllPassed() && result) {
//					// parse failed
//					helper.setAllPassed(false);
//				}
			}
//
			if (!isFailed) {
				estimateSuccessNum++;
//				
//				validDepartmentRawList.add(ImportStaffParser
//						.convertRaw2Department(pStaffRaw.getDepartment()));
//				
			} 

			importStaffRawCodes.add(parseRawResult);
//
//			importStaffRawDao.update(staffRaw);
		}
//
//		logger.debug(
//				"calculatePretreatmentImportStaff - validDepartmentRawList - {}",
//				validDepartmentRawList);

//
//		batch.setEstimateSuccessNum(estimateSuccessNum);
//		batch.setFinalSuccessNum(null);
//		batch.setImportBatchNo(null);
//		batch = importStaffBatchDao.update(batch);
//
//		logger.debug(
//				"calculatePretreatmentImportStaff - import staff raw codes = {}",
//				importStaffRawCodes);
//
//
		response.setImportStaffRawCode(importStaffRawCodes);
//
//		response.setFinalSuccessDeptNum(null);
//		response.setEstNewAutoCardNum(newAutoCardNum);
//		response.setFinalNewAutoCardNum(null);
//		response.setEstNewAssignCardNum(newAssignCardNum);
//		response.setFinalNewAssignCardNum(null);
//		response.setEstOldAssignCardNum(oldAssignCardNum);
//		response.setFinalOldAssignCardNum(null);
		response.setEstimateSuccessNum(estimateSuccessNum);
//		response.setFinalSuccessNum(null);
//		response.setImportBatchNo(importStaffBatchDao.getLastImportBatch(batch
//				.getCorporation().getId()) + 1);
		
		
		//查询 license信息
		try {
			UserContext context=new UserContext();
			context.setCorporationId(request.getCorporationId());
			
			LicenseBo licensebo=licenseService.queryLicenseContent();
			int number=staffService.findNotDeleteStaffNumber(context);
			if(number+estimateSuccessNum>licensebo.getStaffNumber())
			{
				response.setLicenseMessage("当前在职用户数"+number+",预计加入用户数"+estimateSuccessNum+",已经超过软件License最大用户数 "+licensebo.getStaffNumber()+" !请重新申请!");
			}
		} catch (Exception e) {
			response.setLicenseMessage("License异常,请重新申请!");
		}

		
		
		
		
	
		return response;
	}

	@Override
	public List<Staff> getStaffByBatchId(String batchId) {
		return importStaffBatchDao.getAllStaffInSameBatch(batchId);
	}

	@Override
	public List<ImportStaffRaw> getStaffRawByBatchId(String batchId) {
		return importStaffBatchDao.getAllStaffRawInSameBatch(batchId);
	}

	protected LogicContext getLogicContext() {
		return UserContextProvider.get();
	}



	protected Date getNow() {
		return new Date();
	}

	protected void toImportStaffRaw(ImportStaffRaw staffRaw,
			ImportStaffRawParameter pStaffRaw, ImportStaffBatch importStaffBatch) {
		staffRaw.setId(pStaffRaw.getId());
		staffRaw.setImportStaffBatch(importStaffBatch);
		staffRaw.setRowPos(pStaffRaw.getRowPos());
		staffRaw.setDepartment(pStaffRaw.getDepartment());
		staffRaw.setDob(pStaffRaw.getDob());
		staffRaw.setEmailAddress(pStaffRaw.getEmail());
		staffRaw.setMobileTelephoneNumber(pStaffRaw.getPhone());
		staffRaw.setStaffNumber(pStaffRaw.getJobNo());
		staffRaw.setName(pStaffRaw.getName());
		staffRaw.setJobPosition(pStaffRaw.getJobPosition());
		staffRaw.setLeadership(pStaffRaw.getLeadership());

	}

	protected void toImportStaffRawParameter(ImportStaffRaw staffRaw,
			ImportStaffRawParameter pStaffRaw) {
		pStaffRaw.setId(staffRaw.getId());
		pStaffRaw.setRowPos(staffRaw.getRowPos());
		pStaffRaw.setDepartment(staffRaw.getDepartment());
		pStaffRaw.setPhone(staffRaw.getMobileTelephoneNumber());
		pStaffRaw.setEmail(staffRaw.getEmailAddress());
		pStaffRaw.setJobNo(staffRaw.getStaffNumber());
		pStaffRaw.setDob(staffRaw.getDob());
		pStaffRaw.setResult(staffRaw.getResult());
		pStaffRaw.setName(staffRaw.getName());
		pStaffRaw.setJobPosition(staffRaw.getJobPosition());
		pStaffRaw.setLeadership(staffRaw.getLeadership());
	}

//	protected StaffPersistence processImportStaffRawByInstruction(
//			ImportStaffBatch batch, ImportStaffRaw importStaffRaw,
//			com.chinarewards.hr.model.rewards.dataimport.Department department,
//			String targetCorporationId) {
//		StaffPersistence staffPersistence = new StaffPersistence();
//		staffPersistence.setImportStaffBatchId(batch.getId());
//		staffPersistence.setCorporationId(targetCorporationId);
//		staffPersistence.setDepartmentId(department.getId());
//		if (importStaffRaw.getMemberCardNumber() == null
//				|| "".equals(importStaffRaw.getMemberCardNumber().trim())) {
//			staffPersistence.setMemberCardNumber(null);
//			staffPersistence.setAutoCreateCardNumber(true);
//		} else {
//			staffPersistence.setMemberCardNumber(importStaffRaw
//					.getMemberCardNumber());
//			staffPersistence.setAutoCreateCardNumber(false);
//		}
//		staffPersistence.setMobileTelephoneNumber(importStaffRaw
//				.getMobileTelephoneNumber());
//		staffPersistence.setEmailAddress(importStaffRaw.getEmailAddress());
//		staffPersistence
//				.setNotificationMethodConstant(NotificationMethod.EMAIL);
//		staffPersistence.setFirstName(importStaffRaw.getFirstName());
//		staffPersistence.setLastName(importStaffRaw.getLastName());
//		staffPersistence
//				.setForeignLastName(importStaffRaw.getForeignLastName());
//		staffPersistence.setForeignFirstName(importStaffRaw
//				.getForeignFirstName());
//		staffPersistence.setStaffNumber(importStaffRaw.getStaffNumber());
//		staffPersistence.setDob(convertDate(importStaffRaw.getDob(),
//				batch.getDobFormat()));
//		staffPersistence.setDateOfEmployment(convertDate(
//				importStaffRaw.getDateOfEmployment(), batch.getDoeFormat()));
//		staffPersistence.setPosition(importStaffRaw.getStaffPosition());
//		staffPersistence.setGender(convertGender(importStaffRaw.getGender()));
//		staffPersistence.setNativePlace(importStaffRaw.getNativePlace());
//		staffPersistence.setMinorityNationality(importStaffRaw
//				.getMinorityNationality());
//		staffPersistence.setIdNo(importStaffRaw.getIdNo());
//		staffPersistence.setLocation(importStaffRaw.getLocation());
//		return staffPersistence;
//	}

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
	public ImportStaffResponse getImportStaffReport(String batchId) {
		ImportStaffBatch batch = importStaffBatchDao.findById(ImportStaffBatch.class,batchId);
		ImportStaffResponse response = new ImportStaffResponse();
		response.setContentType(batch.getContentType());
		response.setDobFormat(batch.getDobFormat());
		response.setFileName(batch.getFileName());
		response.setHavingTitle(ImportStaffHavingTitle.HAVING_TITLE
				.equals(batch.getHavingTitle()));
		response.setId(batchId);
		response.setImportBatchNo(batch.getImportBatchNo());
		setImportStaffRawAndCodeForResponse(response, batchId, true, true);
		if (batch.getImportBatchNo() != null) {
			setImportStaffDepartmentForResponse(response, batchId, false, true);
		} else {
			setImportStaffDepartmentForResponse(response, batchId, true, false);
		}
		return response;
	}

	@Override
	public Long getCurrentImportedRecord(String batchId) {
		return importStaffBatchDao.getProcessedStaffRawInSameBatch(batchId);
	}

	@Override
	public boolean removeAllInBatchById(String batchId) {
		logger.debug("removeAllInBatchById - " + batchId);
		ImportStaffBatch batch = importStaffBatchDao.findById(ImportStaffBatch.class,batchId);
//		List<ImportStaffRaw> raws = importStaffBatchDao
//				.getAllStaffRawInSameBatch(batchId);
//		for (ImportStaffRaw raw : raws) {
//			this.getEntityManager()
//					.createQuery(
//							"delete from ImportStaffRawCode rawCode"
//									+ " where rawCode.importStaffRaw = :importStaffRaw")
//					.setParameter("importStaffRaw", raw).executeUpdate();
//
//		}
//
//		this.getEntityManager()
//				.createQuery(
//						"delete from ImportStaffDepartment isd"
//								+ " where isd.importStaffBatch = :importStaffBatch")
//				.setParameter("importStaffBatch", batch).executeUpdate();
//
//		this.getEntityManager()
//				.createQuery(
//						"delete from ImportStaffRaw raw"
//								+ " where raw.importStaffBatch = :importStaffBatch")
//				.setParameter("importStaffBatch", batch).executeUpdate();

		importStaffBatchDao.delete(batch);
		return true;
	}

	@Override
	public ImportStaffResponse finalImportOneStaff(ImportStaffRequest request) {
		logger.debug("finalImportOneStaff method starts with request = "
				+ request);
		ImportStaffBatch batch = importStaffBatchDao.findById(ImportStaffBatch.class,request.getId());

		long finalSuccessNum = 0;
//		long newAutoCardNum = 0;
//		long newAssignCardNum = 0;
//		long oldAssignCardNum = 0;
//		long finalSuccessDeptNum = 0;
		List<ImportStaffRaw> rawList = this.importStaffBatchDao	.getOutstandingStaffRawInSameBatch(request.getId());

		logger.debug("outstanding staff raw record number = " + rawList.size());

		// create staff one by one, exit if one was finished
		for (ImportStaffRaw raw : rawList) {

			if (raw.getRowPos().compareTo(new Long(1)) == 0 	&& batch.getHavingTitle().equals(ImportStaffHavingTitle.HAVING_TITLE)) {
				raw.setResult(ImportStaffResultType.SUCCESS);
				break;
			}

			logger.debug("start prepare staff raw = " + raw);
			List<ImportStaffRawCode> rawCodes = importStaffRawCodeDao.getImportStaffRawCodeByRawId(raw.getId());

			boolean isIgnore = false;
			for (ImportStaffRawCode rawCode : rawCodes) {
				// any raw code is fatal, ignore import current record
				if (rawCode.getImportCode().getType().compareTo(ImportCodeType.FATAL) == 0) {
					isIgnore = true;
					break;
				}
			}

			if (isIgnore) {
				raw.setResult(ImportStaffResultType.FAILURE);
				importStaffRawDao.update(raw);
				logger.debug("import staff ignored");
			} else {
				boolean isSuccess = false;
//				DepartmentImportResult result = null;
				try {
//					List<String> currDeptList = ImportStaffParser
//							.convertRaw2Department(raw.getDepartment());
//					String[] currDeptArray = currDeptList
//							.toArray(new String[] {});
//					result = departmentLogic.batchImportDepartment(batch
//							.getCorporation().getId(), currDeptArray);

//					com.chinarewards.hr.model.rewards.dataimport.Department currDepartment = result
//							.getDepts().get(result.getDepts().size() - 1);

					// populate staff parameter and save into db
//					StaffPersistence staffPersistence = processImportStaffRawByInstruction(	batch, raw, currDepartment, batch.getCorporation().getId());
					StaffProcess sp=new StaffProcess();
					sp.setStaffNo(raw.getStaffNumber());
					sp.setStaffName(raw.getName());
					sp.setPhone(raw.getMobileTelephoneNumber());
					sp.setEmail(raw.getEmailAddress());
					sp.setDob(DateUtil.dTStringtoDate(raw.getDob()));
					sp.setStatus(StaffStatus.JOB);
					sp.setJobPosition(raw.getJobPosition());
					sp.setLeadership(raw.getLeadership());

					
					//查询是否有该部门
					if(!StringUtil.isEmptyString(raw.getDepartment()))
					{
						Department dept=departmentLogic.findDepartmentByName(raw.getDepartment().trim());
						if(dept!=null)
						{
							sp.setDepartmentId(dept.getId());
						}
					}
					
					
					
					
					List<UserRole> roles=new ArrayList<UserRole>();
					roles.add(UserRole.STAFF);			
					sp.setUserRoleVos(roles);
					
					UserContext context=new UserContext();
					context.setCorporationId(request.getCorporationId());
					context.setUserId(request.getNowUserId());
					
					String staffId=staffLogic.createOrUpdateStaff(sp, context);
					
					//加员工--------------------------------------------------
					logger.debug("prepare to create staff ");
//					Staff staff = staffLogic
//							.saveStaffAndCreateMember(staffPersistence);
					if(staffId!=null && !"ERROR".equals(staffId))
					{
						isSuccess = true;
						finalSuccessNum++;
					}
//					if (raw.getAssignCardType().compareTo(
//							MemberCardAssignType.AUTOMATIC_NEW) == 0) {
//						newAutoCardNum++;
//					} else if (raw.getAssignCardType().compareTo(
//							MemberCardAssignType.MANUAL_NEW) == 0) {
//						newAssignCardNum++;
//					} else if (raw.getAssignCardType().compareTo(
//							MemberCardAssignType.MANUAL_RECYCLE) == 0) {
//						oldAssignCardNum++;
//					}
					//finalSuccessDeptNum += result.getNewDepartmentCount();

//				}  catch (MemberCardNoDuplicateInCorporationException e) {
//					logger.warn(
//							"import staff failed due to member card no duplicate in corporation!",
//							e);
//				} catch (MobileNoDuplicateInCorporationException e) {
//					logger.warn(
//							"import staff failed due to mobile no duplicate in corporation!",
//							e);
//				} catch (StaffNumberDuplicateInCorporationException e) {
//					logger.warn(
//							"import staff failed due to staff number duplicate in corporation!",
//							e);
//				} catch (EmailDuplicateInCorporationException e) {
//					logger.warn(
//							"import staff failed due to email duplicate in corporation!",
//							e);
				} catch (Exception e) {
					logger.warn("import staff failed due to system error!", e);
				}
				if (isSuccess) {
					raw.setResult(ImportStaffResultType.SUCCESS);
//					if (result != null && result.getNewDepartmentCount() > 0) {
//						long ttlNew = result.getNewDepartmentCount();
//						for (int i = result.getDepts().size() - 1; i >= 0; i--) {
//							if (ttlNew > 0) {
//								List<String> departmentRawList = new ArrayList<String>();
//								for (int j = 0; j <= i; j++) {
//									com.chinarewards.hr.model.rewards.dataimport.Department dept = result
//											.getDepts().get(j);
//									departmentRawList.add(dept.getName());
//								}
//								ImportStaffDepartmentParameter p = new ImportStaffDepartmentParameter();
//								p.setDepartmentRaw(ImportStaffParser
//										.convertDepartment2Raw(departmentRawList));
//								p.setResult(ImportStaffResultType.SUCCESS);
//								logger.debug(
//										"prepare create ImportStaffDepartmentParameter = {}",
//										p);
//								p.setImportStaffBatchId(batch.getId());
//								importStaffDepartmentLogic
//										.createImportStaffDepartment(p);
//								ttlNew--;
//							} else {
//								break;
//							}
//						}
//					}
				} else {
					try {
						raw.setResult(ImportStaffResultType.FAILURE);
						// failed to create staff, roll back department
//						if (result != null
//								&& result.getNewDepartmentCount() > 0) {
//							long ttl = result.getNewDepartmentCount();
//							for (int i = result.getDepts().size() - 1; i >= 0; i--) {
//								com.chinarewards.hr.model.rewards.dataimport.Department dept = result
//										.getDepts().get(i);
//								if (ttl > 0) {
//									this.getEntityManager()
//											.createQuery(
//													"DELETE FROM Department dept WHERE dept.id=:deptId")
//											.setParameter("deptId",
//													dept.getId())
//											.executeUpdate();
//									ttl--;
//								} else {
//									break;
//								}
//							}
//						}
					} catch (Exception e) {
						logger.warn(
								"failed to roll back department due to send email failed after staff created",
								e);
						raw.setResult(ImportStaffResultType.FAILURE_ON_SENDING_EMAIL);
					}
				}
				importStaffRawDao.update(raw);
			}

			logger.debug("jump out import staff raw loop");
			break;
		}

		batch.setImportBatchNo(batch.getImportBatchNo() == null ? importStaffBatchDao
				.getLastImportBatch(batch.getCorporation().getId()) + 1 : batch
				.getImportBatchNo());
		logger.debug("before batch.setFinalSuccessNum - batch.getFinalSuccessNum()="
				+ batch.getFinalSuccessNum()
				+ "; finalSuccessNum="
				+ finalSuccessNum);
		batch.setFinalSuccessNum(batch.getFinalSuccessNum() == null ? finalSuccessNum
				: batch.getFinalSuccessNum() + finalSuccessNum);
		
		
		batch = importStaffBatchDao.update(batch);

		ImportStaffResponse response = new ImportStaffResponse(request);
		response.setImportBatchNo(batch.getImportBatchNo());
		response.setHavingTitle(ImportStaffHavingTitle.HAVING_TITLE
				.equals(batch.getHavingTitle()));
		response.setFinalSuccessNum(batch.getFinalSuccessNum());
		response.setEstimateSuccessNum(batch.getEstimateSuccessNum());

		response.setId(batch.getId());
		if (rawList.size() > 1) {
			response.setHavingPending(true);
		} else {
			// last import action in batch
			response.setHavingPending(false);
//			@SuppressWarnings("unchecked")
//			List<Long> fatalCountLst = getEntityManager()
//					.createQuery(
//							"SELECT COUNT(*) FROM ImportStaffRaw isr WHERE isr.result = :resultType and isr.importStaffBatch.id=:batchId")
//					.setParameter("resultType", ImportStaffResultType.FAILURE)
//					.setParameter("batchId", batch.getId()).getResultList();
			
			
			Long finalFailedNum = new Long(0);
//			if (fatalCountLst != null && fatalCountLst.size() > 0) {
//				finalFailedNum = fatalCountLst.get(0);
//			}
			response.setFinalFailedNum(finalFailedNum);
			setImportStaffRawAndCodeForResponse(response, batch.getId(), false,
					true);
			setImportStaffDepartmentForResponse(response, batch.getId(), false,
					true);
		}
		logger.debug("finalImportOneStaff method end with response = "
				+ response);
		return response;
	}

	protected void setImportStaffDepartmentForResponse(
			ImportStaffResponse response, String batchId,
			boolean isHavingPending, boolean isHavingSuccess) {
		List<String> importStaffDepartment = new ArrayList<String>();
//		if (isHavingSuccess) {
//			for (ImportStaffDepartment isd : importStaffDepartmentLogic
//					.getFinalImportParseResult(batchId)) {
//				importStaffDepartment.add(isd.getDepartmentRaw());
//			}
//		}
//		if (isHavingPending) {
//			for (ImportStaffDepartment isd : importStaffDepartmentLogic
//					.getPretreatmentParseResult(batchId)) {
//				importStaffDepartment.add(isd.getDepartmentRaw());
//			}
//		}
		response.setImportStaffDepartment(importStaffDepartment);
	}

	protected void setImportStaffRawAndCodeForResponse(
			ImportStaffResponse response, String batchId,
			boolean isHavingStaffRaw, boolean isHavingStaffRawCode) {
		logger.debug("retrieveImportStaffRawAndCodeForResponse - isHavingStaffRaw = "
				+ isHavingStaffRaw
				+ "; isHavingStaffRawCode="
				+ isHavingStaffRawCode);
		List<ImportStaffRawParameter> pStaffRaws = new ArrayList<ImportStaffRawParameter>();
		List<List<Long>> pRawCodes = new ArrayList<List<Long>>();
		List<ImportStaffRaw> raws = importStaffBatchDao
				.getAllStaffRawInSameBatch(batchId);
		for (ImportStaffRaw raw : raws) {
			if (response.isHavingTitle()
					&& raw.getRowPos().compareTo(new Long(1)) == 0) {
				continue;
			}
			ImportStaffRawParameter pStaffRaw = new ImportStaffRawParameter();
			this.toImportStaffRawParameter(raw, pStaffRaw);
			pStaffRaws.add(pStaffRaw);
			if (isHavingStaffRawCode) {
				List<ImportStaffRawCode> rawCodes = importStaffRawCodeDao
						.getImportStaffRawCodeByRawId(raw.getId());
				List<Long> pRawCode = new ArrayList<Long>();
				for (ImportStaffRawCode rawCode : rawCodes) {
					pRawCode.add(rawCode.getImportCode().getCode());
				}
				pRawCodes.add(pRawCode);
			}
		}
		if (isHavingStaffRaw) {
			response.setStaffRawList(pStaffRaws);
		}
		if (isHavingStaffRawCode) {
			response.setImportStaffRawCode(pRawCodes);
		}
		logger.debug(
				"retrieveImportStaffRawAndCodeForResponse - response = {}",
				response);
	}


	@Override
	public String getImportStaffBackupDir() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public PageStore<ImportStaffRaw> findImportStaffList(ImportStaffSearchVo searchVo) {
		return importStaffRawDao.queryImportStaffPageAction(searchVo);
	}


	@Override
	public boolean updateImportfal(String rawId, int importfal) {
		try {
			ImportStaffRaw raw=importStaffRawDao.findById(ImportStaffRaw.class, rawId);
			raw.setImportfal(importfal);
			importStaffRawDao.update(raw);
			return true;
		} catch (Exception e) {
			return false;
		}

	}


	@Override
	public int findImportStaffCount(ImportStaffSearchVo searchVo) {
		return importStaffRawDao.getAllStaffRawInSameBatchCount(searchVo);
	}
}
