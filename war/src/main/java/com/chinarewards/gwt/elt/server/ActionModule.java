/**
 * 
 */
package com.chinarewards.gwt.elt.server;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

import com.chinarewards.gwt.elt.client.awardReward.request.AwardRewardAddRequest;
import com.chinarewards.gwt.elt.client.awardReward.request.AwardRewardInitRequest;
import com.chinarewards.gwt.elt.client.chooseStaff.request.SearchStaffChooseRequest;
import com.chinarewards.gwt.elt.client.detailsOfAward.request.DetailsOfAwardInitRequest;
import com.chinarewards.gwt.elt.client.enterprise.EnterpriseInitRequest;
import com.chinarewards.gwt.elt.client.enterprise.EnterpriseRequest;
import com.chinarewards.gwt.elt.client.login.LoginRequest;
import com.chinarewards.gwt.elt.client.nominate.NominateAddRequest;
import com.chinarewards.gwt.elt.client.nominate.NominateInitRequest;
import com.chinarewards.gwt.elt.client.rewardItem.request.CreateRewardsItemRequest;
import com.chinarewards.gwt.elt.client.rewardItem.request.SearchOrganizationRequest;
import com.chinarewards.gwt.elt.client.rewards.request.SearchRewardsRequest;
import com.chinarewards.gwt.elt.client.staff.HrRegisterRequest;
import com.chinarewards.gwt.elt.client.user.UserSearchRequest;
import com.chinarewards.gwt.elt.server.awardReward.AwardRewardActionHandler;
import com.chinarewards.gwt.elt.server.awardReward.AwardRewardAddActionHandler;
import com.chinarewards.gwt.elt.server.chooseStaff.SearchStaffActionHandler;
import com.chinarewards.gwt.elt.server.detailsOfAward.DetailsOfAwardActionHandler;
import com.chinarewards.gwt.elt.server.enterprise.EnterpriseActionHandler;
import com.chinarewards.gwt.elt.server.enterprise.EnterpriseInitActionHandler;
import com.chinarewards.gwt.elt.server.login.LoginActionHandler;
import com.chinarewards.gwt.elt.server.nominate.NominateActionHandler;
import com.chinarewards.gwt.elt.server.nominate.NominateAddActionHandler;
import com.chinarewards.gwt.elt.server.rewardItem.CreateRewardsItemHandler;
import com.chinarewards.gwt.elt.server.rewardItem.SearchOrganizationHandler;
import com.chinarewards.gwt.elt.server.rewards.SearchRewardsHandler;
import com.chinarewards.gwt.elt.server.staff.HrRegisterActionHandler;
import com.chinarewards.gwt.elt.server.user.UserSearchActionHandler;

/**
 * 
 * @author cyril
 * 
 */
public class ActionModule extends ActionHandlerModule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.customware.gwt.dispatch.server.guice.ActionHandlerModule#
	 * configureHandlers()
	 */
	@Override
	protected void configureHandlers() {
		// login module
		bindHandler(LoginRequest.class, LoginActionHandler.class);

		// user module
		bindHandler(UserSearchRequest.class, UserSearchActionHandler.class);
		// staff module
		bindHandler(HrRegisterRequest.class, HrRegisterActionHandler.class);

		//Nominate module
		bindHandler(NominateInitRequest.class, NominateActionHandler.class);
		//Nominate add module
		bindHandler(NominateAddRequest.class, NominateAddActionHandler.class);
		

		bindHandler(EnterpriseRequest.class, EnterpriseActionHandler.class);
		bindHandler(EnterpriseInitRequest.class, EnterpriseInitActionHandler.class);
        bindHandler(CreateRewardsItemRequest.class,CreateRewardsItemHandler.class);
        bindHandler(SearchOrganizationRequest.class,SearchOrganizationHandler.class);

		
		//奖励列表
		bindHandler(SearchRewardsRequest.class, SearchRewardsHandler.class);
		bindHandler(SearchStaffChooseRequest.class, SearchStaffActionHandler.class);

		//颁奖
		bindHandler(AwardRewardInitRequest.class, AwardRewardActionHandler.class);

		bindHandler(AwardRewardAddRequest.class, AwardRewardAddActionHandler.class);

		//奖励详细
		bindHandler(DetailsOfAwardInitRequest.class, DetailsOfAwardActionHandler.class);
		
		
	}
}
