package com.chinarewards.elt.service.reward;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinarewards.elt.dao.reward.RewardItemDao;
import com.chinarewards.elt.dao.reward.RewardItemTypeDao;
import com.chinarewards.elt.domain.org.Department;
import com.chinarewards.elt.domain.reward.base.RewardItem;
import com.chinarewards.elt.domain.reward.base.RewardItemType;
import com.chinarewards.elt.domain.reward.frequency.Frequency;
import com.chinarewards.elt.domain.reward.person.Judge;
import com.chinarewards.elt.domain.reward.rule.CandidateRule;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.reward.base.RequireAutoAward;
import com.chinarewards.elt.model.reward.base.RequireAutoGenerate;
import com.chinarewards.elt.model.reward.base.RewardItemParam;
import com.chinarewards.elt.model.reward.search.RewardItemSearchVo;
import com.chinarewards.elt.model.reward.vo.RewardItemVo;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.service.org.DepartmentLogic;
import com.chinarewards.elt.service.reward.acl.RewardAclProcessorFactory;
import com.chinarewards.elt.service.reward.frequency.FrequencyLogic;
import com.chinarewards.elt.service.reward.rule.CandidateRuleLogic;
import com.chinarewards.elt.service.reward.rule.JudgeLogic;
import com.chinarewards.elt.service.user.UserLogic;
import com.chinarewards.elt.util.DateUtil;
import com.chinarewards.elt.util.StringUtil;
import com.google.inject.Inject;

/**
 * The implementation of {@link RewardItemLogic}
 * 
 * @author yanxin
 * @since 1.0
 */
public class RewardItemLogicImpl implements RewardItemLogic {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private final RewardItemTypeDao rewardItemTypeDao;
	private final RewardItemDao rewardItemDao;
	private final FrequencyLogic frequencyLogic;
	private final CandidateRuleLogic candidateRuleLogic;
	private final JudgeLogic judgeLogic;
	private final RewardAclProcessorFactory rewardAclProcessorFactory;
	private final DepartmentLogic deptLogic;
	private final RewardLogic rewardLogic;
	private final UserLogic userLogic;

	@Inject
	protected RewardItemLogicImpl(RewardItemTypeDao rewardItemTypeDao,
			RewardItemDao rewardItemDao, FrequencyLogic frequencyLogic,
			CandidateRuleLogic candidateRuleLogic, JudgeLogic judgeLogic,
			RewardAclProcessorFactory rewardAclProcessorFactory,
			DepartmentLogic deptLogic, RewardLogic rewardLogic,
			UserLogic userLogic) {
		this.rewardItemTypeDao = rewardItemTypeDao;
		this.rewardItemDao = rewardItemDao;
		this.frequencyLogic = frequencyLogic;
		this.candidateRuleLogic = candidateRuleLogic;
		this.judgeLogic = judgeLogic;
		this.rewardAclProcessorFactory = rewardAclProcessorFactory;
		this.deptLogic = deptLogic;
		this.rewardLogic = rewardLogic;
		this.userLogic = userLogic;
	}

