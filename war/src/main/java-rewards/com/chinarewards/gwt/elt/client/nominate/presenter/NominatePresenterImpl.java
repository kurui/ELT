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
	private String awardsId;//"8a83834534544f870134544f8bfb001f";
	private String instanceId;
	private int headcount;
	
	private final ChooseStaffPanelPresenter staffPanel;
	
	@Inject
	public NominatePresenterImpl(EventBus eventBus,
			NominateDisplay display,DispatchAsync dispatcher,ChooseStaffPanelPresenter staffPanel) {
		super(eventBus, display);
		this.dispatcher=dispatcher;
		this.staffPanel=staffPanel;
	}

	@Override
	public void bind() {
		init();
		InitChoosePanelParam initChooseParam =new InitChoosePanelParam();
		initChooseParam.setTopName("待提名人：");
		staffPanel.initChoosePanel(initChooseParam);
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
					
						
					//	List<NominateCheckBox> checkBoxList=display.getNominateCheckBoxList();
						if(staffIds.size()<=0)
						{
							Window.alert("请选择需要提名的人...");
							return ;
						}
						if(staffIds.size()>headcount)
						{
							if(!Window.confirm("超过名额，需要减少提名人数!.....允许超额提名?"))
								return ;
						}
						
//						String nominateName="";
//						List<String> staffidList=new ArrayList<String>();
//						List<String> candidateidList=new ArrayList<String>();
//						for (int i = 0; i < checkBoxList.size(); i++) {
//							nominateName+=checkBoxList.get(i).getStaffName()+";";
//							staffidList.add(checkBoxList.get(i).getStaffId());
//							candidateidList.add(checkBoxList.get(i).getCandidateId());
//						}
						if(Window.confirm("确定提名:"+nominateName+"?"))
						{
							addNominateData(staffIds,awardsId);
						}
					}
				}));
	}

//	/**
//	 * 提名数据添加 old
//	 */
//	private void addNominateData(List<String> staffidList,List<String> candidateidList,String rewardId)
//	{
//		dispatcher.execute(new NominateAddRequest(staffidList,candidateidList,rewardId),
//				new AsyncCallback<NominateAddResponse>() {
//					public void onFailure(Throwable t) {
//						Window.alert(t.getMessage());
//					}
//
//					@Override
//					public void onSuccess(NominateAddResponse response) {
//						Window.alert("提名成功!---提名记录ID:"+response.getNomineeLotId());
//						Platform.getInstance().getEditorRegistry().closeEditor(NominateConstants.EDITOR_NOMINATE_SEARCH, instanceId);
//					}
//				});
//	}
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
					//	RewardSearchVo awardsVo=response.getAwardsVo();
//						List<String> candidateList=response.getCandidateList();
//						List<String> nominateList=response.getNominateList();
						display.setName(response.getRewardName());
						display.setExplain(response.getDefinition());
						display.setCondition(response.getStandard());
						display.setIntegral(response.getTotalAmtLimit()+"");
						display.setRecordName(response.getCreatedStaffName());
						display.setNumber(response.getHeadcountLimit()+"");
						display.setAwardAmt(response.getAwardAmt()+"");
						display.setJudge(response.getJudgeList());
						//display.setCandidate(response.getCandidateList());
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
