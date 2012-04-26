package com.chinarewards.gwt.elt.client.awardReward.presenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.awardReward.request.AwardRewardAddRequest;
import com.chinarewards.gwt.elt.client.awardReward.request.AwardRewardAddResponse;
import com.chinarewards.gwt.elt.client.awardReward.request.AwardRewardInitRequest;
import com.chinarewards.gwt.elt.client.awardReward.request.AwardRewardInitResponse;
import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter;
import com.chinarewards.gwt.elt.client.budget.model.AskBudgetClientVo;
import com.chinarewards.gwt.elt.client.budget.model.CorpBudgetVo;
import com.chinarewards.gwt.elt.client.budget.plugin.AskBudgetConstants;
import com.chinarewards.gwt.elt.client.budget.plugin.CorpBudgetConstants;
import com.chinarewards.gwt.elt.client.budget.request.InitCorpBudgetRequest;
import com.chinarewards.gwt.elt.client.budget.request.InitCorpBudgetResponse;
import com.chinarewards.gwt.elt.client.chooseStaff.presenter.ChooseStaffPanelPresenter;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.core.presenter.DockPresenter;
import com.chinarewards.gwt.elt.client.core.ui.MenuProcessor;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.rewards.model.OrganicationClient;
import com.chinarewards.gwt.elt.client.rewards.model.ParticipateInfoClient;
import com.chinarewards.gwt.elt.client.rewards.model.ParticipateInfoClient.SomeoneClient;
import com.chinarewards.gwt.elt.client.rewards.plugin.RewardsListConstants;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.win.Win;
import com.chinarewards.gwt.elt.client.win.confirm.ConfirmHandler;
import com.chinarewards.gwt.elt.model.ChoosePanel.InitChoosePanelParam;
import com.chinarewards.gwt.elt.model.rewards.RewardPageType;
import com.chinarewards.gwt.elt.model.rewards.RewardsPageClient;
import com.chinarewards.gwt.elt.model.user.UserRoleVo;
import com.chinarewards.gwt.elt.util.DateTool;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class AwardRewardPresenterImpl extends
		BasePresenter<AwardRewardPresenter.AwardRewardDisplay> implements
		AwardRewardPresenter {

	private final DispatchAsync dispatcher;
	private String awardsId;
	// private String instanceId;
	private int headcount;
	private SessionManager sessionManager;
	final Win win;
	private final ChooseStaffPanelPresenter staffPanel;
	private final BreadCrumbsPresenter breadCrumbs;
	private int oneamt=0;
	final DockPresenter dockPresenter;
	final MenuProcessor menuProcessor;
	@Inject
	public AwardRewardPresenterImpl(EventBus eventBus,
			AwardRewardDisplay display, DispatchAsync dispatcher,
			ChooseStaffPanelPresenter staffPanel,
			SessionManager sessionManager, Win win,BreadCrumbsPresenter breadCrumbs,MenuProcessor menuProcessor,DockPresenter dockPresenter) {
		super(eventBus, display);
		this.dispatcher = dispatcher;
		this.staffPanel = staffPanel;
		this.sessionManager = sessionManager;
		this.win = win;
		this.breadCrumbs=breadCrumbs;
		this.menuProcessor=menuProcessor;
		this.dockPresenter=dockPresenter;
	}

	@Override
	public void bind() {
		breadCrumbs.loadChildPage("颁奖");
		display.setBreadCrumbs(breadCrumbs.getDisplay().asWidget());
		init();
		InitChoosePanelParam initChooseParam = new InitChoosePanelParam();
		initChooseParam.setTopName("获奖人：");
		staffPanel.initChoosePanel(initChooseParam);
		staffPanel.setRewardId(awardsId);
		// 候选人面板显示
		staffPanel.bind();
		display.initStaffPanel(staffPanel.getDisplay().asWidget());

		registerHandler(display.getNominateClickHandlers().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {

						ParticipateInfoClient participate = staffPanel
								.getparticipateInfo();
						final List<String> staffIds = new ArrayList<String>();
						String nominateName = "";
						if (participate instanceof SomeoneClient) {
							List<OrganicationClient> staffs = ((SomeoneClient) participate)
									.getOrganizations();
							for (OrganicationClient staff : staffs) {
								staffIds.add(staff.getId());
								nominateName += staff.getName() + ";";
							}
						}

						if (staffIds.size() <= 0) {
							win.alert("没有选人，需要选人并颁奖~");
							return;
					
						}
						if (staffIds.size() > headcount) {
							win.alert("颁奖人数超过奖励名额，请重新选择!");
							return;
						}
						String winStr = "";
						if (staffIds.size() < headcount) {
							winStr = "<font color='red'>只选了" + staffIds.size()
									+ "人，还可以选择" + (headcount - staffIds.size())
									+ "个名额，继续颁奖还是返回选人?</font>";
						}
						win.confirm("提示", "确定获奖人:" + nominateName + "?<br>"
								+ winStr, new ConfirmHandler() {

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
													 if(list.size()>0){
														 for(int i=0;i<list.size();i++){
															   vo = list.get(i);
															   map.put(vo.getId(), vo.getBudgetTitle());
														 }
														 vo = list.get(0);
														
														 remainCount = vo.getBudgetIntegral()-vo.getUseIntegeral();
															
													 }
													 if(remainCount==0 || remainCount-(oneamt*staffIds.size())<0)
													 {
														 
														// 
														 if(sessionManager.getSession().getLastLoginRole()==UserRoleVo.CORP_ADMIN)
														 {
															 win.confirm("提示", "抱歉，您的预算积分<font color='red'>"+remainCount+"</font>,本次需<font color='red'>"+(oneamt*staffIds.size())+"</font>积分,<font color='blue'>您的预算积分不足，是否需要修改今年财年预算？</font>", new ConfirmHandler() {
																	
																	@Override
																	public void confirm() {
																		dockPresenter.getDisplay().changeTopMenu("Integral");
																		dockPresenter.getDisplay().setMenuTitle("积分管理");
																		menuProcessor.initrender(dockPresenter.getDisplay().getMenu(), "Integral");
																		Platform.getInstance()
																		.getEditorRegistry()
																		.openEditor(
																				CorpBudgetConstants.EDITOR_CORPBUDGET_EDIT,
																				CorpBudgetConstants.ACTION_CORPBUDGET_EDIT,
																				null);
																		menuProcessor.changItemColor("整体预算");
																		
																	}
																});
														 }
														 else if(sessionManager.getSession().getLastLoginRole()==UserRoleVo.DEPT_MGR)
														 {
															 win.confirm("提示", "抱歉，您的预算积分<font color='red'>"+remainCount+"</font>,本次需<font color='red'>"+(oneamt*staffIds.size())+"</font>积分,<font color='blue'>您的预算积分不足，是否需要申请追加预算？</font>", new ConfirmHandler() {
																	
																	@Override
																	public void confirm() {
																		dockPresenter.getDisplay().changeTopMenu("Integral");
																		dockPresenter.getDisplay().setMenuTitle("积分管理");
																		menuProcessor.initrender(dockPresenter.getDisplay().getMenu(), "Integral");
																		AskBudgetClientVo vo = new AskBudgetClientVo();
																		Platform.getInstance()
																		.getEditorRegistry()
																		.openEditor(AskBudgetConstants.EDITOR_ADD_BUDGET,
																				AskBudgetConstants.ACTION_ASKBUDGET_ADD, vo);
																		menuProcessor.changItemColor("预算申请");
																		
																	}
																});
														 }
														 
														
													 }
													 else
													 {
														 addAwardRewardData(staffIds, awardsId);
													 }
													
												}

											});

							}
						});

					}
				}));

		registerHandler(display.getNotWinClickHandlers().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {

		
						win.confirm("提示", "确定无人获奖?"
							, new ConfirmHandler() {

							@Override
							public void confirm() {
								addAwardRewardDataNotStaff(awardsId);

							}
						});

					}
				}));
	}
	/**
	 * 颁奖数据添加(无人获奖)
	 */
	private void addAwardRewardDataNotStaff(String rewardId) {
		dispatcher.execute(new AwardRewardAddRequest(null, rewardId,
				sessionManager.getSession().getToken(),"AWARDREWARDPAGE"),
				new AsyncCallback<AwardRewardAddResponse>() {
					public void onFailure(Throwable t) {
						win.alert(t.getMessage());
					}

					@Override
					public void onSuccess(AwardRewardAddResponse response) {
						win.alert("成功!");
						RewardsPageClient rpc = new RewardsPageClient();
						rpc.setTitleName("确定获奖人");
						rpc.setPageType(RewardPageType.DETERMINEWINNERS);
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										RewardsListConstants.EDITOR_REWARDSLIST_SEARCH,
										"EDITOR_REWARDSLIST_"
												+ RewardPageType.DETERMINEWINNERS,
										rpc);
					}
				});
	}
	/**
	 * 颁奖数据添加
	 */
	private void addAwardRewardData(List<String> staffidList, String rewardId) {
		dispatcher.execute(new AwardRewardAddRequest(staffidList, rewardId,
				sessionManager.getSession().getToken(),"DETERMINEWINNERS"),
				new AsyncCallback<AwardRewardAddResponse>() {
					public void onFailure(Throwable t) {
						win.alert(t.getMessage());
					}

					@Override
					public void onSuccess(AwardRewardAddResponse response) {
						win.alert("确定获奖人成功!");
						RewardsPageClient rpc = new RewardsPageClient();
						rpc.setTitleName("确定获奖人");
						rpc.setPageType(RewardPageType.DETERMINEWINNERS);
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										RewardsListConstants.EDITOR_REWARDSLIST_SEARCH,
										"EDITOR_REWARDSLIST_"
												+ RewardPageType.DETERMINEWINNERS,
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
						oneamt=amt;
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

					}
				});
	}

	@Override
	public void initReward(String rewardId, String instanceId, int headcount) {
		// 加载数据
		this.awardsId = rewardId;
		// this.instanceId = instanceId;
		this.headcount = headcount;
	}

}
