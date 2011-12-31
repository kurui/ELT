package com.chinarewards.gwt.elt.client.nominate.presenter;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.chooseStaff.presenter.ChooseStaffPanelPresenter;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.nominate.NominateAddRequest;
import com.chinarewards.gwt.elt.client.nominate.NominateAddResponse;
import com.chinarewards.gwt.elt.client.nominate.NominateInitRequest;
import com.chinarewards.gwt.elt.client.nominate.NominateInitResponse;
import com.chinarewards.gwt.elt.client.nominate.plugin.NominateConstants;
import com.chinarewards.gwt.elt.client.rewards.model.OrganicationClient;
import com.chinarewards.gwt.elt.client.rewards.model.ParticipateInfoClient;
import com.chinarewards.gwt.elt.client.rewards.model.ParticipateInfoClient.SomeoneClient;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.model.ChoosePanel.InitChoosePanelParam;
import com.chinarewards.gwt.elt.util.DateTool;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class NominatePresenterImpl extends
		BasePresenter<NominatePresenter.NominateDisplay> implements
		NominatePresenter {

	private final DispatchAsync dispatcher;
	private String awardsId;
	private String instanceId;
	private int headcount;
	final SessionManager sessionManager;

	private final ChooseStaffPanelPresenter staffPanel;

	@Inject
	public NominatePresenterImpl(EventBus eventBus, NominateDisplay display,
			DispatchAsync dispatcher, ChooseStaffPanelPresenter staffPanel,
			SessionManager sessionManager) {
		super(eventBus, display);
		this.dispatcher = dispatcher;
		this.staffPanel = staffPanel;
		this.sessionManager = sessionManager;
	}

	@Override
	public void bind() {
		init();
		InitChoosePanelParam initChooseParam = new InitChoosePanelParam();
		initChooseParam.setTopName("待提名人：");
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
						List<String> staffIds = new ArrayList<String>();
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
							Window.alert("请选择需要提名的人...");
							return;
						}
						if (staffIds.size() > headcount) {
							if (!Window.confirm("超过名额，需要减少提名人数!.....允许超额提名?"))
								return;
						}

						if (Window.confirm("确定提名:" + nominateName + "?")) {
							addNominateData(staffIds, awardsId);
						}
					}
				}));
	}

	/**
	 * 提名数据添加
	 */
	private void addNominateData(List<String> staffidList, String rewardId) {
		dispatcher.execute(new NominateAddRequest(staffidList, rewardId,sessionManager.getSession().getToken()),
				new AsyncCallback<NominateAddResponse>() {
					public void onFailure(Throwable t) {
						Window.alert(t.getMessage());
					}

					@Override
					public void onSuccess(NominateAddResponse response) {
						Window.alert("提名成功!");
						Platform.getInstance()
								.getEditorRegistry()
								.closeEditor(
										NominateConstants.EDITOR_NOMINATE_SEARCH,
										instanceId);
					}
				});
	}

	/**
	 * 加载初始化数据
	 */
	private void init() {
		// 根据传入ID初始化提名页面

		dispatcher.execute(new NominateInitRequest(awardsId),
				new AsyncCallback<NominateInitResponse>() {
					public void onFailure(Throwable t) {
						Window.alert(t.getMessage());
					}

					@Override
					public void onSuccess(NominateInitResponse response) {
						// RewardSearchVo awardsVo=response.getAwardsVo();
						// List<String>
						// candidateList=response.getCandidateList();
						// List<String> nominateList=response.getNominateList();
						display.setName(response.getRewardName());
						display.setExplain(response.getDefinition());
						display.setCondition(response.getStandard());
						display.setIntegral(response.getTotalAmtLimit() + "");
						display.setRecordName(response.getCreatedStaffName());
						display.setNumber(response.getHeadcountLimit() + "");
						display.setAwardAmt(response.getAwardAmt() + "");
						display.setJudge(response.getJudgeList());
						// display.setCandidate(response.getCandidateList());
						display.setAwardNature(response.getAwardMode());
						display.setBegindate(DateTool.dateToString(response
								.getCreatedAt()));
						display.setAwarddate(DateTool.dateToString(response
								.getExpectAwardDate()));
						display.setNominateMessage("提名信息");// wating.........
						display.setNominateStaff(sessionManager.getSession().getLoginName());
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
		this.instanceId = instanceId;
		this.headcount = headcount;
	}

}
