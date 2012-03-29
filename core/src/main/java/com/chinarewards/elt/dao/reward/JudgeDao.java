package com.chinarewards.elt.dao.reward;

import java.util.List;

import com.chinarewards.elt.common.BaseDao;
import com.chinarewards.elt.domain.reward.person.Judge;
import com.chinarewards.elt.model.reward.base.JudgeStatus;
import com.chinarewards.elt.model.reward.base.RewardStatus;

public class JudgeDao extends BaseDao<Judge> {

	/**
	 * Find list of judge by id of a RewardItem.
	 * 
	 * @param rewardItemId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Judge> findJudgesFromRewardItem(String rewardItemId) {
		return getEm()
				.createQuery(
						"FROM Judge j WHERE j.rewardItem.id =:rewardItemId")
				.setParameter("rewardItemId", rewardItemId).getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<Judge> findJudgesFromRewardItemForDel(String rewardItemId) {
		return getEmNoFlush()
				.createQuery(
						"FROM Judge j WHERE j.rewardItem.id =:rewardItemId")
				.setParameter("rewardItemId", rewardItemId).getResultList();
	}
	/**
	 * Find list of judge by id of a RewardItemStore.
	 * 
	 * @param rewardItemStoreId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Judge> findJudgesFromRewardItemStore(String rewardItemStoreId) {
		return getEm()
				.createQuery(
						"FROM Judge j WHERE j.rewardItemStore.id =:rewardItemStoreId")
				.setParameter("rewardItemStoreId", rewardItemStoreId).getResultList();
	}
	@SuppressWarnings("unchecked")
	public List<Judge> findJudgesFromRewardItemStoreForDel(String rewardItemStoreId) {
		return getEmNoFlush()
				.createQuery(
						"FROM Judge j WHERE j.rewardItemStore.id =:rewardItemStoreId")
				.setParameter("rewardItemStoreId", rewardItemStoreId).getResultList();
	}
	/**
	 * Find list of judge by id of a Reward.
	 * 
	 * @param rewardId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Judge> findJudgesFromReward(String rewardId) {
		return getEm().createQuery("FROM Judge j WHERE j.reward.id =:rewardId")
				.setParameter("rewardId", rewardId).getResultList();
	}

	/**
	 * Find Judge by staffId and rewardId.
	 * 
	 * @param staffId
	 * @param rewardId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Judge findJudgeByStaffIdAndRewardId(String staffId, String rewardId) {
		List<Judge> judgeList = getEm()
				.createQuery(
						"FROM Judge j WHERE j.reward.id =:rewardId AND j.staff.id =:staffId")
				.setParameter("rewardId", rewardId)
				.setParameter("staffId", staffId).getResultList();
		if (judgeList.size() > 0)
			return judgeList.get(0);
		else
			return null;
	}
	@SuppressWarnings("unchecked")
	public List<Judge> getNominatorToMessage(){
	 	List<Judge> judgeList=getEm().createQuery("select j FROM Judge j,Reward r WHERE j.reward.id =r.id and j.status =:jstatus and r.status =:rstatus and r.expectNominateDate=sysdate")
		.setParameter("jstatus", JudgeStatus.NONE)
		.setParameter("rstatus", RewardStatus.PENDING_NOMINATE)
		.getResultList();
	 	return judgeList;
	}
}
