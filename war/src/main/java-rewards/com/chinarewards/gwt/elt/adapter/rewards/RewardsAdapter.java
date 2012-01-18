package com.chinarewards.gwt.elt.adapter.rewards;

import java.util.ArrayList;
import java.util.List;

import com.chinarewards.elt.domain.org.Department;
import com.chinarewards.elt.domain.reward.person.Candidate;
import com.chinarewards.elt.domain.reward.person.Judge;
import com.chinarewards.elt.model.reward.vo.RewardVo;
import com.chinarewards.gwt.elt.client.rewards.model.DepartmentClient;
import com.chinarewards.gwt.elt.client.rewards.model.JudgeModelClient;
import com.chinarewards.gwt.elt.client.rewards.model.NomineeModelClient;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsClient;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsCriteria.RewardsStatus;

/**
 * This utility class use to adapter EJB entity to WAR domain.
 * 
 * @author nicho
 * @since 0.2.0
 */
public class RewardsAdapter {

	public static RewardsClient adapter(RewardVo rewards) {
		if (null == rewards) {
			return null;
		}

		RewardsClient result = new RewardsClient();

		result.setAccountDept(rewards.getAccountDept().getId());
		result.setBuilderDept(rewards.getBuilderDept().getId());
		result.setCreateAt(rewards.getCreatedAt());
		result.setDefinition(rewards.getDefinition());
		result.setId(rewards.getId());
		result.setName(rewards.getName());
		result.setRewardsItemId(rewards.getRewardItem().getId());
		result.setStandard(rewards.getStandard());
		result.setRewardsDate(rewards.getAwardDate());
		if (rewards.getCreatedBy() != null
				&& rewards.getCreatedBy().getStaff() != null)
			result.setCreatedBy(rewards.getCreatedBy().getStaff().getName());
		result.setTotalAmtLimit(rewards.getTotalAmtLimit());
		result.setExpectNominateDate(rewards.getExpectNominateDate());
		result.setHeadcountLimit(rewards.getHeadcountLimit());
		if (rewards.getStatus() != null) {
			result.setStatus(RewardsStatus.valueOf(rewards.getStatus()
					.toString()));
		}

		if (rewards.getJudges().size() > 0) {
			List<JudgeModelClient> judgeList = new ArrayList<JudgeModelClient>();
			for (Judge judge : rewards.getJudges()) {

				judgeList.add(new JudgeModelClient(judge.getStaff().getId(),
						judge.getStaff().getName(), judge.getStaff()
								.getDepartment().getName(), judge.getStatus()
								.name()));
			}
			result.setJudgeList(judgeList);
		}
		if (rewards.getCandidates().size() > 0) {
			List<NomineeModelClient> candidatesList = new ArrayList<NomineeModelClient>();
			for (Candidate candidate : rewards.getCandidates()) {

				candidatesList.add(new NomineeModelClient(candidate.getStaff()
						.getId(), candidate.getStaff().getName(), candidate
						.getStaff().getDepartment().getName()));
			}
			result.setRewardList(candidatesList);
		}
		return result;
	}

	public static List<RewardsClient> adapter(List<RewardVo> rewardsList) {
		if (null == rewardsList) {
			return null;
		}

		List<RewardsClient> resultList = new ArrayList<RewardsClient>();
		for (RewardVo rewards : rewardsList) {
			resultList.add(adapter(rewards));
		}
		return resultList;
	}

	public static DepartmentClient adapter(Department dept) {
		if (dept == null) {
			return null;
		}

		DepartmentClient ret = null; // return value
		DepartmentClient tmpRet = null; // tmp index on current department

		Department i = dept;
		while (i != null) {
			// convert it
			DepartmentClient converted = doAdapt(i);

			// make the parent hierarchy
			if (ret == null) { // first time to run, save it.
				ret = converted;
				tmpRet = ret; // update the ref where parent will be set to.
			} else {
				tmpRet.setParent(converted);
				tmpRet = converted;
			}

			// loop to the next parent
			i = i.getParent();
		}

		return ret;
	}

	protected static DepartmentClient doAdapt(Department dept) {
		DepartmentClient result = new DepartmentClient();
		result.setId(dept.getId());
		result.setName(dept.getName());
		return result;
	}

}
