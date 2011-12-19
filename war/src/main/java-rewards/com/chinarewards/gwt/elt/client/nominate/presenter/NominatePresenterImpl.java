package com.chinarewards.gwt.elt.client.nominate.presenter;



import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.nominate.NominateAddRequest;
import com.chinarewards.gwt.elt.client.nominate.NominateAddResponse;
import com.chinarewards.gwt.elt.client.nominate.NominateInitRequest;
import com.chinarewards.gwt.elt.client.nominate.NominateInitResponse;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class NominatePresenterImpl extends
		BasePresenter<NominatePresenter.NominateDisplay> implements
		NominatePresenter {

	private final DispatchAsync dispatcher;
	private String awardsId="8a83834534544f870134544f8bfb001f";
	
	@Inject
	public NominatePresenterImpl(EventBus eventBus,
			NominateDisplay display,DispatchAsync dispatcher) {
		super(eventBus, display);
		this.dispatcher=dispatcher;
	}

	@Override
	public void bind() {
		init();
		registerHandler(display.getNominateClickHandlers().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {
						List<String> staffidList=display.getStaffList();
						List<String> candidateidList=display.getCandidateList();
						if(staffidList.size()<=0)
						{
							Window.alert("请选择需要提名的人...");
							return ;
						}
						String message="";
						for (int i = 0; i < candidateidList.size(); i++) {
							message+="提名ID:"+candidateidList.get(i)+"----提名次数+1;";
						}
						Window.alert(message);
						addNominateData(staffidList,candidateidList,awardsId);
					}
				}));
	}

	/**
	 * 提名数据添加
	 */
	private void addNominateData(List<String> staffidList,List<String> candidateidList,String rewardId)
	{
		dispatcher.execute(new NominateAddRequest(staffidList,candidateidList,rewardId),
				new AsyncCallback<NominateAddResponse>() {
					public void onFailure(Throwable t) {
						Window.alert(t.getMessage());
					}

					@Override
					public void onSuccess(NominateAddResponse response) {
						Window.alert("提名记录ID:"+response.getNomineeLotId());
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
						display.setCandidate(response.getCandidateList());
						display.setAwardNature(response.getAwardMode());
						display.setBegindate(response.getCreatedAt()+"");
						display.setAwarddate(response.getExpectAwardDate()+"");
						display.setNominateMessage("提名信息");//wating.........
						display.setNominateStaff("当前用户是谁");
						display.setExpectNominateDate(response.getExpectNominateDate()+"");
						display.setAwardName(response.getAwardingStaffName());
						
					}
				});
	}

	@Override
	public void setNominateByRewards(String rewardId) {
		// TODO Auto-generated method stub
		
	}

}