	private RewardItem assembleRewardItemObject(SysUser caller,
			RewardItemParam param) {
		logger.debug("Invoking method assembleRewardItemObject()");
		Date currTime = DateUtil.getTime();
		RewardItem item = null;
		if (StringUtil.isEmptyString(param.getId())) {
			// Create a new record
			item = new RewardItem();
			item.setEnabled(false);
			item.setDeleted(false);
			item.setCreatedBy(caller);
			item.setCreatedAt(currTime);
		} else {
			// Update a existed record
			item = rewardItemDao.findById(RewardItem.class, param.getId());
		}

		// RewardItemType
		if (!StringUtil.isEmptyString(param.getTypeId())) {
			RewardItemType type = rewardItemTypeDao.findById(
					RewardItemType.class, param.getTypeId());
			item.setType(type);
		}

		// Found build department and account department
		Department builderDept = deptLogic.findDepartmentById(param
				.getBuilderDeptId());
		Department accountDept = deptLogic.findDepartmentById(param
				.getAccountDeptId());
		item.setBuilderDept(builderDept);
		item.setAccountDept(accountDept);
		item.setCorporation(builderDept.getCorporation());

		// Calculate publish date and ahead publish days
		int publishAheadDays = DateUtil.betweenDays(param.getPublishDate(),
				param.getExpectAwardDate());
		item.setPublishDate(param.getPublishDate());
		item.setPublishAheadDays(publishAheadDays);

		// Save expect award date
		item.setExpectAwardDate(param.getExpectAwardDate());

		// Calculate nominate date
		item.setNominateAheadDays(param.getNominateAheadDays());
		Date nominateDate = DateUtil.getDateOfParameterOnDay(
				param.getExpectAwardDate(), -param.getNominateAheadDays());
		item.setNominateDate(nominateDate);

		// calculate RunBatchTime
		if (param.getAutoGenerate() == RequireAutoGenerate.requireCyclic
				|| param.getAutoGenerate() == RequireAutoGenerate.requireOneOff) {
			if (param.getAutoAward() == RequireAutoAward.requireAutoAward) {
				item.setNexRunBatchTime(param.getExpectAwardDate());
			} else {
				item.setNexRunBatchTime(param.getPublishDate());
			}
		}

		item.setName(param.getName());
		item.setStandard(param.getStandard());
		item.setDefinition(param.getDefinition());
		item.setAwardAmt(param.getAwardAmt());
		item.setAwardUnit(param.getAwardUnit());
		item.setHeadcountLimit(param.getHeadcountLimit());
		item.setTotalAmtLimit(param.getTotalAmtLimit());
		item.setAutoGenerate(param.getAutoGenerate());
		item.setAutoAward(param.getAutoAward());
		item.setLastModifiedAt(currTime);
		item.setLastModifiedBy(caller);

		return item;
	}

	@Override
	public RewardItem saveRewardItem(SysUser caller, RewardItemParam param) {
		logger.debug("Invoking method saveRewardItem(), parameter:{}", param);
		RewardItem itemObj = assembleRewardItemObject(caller, param);
		if (StringUtil.isEmptyString(itemObj.getId())) {
			// Create
			rewardItemDao.save(itemObj);
		} else {
			// Update
			rewardItemDao.update(itemObj);
			// clear frequency
			frequencyLogic.removeFrequencyFromRewardItem(itemObj.getId());
			// clear short-list rule
			candidateRuleLogic.removeCandidateRuleFromRewardItem(itemObj
					.getId());
			// clear judge list
			judgeLogic.removeJudgesFromRewardItem(itemObj.getId());
		}

		// Add frequency
		frequencyLogic.bindFrequencyToRewardItem(caller, itemObj.getId(),
				param.getFrequency());
		// Add short-list rule
		if (param.getCandidateList() != null
				&& !param.getCandidateList().isEmpty()) {
			candidateRuleLogic.bindDirectCandidateRuleToRewardItem(caller,
					itemObj.getId(), param.getCandidateList());
		}
		if (param.isDob()) {
			candidateRuleLogic.bindDobRuleToRewardItem(caller, itemObj.getId());
		}

		// Add judge list
		if (param.getJudgeIds() != null && !param.getJudgeIds().isEmpty()) {
			judgeLogic.bindJudgesToRewardItem(caller, itemObj.getId(),
					param.getJudgeIds());
		}

		return itemObj;
	}

	@Override
	public void deleteRewardItem(String rewardItemId) {
		logger.debug("Invoking method deleteRewardItem(), rewardItemId:{}",
				rewardItemId);
		RewardItem rewardItem = rewardItemDao.findById(RewardItem.class,
				rewardItemId);
		rewardItem.setDeleted(true);
		rewardItemDao.update(rewardItem);
	}

	/**
	 * Convert from {@link RewardItem} to {@link RewardItemVo}. Here have two
	 * choices:
	 * <ul>
	 * <li>1. Just copy from RewardItem, do not need to query more detail
	 * informations.</li>
	 * <li>2. Need to query more detail informations. Maybe it is slow, please
	 * be careful.</li>
	 * </ul>
	 * 
	 * @param item
	 * @param isEntire
	 *            true - get more details according to query database. It is
	 *            slow. <br>
	 *            false - just copy from RewardItem.
	 * @return
	 */
	private RewardItemVo convertFromRewardItemToVo(RewardItem item,
			boolean isEntire) {
		RewardItemVo itemVo = new RewardItemVo();
		if (isEntire) {
			String rewardItemId = item.getId();
			// Get frequency info
			Frequency frequencie = frequencyLogic
					.getFrequencyOfRewardItem(rewardItemId);
			// Get candidate list rule
			CandidateRule candidateRule = candidateRuleLogic
					.findCandidateRuleFromRewardItem(rewardItemId);
			// Get judge list
			List<Judge> judges = judgeLogic
					.findJudgesFromRewardItem(rewardItemId);

			itemVo.setFrequency(frequencie);
			itemVo.setCandidateRule(candidateRule);
			itemVo.setJudgeList(judges);
		}
		itemVo.setItem(item);

		return itemVo;
	}

