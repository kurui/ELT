package com.chinarewards.elt.service.reward.rule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.chinarewards.elt.dao.org.StaffDao;
import com.chinarewards.elt.dao.reward.JudgeDao;
import com.chinarewards.elt.dao.reward.RewardDao;
import com.chinarewards.elt.dao.reward.RewardItemDao;
import com.chinarewards.elt.dao.reward.RewardItemStoreDao;
import com.chinarewards.elt.domain.org.Staff;
import com.chinarewards.elt.domain.reward.base.Reward;
import com.chinarewards.elt.domain.reward.base.RewardItem;
import com.chinarewards.elt.domain.reward.base.RewardItemStore;
import com.chinarewards.elt.domain.reward.person.Judge;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.broadcast.BroadcastingVo;
import com.chinarewards.elt.model.broadcast.OrganType;
import com.chinarewards.elt.model.reward.base.JudgeStatus;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.service.broadcast.BroadcastService;
import com.chinarewards.elt.service.user.UserLogic;
import com.chinarewards.elt.util.DateUtil;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

/**
 * The implementation of {@link JudgeLogic}
 * 
 * @author yanxin
 * @since 1.0
 */
@Transactional
public class JudgeLogicImpl implements JudgeLogic {

	private final JudgeDao judgeDao;
	private final RewardItemDao rewardItemDao;
	private final RewardDao rewardDao;
	private final StaffDao staffDao;
	private final RewardItemStoreDao rewardItemStoreDao;
	private final BroadcastService broadcastService;
	private final UserLogic userLogic;

	@Inject
	public JudgeLogicImpl(JudgeDao judgeDao, RewardItemDao rewardItemDao,
			RewardDao rewardDao, StaffDao staffDao,
			RewardItemStoreDao rewardItemStoreDao,
			BroadcastService broadcastService,UserLogic userLogic) {
		this.judgeDao = judgeDao;
		this.rewardItemDao = rewardItemDao;
		this.rewardDao = rewardDao;
		this.staffDao = staffDao;
		this.rewardItemStoreDao = rewardItemStoreDao;
		this.broadcastService = broadcastService;
		this.userLogic=userLogic;
	}

	@Override
	public void bindJudgesToRewardItem(SysUser caller, String rewardItemId,
			List<String> staffIds) {
		RewardItem rewardItem = rewardItemDao.findByIdNoFlush(RewardItem.class,
				rewardItemId);
		Date now = DateUtil.getTime();
		for (String id : staffIds) {
			Judge judge = new Judge();
			Staff staff = staffDao.findByIdNoFlush(Staff.class, id);
			judge.setStaff(staff);
			judge.setRewardItem(rewardItem);
			judge.setCreatedAt(now);
			judge.setCreatedBy(caller);
			judge.setLastModifiedAt(now);
			judge.setLastModifiedBy(caller);
			judgeDao.saveNoFlush(judge);
		}
	}

	@Override
	public void bindJudgesToRewardItemStore(SysUser caller,
			String rewardItemStoreId, List<String> staffIds) {
		RewardItemStore rewardItemStore = rewardItemStoreDao.findByIdNoFlush(
				RewardItemStore.class, rewardItemStoreId);
		Date now = DateUtil.getTime();
		for (String id : staffIds) {
			Judge judge = new Judge();
			Staff staff = staffDao.findByIdNoFlush(Staff.class, id);
			judge.setStaff(staff);
			judge.setRewardItemStore(rewardItemStore);
			judge.setCreatedAt(now);
			judge.setCreatedBy(caller);
			judge.setLastModifiedAt(now);
			judge.setLastModifiedBy(caller);
			judgeDao.saveNoFlush(judge);
		}
	}

	@Override
	public void removeJudgesFromRewardItem(String rewardItemId) {
		List<Judge> judgeList = judgeDao.findJudgesFromRewardItemForDel(rewardItemId);
		for (Judge judge : judgeList) {
			judgeDao.deleteNoFlush(judge);
		}
	}

