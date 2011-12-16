package com.chinarewards.elt.service.reward.rule;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.chinarewards.elt.dao.org.OrganizationDao;
import com.chinarewards.elt.dao.reward.CandidateRuleDao;
import com.chinarewards.elt.dao.reward.DirectCandidateDataDao;
import com.chinarewards.elt.dao.reward.DirectCandidateRuleDao;
import com.chinarewards.elt.dao.reward.DobRuleDao;
import com.chinarewards.elt.dao.reward.RewardDao;
import com.chinarewards.elt.dao.reward.RewardItemDao;
import com.chinarewards.elt.domain.org.Department;
import com.chinarewards.elt.domain.org.Organization;
import com.chinarewards.elt.domain.org.Staff;
import com.chinarewards.elt.domain.reward.base.Reward;
import com.chinarewards.elt.domain.reward.base.RewardItem;
import com.chinarewards.elt.domain.reward.frequency.Frequency;
import com.chinarewards.elt.domain.reward.rule.CandidateRule;
import com.chinarewards.elt.domain.reward.rule.DirectCandidateData;
import com.chinarewards.elt.domain.reward.rule.DirectCandidateRule;
import com.chinarewards.elt.domain.reward.rule.DobRule;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.common.DateRangeModel;
import com.chinarewards.elt.service.reward.frequency.FrequencyLogic;
import com.chinarewards.elt.service.staff.StaffLogic;
import com.chinarewards.elt.util.DateUtil;
import com.google.inject.Inject;

/**
 * The implementation of {@link CandidateRuleLogic}
 * 
 * @author yanxin
 * @since 1.0
 */
public class CandidateRuleLogicImpl implements CandidateRuleLogic {

	private final CandidateRuleDao candidateRuleDao;
	private final DirectCandidateRuleDao directCandidateRuleDao;
	private final DirectCandidateDataDao directCandidateDataDao;
	private final DobRuleDao dobRuleDao;
	private final RewardItemDao rewardItemDao;
	private final RewardDao rewardDao;
	private final OrganizationDao organizationDao;
	private final StaffLogic staffLogic;
	private final FrequencyLogic frequencyLogic;

	@Inject
	public CandidateRuleLogicImpl(CandidateRuleDao candidateRuleDao,
			DirectCandidateRuleDao directCandidateRuleDao,
			DirectCandidateDataDao directCandidateDataDao,
			DobRuleDao dobRuleDao, RewardDao rewardDao,
			RewardItemDao rewardItemDao, OrganizationDao organizationDao,
			StaffLogic staffLogic, FrequencyLogic frequencyLogic) {
		this.candidateRuleDao = candidateRuleDao;
		this.directCandidateRuleDao = directCandidateRuleDao;
		this.directCandidateDataDao = directCandidateDataDao;
		this.dobRuleDao = dobRuleDao;
		this.rewardItemDao = rewardItemDao;
		this.rewardDao = rewardDao;
		this.organizationDao = organizationDao;
		this.staffLogic = staffLogic;
		this.frequencyLogic = frequencyLogic;
	}

	@Override
	public DirectCandidateRule bindDirectCandidateRuleToRewardItem(
			SysUser caller, String rewardItemId, List<String> candidateList) {
		DirectCandidateRule directCandidateRule = new DirectCandidateRule();
		Date now = DateUtil.getTime();
		directCandidateRule.setCreatedAt(now);
		directCandidateRule.setLastModifiedAt(now);
		directCandidateRule.setCreatedBy(caller);
		directCandidateRule.setLastModifiedBy(caller);

		directCandidateRuleDao.save(directCandidateRule);

		// Add ShortList
		for (String id : candidateList) {
			DirectCandidateData dsl = new DirectCandidateData();
			dsl.setDirectCandidateRule(directCandidateRule);
			Organization org = organizationDao.findById(Organization.class, id);
			dsl.setOrg(org);
			dsl.setCreatedAt(now);
			dsl.setCreatedBy(caller);
			dsl.setLastModifiedAt(now);
			dsl.setLastModifiedBy(caller);
			directCandidateDataDao.save(dsl);
		}

		RewardItem rewardItem = rewardItemDao.findById(RewardItem.class,
				rewardItemId);
		rewardItem.setCandidateRule(directCandidateRule);

		return directCandidateRule;
	}

	@Override
	public DobRule bindDobRuleToRewardItem(SysUser caller, String rewardItemId) {
		DobRule dobRule = new DobRule();
		Date now = DateUtil.getTime();
		dobRule.setCreatedAt(now);
		dobRule.setLastModifiedAt(now);
		dobRule.setCreatedBy(caller);
		dobRule.setLastModifiedBy(caller);
		dobRuleDao.save(dobRule);

		RewardItem rewardItem = rewardItemDao.findById(RewardItem.class,
				rewardItemId);

		rewardItem.setCandidateRule(dobRule);
		return dobRule;
	}

