package com.chinarewards.gwt.elt.server;

import java.util.Calendar;
import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinarewards.elt.service.order.OrderService;
import com.chinarewards.elt.service.reward.RewardItemService;
import com.chinarewards.elt.service.reward.RewardService;
import com.chinarewards.elt.service.reward.rule.JudgeLogic;
import com.google.inject.Inject;


/**
 * Initialize servlet.
 * 
 * @author yanxin
 * @since 1.0
 */
public class InitServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1703637354573522229L;

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static Timer batchTimer = new Timer();


	@Inject
	RewardItemService rewardItemService;
	@Inject
	JudgeLogic judgeLogic;
	@Inject
	RewardService rewardService;
	@Inject
	OrderService orderService;
	public void init() throws ServletException {
		// this must be invoked.
		super.init();

		logger.info("System initialization started");

		try {
			autoGenerateRewardTask();
			autoSendMessage();//自动发送通知提名或颁奖的消息
			autoUpdateOrderStauts();//自动更新定单状态
			logger.info("System initialization completed successfully");

		} catch (Throwable t) {
			logger.error(
					"An exception is thrown during the initialization phrase",
					t);
		}

	}

	private void autoGenerateRewardTask() {
		logger.debug(" begin to autoRewardsTask ");
		AutoGenerateRewardTask autoGenerateRewardTask = AutoGenerateRewardTask.getInstance();
		autoGenerateRewardTask.setRewardItemService(rewardItemService);
		try {
			AutoGenerateRewardTask instance = AutoGenerateRewardTask.getInstance();
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 01);
			calendar.set(Calendar.MINUTE, 00);
			calendar.set(Calendar.SECOND, 00);
			long period = 24 * 60 * 60 * 1000;
			// long period = 10 * 1000;
			batchTimer.schedule(instance, calendar.getTime(),
					period);
		} catch (Exception e) {
			logger.error("Auto run generate reward,", e);
		}
	}
	private void autoSendMessage() {
		logger.debug(" begin to SendNominatorToMessage ");
		AutoSendMessageToNominatorTask autoSendMessageToNominatorTask = AutoSendMessageToNominatorTask.getInstance();
		autoSendMessageToNominatorTask.setRewardService(rewardService);
		try {
			AutoSendMessageToNominatorTask instance = AutoSendMessageToNominatorTask.getInstance();
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 01);
			calendar.set(Calendar.MINUTE, 00);
			calendar.set(Calendar.SECOND, 00);
			long period = 24 * 60 * 60 * 1000;
			// long period = 10 * 1000;
			batchTimer.schedule(instance, calendar.getTime(),period);
		} catch (Exception e) {
			logger.error("Auto run SendNominatorToMessage,", e);
		}
	}
	private void autoUpdateOrderStauts() {
		logger.debug(" begin to updateOrder ");
		AutoUpdateOrderTask autoUpdateOrderTask = AutoUpdateOrderTask.getInstance();
		autoUpdateOrderTask.setOrderService(orderService);
		try {
			AutoUpdateOrderTask instance = AutoUpdateOrderTask.getInstance();
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 01);
			calendar.set(Calendar.MINUTE, 00);
			calendar.set(Calendar.SECOND, 00);
			long period = 24 * 60 * 60 * 1000;
			// long period = 10 * 1000;
			batchTimer.schedule(instance, calendar.getTime(),period);
		} catch (Exception e) {
			logger.error("Auto run SendNominatorToMessage,", e);
		}
	}
}
