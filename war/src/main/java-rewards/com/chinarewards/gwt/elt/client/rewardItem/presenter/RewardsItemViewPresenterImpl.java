package com.chinarewards.gwt.elt.client.rewardItem.presenter;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.chinarewards.gwt.elt.client.rewardItem.dialog.ChooseStaffWinDialog;
import com.chinarewards.gwt.elt.client.rewardItem.dialog.FrequencySettingDialog;
import com.chinarewards.gwt.elt.client.rewardItem.event.ChooseStaffEvent;
import com.chinarewards.gwt.elt.client.rewardItem.event.FrequencySettingEvent;
import com.chinarewards.gwt.elt.client.rewardItem.handler.ChooseStaffHandler;
import com.chinarewards.gwt.elt.client.rewardItem.handler.FrequencySettingHandler;
import com.chinarewards.gwt.elt.client.rewardItem.plugin.RewardsItemConstants;
import com.chinarewards.gwt.elt.client.rewardItem.request.CreateRewardsItemRequest;
import com.chinarewards.gwt.elt.client.rewardItem.request.CreateRewardsItemResponse;
import com.chinarewards.gwt.elt.client.rewardItem.request.SearchRewardsItemByIdRequest;
import com.chinarewards.gwt.elt.client.rewardItem.request.SearchRewardsItemByIdResponse;
import com.chinarewards.gwt.elt.client.rewards.model.DayFrequencyClient;
import com.chinarewards.gwt.elt.client.rewards.model.FrequencyClient;
import com.chinarewards.gwt.elt.client.rewards.model.MonthFrequencyClient;
import com.chinarewards.gwt.elt.client.rewards.model.OrganicationClient;
import com.chinarewards.gwt.elt.client.rewards.model.ParticipateInfoClient;
import com.chinarewards.gwt.elt.client.rewards.model.ParticipateInfoClient.EveryoneClient;
import com.chinarewards.gwt.elt.client.rewards.model.ParticipateInfoClient.SomeoneClient;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsBaseInfo;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsItemClient;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsTypeClient;
import com.chinarewards.gwt.elt.client.rewards.model.SpecialCondition;
import com.chinarewards.gwt.elt.client.rewards.model.StaffClient;
import com.chinarewards.gwt.elt.client.rewards.model.WeekFrequencyClient;
import com.chinarewards.gwt.elt.client.rewards.model.YearFrequencyClient;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.util.DateTool;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class RewardsItemViewPresenterImpl extends
		BasePresenter<RewardsItemViewPresenter.RewardsItemViewDisplay> implements		RewardsItemViewPresenter {
	String instanceId;//修改时传过来的ID
	/**
	 * 是否为修改页，默认为false
	 */
	private boolean isEditPage = false;
	private String rewardsItemId;
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
	private final SessionManager sessionManager;
	private final RewardStartDateCalculator startDateCalculator;
	private final CalculatorSelectFactory factory;
	RewardsItemClient item;
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
		
		private  Date tmdays;//提前提名的日期

		// 用来判断修改前是否有平率
		private boolean isFrequency = false;
		private final Provider<ChooseStaffWinDialog> chooseStaffDialogProvider;
	@Inject
	public RewardsItemViewPresenterImpl(EventBus eventBus,ChooseStaffBlockPresenter staffBlock,
			RewardsItemViewDisplay display,DispatchAsync dispatcher,ErrorHandler errorHandler,Provider<FrequencySettingDialog> freProvider
			,RewardStartDateCalculator startDateCalculator,CalculatorSelectFactory factory,SessionManager sessionManager
			,Provider<ChooseStaffWinDialog> chooseStaffDialogProvider) {
		super(eventBus, display);
		this.dispatcher=dispatcher;
		this.staffBlock = staffBlock;
		this.errorHandler = errorHandler;
		this.freProvider = freProvider;
		this.startDateCalculator = startDateCalculator;
		this.factory = factory;
		this.sessionManager = sessionManager;
		this.chooseStaffDialogProvider = chooseStaffDialogProvider;
	}

	@Override
	public void bind() {
		//绑定事件
		// init();
		//候选人面板显示
		staffBlock.bind();
		display.initStaffBlock(staffBlock.getDisplay().asWidget());
	}
	
	
        
				@Override
				public void initInstanceId(String instanceId,RewardsItemClient item) {
					this.instanceId = instanceId;
					initDataToEditRewardsItem( item);
				}
				
				private void initDataToEditRewardsItem(final RewardsItemClient item) {
					String id = item.getId();
					  
					isEditPage  = true;
					{
						dispatcher.execute(new SearchRewardsItemByIdRequest(id),
								new AsyncCallback<SearchRewardsItemByIdResponse>() {
									@Override
									public void onFailure(Throwable arg0) {
										errorHandler.alert("查询奖项出错!");

									}

									@Override
									public void onSuccess(SearchRewardsItemByIdResponse response) {
										RewardsItemClient item = response.getRewardsItem();
										
										display.showRewardsItem(item);

										// 初始前一次颁奖时间
										rewardsDateCopuy = item.getLastRewardedDate();
										// 初始结束时间副本
										endDateCopy = item.getEndTime();
										// 初始下次公布时间副本
										nextPublishCopy = item.getNextPublishTime();
										// 记录最初的开始时间
										Date stD = item.getStartTime();
										if (stD == null)
											stD = new Date();
										startDateCopy = stD;
										// 记录第一次赋值的颁奖时间
										nextRewardsDateCopy = item.getNextTime();
										if (nextPublishCopy != null
												&& nextRewardsDateCopy != null) {
											day = DateTool.getIntervalDays(nextPublishCopy,
													nextRewardsDateCopy);
										}
										// 用了记录修改前是否有平率
										if (item.getFrequency() != null) {
											isFrequency = true;
										}
										//display.showHistoryBtn();
										staffBlock.getDisplay().showParticipateInfo(item.getBaseInfo());
										setId(item.getId());
										lastRewardsDate = item.getLastRewardedDate();
										

									}

								});
					}

				}
				

				public void setId(String id) {
					this.rewardsItemId = id;
					isEditPage = true;
				}

				
				

				
}
