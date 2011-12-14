package com.chinarewards.elt.service.reward;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinarewards.elt.dao.org.DepartmentDao;
import com.chinarewards.elt.dao.reward.RewardDao;
import com.chinarewards.elt.dao.reward.RewardItemDao;
import com.chinarewards.elt.dao.user.SysUserDao;
import com.chinarewards.elt.domain.org.Department;
import com.chinarewards.elt.domain.reward.base.Reward;
import com.chinarewards.elt.domain.reward.base.RewardItem;
import com.chinarewards.elt.domain.reward.person.NomineeLot;
import com.chinarewards.elt.domain.reward.person.PreWinnerLot;
import com.chinarewards.elt.domain.reward.rule.CandidateRule;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.reward.base.RequireAutoAward;
import com.chinarewards.elt.model.reward.base.RewardParam;
import com.chinarewards.elt.model.reward.base.RewardStatus;
import com.chinarewards.elt.model.reward.exception.ApproveRewardException;
import com.chinarewards.elt.model.reward.exception.DenyRewardException;
import com.chinarewards.elt.model.reward.exception.JudgeException;
import com.chinarewards.elt.model.reward.exception.NoEffectivePreWinnerException;
import com.chinarewards.elt.model.reward.exception.NominateRewardException;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.service.reward.rule.AwardApprovalDeterminer;
import com.chinarewards.elt.service.reward.rule.CandidateLogic;
import com.chinarewards.elt.service.reward.rule.CandidateRuleLogic;
import com.chinarewards.elt.service.reward.rule.JudgeLogic;
import com.chinarewards.elt.service.reward.rule.NomineeLogic;
import com.chinarewards.elt.service.reward.rule.PreWinnerLogic;
import com.chinarewards.elt.service.reward.rule.WinnerLogic;
import com.chinarewards.elt.util.DateUtil;
import com.chinarewards.elt.util.StringUtil;
import com.google.inject.Inject;

/**
 * The implementation of {@link RewardLogic}
 * 
 * @author yanxin
 * @since 1.0
 */
public class RewardLogicImpl implements RewardLogic {
	Logger logger = LoggerFactory.getLogger(getClass());

	private final RewardDao rewardDao;
	private final RewardItemDao rewardItemDao;
	private final DepartmentDao deptDao;
	private final SysUserDao sysUserDao;
	private final CandidateLogic candidateLogic;
	private final CandidateRuleLogic candidateRuleLogic;
	private final JudgeLogic judgeLogic;
	private final PreWinnerLogic preWinnerLogic;
	private final WinnerLogic winnerLogic;
	private final NomineeLogic nomineeLogic;
	private final AwardApprovalDeterminer awardApprovalDeterminer;

	@Inject
	public RewardLogicImpl(RewardDao rewardDao, RewardItemDao rewardItemDao,
			DepartmentDao deptDao, SysUserDao sysUserDao,
			CandidateLogic candidateLogic,
			CandidateRuleLogic candidateRuleLogic, JudgeLogic judgeLogic,
			PreWinnerLogic preWinnerLogic, WinnerLogic winnerLogic,
			NomineeLogic nomineeLogic,
			AwardApprovalDeterminer awardApprovalDeterminer) {
		this.rewardDao = rewardDao;
		this.rewardItemDao = rewardItemDao;
		this.deptDao = deptDao;
		this.sysUserDao = sysUserDao;
		this.candidateLogic = candidateLogic;
		this.candidateRuleLogic = candidateRuleLogic;
		this.judgeLogic = judgeLogic;
		this.preWinnerLogic = preWinnerLogic;
		this.winnerLogic = winnerLogic;
		this.nomineeLogic = nomineeLogic;
		this.awardApprovalDeterminer = awardApprovalDeterminer;
	}

