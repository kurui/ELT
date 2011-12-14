//package com.chinarewards.elt.service.rewarditem;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.chinarewards.elt.dao.org.DepartmentDao;
//import com.chinarewards.elt.dao.org.IndustryDao;
//import com.chinarewards.elt.domain.org.Corporation;
//import com.chinarewards.elt.domain.org.Department;
//import com.chinarewards.elt.domain.org.Industry;
//import com.chinarewards.elt.domain.org.Staff;
//import com.chinarewards.elt.domain.reward.base.RewardItem;
//import com.chinarewards.elt.model.reward.base.RewardItemParam;
//import com.chinarewards.elt.model.reward.frequency.WeekDays;
//import com.chinarewards.elt.model.reward.frequency.WeeklyVo;
//import com.chinarewards.elt.model.transaction.TransactionUnit;
//import com.chinarewards.elt.model.user.DeleteMarkConstant;
//import com.chinarewards.elt.model.user.UserContext;
//import com.chinarewards.elt.service.common.JPATestCase;
//import com.chinarewards.elt.service.helper.CorporationHelper;
//import com.chinarewards.elt.service.org.CorporationLogic;
//import com.chinarewards.elt.service.reward.RewardItemLogic;
//import com.chinarewards.elt.tx.exception.DuplicateUnitCodeException;
//import com.chinarewards.elt.tx.service.TransactionService;
//import com.chinarewards.elt.util.DateUtil;
//
///**
// * The test case of {@link RewardItemLogic}
// * 
// * @author yanxin
// * @since 1.0
// */
//public class RewardItemLogicTest extends JPATestCase {
//
//	/**
//	 * Test about creating a new {@link RewardItem} successful.
//	 */
//	public void testCreateNewRewardItem_ok() {
//
//	}
//
//	private RewardItemParam prepareRewardItem(Corporation corp,
//			Department dept, List<Staff> staffList) {
//
//		// prepare RewardItemParam
//		RewardItemParam param = new RewardItemParam();
//		param.setName("每月之星");
//		param.setAwardAmt(100);
//		param.setAwardUnit(CorporationHelper.getDefaultTxUnit());
//		param.set
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
// }