	@Override
	public void removeCandidateRuleFromRewardItem(String rewardItemId) {
		CandidateRule rule = candidateRuleDao
				.findCandidateRuleByRIID(rewardItemId);
		if (rule instanceof DirectCandidateRule) {
			List<DirectCandidateData> shortLists = directCandidateDataDao
					.findDirectCandidateDataListByDirectRuleId(rule.getId());
			for (DirectCandidateData sl : shortLists) {
				directCandidateDataDao.delete(sl);
			}
		}
		candidateRuleDao.delete(rule);
	}

	@Override
	public CandidateRule findCandidateRuleFromRewardItem(String rewardItemId) {
		CandidateRule rule = candidateRuleDao
				.findCandidateRuleByRIID(rewardItemId);
		if (rule instanceof DirectCandidateRule) {
			List<DirectCandidateData> CandidateList = directCandidateDataDao
					.findDirectCandidateDataListByDirectRuleId(rule.getId());
			DirectCandidateRule candidateRule = (DirectCandidateRule) rule;
			candidateRule.setCandidateDataList(CandidateList);
		}
		return rule;
	}

	@Override
	public CandidateRule findCandidateRuleFromReward(String rewardId) {
		CandidateRule rule = candidateRuleDao
				.findCandidateRuleByRewardId(rewardId);
		if (rule instanceof DirectCandidateRule) {
			List<DirectCandidateData> CandidateList = directCandidateDataDao
					.findDirectCandidateDataListByDirectRuleId(rule.getId());
			DirectCandidateRule candidateRule = (DirectCandidateRule) rule;
			candidateRule.setCandidateDataList(CandidateList);
		}
		return rule;
	}

	@Override
	public CandidateRule cloneCandidateRuleFromRewardItemToReward(
			SysUser caller, String fromRewardItemId, String toRewardId,
			Date currTime) {
		Date now = DateUtil.getTime();
		CandidateRule rule = findCandidateRuleFromRewardItem(fromRewardItemId);
		Reward reward = rewardDao.findById(Reward.class, toRewardId);
		if (rule instanceof DirectCandidateRule) {
			DirectCandidateRule oldRule = (DirectCandidateRule) rule;
			DirectCandidateRule newRule = new DirectCandidateRule();
			newRule.setCreatedAt(now);
			newRule.setCreatedBy(caller);
			newRule.setLastModifiedAt(now);
			newRule.setLastModifiedBy(caller);
			directCandidateRuleDao.save(newRule);
			List<DirectCandidateData> candidateList = oldRule
					.getCandidateDataList();
			for (DirectCandidateData dsl : candidateList) {
				DirectCandidateData newShortList = new DirectCandidateData();
				newShortList.setDirectCandidateRule(newRule);
				newShortList.setOrg(dsl.getOrg());
				newShortList.setCreatedAt(now);
				newShortList.setLastModifiedAt(now);
				newShortList.setCreatedBy(caller);
				newShortList.setLastModifiedBy(caller);
				directCandidateDataDao.save(newShortList);
			}
			reward.setCandidateRule(newRule);
			rewardDao.update(reward);
			return newRule;
		} else if (rule instanceof DobRule) {
			DobRule newRule = new DobRule();
			newRule.setCreatedAt(now);
			newRule.setCreatedBy(caller);
			newRule.setLastModifiedAt(now);
			newRule.setLastModifiedBy(caller);
			// Add date range..
			Frequency frequency = frequencyLogic
					.getFrequencyOfRewardItem(fromRewardItemId);
			DateRangeModel range = frequencyLogic.calDateRangeFromFrequency(
					frequency, currTime);
			newRule.setRangeFrom(range.getFrom());
			newRule.setRangeTo(range.getTo());

			dobRuleDao.save(newRule);
			reward.setCandidateRule(newRule);
			rewardDao.update(reward);
			return newRule;
		} else {
			throw new UnsupportedOperationException();
		}
	}

	private Set<Staff> getQualifiedStaffsFromDirectCandidateRule(
			DirectCandidateRule candidateRule) {
		Set<Staff> staffs = new HashSet<Staff>();
		List<DirectCandidateData> list = directCandidateDataDao
				.findDirectCandidateDataListByDirectRuleId(candidateRule
						.getId());
		for (DirectCandidateData data : list) {
			if (data.getOrg() instanceof Department) {
				staffs.addAll(staffLogic.getStaffsFromDeptId(data.getOrg()
						.getId(), true));
			} else if (data.getOrg() instanceof Staff) {
				staffs.add((Staff) data.getOrg());
			}
		}

		return staffs;
	}

	private Set<Staff> getQualifiedStaffsFromDobRule(DobRule dobRule) {
		return null;
	}

	@Override
	public Set<Staff> getQualifiedStaffsFromCandidateRuleId(
			String candidateRuleId) {
		Set<Staff> set = null;
		CandidateRule rule = candidateRuleDao.findById(CandidateRule.class,
				candidateRuleId);
		if (rule instanceof DirectCandidateRule) {
			set = getQualifiedStaffsFromDirectCandidateRule((DirectCandidateRule) rule);
		} else if (rule instanceof DobRule) {
			set = getQualifiedStaffsFromDobRule((DobRule) rule);
		}
		return set;
	}
}
