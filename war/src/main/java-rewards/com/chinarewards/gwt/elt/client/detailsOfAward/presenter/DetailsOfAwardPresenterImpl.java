package com.chinarewards.gwt.elt.client.detailsOfAward.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.chooseStaff.presenter.ChooseStaffPanelPresenter;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.detailsOfAward.plugin.DetailsOfAwardConstants;
import com.chinarewards.gwt.elt.client.detailsOfAward.request.DetailsOfAwardInitRequest;
import com.chinarewards.gwt.elt.client.detailsOfAward.request.DetailsOfAwardInitResponse;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.util.DateTool;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class DetailsOfAwardPresenterImpl extends
		BasePresenter<DetailsOfAwardPresenter.DetailsOfAwardDisplay> implements
		DetailsOfAwardPresenter {

	private final DispatchAsync dispatcher;
	private String awardsId;
	private String instanceId;

	private final ChooseStaffPanelPresenter staffPanel;

	@Inject
	public DetailsOfAwardPresenterImpl(EventBus eventBus,
			DetailsOfAwardDisplay display, DispatchAsync dispatcher,
			ChooseStaffPanelPresenter staffPanel) {
		super(eventBus, display);
		this.dispatcher = dispatcher;
		this.staffPanel = staffPanel;
	}

	@Override
	public void bind() {
		init();
		staffPanel.initChoosePanel("待提名人：");
		staffPanel.setRewardId(awardsId);
		// 候选人面板显示
		staffPanel.bind();
		display.initStaffPanel(staffPanel.getDisplay().asWidget());

		registerHandler(display.getreturnClickHandlers().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {

						Platform.getInstance()
								.getEditorRegistry()
								.closeEditor(
										DetailsOfAwardConstants.EDITOR_DETAILSOFAWARD_SEARCH,
										instanceId);
					}
				}));
	}

	/**
	 * 加载初始化数据
	 */
	private void init() {
		// 根据传入ID初始化提名页面

		dispatcher.execute(new DetailsOfAwardInitRequest(awardsId),
				new AsyncCallback<DetailsOfAwardInitResponse>() {
					public void onFailure(Throwable t) {
						Window.alert(t.getMessage());
					}

					@Override
					public void onSuccess(DetailsOfAwardInitResponse response) {

						display.setName(response.getRewardName());
						display.setExplain(response.getDefinition());
						display.setCondition(response.getStandard());
						display.setIntegral(response.getTotalAmtLimit() + "");
						display.setRecordName(response.getCreatedStaffName());
						display.setNumber(response.getHeadcountLimit() + "");
						display.setAwardAmt(response.getAwardAmt() + "");
						display.setJudge(response.getJudgeList());

						display.setAwardNature(response.getAwardMode());
						display.setBegindate(DateTool.dateToString(response
								.getCreatedAt()));
						display.setAwarddate(DateTool.dateToString(response
								.getExpectAwardDate()));
						display.setNominateMessage("提名信息");// wating.........
						display.setNominateStaff("当前用户");
						display.setExpectNominateDate(DateTool
								.dateToString(response.getExpectNominateDate()));
						display.setAwardName(response.getAwardingStaffName());
						display.setWinners(response.getWinnerList());

					}
				});
	}

	@Override
	public void initReward(String rewardId, String instanceId, int headcount) {
		// 加载数据
		this.awardsId = rewardId;
		this.instanceId = instanceId;

	}

}
