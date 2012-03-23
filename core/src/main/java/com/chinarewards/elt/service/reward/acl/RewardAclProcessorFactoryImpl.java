package com.chinarewards.elt.service.reward.acl;

import java.util.Arrays;
import java.util.List;

import com.chinarewards.elt.model.user.UserRole;
import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * The factory implementation of {@link RewardAclProcessorFactory}.
 * 
 * @author yanxin
 * @since 1.0
 */
public class RewardAclProcessorFactoryImpl implements RewardAclProcessorFactory {

	private final RewardAclProcessor hrRewardProcessor;
	private final RewardAclProcessor deptRewardProcessor;
	private final RewardAclProcessor staffRewardProcessor;

	@Inject
	public RewardAclProcessorFactoryImpl(
			@Named("RewardAclProcessorHr") RewardAclProcessor hrRewardProcessor,
			@Named("RewardAclProcessorDept") RewardAclProcessor deptRewardProcessor,
			@Named("RewardAclProcessorStaff") RewardAclProcessor staffRewardProcessor) {
		this.hrRewardProcessor = hrRewardProcessor;
		this.deptRewardProcessor = deptRewardProcessor;
		this.staffRewardProcessor=staffRewardProcessor;
	}

	public RewardAclProcessor generateRewardAclProcessor(List<UserRole> roles) {

		RewardAclProcessor res = null;
		if (roles.contains(UserRole.CORP_ADMIN)) {
			res = hrRewardProcessor;
		} else if (roles.contains(UserRole.DEPT_MGR)) {
			res = deptRewardProcessor;
		} else if (roles.contains(UserRole.STAFF)) {
			res = staffRewardProcessor;
		} else {
			throw new UnsupportedOperationException();
		}
		return res;
	}

	public RewardAclProcessor generateRewardAclProcessor(UserRole[] userRoles) {
		return generateRewardAclProcessor(Arrays.asList(userRoles));
	}

}
