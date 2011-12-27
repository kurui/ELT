package com.chinarewards.gwt.elt.server;

import java.util.Date;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinarewards.elt.service.reward.RewardItemService;
import com.chinarewards.elt.util.DateUtil;
import com.google.inject.Inject;

public class AutoGenerateRewardTask extends TimerTask {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	public  RewardItemService rewardItemService;

	@Inject
	public AutoGenerateRewardTask(RewardItemService rewardItemService) {
		this.rewardItemService = rewardItemService;
	}

	public AutoGenerateRewardTask() {

	}

	static AutoGenerateRewardTask task = new AutoGenerateRewardTask();


	public static AutoGenerateRewardTask getInstance() {
		if (task == null)
			return new AutoGenerateRewardTask();
		else
			return task;
	}
	
	@Override
	public void run() {
		logger.info(" BEGIN to RUN AutoGenerateRewardTask ");
		Date now = DateUtil.getTime();
//@Inject 进不来..waiting............
	//	rewardItemService.runAutoRewardGeneratorBatch(now);
	}

}
