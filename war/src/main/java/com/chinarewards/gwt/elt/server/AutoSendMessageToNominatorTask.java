package com.chinarewards.gwt.elt.server;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinarewards.elt.service.reward.RewardService;
import com.chinarewards.elt.service.reward.rule.JudgeLogic;

public class AutoSendMessageToNominatorTask extends TimerTask {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	private RewardService rewardService;
	private JudgeLogic judgeLogic;
	private static AutoSendMessageToNominatorTask instance;


	private AutoSendMessageToNominatorTask() {
	}

	public static AutoSendMessageToNominatorTask getInstance() {
		if (instance == null) {
			instance = new AutoSendMessageToNominatorTask();
		}

		return instance;
	}


	@Override
	public void run() {
		logger.info(" BEGIN to RUN AutoGenerateRewardTask ");
		//Date now = DateUtil.getTime();
		judgeLogic.getNominatorToMessage();//给提名人发消息
		rewardService.toMessageForReward();// 给颁奖人发消息
	}

	public void setJudgeLogic(JudgeLogic judgeLogic) {
		this.judgeLogic = judgeLogic;
	}
	public void setRewardService(RewardService rewardService) {
		this.rewardService = rewardService;
	}
}
