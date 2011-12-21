package com.chinarewards.gwt.elt.client.awardReward.presenter;



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
import com.chinarewards.gwt.elt.util.DateTool;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class AwardRewardPresenterImpl extends
		BasePresenter<AwardRewardPresenter.AwardRewardDisplay> implements
		AwardRewardPresenter {

	private final DispatchAsync dispatcher;
	private String awardsId;
	private String instanceId;
	private int headcount;
	
	private final ChooseStaffPanelPresenter staffPanel;
	
	@Inject
	public AwardRewardPresenterImpl(EventBus eventBus,
			AwardRewardDisplay display,DispatchAsync dispatcher,ChooseStaffPanelPresenter staffPanel) {
		super(eventBus, display);
		this.dispatcher=dispatcher;
		this.staffPanel=staffPanel;
	}

	@Override
	public void bind() {
		init();
		staffPanel.setRewardId(awardsId);
		//候选人面板显示
		staffPanel.bind();
		display.initStaffPanel(staffPanel.getDisplay().asWidget());
		
		
		registerHandler(display.getNominateClickHandlers().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {
						
						ParticipateInfoClient participate = staffPanel.getparticipateInfo();
						List<String> staffIds = new ArrayList<String>();
						String nominateName="";
						if (participate instanceof SomeoneClient) {
							List<OrganicationClient> staffs = ((SomeoneClient) participate).getOrganizations();
							for (OrganicationClient staff : staffs) {
								staffIds.add(staff.getId());
								nominateName+=staff.getName()+";";
							}
						}
					
				
						if(staffIds.size()<=0)
						{
							Window.alert("请选择获奖的人!");
							return ;
						}
						if(staffIds.size()>headcount)
						{
							Window.alert("获奖的人超出预定人数!");
							return ;
						}

						if(Window.confirm("确定获奖人:"+nominateName+"?"))
						{
							addNominateData(staffIds,awardsId);
						}
					}
				}));
	}


	/**
	 * 提名数据添加
	 */
	private void addNominateData(List<String> staffidList,String rewardId)
	{
		dispatcher.execute(new NominateAddRequest(staffidList,rewardId),
				new AsyncCallback<NominateAddResponse>() {
					public void onFailure(Throwable t) {
						Window.alert(t.getMessage());
					}

					@Override
					public void onSuccess(NominateAddResponse response) {
						Window.alert("提名成功!---提名记录ID:"+response.getNomineeLotId());
						Platform.getInstance().getEditorRegistry().closeEditor(NominateConstants.EDITOR_NOMINATE_SEARCH, instanceId);
					}
				});
	}
	
	 /**
		 * 加载初始化数据
		 */
	private void init()
	{
		//根据传入ID初始化提名页面
	
		dispatcher.execute(new NominateInitRequest(awardsId),
				new AsyncCallback<NominateInitResponse>() {
					public void onFailure(Throwable t) {
						Window.alert(t.getMessage());
					}

					@Override
					public void onSuccess(NominateInitResponse response) {

						display.setName(response.getRewardName());
						display.setExplain(response.getDefinition());
						display.setCondition(response.getStandard());
						display.setIntegral(response.getTotalAmtLimit()+"");
						display.setRecordName(response.getCreatedStaffName());
						display.setNumber(response.getHeadcountLimit()+"");
						display.setAwardAmt(response.getAwardAmt()+"");
						display.setJudge(response.getJudgeList());

						display.setAwardNature(response.getAwardMode());
						display.setBegindate(DateTool.dateToString(response.getCreatedAt()));
						display.setAwarddate(DateTool.dateToString(response.getExpectAwardDate()));
						display.setNominateMessage("提名信息");//wating.........
						display.setNominateStaff("当前用户");
						display.setExpectNominateDate(DateTool.dateToString(response.getExpectNominateDate()));
						display.setAwardName(response.getAwardingStaffName());
						
					}
				});
	}


	
	@Override
	public void initReward(String rewardId,String instanceId,int headcount) {
		// 加载数据
		this.awardsId=rewardId;
		this.instanceId=instanceId;
		this.headcount=headcount;
	}


}
