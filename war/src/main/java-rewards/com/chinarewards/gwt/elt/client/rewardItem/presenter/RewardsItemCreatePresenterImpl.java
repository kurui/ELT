package com.chinarewards.gwt.elt.client.rewardItem.presenter;



import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.frequency.CalculatorSelectFactory;
import com.chinarewards.gwt.elt.client.frequency.RewardStartDateCalculator;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.rewardItem.request.CreateRewardsItemRequest;
import com.chinarewards.gwt.elt.client.rewardItem.request.CreateRewardsItemResponse;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsItemClient;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.google.inject.Inject;

public class RewardsItemCreatePresenterImpl extends
		BasePresenter<RewardsItemCreatePresenter.RewardsItemDisplay> implements
		RewardsItemCreatePresenter {

	
	/**
	 * 选择员工模块
	 */
	private final ChooseStaffBlockPresenter staffBlock;
	private final DispatchAsync dispatcher;
//	private final ErrorHandler errorHandler;
	//private final Win win;
//	private final SessionManager sessionManager;
//	private final RewardStartDateCalculator startDateCalculator;
//	private final CalculatorSelectFactory factory;
	@Inject
	public RewardsItemCreatePresenterImpl(EventBus eventBus,ChooseStaffBlockPresenter staffBlock,
			RewardsItemDisplay display,DispatchAsync dispatcher) {
		super(eventBus, display);
		this.dispatcher=dispatcher;
		this.staffBlock = staffBlock;
	}

	@Override
	public void bind() {
//		registerHandler(display.getRewardsItemClickHandlers().addClickHandler(
//				new ClickHandler() {
//					public void onClick(ClickEvent paramClickEvent) {
//						
//				//		doHrRegister();
//					}
//				}));
		
		staffBlock.bind();
		display.initStaffBlock(staffBlock.getDisplay().asWidget());
	}
	private void doSave(RewardsItemClient rewardsItem) {

//		dispatcher.execute(new CreateRewardsItemRequest(rewardsItem),
//				new AsyncCallback<CreateRewardsItemResponse>() {
//					@Override
//					public void onFailure(Throwable t) {
//						errorHandler.alert(t);
//					}
//
//					@Override
//					public void onSuccess(CreateRewardsItemResponse response) {
//						win.alert("添加成功");
//						Platform.getInstance()
//								.getEditorRegistry()
//								.closeEditor(
//										RewardsConstants.EDITOR_REWARDS_ITEM_CREATE,
//										instanceId);
//
//					}
//				});
	}
	@Override
	public void initRewardsItemOrRewardsTemplate(Object item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initInstanceId(String instanceId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkIsAmountRoleLevel() {
		// TODO Auto-generated method stub
		
	}


}
