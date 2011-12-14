package com.chinarewards.gwt.elt.client.nominate.presenter;



import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
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
						List<String> idList=display.getCandidateList();
						String message="";
						for (int i = 0; i < idList.size(); i++) {
							message+=idList.get(i)+"提名次数+1";
						}
						Window.alert(message);
					}
				}));
	}

	 /**
		 * 加载初始化数据
		 */
	private void init()
	{
		//根据传入ID初始化提名页面
		String awardsId="1";
		dispatcher.execute(new NominateInitRequest(awardsId),
				new AsyncCallback<NominateInitResponse>() {
					public void onFailure(Throwable t) {
						Window.alert(t.getMessage());
					}

					@Override
					public void onSuccess(NominateInitResponse response) {
						String awardsVo=response.getAwardsVo();
						List<String> candidateList=response.getCandidateList();
						List<String> nominateList=response.getNominateList();
						display.setName(awardsVo);
						display.setExplain("explain");
						display.setCondition("condition");
						display.setIntegral("integral");
						display.setRecordName("recordName");
						display.setNumber("number");
						display.setNominate("nominate");
						display.setCandidate(candidateList);
						display.setAwardNature("awardNature");
						display.setBegindate("begindate");
						display.setAwarddate("awarddate");
						display.setNominateMessage("nominateMessage");
						display.setAwardName("awardName");
						
					}
				});
	}

}
