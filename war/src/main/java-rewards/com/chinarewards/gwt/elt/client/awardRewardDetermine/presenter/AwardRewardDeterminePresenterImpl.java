package com.chinarewards.gwt.elt.client.awardRewardDetermine.presenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.awardReward.request.AwardRewardAddRequest;
import com.chinarewards.gwt.elt.client.awardReward.request.AwardRewardAddResponse;
import com.chinarewards.gwt.elt.client.awardReward.request.AwardRewardInitRequest;
import com.chinarewards.gwt.elt.client.awardReward.request.AwardRewardInitResponse;
import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.budget.model.CorpBudgetVo;
import com.chinarewards.gwt.elt.client.budget.request.InitCorpBudgetRequest;
import com.chinarewards.gwt.elt.client.budget.request.InitCorpBudgetResponse;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.core.ui.DialogCloseListener;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.rewardItem.dialog.ChooseStaffWinDialog;
import com.chinarewards.gwt.elt.client.rewardItem.event.ChooseStaffEvent;
import com.chinarewards.gwt.elt.client.rewardItem.handler.ChooseStaffHandler;
import com.chinarewards.gwt.elt.client.rewards.model.StaffClient;
import com.chinarewards.gwt.elt.client.rewards.plugin.RewardsListConstants;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.win.Win;
import com.chinarewards.gwt.elt.client.win.confirm.ConfirmHandler;
import com.chinarewards.gwt.elt.model.rewards.RewardPageType;
import com.chinarewards.gwt.elt.model.rewards.RewardsPageClient;
import com.chinarewards.gwt.elt.util.DateTool;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class AwardRewardDeterminePresenterImpl extends
		BasePresenter<AwardRewardDeterminePresenter.AwardRewardDetermineDisplay> implements
		AwardRewardDeterminePresenter {

	private final DispatchAsync dispatcher;
	private String awardsId;
	private boolean emailBox;
	private boolean messageBox;
	// private String instanceId;
//	private int headcount;
	private SessionManager sessionManager;
	final Win win;
	private final Provider<ChooseStaffWinDialog> chooseStaffDialogProvider;
	private final BreadCrumbsPresenter breadCrumbs;
	private int oneamt=0;
	private int winSize=0;
	@Inject
	public AwardRewardDeterminePresenterImpl(EventBus eventBus,
			AwardRewardDetermineDisplay display, DispatchAsync dispatcher,
			SessionManager sessionManager, Win win,BreadCrumbsPresenter breadCrumbs,Provider<ChooseStaffWinDialog> chooseStaffDialogProvider) {
		super(eventBus, display);
		this.dispatcher = dispatcher;
		this.chooseStaffDialogProvider=chooseStaffDialogProvider;
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
							
								dispatcher.execute(new InitCorpBudgetRequest(sessionManager.getSession()),
										new AsyncCallback<InitCorpBudgetResponse>() {
								          	@Override
											public void onFailure(Throwable arg0) {
												win.alert("查询财年周期出错!");
												
											}

											@Override
											public void onSuccess(InitCorpBudgetResponse response) {
												 double remainCount=0;
												 List<CorpBudgetVo> list = response.getResult();
												 Map<String, String> map = new HashMap<String, String>();
												 map.clear();
												 CorpBudgetVo vo = new CorpBudgetVo();
												 if(list!=null && list.size()>0){
													 for(int i=0;i<list.size();i++){
														   vo = list.get(i);
														   map.put(vo.getId(), vo.getBudgetTitle());
													 }
													 vo = list.get(0);
													
													 remainCount = vo.getBudgetIntegral()-vo.getUseIntegeral();
														
												 }
												 if(remainCount==0 || remainCount-(oneamt*winSize)<0)
												 {
													win.alert("抱歉，您的预算积分<font color='red'>"+StringUtil.subZeroAndDot(remainCount+"")+"</font>,本次需<font color='red'>"+(oneamt*winSize)+"</font>积分,<font color='blue'>您的预算积分不足!</font>");
													return;
												 }
												 else
												 {
													addAwardRewardData(awardsId);
												 }
												
											}

										});
							}
						});

					}
				}));
		
		registerHandler(display.getEmailCheckbox().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {
						emailBox=display.getEmailCheckbox().getValue();

					}
				}));
		registerHandler(display.getMessageCheckbox().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {
						messageBox=display.getMessageCheckbox().getValue();

					}
				}));
		registerHandler(display.getChooseStaffBtnClick().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent arg0) {
						final ChooseStaffWinDialog dialog = chooseStaffDialogProvider.get();
						dialog.setNominee(false, true, null);
						final HandlerRegistration registration = eventBus.addHandler(ChooseStaffEvent.getType(),new ChooseStaffHandler() {
											@Override
											public void chosenStaff(List<StaffClient> list) {
												for (StaffClient r : list) {									
													if (!display.getSpecialTextArea().containsItem(r)) {	
														display.getSpecialTextArea().addItem(r);
													}
												}
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
	}

	/**
	 * 颁奖数据添加
	 */
	private void addAwardRewardData(String rewardId) {
		AwardRewardAddRequest request=new AwardRewardAddRequest(null, rewardId,sessionManager.getSession().getToken(),"AWARDREWARDPAGE");
		request.setEmailBox(emailBox);
		request.setMessageBox(messageBox);
		request.setMessageStaffId(display.getRealOrginzationIds());
		dispatcher.execute(request,
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
						
						oneamt=amt;
						if(response.getWinnerList()!=null)
						winSize=response.getWinnerList().size();

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