	/**
	 * Assemble Reward Object.
	 * 
	 * @param caller
	 * @param param
	 * @return
	 */
	private Reward assembleRewardObj(SysUser caller, RewardParam param) {
		logger.debug("Invoking method assembleRewardObj()", param);
		Reward reward = null;
		Date now = DateUtil.getTime();
		if (StringUtil.isEmptyString(param.getId())) {
			// Creates
			reward = new Reward();
			reward.setCreatedAt(now);
			reward.setCreatedBy(caller);
		} else {
			// Update
			reward = rewardDao.findById(Reward.class, param.getId());
		}
		if (!StringUtil.isEmptyString(param.getRewardItemId())) {
			RewardItem rewardItem = rewardItemDao.findById(RewardItem.class,
					param.getRewardItemId());
			reward.setRewardItem(rewardItem);
		}
		if (!StringUtil.isEmptyString(param.getBuilderDeptId())) {
			Department builderDept = deptDao.findById(Department.class,
					param.getBuilderDeptId());
			reward.setBuilderDept(builderDept);
			reward.setCorporation(builderDept.getCorporation());
		}
		if (!StringUtil.isEmptyString(param.getAccountDeptId())) {
			Department accountDept = deptDao.findById(Department.class,
					param.getAccountDeptId());
			reward.setAccountDept(accountDept);
		}
		reward.setName(param.getName());
		reward.setDefinition(param.getDefinition());
		reward.setStandard(param.getStandard());
		reward.setHeadcountLimit(param.getHeadcountLimit());
		reward.setTotalAmtLimit(param.getTotalAmtLimit());
		reward.setAwardAmt(param.getAwardAmt());
		reward.setRewardUnit(param.getRewardUnit());
		reward.setExpectAwardDate(param.getExpectAwardDate());
		reward.setExpectNominateDate(param.getExpectNominateDate());
		reward.setLastModifiedAt(now);
		reward.setLastModifiedBy(caller);

		return reward;
	}

	@Override
	public Reward awardByHand(UserContext context, RewardParam param) {
		logger.debug("Invoking method saveRewardByHand(), parameter:{}", param);
		SysUser caller = sysUserDao
				.findById(SysUser.class, context.getUserId());
		Reward reward = assembleRewardObj(caller, param);
		if (StringUtil.isEmptyString(reward.getId())) {
			rewardDao.save(reward);
		} else {
			rewardDao.update(reward);
			// XXX here need not do more things.
		}

		return reward;
	}

	@Override
	public Reward awardFromRewardItem(SysUser caller, String rewardItemId) {
		logger.debug(
				"Invoking method generateRewardFromRewardItem(), parameter:{}",
				rewardItemId);
		Date now = DateUtil.getTime();
		RewardItem item = rewardItemDao
				.findById(RewardItem.class, rewardItemId);
		Reward reward = new Reward();
		reward.setName(item.getName());
		reward.setRewardItem(item);
		reward.setCorporation(item.getCorporation());
		reward.setStatus(RewardStatus.NEW);
		reward.setDefinition(item.getDefinition());
		reward.setStandard(item.getStandard());
		reward.setHeadcountLimit(item.getHeadcountLimit());
		reward.setTotalAmtLimit(item.getTotalAmtLimit());
		reward.setAwardAmt(item.getAwardAmt());
		reward.setBuilderDept(item.getBuilderDept());
		reward.setAccountDept(item.getAccountDept());
		reward.setRewardUnit(item.getAwardUnit());
		reward.setExpectAwardDate(item.getExpectAwardDate());
		reward.setExpectNominateDate(item.getExpectAwardDate());
		reward.setCreatedAt(now);
		reward.setCreatedBy(caller);
		reward.setLastModifiedAt(now);
		reward.setLastModifiedBy(caller);
		rewardDao.save(reward);

		// Add judges
		judgeLogic.cloneJudgesFromRewardItemToReward(caller, rewardItemId,
				reward.getId());

		// Clone candidate rule from RewardItem to Reward
		CandidateRule candidateRule = candidateRuleLogic
				.cloneCandidateRuleFromRewardItemToReward(caller, rewardItemId,
						reward.getId(), now);

		// Add candidate list
		candidateLogic.AddCandidateToReward(caller, reward.getId(),
				candidateRule);

		// Whether require autoAward or nominate mechanism
		if (RequireAutoAward.requireNominate == item.getAutoAward()) {
			reward.setStatus(RewardStatus.PENDING_NOMINATE);
		} else if (RequireAutoAward.requireAutoAward == item.getAutoAward()) {
			// Add pre-winner record
			String lotId = preWinnerLogic.addPreWinnerFromCandidateOfReward(
					caller, reward.getId());
			reward.setStatus(RewardStatus.PENDING_APPLICATION);
			if (!awardApprovalDeterminer.isApprovalRequired(reward.getId())) {
				// if not require approve, award directly.
				// Add winner record
				winnerLogic.approveWinnerFromEffectivePreWinnerLotOfReward(
						caller, lotId, "不需要审核");
				reward.setStatus(RewardStatus.REWARDED);
				winnerLogic.processWinnerAward(reward.getId());
			} else {
				// Entering pending approval status.
				reward.setStatus(RewardStatus.PENDING_APPLICATION);
			}
		}
		rewardDao.update(reward);

		return reward;
	}

