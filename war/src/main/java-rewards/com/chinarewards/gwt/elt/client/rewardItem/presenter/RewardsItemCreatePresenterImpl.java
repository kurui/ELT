package com.chinarewards.gwt.elt.client.rewardItem.presenter;



import java.util.Date;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.core.ui.DialogCloseListener;
import com.chinarewards.gwt.elt.client.frequency.CalculatorSelectFactory;
import com.chinarewards.gwt.elt.client.frequency.NextRewardsDateCalculator;
import com.chinarewards.gwt.elt.client.frequency.RewardStartDateCalculator;
import com.chinarewards.gwt.elt.client.frequency.RewardStartDateCalculator.RewardDateInfo;
import com.chinarewards.gwt.elt.client.frequency.SimpleRewardDateInfo;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.rewardItem.dialog.FrequencySettingDialog;
import com.chinarewards.gwt.elt.client.rewardItem.event.FrequencySettingEvent;
import com.chinarewards.gwt.elt.client.rewardItem.handler.FrequencySettingHandler;
import com.chinarewards.gwt.elt.client.rewards.model.FrequencyClient;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsItemClient;
import com.chinarewards.gwt.elt.util.DateTool;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class RewardsItemCreatePresenterImpl extends
		BasePresenter<RewardsItemCreatePresenter.RewardsItemDisplay> implements
		RewardsItemCreatePresenter {
   
	/**
	 * 是否为修改页，默认为false
	 */
	private boolean isEditPage = false;
	/** 上一次颁奖时间 **/
	private Date lastRewardsDate;
	/**
	 * 选择员工模块
	 */
	private final ChooseStaffBlockPresenter staffBlock;
	private final DispatchAsync dispatcher;
	private final ErrorHandler errorHandler;
	private final Provider<FrequencySettingDialog> freProvider;
	//private final Win win;
//	private final SessionManager sessionManager;
	private final RewardStartDateCalculator startDateCalculator;
	private final CalculatorSelectFactory factory;
	
	// 保存上一次修改正确的结束时间
		private Date endDateCopy;
		// 保存下次公布时间副本
		private Date nextPublishCopy;
		// 保存下次公布时间和下次颁奖相隔多少天,默认间隔一天
		private int day = 1;
		// 保存最初的开始时间
		private Date startDateCopy;

		// 不正规修改颁奖时间记录是否有过提示,默认是没有
		private boolean okAndNo = false;
		// 保存最近一次颁奖时间
		private Date rewardsDateCopuy;
		// 保存修改前一次的下次颁奖日期
		private Date nextRewardsDateCopy;

		// 用来判断修改前是否有平率
		private boolean isFrequency = false;
	@Inject
	public RewardsItemCreatePresenterImpl(EventBus eventBus,ChooseStaffBlockPresenter staffBlock,
			RewardsItemDisplay display,DispatchAsync dispatcher,ErrorHandler errorHandler,Provider<FrequencySettingDialog> freProvider
			,RewardStartDateCalculator startDateCalculator,CalculatorSelectFactory factory) {
		super(eventBus, display);
		this.dispatcher=dispatcher;
		this.staffBlock = staffBlock;
		this.errorHandler = errorHandler;
		this.freProvider = freProvider;
		this.startDateCalculator = startDateCalculator;
		this.factory = factory;
	}

	@Override
	public void bind() {
		// handles the event for the frequency settings
				registerHandler(display.getFrequencySettingClick().addClickHandler(
						new ClickHandler() {
							@Override
							public void onClick(ClickEvent arg0) {
								FrequencySettingDialog dialog = freProvider.get();
								// load default values
								dialog.initNewFrequency();

								if (display.getFrequencyObj() != null) {
									dialog.initFrequency(display.getFrequencyObj(),
											true);
								}
								final HandlerRegistration registration = eventBus
										.addHandler(FrequencySettingEvent.getType(),
												new FrequencySettingHandler() {
													@Override
													public void setting(FrequencyClient frequency) {
														doSettingFrequency(frequency);
													}
												});
								Platform.getInstance().getSiteManager()
										.openDialog(dialog, new DialogCloseListener() {
											public void onClose(String dialogId,
													String instanceId) {
												registration.removeHandler();
											}
										});
							}
						}));
		staffBlock.bind();
		display.initStaffBlock(staffBlock.getDisplay().asWidget());
	}
	private void doSave(RewardsItemClient rewardsItem) {

//		dispatcher.execute(new CreateRewardsItemRequest(rewardsItem),
//				new AsyncCallback<CreateRewardsItemResponse>() {
//					@Override
//					public void onFailure(Throwable t) {
//						errorHandler.alert(t);
//					}
//
//					@Override
//					public void onSuccess(CreateRewardsItemResponse response) {
//						win.alert("添加成功");
//						Platform.getInstance()
//								.getEditorRegistry()
//								.closeEditor(
//										RewardsConstants.EDITOR_REWARDS_ITEM_CREATE,
//										instanceId);
//
//					}
//				});
	}
	
	// setting a new frequency.
		private void doSettingFrequency(FrequencyClient frequency) {
			display.showFrequencyInfo(frequency);
			Date startDate = display.getStartTime().getValue();
			Date endDate = display.getEndTime().getValue();
			Date currentDate = new Date();
			// 开始时间设为依据时间
			RewardDateInfo info = new SimpleRewardDateInfo(startDate, endDate,
					currentDate, lastRewardsDate);
			Date startTime = startDateCalculator.calculateStartDate(info);
			// 初始化时默认开始时间是当日时间
			if (startTime == null) {
				startTime = new Date();
			}
			NextRewardsDateCalculator cal = factory.getCalculator();
			Date nextRewardsDate = cal.calNextRewardsDate(startTime, frequency);
			startDate = startDate == null ? new Date() : startDate;
			if (!isEditPage || (startDate.getTime() > nextRewardsDate.getTime())) {
				// 开始时间和下次颁奖时间一样
				display.getStartTime().setValue(nextRewardsDate);
			} else if (!isEditPage
					|| (nextRewardsDate.getTime() >= startDateCopy.getTime())) {
				if (isFrequency) {
					display.getStartTime().setValue(startDateCopy);
				} else {
					display.getStartTime().setValue(nextRewardsDate);
				}
			} else if (isEditPage
					&& nextRewardsDate.getTime() <= startDateCopy.getTime()) {
				display.getStartTime().setValue(nextRewardsDate);
			}

			display.getNextRewardsTime().setValue(nextRewardsDate);
			if (display.getEndTime().getValue() != null) {
				if (display.getEndTime().getValue().getTime() < nextRewardsDate
						.getTime()) {
					Window.alert("请重新修改结束时间！");
					display.getEndTime().setValue(nextRewardsDate);
					endDateCopy = nextRewardsDate;
				}
			}
			modifyNextPublishTime();

		}
		// 修改下次颁奖时间
		private void modifyNextPublishTime() {
			Date startDate = display.getNextRewardsTime().getValue();
			Date date = DateTool.addSomeDay(startDate, 0 - day);
			display.getNextPublishTime().setValue(date);
			nextPublishCopy = date;
		}
	@Override
	public void initRewardsItemOrRewardsTemplate(Object item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initInstanceId(String instanceId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkIsAmountRoleLevel() {
		// TODO Auto-generated method stub
		
	}


}
