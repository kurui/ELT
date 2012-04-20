package com.chinarewards.gwt.elt.client.awardRewardDetermine.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.awardReward.request.AwardRewardAddRequest;
import com.chinarewards.gwt.elt.client.awardReward.request.AwardRewardAddResponse;
import com.chinarewards.gwt.elt.client.awardReward.request.AwardRewardInitRequest;
import com.chinarewards.gwt.elt.client.awardReward.request.AwardRewardInitResponse;
import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.rewards.plugin.RewardsListConstants;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.win.Win;
import com.chinarewards.gwt.elt.client.win.confirm.ConfirmHandler;
import com.chinarewards.gwt.elt.model.rewards.RewardPageType;
import com.chinarewards.gwt.elt.model.rewards.RewardsPageClient;
import com.chinarewards.gwt.elt.util.DateTool;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class AwardRewardDeterminePresenterImpl extends
		BasePresenter<AwardRewardDeterminePresenter.AwardRewardDetermineDisplay> implements
		AwardRewardDeterminePresenter {

	private final DispatchAsync dispatcher;
	private String awardsId;
	// private String instanceId;
//	private int headcount;
	private SessionManager sessionManager;
	final Win win;

	private final BreadCrumbsPresenter breadCrumbs;
	@Inject
	public AwardRewardDeterminePresenterImpl(EventBus eventBus,
			AwardRewardDetermineDisplay display, DispatchAsync dispatcher,
			SessionManager sessionManager, Win win,BreadCrumbsPresenter breadCrumbs) {
		super(eventBus, display);
		this.dispatcher = dispatcher;
		
		this.sessionManager = sessionManager;
		this.win = win;
		this.breadCrumbs=breadCrumbs;
	}

	@Override
	public void bind() {
		breadCrumbs.loadChildPage("颁奖");
		display.setBreadCrumbs(breadCrumbs.getDisplay().asWidget());
		init();

		registerHandler(display.getNominateClickHandlers().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {

					
					
						win.confirm("提示", "确定颁奖?<br>", new ConfirmHandler() {

							@Override
							public void confirm() {
								addAwardRewardData(awardsId);

							}
						});

					}
				}));
	}

	/**
	 * 颁奖数据添加
	 */
	private void addAwardRewardData(String rewardId) {
		dispatcher.execute(new AwardRewardAddRequest(null, rewardId,
				sessionManager.getSession().getToken(),"AWARDREWARDPAGE"),
				new AsyncCallback<AwardRewardAddResponse>() {
					public void onFailure(Throwable t) {
						win.alert(t.getMessage());
					}

					@Override
					public void onSuccess(AwardRewardAddResponse response) {
						win.alert("颁奖成功!");
						RewardsPageClient rpc = new RewardsPageClient();
						rpc.setTitleName("颁奖列表");
						rpc.setPageType(RewardPageType.AWARDREWARDPAGE);
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										RewardsListConstants.EDITOR_REWARDSLIST_SEARCH,
										"EDITOR_REWARDSLIST_"
												+ RewardPageType.AWARDREWARDPAGE,
										rpc);
					}
				});
	}

	/**
	 * 加载初始化数据
	 */
	private void init() {
		// 根据传入ID初始化提名页面

		dispatcher.execute(new AwardRewardInitRequest(awardsId),
				new AsyncCallback<AwardRewardInitResponse>() {
					public void onFailure(Throwable t) {
						win.alert(t.getMessage());
					}

					@Override
					public void onSuccess(AwardRewardInitResponse response) {

						display.setName(response.getRewardName());
						display.setExplain(response.getDefinition());
						display.setCondition(response.getStandard());
						int total = (int) (response.getTotalAmtLimit() );
						display.setIntegral(total + "");
						display.setRecordName(response.getCreatedStaffName());
						display.setNumber(response.getHeadcountLimit() + "");
						int amt = (int) (response.getAwardAmt() );
						display.setAwardAmt(amt + "");
						display.setJudge(response.getJudgeList());

						display.setAwardNature(response.getAwardMode());
						display.setBegindate(DateTool.dateToString(response
								.getCreatedAt()));
						display.setAwarddate(DateTool.dateToString(response
								.getExpectAwardDate()));
						display.setNominateMessage("领导提名");// wating.........
						display.setNominateStaff(response
								.getJudgeListToString());
						display.setExpectNominateDate(DateTool
								.dateToString(response.getExpectNominateDate()));
						display.setAwardName(response.getAwardingStaffName());
						if(response.getWinnerList()!=null && response.getWinnerList().size()>0)
						display.setWinners(response.getWinnerList());

					}
				});
	}

	@Override
	public void initReward(String rewardId, String instanceId, int headcount) {
		// 加载数据
		this.awardsId = rewardId;
		// this.instanceId = instanceId;

	}

}