	@Override
	public RewardItemVo fetchEntireRewardItemById(String rewardItemId) {
		RewardItem rewardItem = rewardItemDao.findById(RewardItem.class,
				rewardItemId);
		RewardItemVo itemVo = convertFromRewardItemToVo(rewardItem, true);
		return itemVo;
	}

	@Override
	public PageStore<RewardItemVo> fetchRewardItems(UserContext context,
			RewardItemSearchVo criteria) {
		logger.debug("Process in fetchRewardItems method, parameter RewardItemSearchVo.toString:"
				+ criteria);
		PageStore<RewardItem> pageStore = rewardAclProcessorFactory
				.generateRewardAclProcessor(context.getUserRoles())
				.fetchRewardItems(context, criteria);

		List<RewardItem> itemList = pageStore.getResultList();
		// post-process and convert
		List<RewardItemVo> itemVoList = new ArrayList<RewardItemVo>();
		for (RewardItem item : itemList) {
			itemVoList.add(convertFromRewardItemToVo(item, true));
		}
		logger.debug("The result size:{}, total:{}",
				new Object[] { itemVoList.size(), pageStore.getResultCount() });

		PageStore<RewardItemVo> storeVo = new PageStore<RewardItemVo>();
		storeVo.setResultCount(pageStore.getResultCount());
		storeVo.setResultList(itemVoList);

		return storeVo;
	}

	@Override
	public PageStore<RewardItemVo> fetchRewardItemsNoAcl(
			RewardItemSearchVo criteria) {
		// resolve the department IDs if told to do so.
		if (criteria.isSubDepartmentChosen()
				&& (criteria.getDeptIds() != null && !criteria.getDeptIds()
						.isEmpty())) {
			Set<String> deptIds = new HashSet<String>();
			for (String id : criteria.getDeptIds()) {
				deptIds.addAll(deptLogic.getWholeChildrenIds(id, true));
			}
			// since we have resolved all sub-departments
			criteria.setSubDepartmentChosen(false);
			criteria.setDeptIds(new ArrayList<String>(deptIds));
		}

		// search it
		List<RewardItem> itemList = rewardItemDao.fetchRewardsItems(criteria);
		int count = rewardItemDao.countRewardsItems(criteria);

		// / post-process and convert
		List<RewardItemVo> itemVoList = new ArrayList<RewardItemVo>();
		for (RewardItem item : itemList) {
			itemVoList.add(convertFromRewardItemToVo(item, true));
		}

		PageStore<RewardItemVo> storeVo = new PageStore<RewardItemVo>();
		storeVo.setResultCount(count);
		storeVo.setResultList(itemVoList);

		logger.debug("The result size:{}, total:{}",
				new Object[] { itemVoList.size(), count });
		return storeVo;
	}

	@Override
	public RewardItem enableRewardItem(SysUser caller, String rewardItemId) {
		logger.debug("Invoking method enableRewardItem(), rewardItemId:{}",
				rewardItemId);
		Date now = DateUtil.getTime();
		RewardItem rewardItem = rewardItemDao.findById(RewardItem.class,
				rewardItemId);
		rewardItem.setEnabled(true);
		rewardItem.setLastModifiedAt(now);
		rewardItem.setLastModifiedBy(caller);
		rewardItemDao.update(rewardItem);
		return rewardItem;
	}

	@Override
	public RewardItem disableRewardItem(SysUser caller, String rewardItemId) {
		logger.debug("Invoking method disableRewardItem(), rewardItemId:{}",
				rewardItemId);
		Date now = DateUtil.getTime();
		RewardItem rewardItem = rewardItemDao.findById(RewardItem.class,
				rewardItemId);
		rewardItem.setEnabled(false);
		rewardItem.setLastModifiedAt(now);
		rewardItem.setLastModifiedBy(caller);
		rewardItemDao.update(rewardItem);
		return rewardItem;
	}

