///**
// * 
// */
//package com.chinarewards.elt.service.rewards;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.chinarewards.elt.dao.org.DepartmentDao;
//import com.chinarewards.elt.dao.org.IndustryDao;
//import com.chinarewards.elt.dao.org.OrgPolicyDao;
//import com.chinarewards.elt.dao.reward.CandidateRuleDao;
//import com.chinarewards.elt.dao.reward.RewardItemDao;
//import com.chinarewards.elt.domain.org.Corporation;
//import com.chinarewards.elt.domain.org.Department;
//import com.chinarewards.elt.domain.org.Industry;
//import com.chinarewards.elt.domain.org.Staff;
//import com.chinarewards.elt.domain.reward.base.Reward;
//import com.chinarewards.elt.domain.reward.base.RewardItem;
//import com.chinarewards.elt.domain.reward.person.Nominee;
//import com.chinarewards.elt.domain.reward.person.PreWinnerLot;
//import com.chinarewards.elt.domain.reward.rule.DobRule;
//import com.chinarewards.elt.domain.user.SysUser;
//import com.chinarewards.elt.model.common.PageStore;
//import com.chinarewards.elt.model.org.DepartmentPolicyConstants;
//import com.chinarewards.elt.model.org.RewardsApprovalPolicyEnum;
//import com.chinarewards.elt.model.reward.base.RewardItemParam;
//import com.chinarewards.elt.model.reward.base.RewardStatus;
//import com.chinarewards.elt.model.reward.frequency.WeekDays;
//import com.chinarewards.elt.model.reward.frequency.WeeklyVo;
//import com.chinarewards.elt.model.rewards.NomineeStatus;
//import com.chinarewards.elt.model.transaction.TransactionUnit;
//import com.chinarewards.elt.model.user.DeleteMarkConstant;
//import com.chinarewards.elt.model.user.UserContext;
//import com.chinarewards.elt.model.user.UserStatus;
//import com.chinarewards.elt.service.common.JPATestCase;
//import com.chinarewards.elt.service.exception.ConsumeAmountIsOutException;
//import com.chinarewards.elt.service.exception.CorporationDataException;
//import com.chinarewards.elt.service.exception.CorporationEnoughBalanceRollBackException;
//import com.chinarewards.elt.service.exception.HrUserDataException;
//import com.chinarewards.elt.service.exception.NomineeStatusErrorException;
//import com.chinarewards.elt.service.exception.OneCrmEnterpriseOneAdministratorException;
//import com.chinarewards.elt.service.exception.OrganizationInstanceException;
//import com.chinarewards.elt.service.exception.RewardsStatusErrorException;
//import com.chinarewards.elt.service.exception.TransactionHasHappenException;
//import com.chinarewards.elt.service.org.CorporationLogic;
//import com.chinarewards.elt.service.reward.RewardItemLogic;
//import com.chinarewards.elt.service.reward.RewardLogic;
//import com.chinarewards.elt.tx.exception.DuplicateUnitCodeException;
//import com.chinarewards.elt.tx.service.TransactionService;
//import com.chinarewards.elt.util.DateUtil;
//
//public class RewardsLogicTest extends JPATestCase {
//
//	// 不需要审核直接成功
//	public void testGeneratorRewards_ignoreTreated()
//			throws CorporationDataException {
//		UserContext context = getUserContext();
//		RewardLogic rewardLogic = this.injector.getInstance(RewardLogic.class);
//		// OrganizationPolicyDao policyDao = this.injector
//		// .getInstance(OrganizationPolicyDao.class);
//		assertNotNull(rewardLogic);
//		DepartmentDao departmentDao = this.injector
//				.getInstance(DepartmentDao.class);
//		CandidateRuleDao rewardsItemRuleDao = this.injector
//				.getInstance(CandidateRuleDao.class);
//		OrgPolicyDao policyDao = injector
//				.getInstance(OrgPolicyDao.class);
//		SysUserDao userDao = injector.getInstance(SysUserDao.class);
//
//		RewardItem item = prepareRewardItem();
//
//		String buildDeptId = item.getBuilderDept().getId();
//		// 设置为不需审核
//		Department dept = departmentDao.findById(Department.class, buildDeptId);
//		OrganizationPolicy policy = new OrganizationPolicy();
//		policy.setKey2(DepartmentPolicyConstants.REWARDS_APPROVAL_POLICY);
//		policy.setValue(RewardsApprovalPolicyEnum.NOT_REQUIRED.toString());
//		policy.setOwner(dept);
//		policyDao.save(policy);
//		try {
//			SysUser caller = userDao.findById(SysUser.class,
//					context.getUserId());
//			Reward reward = rewardLogic.awardFromRewardItem(caller,
//					item.getId());
//			assertNotNull(reward);
//			assertNotNull(reward.getId());
//
//			// check PreWinner
//			List<PreWinnerLot> lots = assertEquals(1, nomineeRecords.size());
//
//			// 验证Nominee的大小
//			List<Nominee> nominees = rewardsLogic
//					.getNomineeByNomineeRewardsId(nomineeRecords.get(0).getId());
//			assertEquals(2, nominees.size());
//			// 验证Winners的大小
//			List<Winners> winners = rewardsLogic.getWinnersByRewardsId(rewards
//					.getId());
//			assertEquals(2, winners.size());
//			assertEquals(RewardStatus.REWARDED, rewards.getStatus());
//			RewardsParameter parameter = rewardsModel.getRewardsParameter();
//			assertEquals(parameter.getName(), rewards.getName());
//			assertEquals(parameter.getDefinition(), rewards.getDefinition());
//			assertEquals(parameter.getRewardsItemId(), rewards.getRewardsItem()
//					.getId());
//			// test participate
//			List<RewardsItemRule> rules = rewardsItemRuleDao
//					.findCandidateRuleByRIID(rewards.getRewardsItem().getId());
//			List<RewardsItemRule> rules_rewards = rewardsItemRuleDao
//					.findCandidateRuleByRewardId(rewards.getId());
//			assertEquals(rules.size(), rules_rewards.size());
//			assertEquals(parameter.getStandard(), rewards.getStandard());
//			assertEquals(item.getAmount(), rewards.getAmount());
//			assertEquals(parameter.getBuilderDeptId(), rewards.getBuilderDept()
//					.getId());
//			assertEquals(parameter.getBuilderDeptId(), rewards.getBuilderDept()
//					.getId());
//
//		} catch (Exception e) {
//			fail("Should not run here!");
//		}
//	}
//
//	// 需要审核
//	public void testGeneratorRewards_needTreated_ok()
//			throws CorporationDataException {
//		RewardsLogic rewardsLogic = this.injector
//				.getInstance(RewardsLogic.class);
//		assertNotNull(rewardsLogic);
//		UserContext context = getUserContext();
//		GeneratorRewardsModel rewardsModel = prepareGeneratorRewardsModel();
//		try {
//			Rewards rewards = rewardsLogic.generatorRewards(context,
//					rewardsModel);
//			assertNotNull(rewards);
//			assertNotNull(rewards.getId());
//
//			// 验证NomineeRecords的大小
//			List<NomineeRecords> nomineeRecords = rewardsLogic
//					.getPreWinnerLotsByRewardId(rewards.getId());
//			assertEquals(nomineeRecords.size(), 1);
//
//			// 验证Nominee的大小
//			List<Nominee> nominees = rewardsLogic
//					.getNomineeByNomineeRewardsId(nomineeRecords.get(0).getId());
//			assertEquals(nominees.size(), 2);
//			// 验证Winners的大小
//			List<Winners> winners = rewardsLogic.getWinnersByRewardsId(rewards
//					.getId());
//			assertEquals(winners.size(), 0);
//			assertEquals(RewardStatus.PENDING_APPLICATION, rewards.getStatus());
//
//		} catch (NomineeStatusErrorException e) {
//		} catch (OrganizationInstanceException e) {
//		} catch (TransactionHasHappenException e) {
//		} catch (RewardsStatusErrorException e) {
//		} catch (CorporationEnoughBalanceRollBackException e) {
//		} catch (ConsumeAmountIsOutException e) {
//		}
//
//		// Integer i = (Integer)em
//		// .createQuery("SELECT MONTH(r.createTime) FROM Rewards r ")
//		// .getSingleResult();
//		// System.out.println("XXX" + i);
//	}
//
//	private RewardItem prepareRewardItem() throws CorporationDataException {
//
//		CorporationLogic corporationLogic = this.injector
//				.getInstance(CorporationLogic.class);
//		IndustryDao industryDao = this.injector.getInstance(IndustryDao.class);
//		TransactionService transactionService = injector
//				.getInstance(TransactionService.class);
//		DepartmentDao departmentDao = this.injector
//				.getInstance(DepartmentDao.class);
//		assertNotNull(corporationLogic);
//		RewardItemLogic rewardItemLogic = injector
//				.getInstance(RewardItemLogic.class);
//
//		// 保存行业信息
//		Industry industry = prepareIndustryData("Roger", industryDao);
//		// 保存企业信息
//		Corporation corporation = prepareCorporationData("Roger",
//				corporationLogic, industry.getId());
//
//		// 创建企业的tx账户,并给企业的tx账户充点钱
//		String accountId = transactionService.createNewAccount();
//
//		String code = TransactionUnit.BEANPOINTS.toString();
//		try {
//			transactionService.createNewUnit("缤分", code, 0.8);
//		} catch (DuplicateUnitCodeException e) {
//			fail("should not throw exception in normal!");
//		}
//		transactionService.deposit(accountId, code, 10000);
//		corporation.setTxAccountId(accountId);
//		em.merge(corporation);
//		UserContext context = getUserContext(corporation.getId());
//
//		// 保存支出和付帐部门
//		Department buildDepartment = prepareDepartmentDate("Roger_build",
//				departmentDao, corporation.getId());
//		Department accountDepartment = prepareDepartmentDate("Roger_account",
//				departmentDao, corporation.getId());
//
//		Staff staff = new Staff();
//		staff.setName("张三丰");
//		staff.setEmailAddress("zhangsanfeng@qq.com");
//		staff.setMemberCardNumber("100012");
//		staff.setDepartment(buildDepartment);
//		staff.setDeleteMarkConstant(DeleteMarkConstant.ACTIVING);
//		staff.setCorporation(em.find(Corporation.class, corporation.getId()));
//		staff.setTxAccountId(transactionService.createNewAccount());
//
//		em.persist(staff);
//
//		Staff staff2 = new Staff();
//		staff2.setName("张无忌");
//		staff2.setEmailAddress("zhangwuji@qq.com");
//		staff2.setDepartment(buildDepartment);
//		staff2.setDeleteMarkConstant(DeleteMarkConstant.ACTIVING);
//		staff2.setMemberCardNumber("10001");
//		staff2.setCorporation(em.find(Corporation.class,
//				context.getCorporationId()));
//		staff2.setTxAccountId(transactionService.createNewAccount());
//		em.persist(staff2);
//
//		Staff staff1 = new Staff();
//		staff1.setName("张无忌2");
//		staff1.setEmailAddress("zhangwuji@qq.com");
//		staff1.setDepartment(buildDepartment);
//		staff1.setDeleteMarkConstant(DeleteMarkConstant.ACTIVING);
//		staff1.setMemberCardNumber("1003");
//		staff1.setCorporation(em.find(Corporation.class,
//				context.getCorporationId()));
//		staff1.setTxAccountId(transactionService.createNewAccount());
//		em.persist(staff1);
//
//		List<String> organizationIds = new ArrayList<String>();
//		organizationIds.add(buildDepartment.getId());
//
//		RewardItemParam parameter = new RewardItemParam();
//		parameter.setAccountDeptId(accountDepartment.getId());
//		parameter.setHeadcountLimit(10);
//		parameter.setTotalAmtLimit(1000);
//		parameter.setAwardAmt(100);
//		parameter.setBuilderDeptId(buildDepartment.getId());
//		parameter.setDefinition("Rewardsdefiniton");
//		parameter.setName("RewardsName");
//		parameter.setPublishDate(DateUtil.getEarlierTimeOfThisDay(DateUtil
//				.getTime()));
//		parameter.setAwardUnit(TransactionUnit.RMB);
//
//		parameter.setFrequency(new WeeklyVo(10, new WeekDays[] { WeekDays.MON,
//				WeekDays.WEN }));
//		parameter.setCandidateList(organizationIds);
//
//		RewardItem item = rewardItemLogic.saveRewardItem(context, parameter);
//
//		return item;
//	}
//
//	// 审核奖励通过
//	public void testApproveRewards_Ok() throws CorporationDataException {
//		// 准备待审核奖励
//		// ----
//		RewardsLogic rewardsLogic = this.injector
//				.getInstance(RewardsLogic.class);
//		assertNotNull(rewardsLogic);
//		GeneratorRewardsModel rewardsModel = prepareGeneratorRewardsModel();
//		Rewards rewards = new Rewards();
//		UserContext context = getUserContext();
//		try {
//			rewards = rewardsLogic.generatorRewards(context, rewardsModel);
//			assertNotNull(rewards);
//			assertNotNull(rewards.getId());
//
//			// 验证NomineeRecords的大小
//			List<NomineeRecords> nomineeRecords = rewardsLogic
//					.getPreWinnerLotsByRewardId(rewards.getId());
//			assertEquals(nomineeRecords.size(), 1);
//
//			// 验证Nominee的大小
//			List<Nominee> nominees = rewardsLogic
//					.getNomineeByNomineeRewardsId(nomineeRecords.get(0).getId());
//			assertEquals(nominees.size(), 2);
//			// 验证Winners的大小
//			List<Winners> winners = rewardsLogic.getWinnersByRewardsId(rewards
//					.getId());
//			assertEquals(winners.size(), 0);
//			assertEquals(RewardStatus.PENDING_APPLICATION, rewards.getStatus());
//
//		} catch (NomineeStatusErrorException e) {
//		} catch (OrganizationInstanceException e) {
//		} catch (RewardsStatusErrorException e) {
//		} catch (CorporationEnoughBalanceRollBackException e) {
//		} catch (TransactionHasHappenException e) {
//		} catch (ConsumeAmountIsOutException e) {
//		}
//
//		// ----
//
//		// ---开始审核
//		RewardsTreatedParameter parameter = new RewardsTreatedParameter();
//		parameter.setReason("神马东东，恩，过吧！！");
//		parameter.setRewardsId(rewards.getId());
//
//		try {
//			rewardsLogic.approveRewards(context, parameter);
//		} catch (RewardsStatusErrorException e) {
//			e.printStackTrace();
//		} catch (NomineeStatusErrorException e) {
//			e.printStackTrace();
//		} catch (OrganizationInstanceException e) {
//			e.printStackTrace();
//		} catch (CorporationEnoughBalanceRollBackException e) {
//		} catch (TransactionHasHappenException e) {
//
//		}
//
//		List<Winners> winners = rewardsLogic.getWinnersByRewardsId(rewards
//				.getId());
//		assertEquals(winners.size(), 2);
//
//		List<NomineeRecords> recordsList = rewardsLogic
//				.getPreWinnerLotsByRewardId(rewards.getId());
//		assertEquals(1, recordsList.size());
//		assertEquals(NomineeStatus.PASS, recordsList.get(0).getNomineeStatus());
//
//		assertEquals(RewardStatus.REWARDED, rewards.getStatus());
//	}
//
//	// 审核奖励失败,该奖励不是待审核奖励
//	public void testApproveRewards_fail_statusNotCorrect()
//			throws CorporationDataException {
//		// 准备待审核奖励
//		// ----
//		RewardsLogic rewardsLogic = this.injector
//				.getInstance(RewardsLogic.class);
//		// OrganizationPolicyDao policyDao = this.injector
//		// .getInstance(OrganizationPolicyDao.class);
//		RewardItemDao rewardsItemDao = this.injector
//				.getInstance(RewardItemDao.class);
//		assertNotNull(rewardsLogic);
//		DepartmentDao departmentDao = this.injector
//				.getInstance(DepartmentDao.class);
//		OrgPolicyDao policyDao = injector
//				.getInstance(OrgPolicyDao.class);
//		GeneratorRewardsModel rewardsModel = prepareGeneratorRewardsModel();
//		// 设置为不需审核
//		String rewardsItemId = rewardsModel.getRewardsParameter()
//				.getRewardsItemId();
//		RewardsItem item = rewardsItemDao.findById(RewardsItem.class,
//				rewardsItemId);
//		UserContext context = getUserContext(item.getCorporation().getId());
//
//		String buildDeptId = item.getBuilderDept().getId();
//		Department dept = departmentDao.findById(Department.class, buildDeptId);
//		OrganizationPolicy policy = new OrganizationPolicy();
//		policy.setKey2(DepartmentPolicyConstants.REWARDS_APPROVAL_POLICY);
//		policy.setValue(RewardsApprovalPolicyEnum.NOT_REQUIRED.toString());
//		policy.setOwner(dept);
//		policyDao.save(policy);
//		Rewards rewards = new Rewards();
//		try {
//			rewards = rewardsLogic.generatorRewards(context, rewardsModel);
//			assertNotNull(rewards);
//			assertNotNull(rewards.getId());
//
//			// 验证NomineeRecords的大小
//			List<NomineeRecords> nomineeRecords = rewardsLogic
//					.getPreWinnerLotsByRewardId(rewards.getId());
//			assertEquals(1, nomineeRecords.size());
//
//			// 验证Nominee的大小
//			List<Nominee> nominees = rewardsLogic
//					.getNomineeByNomineeRewardsId(nomineeRecords.get(0).getId());
//			assertEquals(2, nominees.size());
//			// 验证Winners的大小
//			List<Winners> winners = rewardsLogic.getWinnersByRewardsId(rewards
//					.getId());
//			assertEquals(2, winners.size());
//			assertEquals(RewardStatus.REWARDED, rewards.getStatus());
//
//		} catch (NomineeStatusErrorException e) {
//			fail("Should not run here!");
//		} catch (OrganizationInstanceException e) {
//			fail("Should not run here!");
//		} catch (TransactionHasHappenException e) {
//			fail("Should not run here!");
//		} catch (RewardsStatusErrorException e) {
//			fail("Should not run here!");
//		} catch (CorporationEnoughBalanceRollBackException e) {
//			fail("Should not run here!");
//		} catch (ConsumeAmountIsOutException e) {
//			fail("Should not run here!");
//		}
//
//		// ----
//
//		// ---开始审核
//		RewardsTreatedParameter parameter = new RewardsTreatedParameter();
//		parameter.setReason("神马东东，恩，过吧！！");
//		parameter.setRewardsId(rewards.getId());
//
//		try {
//			rewardsLogic.approveRewards(context, parameter);
//			fail("should throw RewardsStatusErrorException");
//		} catch (RewardsStatusErrorException e) {
//
//		} catch (NomineeStatusErrorException e) {
//			fail("should throw RewardsStatusErrorException");
//		} catch (OrganizationInstanceException e) {
//			fail("should throw RewardsStatusErrorException");
//		} catch (CorporationEnoughBalanceRollBackException e) {
//			fail("should throw RewardsStatusErrorException");
//		} catch (TransactionHasHappenException e) {
//
//		}
//	}
//
//	// 审核奖励通过
//	public void testDenyRewards_Ok() throws CorporationDataException {
//		// 准备待审核奖励
//		// ----
//		RewardsLogic rewardsLogic = this.injector
//				.getInstance(RewardsLogic.class);
//		assertNotNull(rewardsLogic);
//		GeneratorRewardsModel rewardsModel = prepareGeneratorRewardsModel();
//		Rewards rewards = new Rewards();
//		UserContext context = getUserContext();
//
//		try {
//			rewards = rewardsLogic.generatorRewards(context, rewardsModel);
//			assertNotNull(rewards);
//			assertNotNull(rewards.getId());
//
//			// 验证NomineeRecords的大小
//			List<NomineeRecords> nomineeRecords = rewardsLogic
//					.getPreWinnerLotsByRewardId(rewards.getId());
//			assertEquals(nomineeRecords.size(), 1);
//
//			// 验证Nominee的大小
//			List<Nominee> nominees = rewardsLogic
//					.getNomineeByNomineeRewardsId(nomineeRecords.get(0).getId());
//			assertEquals(nominees.size(), 2);
//			// 验证Winners的大小
//			List<Winners> winners = rewardsLogic.getWinnersByRewardsId(rewards
//					.getId());
//			assertEquals(winners.size(), 0);
//			assertEquals(RewardStatus.PENDING_APPLICATION, rewards.getStatus());
//
//		} catch (NomineeStatusErrorException e) {
//		} catch (OrganizationInstanceException e) {
//		} catch (TransactionHasHappenException e) {
//		} catch (RewardsStatusErrorException e) {
//		} catch (CorporationEnoughBalanceRollBackException e) {
//		} catch (ConsumeAmountIsOutException e) {
//		}
//
//		// ----
//
//		// ---开始审核
//		RewardsTreatedParameter parameter = new RewardsTreatedParameter();
//		parameter.setReason("神马东东，恩，不过吧！！");
//		parameter.setRewardsId(rewards.getId());
//
//		try {
//			rewardsLogic.denyRewards(context, parameter);
//		} catch (RewardsStatusErrorException e) {
//			e.printStackTrace();
//		}
//
//		List<Winners> winners = rewardsLogic.getWinnersByRewardsId(rewards
//				.getId());
//		assertEquals(winners.size(), 0);
//
//		List<NomineeRecords> recordsList = rewardsLogic
//				.getPreWinnerLotsByRewardId(rewards.getId());
//		assertEquals(1, recordsList.size());
//		assertEquals(NomineeStatus.DENY, recordsList.get(0).getNomineeStatus());
//
//		assertEquals(RewardStatus.DENIED, rewards.getStatus());
//	}
//
//	private StaffLevel prepareStaffLevel(String pre, Corporation corporation) {
//		StaffLevel staffLevel = new StaffLevel();
//		staffLevel.setCorporation(corporation);
//		staffLevel.setName(pre + "等级");
//		em.persist(staffLevel);
//		assertNotNull(staffLevel.getId());
//		return staffLevel;
//	}
//
//	private GeneratorRewardsItemModel prepareGeneratorRewardsItemModel()
//			throws CorporationDataException {
//		CorporationLogic corporationLogic = this.injector
//				.getInstance(CorporationLogic.class);
//		RewardsTypeLogic rewardsTypeLogic = this.injector
//				.getInstance(RewardsTypeLogic.class);
//		IndustryDao industryDao = this.injector.getInstance(IndustryDao.class);
//
//		DepartmentDao departmentDao = this.injector
//				.getInstance(DepartmentDao.class);
//		assertNotNull(corporationLogic);
//		assertNotNull(rewardsTypeLogic);
//
//		// 保存指标类型
//		RewardsType rewardsType = prepareRewardsTypeData("Roger",
//				rewardsTypeLogic);
//
//		// 保存行业信息
//		Industry industry = prepareIndustryData("Roger", industryDao);
//		// 保存企业信息
//		Corporation corporation = prepareCorporationData("Roger",
//				corporationLogic, industry.getId());
//		em.flush();
//		// 保存支出和付帐部门
//		Department buildDepartment = prepareDepartmentDate("Roger_build",
//				departmentDao, corporation.getId());
//		Department accountDepartment = prepareDepartmentDate("Roger_account",
//				departmentDao, corporation.getId());
//		// 保存员工级别
//		StaffLevel levelOne = prepareStaffLevel("one",
//				buildDepartment.getCorporation());
//		StaffLevel levelTwo = prepareStaffLevel("two",
//				accountDepartment.getCorporation());
//
//		GeneratorRewardsItemModel rewardsModel = new GeneratorRewardsItemModel();
//
//		// 金额规则
//		List<CreateRewardsAmountRuleRequest> ruleList = new ArrayList<CreateRewardsAmountRuleRequest>();
//		CreateUnifiedAmountRuleRequest unifiedAmountRule = new CreateUnifiedAmountRuleRequest(
//				100d, 200d);
//		ruleList.add(unifiedAmountRule);
//		List<StaffLevelAmountDataRequest> dataList = new ArrayList<StaffLevelAmountDataRequest>();
//		StaffLevelAmountDataRequest amount1 = new StaffLevelAmountDataRequest(
//				levelOne.getId(), 100L * Math.random());
//		dataList.add(amount1);
//		StaffLevelAmountDataRequest amount2 = new StaffLevelAmountDataRequest(
//				levelTwo.getId(), 500L * Math.random());
//		dataList.add(amount2);
//		CreateStaffLevelAmountRuleRequest staffLevelAmountRule = new CreateStaffLevelAmountRuleRequest(
//				dataList);
//		ruleList.add(staffLevelAmountRule);
//
//		rewardsModel.setAmountRules(ruleList);
//
//		List<String> organizationIds = new ArrayList<String>();
//		organizationIds.add(buildDepartment.getId());
//
//		RewardsItemParameter parameter = new RewardsItemParameter();
//		parameter.setAccountDeptId(accountDepartment.getId());
//		parameter.setAmount(10);
//		parameter.setBuilderDeptId(buildDepartment.getId());
//		parameter.setDefinition("Rewardsdefiniton");
//		parameter.setEndTime(DateUtil.getLastTimeOfThisDay(DateUtil.getTime()));
//		parameter.setName("RewardsName");
//		parameter.setRewardsTypeId(rewardsType.getId());
//		parameter.setStartTime(DateUtil.getEarlierTimeOfThisDay(DateUtil
//				.getTime()));
//		parameter.setRewardsUnit(TransactionUnit.RMB);
//		rewardsModel.setItemParameter(parameter);
//		// rewardsModel.setOrganizationIds(organizationIds);
//
//		rewardsModel.setFrequency(new WeeklyVo(10, new WeekDays[] {
//				WeekDays.MON, WeekDays.WEN }));
//
//		DirectRule directRule = new DirectRule();
//		List<DirectRuleSelectedParameter> directSelectors = new ArrayList<DirectRuleSelectedParameter>();
//		for (String id : organizationIds) {
//			DirectRuleSelectedParameter selector = new DirectRuleSelectedParameter();
//			selector.setOrgId(id);
//			directSelectors.add(selector);
//		}
//		rewardsModel.getItemRules().add(directRule);
//		rewardsModel.setDirectRuleSelectedParameters(directSelectors);
//
//		DobRule dobRule = new DobRule();
//		rewardsModel.getItemRules().add(dobRule);
//		return rewardsModel;
//	}
//
//	public void testCreateRewardsItem_OK() throws CorporationDataException {
//		RewardsItemLogic rewardsItemLogic = this.injector
//				.getInstance(RewardsItemLogic.class);
//		RewardsAmountRuleDao rewardsAmountRuleDao = this.injector
//				.getInstance(RewardsAmountRuleDao.class);
//		StaffLevelAmountDataDao staffLevelAmountDataDao = this.injector
//				.getInstance(StaffLevelAmountDataDao.class);
//
//		UserContext context = getUserContext();
//
//		assertNotNull(rewardsItemLogic);
//
//		GeneratorRewardsItemModel rewardsModel = prepareGeneratorRewardsItemModel();
//		RewardsItemParameter parameter = rewardsModel.getItemParameter();
//
//		RewardsItem rewardsItem = new RewardsItem();
//
//		rewardsItem = rewardsItemLogic.generatorRewardsItem(context,
//				rewardsModel);
//
//		assertEquals(parameter.getAccountDeptId(), rewardsItem.getAccountDept()
//				.getId());
//		assertEquals(parameter.getBuilderDeptId(), rewardsItem.getBuilderDept()
//				.getId());
//		assertEquals(parameter.getDefinition(), rewardsItem.getDefinition());
//		assertEquals(parameter.getName(), rewardsItem.getName());
//		assertEquals(parameter.getRewardsTypeId(), rewardsItem.getType()
//				.getId());
//		assertEquals(parameter.getStandard(), rewardsItem.getStandard());
//		assertEquals(parameter.getAmount(), rewardsItem.getAmount());
//		assertEquals(parameter.getAutoRewards(), rewardsItem.getAutoRewards());
//		assertEquals(parameter.getEndTime(), rewardsItem.getEndTime());
//		assertEquals(parameter.getNextRewardsTime(),
//				rewardsItem.getNextRewardsTime());
//		assertEquals(parameter.getPublishRewardsTime(),
//				rewardsItem.getPublishRewardsTime());
//		assertEquals(parameter.getRewardsUnit(), rewardsItem.getRewardsUnit());
//		assertEquals(parameter.getStartTime(), rewardsItem.getStartTime());
//		logger.debug("start--");
//		List<FrequencySelectorUnit> frequencyList = rewardsItemLogic
//				.findFrequencySelectUnitByRewardItemId(rewardsItem.getId());
//		logger.debug("end--");
//		assertEquals(frequencyList.size(), 1);
//		assertEquals(true, frequencyList.get(0) instanceof WeekSelectorUnit);
//		List<WeekSelectorUnitData> datas = rewardsItemLogic
//				.findWeekFrequencyDaysByFrequencyId(frequencyList.get(0)
//						.getId());
//		assertEquals(datas.size(), 2);
//		List<RewardsItemRule> rules = rewardsItemLogic
//				.findCandidateRuleByRIID(rewardsItem.getId());
//		assertEquals(2, rules.size());
//		for (RewardsItemRule rule : rules) {
//			if (rule instanceof DirectRule) {
//				List<DirectRuleSelected> selectors = rewardsItemLogic
//						.findDirectCandidateDataListByDirectRuleId(rule.getId());
//				assertEquals(1, selectors.size());
//				assertEquals(rewardsModel.getDirectRuleSelectedParameters()
//						.get(0).getOrgId(), selectors.get(0).getDirectObject()
//						.getId());
//			}
//		}
//
//		// test amount rule
//		List<RewardsAmountRule> ruleList = rewardsAmountRuleDao
//				.findRewardsAmountByRewardsItemId(rewardsItem.getId());
//		assertNotNull(ruleList);
//		assertEquals(2, ruleList.size());
//		for (RewardsAmountRule rule : ruleList) {
//			if (rule instanceof UnifiedAmountRule) {
//				UnifiedAmountRule unifiedAmountRule = (UnifiedAmountRule) rule;
//				assertEquals(100d, unifiedAmountRule.getAmountFrom());
//				assertEquals(200d, unifiedAmountRule.getAmountTo());
//				assertEquals(RewardsAmountRuleConstants.UNIFIED_AMOUNT_RULE,
//						unifiedAmountRule.getOrderInt());
//			} else if (rule instanceof StaffLevelAmountRule) {
//				assertEquals(
//						RewardsAmountRuleConstants.STAFF_LEVEL_AMOUNT_RULE,
//						rule.getOrderInt());
//				List<StaffLevelAmountData> dataList = staffLevelAmountDataDao
//						.findByRuleId(rule.getId());
//				assertEquals(2, dataList.size());
//				List<CreateRewardsAmountRuleRequest> ruless = rewardsModel
//						.getAmountRules();
//				Map<String, Double> amountMap = assembleAmountMap(ruless);
//				for (StaffLevelAmountData data : dataList) {
//					assertEquals(data.getRewardsAmount().doubleValue(),
//							amountMap.get(data.getLevel().getId()));
//				}
//			}
//		}
//	}
//
//	public void testEditRewardsItem_OK() throws CorporationDataException {
//		RewardsItemLogic rewardsItemLogic = this.injector
//				.getInstance(RewardsItemLogic.class);
//		RewardsAmountRuleDao rewardsAmountRuleDao = this.injector
//				.getInstance(RewardsAmountRuleDao.class);
//		StaffLevelAmountDataDao staffLevelAmountDataDao = this.injector
//				.getInstance(StaffLevelAmountDataDao.class);
//		assertNotNull(rewardsItemLogic);
//
//		GeneratorRewardsItemModel rewardsModel = prepareGeneratorRewardsItemModel();
//
//		RewardsItem item = new RewardsItem();
//		UserContext context = getUserContext();
//
//		item = rewardsItemLogic.generatorRewardsItem(context, rewardsModel);
//
//		RewardsItemParameter parameter = rewardsModel.getItemParameter();
//		parameter.setId(item.getId());
//		parameter.setAmount(10);
//		parameter.setDefinition("Rewardsdefiniton");
//		parameter.setEndTime(new Date());
//		parameter.setName("RewardsName");
//		;
//		parameter.setStartTime(new Date());
//		parameter.setRewardsUnit(TransactionUnit.RMB);
//		rewardsModel.setItemParameter(parameter);
//		// rewardsModel.setOrganizationIds(organizationIds);
//
//		rewardsModel.setFrequency(new WeeklyVo(10, new WeekDays[] {
//				WeekDays.MON, WeekDays.WEN }));
//
//		List<String> organizationIds = new ArrayList<String>();
//		organizationIds.add(parameter.getAccountDeptId());
//		organizationIds.add(parameter.getBuilderDeptId());
//
//		DirectRule directRule = new DirectRule();
//		List<DirectRuleSelectedParameter> directSelectors = new ArrayList<DirectRuleSelectedParameter>();
//		for (String id : organizationIds) {
//			DirectRuleSelectedParameter selector = new DirectRuleSelectedParameter();
//			selector.setOrgId(id);
//			directSelectors.add(selector);
//		}
//		rewardsModel.getItemRules().clear();
//		rewardsModel.getItemRules().add(directRule);
//		rewardsModel.setDirectRuleSelectedParameters(directSelectors);
//
//		RewardsItem rewardsItem = new RewardsItem();
//
//		rewardsItem = rewardsItemLogic.generatorRewardsItem(context,
//				rewardsModel);
//
//		assertEquals(parameter.getAccountDeptId(), rewardsItem.getAccountDept()
//				.getId());
//		assertEquals(parameter.getBuilderDeptId(), rewardsItem.getBuilderDept()
//				.getId());
//		assertEquals(parameter.getDefinition(), rewardsItem.getDefinition());
//		assertEquals(parameter.getName(), rewardsItem.getName());
//		assertEquals(parameter.getRewardsTypeId(), rewardsItem.getType()
//				.getId());
//		assertEquals(parameter.getStandard(), rewardsItem.getStandard());
//		assertEquals(parameter.getAmount(), rewardsItem.getAmount());
//		assertEquals(parameter.getAutoRewards(), rewardsItem.getAutoRewards());
//		Date endTime = parameter.getEndTime();
//		if (endTime != null) {
//			endTime = DateUtil.getLastTimeOfThisDay(endTime);
//		}
//
//		assertEquals(endTime, rewardsItem.getEndTime());
//		assertEquals(parameter.getNextRewardsTime(),
//				rewardsItem.getNextRewardsTime());
//		assertEquals(parameter.getPublishRewardsTime(),
//				rewardsItem.getPublishRewardsTime());
//		assertEquals(parameter.getRewardsUnit(), rewardsItem.getRewardsUnit());
//		// assertEquals(parameter.getStartTime(), rewardsItem.getStartTime());
//
//		List<FrequencySelectorUnit> frequencyList = rewardsItemLogic
//				.findFrequencySelectUnitByRewardItemId(rewardsItem.getId());
//		assertEquals(frequencyList.size(), 1);
//		assertEquals(true, frequencyList.get(0) instanceof WeekSelectorUnit);
//		List<WeekSelectorUnitData> datas = rewardsItemLogic
//				.findWeekFrequencyDaysByFrequencyId(frequencyList.get(0)
//						.getId());
//		assertEquals(datas.size(), 2);
//
//		List<RewardsItemRule> rules = rewardsItemLogic
//				.findCandidateRuleByRIID(rewardsItem.getId());
//		assertEquals(1, rules.size());
//		for (RewardsItemRule rule : rules) {
//			if (rule instanceof DirectRule) {
//				List<DirectRuleSelected> selectors = rewardsItemLogic
//						.findDirectCandidateDataListByDirectRuleId(rule.getId());
//				assertEquals(2, selectors.size());
//				assertEquals(rewardsModel.getDirectRuleSelectedParameters()
//						.get(0).getOrgId(), selectors.get(0).getDirectObject()
//						.getId());
//				assertEquals(rewardsModel.getDirectRuleSelectedParameters()
//						.get(1).getOrgId(), selectors.get(1).getDirectObject()
//						.getId());
//			}
//		}
//		// test amount rule
//		List<RewardsAmountRule> ruleList = rewardsAmountRuleDao
//				.findRewardsAmountByRewardsItemId(rewardsItem.getId());
//		assertNotNull(ruleList);
//		assertEquals(2, ruleList.size());
//		for (RewardsAmountRule rule : ruleList) {
//			if (rule instanceof UnifiedAmountRule) {
//				UnifiedAmountRule unifiedAmountRule = (UnifiedAmountRule) rule;
//				assertEquals(100d, unifiedAmountRule.getAmountFrom());
//				assertEquals(200d, unifiedAmountRule.getAmountTo());
//				assertEquals(RewardsAmountRuleConstants.UNIFIED_AMOUNT_RULE,
//						unifiedAmountRule.getOrderInt());
//			} else if (rule instanceof StaffLevelAmountRule) {
//				assertEquals(
//						RewardsAmountRuleConstants.STAFF_LEVEL_AMOUNT_RULE,
//						rule.getOrderInt());
//				List<StaffLevelAmountData> dataList = staffLevelAmountDataDao
//						.findByRuleId(rule.getId());
//				assertEquals(2, dataList.size());
//				List<CreateRewardsAmountRuleRequest> ruless = rewardsModel
//						.getAmountRules();
//				Map<String, Double> amountMap = assembleAmountMap(ruless);
//				for (StaffLevelAmountData data : dataList) {
//					assertEquals(data.getRewardsAmount().doubleValue(),
//							amountMap.get(data.getLevel().getId()));
//				}
//			}
//		}
//	}
//
//	private Map<String, Double> assembleAmountMap(
//			List<CreateRewardsAmountRuleRequest> rules) {
//		Map<String, Double> amountMap = new HashMap<String, Double>();
//		for (CreateRewardsAmountRuleRequest rule : rules) {
//			if (rule instanceof CreateStaffLevelAmountRuleRequest) {
//				List<StaffLevelAmountDataRequest> dataList = ((CreateStaffLevelAmountRuleRequest) rule)
//						.getDataList();
//				for (StaffLevelAmountDataRequest data : dataList) {
//					logger.debug("amountMap:{}", data.getStaffLevelId());
//					amountMap.put(data.getStaffLevelId(), data.getAmount());
//				}
//			}
//		}
//
//		return amountMap;
//	}
//
//	private RewardsType prepareRewardsTypeData(String pre,
//			RewardsTypeLogic rewardsTypeLogic) {
//		RewardsType rewardsType = new RewardsType();
//		rewardsType.setName(pre + "学习");
//		RewardsType preRewardsType = rewardsTypeLogic
//				.saveRewardsType(rewardsType);
//		em.flush();
//		assertNotNull(preRewardsType);
//		assertNotNull(preRewardsType);
//		assertEquals(preRewardsType.getName(), rewardsType.getName());
//		return preRewardsType;
//	}
//
//	private Department prepareDepartmentDate(String pre,
//			DepartmentDao departmentDao, String corporationId) {
//		Department department = new Department();
//		department.setDescription(pre + "优秀的程序设计");
//		department.setName(pre + "IT-JAVA开发");
//		Corporation corp = em.find(Corporation.class, corporationId);
//		department.setCorporation(corp);
//		Department preDepartment = departmentDao.save(department);
//		em.flush();
//		assertNotNull(preDepartment);
//		assertNotNull(preDepartment);
//		assertEquals(preDepartment.getName(), department.getName());
//		assertEquals(preDepartment.getDescription(),
//				department.getDescription());
//		return preDepartment;
//
//	}
//
//	private SysUser prepareHrUser(String pre, SysUserDao userDao)
//			throws CorporationDataException, HrUserDataException,
//			OneCrmEnterpriseOneAdministratorException {
//
//		// ConfigurationLogic confLogic = injector
//		// .getInstance(ConfigurationLogic.class);
//		//
//		// // We need to configure the system property first!
//		// confLogic.setConfigValue(ConfigKeyConstants.ENTRY_URL_IN_EMAIL,
//		// "https://${app_domain}:1234/elt/");
//		// confLogic.setConfigValue(ConfigKeyConstants.CONTACT_URL_IN_EMAIL,
//		// "http://someurl.com/contactus");
//		// confLogic.setConfigValue(ConfigKeyConstants.APP_DOMAIN,
//		// "test-elt.com");
//		//
//		// // some email template as well.
//		// EmailTemplate et = new EmailTemplate();
//		// et.setFromEmail("elt@test-elt.com");
//		// et.setFromName("ELT Master");
//		// et.setHtml(false);
//		// et.setNeedReplaceFields("?");
//		// et.setSymbol("$");
//		// et.setTempletName(EmailTemplateConstants.CREATING_MAIN_ACCOUNT);
//		// et.setTempletValue("Login Name: ${loginName}\n"
//		// + "Password: ${password}\n" + "Entry URL: ${entryUrl}\n"
//		// + "Contact URL: ${contactUrl}\n");
//		// em.persist(et);
//
//		// ---
//
//		SysUser user = new SysUser();
//		user.setUserName(pre + "testAccount1");
//		user.setPassword("123456");
//		user.setStatus(UserStatus.Active);
//
//		userDao.save(user);
//		// IndustryLogic industryLogic = this.injector
//		// .getInstance(IndustryLogic.class);
//		// assertNotNull(industryLogic);
//		// Industry industry = new Industry();
//		// industry.setName("快餐");
//		// industry = industryLogic.saveIndustry(industry);
//		// SaveHrUserRequest request = new SaveHrUserRequest();
//		// request.setIndustryId(industry.getId());
//		// request.setCorporationDesc(pre + "-corporation");
//		// request.setCorporationName(pre + "-corporation");
//		// request.setEmail(user.getEmail());
//		// request.setEnterpriseId(pre + "-enterprise-id");
//		// request.setLoginName(user.getLoginName());
//		// request.setMobile(user.getMobile());
//		// request.setSubdomain(pre + "-subdomain");
//		// SaveHrUserResponse response = userLogic.saveHrAdminUser(request);
//		// HrUser resp = userLogic.getHrUserById(response.getHrUserId());
//
//		assertNotNull(user);
//		assertNotNull(user.getId());
//
//		return user;
//	}
//
//	private Industry prepareIndustryData(String pre, IndustryDao industryDao) {
//		Industry para = new Industry();
//		para.setName(pre + "软件");
//		para.setDescription(pre + "开发快乐");
//		Industry perIndustry = industryDao.save(para);
//		// em.persist(para);
//		// Industry perIndustry = para;
//		em.flush();
//		assertNotNull(perIndustry);
//		assertNotNull(perIndustry.getId());
//		assertEquals(perIndustry.getName(), para.getName());
//		assertEquals(perIndustry.getDescription(), para.getDescription());
//		return perIndustry;
//	}
//
//	private Corporation prepareCorporationData(String pre,
//			CorporationLogic corporationLogic, String corporationTypeId)
//			throws CorporationDataException {
//		Corporation corporation = new Corporation();
//		corporation.setCorporationId(pre + "corporationID");
//		corporation.setDescription(pre + "优秀的代码设计");
//		corporation.setName(pre + "积享通");
//		Corporation preCorporation = corporationLogic.saveCorporation(
//				corporation, corporationTypeId);
//		em.flush();
//		assertNotNull(preCorporation);
//		assertNotNull(preCorporation);
//		assertEquals(preCorporation.getName(), corporation.getName());
//		assertEquals(preCorporation.getDescription(),
//				corporation.getDescription());
//		assertEquals(preCorporation.getCorporationId(),
//				corporation.getCorporationId());
//		return preCorporation;
//	}
//
//	/**
//	 * @throws CorporationDataException
//	 * @throws DuplicateSubdomainException
//	 * @throws HrUserDuplicateException
//	 * @throws ValidationManagerException
//	 * @throws HrUserDataException
//	 * @throws OneCrmEnterpriseOneAdministratorException
//	 * 
//	 */
//	public void testFetchRewards_all() throws CorporationDataException,
//			HrUserDataException, OneCrmEnterpriseOneAdministratorException {
//		SysUserDao userDao = this.injector.getInstance(SysUserDao.class);
//		IndustryDao industryDao = this.injector.getInstance(IndustryDao.class);
//		CorporationLogic corporationLogic = this.injector
//				.getInstance(CorporationLogic.class);
//		DepartmentDao departmentDao = this.injector
//				.getInstance(DepartmentDao.class);
//		RewardsTypeLogic rewardsTypeLogic = this.injector
//				.getInstance(RewardsTypeLogic.class);
//		RewardsLogic rewardsLogic = this.injector
//				.getInstance(RewardsLogic.class);
//		assertNotNull(corporationLogic);
//		assertNotNull(departmentDao);
//		assertNotNull(rewardsTypeLogic);
//
//		// 保存行业信息
//		Industry industry = prepareIndustryData("Cream", industryDao);
//		// 保存企业信息
//		Corporation corporation = prepareCorporationData("Cream",
//				corporationLogic, industry.getId());
//		UserContext context = getUserContext(corporation.getId());
//		// 保存支出和付帐部门
//		Department buildDepartment = prepareDepartmentDate("Cream_build",
//				departmentDao, corporation.getId());
//		Department accountDepartment = prepareDepartmentDate("Cream_account",
//				departmentDao, corporation.getId());
//		// User
//		SysUser user = prepareHrUser("cream", userDao);
//
//		Date now = new Date();
//
//		// prepared dummy date.
//		Rewards rewards = new Rewards();
//		rewards.setAccountDept(accountDepartment);
//		rewards.setBuilderDept(buildDepartment);
//		rewards.setCreateBy(user);
//		rewards.setCreateTime(now);
//		rewards.setLastUpdateBy(user);
//		rewards.setLastUpdateTime(now);
//		rewards.setRewardsItem(null);
//		rewards.setRewardsUnit(TransactionUnit.RMB);
//		rewards.setOrganization(corporation);
//		em.persist(rewards);
//		assertNotNull(rewards.getId());
//
//		// fetch all rewards.
//
//		RewardsSearchVo criteria = new RewardsSearchVo();
//		PageStore<Rewards> result = rewardsLogic.fetchRewards(context,
//				criteria, false);
//		assertNotNull(result);
//		assertEquals(1, result.getResultCount());
//		assertEquals(rewards.getId(), result.getResultList().get(0).getId());
//
//	}
//
//	public void testGetRewardsMoney() throws CorporationDataException {
//		RewardsLogic rewardsLogic = this.injector
//				.getInstance(RewardsLogic.class);
//		assertNotNull(rewardsLogic);
//		GeneratorRewardsModel rewardsModel = prepareGeneratorRewardsModel();
//		Rewards rewards = null;
//		UserContext context = getUserContext();
//		try {
//			rewards = rewardsLogic.generatorRewards(context, rewardsModel);
//
//		} catch (NomineeStatusErrorException e) {
//		} catch (OrganizationInstanceException e) {
//		} catch (TransactionHasHappenException e) {
//		} catch (RewardsStatusErrorException e) {
//		} catch (CorporationEnoughBalanceRollBackException e) {
//		} catch (ConsumeAmountIsOutException e) {
//		}
//
//		List<NomineeModel> models = rewardsModel.getNomineeModel();
//		String staffId = "";
//		for (NomineeModel m : models) {
//			staffId = m.getOrganizationId();
//			break;
//		}
//		Department dept = new Department();
//		dept.setId(rewards.getAccountDept().getId());
//		double d = rewardsLogic.getRewardsMoney(dept, null, null);
//		assertEquals(20.0, d);
//		logger.debug("total money:{}", d);
//
//		Date now = DateUtil.getTime();
//		d = rewardsLogic.getRewardsMoney(null, now, now);
//		assertEquals(0.0, d);
//
//		Date from = DateUtil.getDateOfParameterOnDay(now, -1);
//		Date to = DateUtil.getDateOfParameterOnDay(now, 1);
//		logger.debug("from:{}, to:{}", new Object[] { from, to });
//		d = rewardsLogic.getRewardsMoney(dept, from, to);
//		assertEquals(20.0, d);
//	}
//
// }
