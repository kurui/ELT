package com.chinarewards.elt.service.reward;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.chinarewards.elt.domain.reward.base.RewardItem;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.reward.base.RewardItemParam;
import com.chinarewards.elt.model.reward.search.RewardItemSearchVo;
import com.chinarewards.elt.model.reward.vo.RewardItemVo;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.model.vo.StaffAndDeptmentAutoCompile;
import com.chinarewards.elt.service.org.OrganizationLogic;
import com.chinarewards.elt.service.reward.rule.CandidateLogic;
import com.chinarewards.elt.service.reward.rule.JudgeLogic;
import com.chinarewards.elt.service.user.UserLogic;
import com.google.inject.Inject;

/**
 * The implementation of {@link RewardItemService}
 * 
 * @author yanxin
 * @since 1.0
 */
public class RewardItemServiceImpl implements RewardItemService {
	private final RewardItemLogic rewardItemLogic;
	private final UserLogic userLogic;
	private final JudgeLogic judgeLogic;
	private final EntityManager em;
	private final CandidateLogic candidateLogic;
    private final OrganizationLogic organizationLogic;
	@Inject
	public RewardItemServiceImpl(RewardItemLogic rewardItemLogic, UserLogic userLogic,
			JudgeLogic judgeLogic, EntityManager em,CandidateLogic candidateLogic,OrganizationLogic organizationLogic) {
		this.rewardItemLogic = rewardItemLogic;
		this.userLogic = userLogic;
		this.judgeLogic = judgeLogic;
		this.em = em;
		this.candidateLogic=candidateLogic;
		this.organizationLogic = organizationLogic;

	}
	@Override
	public RewardItem saveRewardItem(UserContext context, RewardItemParam param) {
		// 获取当前登录人.登录没实现,先默认当前第一个提名人
		if (em.getTransaction().isActive() != true) {
			em.getTransaction().begin();
		}
		SysUser caller = userLogic.getDefaultUser();//暂时用
		RewardItem rewardItem = rewardItemLogic.saveRewardItem(caller, param);
		em.getTransaction().commit();
		return rewardItem;
	}
	@Override
	public List<StaffAndDeptmentAutoCompile> staffAndDeptmentAutoCompile(String corporationId,	String falg, int limit) {
		return organizationLogic.staffAndDeptmentAutoCompile(corporationId,falg, limit);
	}
	@Override
	public void deleteRewardItem(String rewardItemId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RewardItemVo fetchEntireRewardItemById(String rewardItemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageStore<RewardItemVo> fetchRewardItems(UserContext context,
			RewardItemSearchVo criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageStore<RewardItemVo> fetchRewardItemsNoAcl(
			RewardItemSearchVo criteria) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RewardItem> fetchAutoGenerateRewardItem(Date currTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RewardItem enableRewardItem(UserContext context, String rewardItemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RewardItem disableRewardItem(UserContext context, String rewardItemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void runAutoRewardGeneratorBatch(Date flagTime) {
		// TODO Auto-generated method stub
		
	}
	
	

}