	@Override
	public NomineeLot nominateReward(SysUser caller, String rewardId,
			List<String> staffIds) throws NominateRewardException {
		Reward reward = rewardDao.findById(Reward.class, rewardId);
		if (RewardStatus.PENDING_NOMINATE != reward.getStatus()) {
			throw new NominateRewardException(
					"Status is not support this operation!");
		}
		NomineeLot lot;
		try {
			lot = nomineeLogic
					.addNomineeLotToReward(caller, rewardId, staffIds);
		} catch (JudgeException e) {
			throw new NominateRewardException(
					"Can not find correct unnominated judge.");
		}
		return lot;
	}

	@Override
	public void awardReward(SysUser caller, String rewardId,
			List<String> staffIds) {
		Reward reward = rewardDao.findById(Reward.class, rewardId);
		String lotId = preWinnerLogic.addPreWinnerFromOuter(caller, rewardId,
				staffIds);
		// whether need approve
		if (!awardApprovalDeterminer.isApprovalRequired(reward.getId())) {
			// if not require, Add winner record
			winnerLogic.approveWinnerFromEffectivePreWinnerLotOfReward(caller,
					lotId, "不需要审核");
			reward.setStatus(RewardStatus.REWARDED);
			// process transaction
			winnerLogic.processWinnerAward(reward.getId());
			rewardDao.update(reward);
		}
	}

	@Override
	public Reward approveReward(SysUser caller, String rewardId, String reason)
			throws ApproveRewardException {
		Reward reward = rewardDao.findById(Reward.class, rewardId);

		if (RewardStatus.PENDING_APPLICATION != reward.getStatus()) {
			throw new ApproveRewardException(
					"Status can not support this operation!");
		}

		try {
			PreWinnerLot lot = preWinnerLogic
					.getUntreatedPreWinnerLotOfReward(rewardId);
			winnerLogic.approveWinnerFromEffectivePreWinnerLotOfReward(caller,
					lot.getId(), reason);
		} catch (NoEffectivePreWinnerException e) {
			throw new ApproveRewardException(e);
		}

		reward.setStatus(RewardStatus.REWARDED);
		rewardDao.update(reward);

		return reward;
	}

	@Override
	public Reward denyReward(SysUser caller, String rewardId, String reason)
			throws DenyRewardException {
		Reward reward = rewardDao.findById(Reward.class, rewardId);
		if (RewardStatus.PENDING_APPLICATION != reward.getStatus()) {
			throw new DenyRewardException(
					"Status can not support this operation!");
		}

		try {
			PreWinnerLot lot = preWinnerLogic
					.getUntreatedPreWinnerLotOfReward(rewardId);
			preWinnerLogic.denyPreWinnerLot(caller, lot.getId(), reason);
		} catch (NoEffectivePreWinnerException e) {
			throw new DenyRewardException(e);
		}

		reward.setStatus(RewardStatus.DENIED);
		rewardDao.update(reward);

		return reward;
	}

}