	@Override
	public List<RewardItem> fetchAutoGenerateRewardItem(Date currTime) {
		logger.debug(
				" Invoking method fetchAutoGenerateRewardItem,para[CurrTime={}]",
				DateUtil.formatData(null, currTime));
		List<RewardItem> res = rewardItemDao
				.findAllNeedToOperatorAutoRewardsItem(currTime);
		logger.debug(" fetchAutoGenerateRewardItem method has return RewardsItem size:"
				+ res.size());
		return res;
	}

	@Override
	public void runAutoRewardGeneratorBatch(Date flagTime) {
		logger.debug(
				"Invoking method runAutoRewardGeneratorBatch(), param[flagTime={}]",
				flagTime);
		List<RewardItem> autoItems = fetchAutoGenerateRewardItem(flagTime);
		logger.debug("This time will run {} rewardItems total automatic!",
				autoItems.size());
		SysUser caller = userLogic.getDefaultUser();
		for (RewardItem item : autoItems) {
			// this time to run
			Date thisRunTime = item.getNexRunBatchTime();
			// failure times
			int errorTimes = 0;
			boolean isRunnable = true;
			while (flagTime.after(thisRunTime) && errorTimes < 3 && isRunnable) {
				try {
					Date nextRunTime = calNextRunBatchTime(item.getId());
					rewardLogic.awardFromRewardItem(caller, item.getId(),
							flagTime);
					// update next run batch date
					item.setNexRunBatchTime(nextRunTime);
					// update publish date and expect award date
					// Notice: [1] .If a require auto award RewardItem, the run
					// batch time = expect award time. [2] . If not, the run
					// batch
					// time = next publish time.
					if (RequireAutoAward.requireAutoAward == item
							.getAutoAward()) {
						// Calculate next publish date
						Date nextPublishDate = DateUtil
								.getDateOfParameterOnDay(nextRunTime,
										-item.getPublishAheadDays());
						// Calculate next expect award date
						Date nextExpectAwardDate = nextRunTime;
						// Calculate next nominate date
						Date nextNominateDate = DateUtil
								.getDateOfParameterOnDay(nextRunTime,
										-item.getNominateAheadDays());
						item.setExpectAwardDate(nextExpectAwardDate);
						item.setPublishDate(nextPublishDate);
						item.setNominateDate(nextNominateDate);
					} else {
						// Calculate next publish date
						Date nextPublishDate = nextRunTime;
						// Calculate next expect award date
						Date nextExpectAwardDate = DateUtil
								.getDateOfParameterOnDay(nextPublishDate,
										item.getPublishAheadDays());
						// Calculate next nominate date
						Date nextNominateDate = DateUtil
								.getDateOfParameterOnDay(nextExpectAwardDate,
										-item.getNominateAheadDays());
						item.setExpectAwardDate(nextExpectAwardDate);
						item.setPublishDate(nextPublishDate);
						item.setNominateDate(nextNominateDate);
					}

					// Set it to false which means it can not work again.
					if (RequireAutoGenerate.requireOneOff == item
							.getAutoGenerate()) {
						item.setEnabled(false);
						isRunnable = false;
					}

					rewardItemDao.update(item);
					thisRunTime = nextRunTime;
					errorTimes = 0;
					logger.debug(
							"Reward item id:{},name={}, generate a reward successful, nextRunBatchTime: {}, lastRunBatchTime:{}",
							new Object[] { item.getId(), nextRunTime,
									thisRunTime });
				} catch (RuntimeException e) {
					errorTimes++;
					logger.warn(
							"Rewarditem id:{}, name:{}, generate a reward failure {} Times",
							new Object[] { item.getId(), item.getName(),
									errorTimes });
				}
			}
		}

	}

	@Override
	public Date calNextRunBatchTime(String rewardItemId) {
		logger.debug(
				"Invoking method calNextRunBatchTime(), param[rewardItemId={}]",
				rewardItemId);
		RewardItem item = rewardItemDao
				.findById(RewardItem.class, rewardItemId);
		return frequencyLogic.calNextAwardTime(item.getFrequency(),
				item.getNexRunBatchTime());
	}
}