	@Override
	public void removeJudgesFromRewardItemStore(String rewardItemIdStore) {
		List<Judge> judgeList = judgeDao.findJudgesFromRewardItemStoreForDel(rewardItemIdStore);
		for (Judge judge : judgeList) {
			judgeDao.deleteNoFlush(judge);
		}
		
	}

	@Override
	public List<Judge> findJudgesFromRewardItem(String rewardItemId) {
		return judgeDao.findJudgesFromRewardItem(rewardItemId);
	}

	@Override
	public List<Judge> findJudgesFromRewardItemStore(String rewardItemStoreId) {
		return judgeDao.findJudgesFromRewardItemStore(rewardItemStoreId);
	}

	@Override
	public List<Judge> findJudgesFromReward(String rewardId) {
		return judgeDao.findJudgesFromReward(rewardId);
	}

	@Override
	public void cloneJudgesFromRewardItemToReward(SysUser caller,
			String fromRewardItemId, String toRewardId) {
		Date now = DateUtil.getTime();
		Reward reward = rewardDao.findById(Reward.class, toRewardId);
		List<Judge> judges = judgeDao
				.findJudgesFromRewardItem(fromRewardItemId);
		for (Judge j : judges) {
			Judge newJudge = new Judge();
			newJudge.setReward(reward);
			newJudge.setStaff(j.getStaff());
			newJudge.setCreatedAt(now);
			newJudge.setCreatedBy(caller);
			newJudge.setLastModifiedAt(now);
			newJudge.setLastModifiedBy(caller);
			newJudge.setStatus(JudgeStatus.NONE);
			judgeDao.save(newJudge);
			
			UserContext uc=new UserContext();
			uc.setUserId(caller.getId());
			userLogic.addUserNominateRole(j.getStaff().getId(), uc);
		}
	}

	@Override
	public void copyJudgeToRewardItem(SysUser caller, String rewardItemStoreId,
			String rewardItemId) {
		Date now = DateUtil.getTime();
		RewardItem rewardItem = rewardItemDao.findById(RewardItem.class,
				rewardItemId);
		List<Judge> judges = judgeDao
				.findJudgesFromRewardItemStore(rewardItemStoreId);
		for (Judge j : judges) {
			Judge newJudge = new Judge();
			newJudge.setRewardItem(rewardItem);
			newJudge.setStaff(j.getStaff());
			newJudge.setCreatedAt(now);
			newJudge.setCreatedBy(caller);
			newJudge.setLastModifiedAt(now);
			newJudge.setLastModifiedBy(caller);
			newJudge.setStatus(JudgeStatus.NONE);
			judgeDao.save(newJudge);
		}

	}

	@Override
	public void getNominatorToMessage() {
		
		List<Judge> judges = judgeDao.getNominatorToMessage();

		if (judges != null && judges.size() > 0) {
			for (Judge j : judges) {
				List<String[]> organList = new ArrayList<String[]>();
				String[] nameAndId = new String[3];
				nameAndId[0] = j.getStaff().getId();
				nameAndId[1] = j.getStaff().getName();
				nameAndId[2] = OrganType.STAFF.toString();
				organList.add(nameAndId);
				Reward r = j.getReward();
				sendMessage(organList,j.getStaff().getCorporation().getId(),r.getCreatedBy().getId(),r.getName());
			}
			
		}
	}
	private void sendMessage(List<String[]> organList,String corpId,String userId,String rewardname){
		UserContext context = new UserContext();
		context.setCorporationId(corpId);
		context.setUserId(userId);
		// 对提名人.发送消息
		BroadcastingVo messagevo = new BroadcastingVo();
		messagevo.setOrganList(organList);
		messagevo.setContent("系统提示："+rewardname+"提名期限将至，请参加提名");
		broadcastService.createOrUpdateMessage(messagevo, context);
	}
}
