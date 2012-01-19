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
import com.chinarewards.gwt.elt.client.login.TokenValidRequest;
import com.chinarewards.gwt.elt.client.nominate.NominateAddRequest;
import com.chinarewards.gwt.elt.client.nominate.NominateInitRequest;
import com.chinarewards.gwt.elt.client.order.request.DeleteOrderRequest;
import com.chinarewards.gwt.elt.client.order.request.EditOrderRequest;
import com.chinarewards.gwt.elt.client.order.request.SearchOrderByIdRequest;
import com.chinarewards.gwt.elt.client.order.request.SearchOrderRequest;
import com.chinarewards.gwt.elt.client.order.request.UpdateOrderStatusRequest;
import com.chinarewards.gwt.elt.client.rewardItem.request.ActivationRewardsItemRequest;
import com.chinarewards.gwt.elt.client.rewardItem.request.ActivationRewardsItemStoreRequest;
import com.chinarewards.gwt.elt.client.rewardItem.request.CreateRewardsItemRequest;
import com.chinarewards.gwt.elt.client.rewardItem.request.DeleteRewardsItemRequest;
import com.chinarewards.gwt.elt.client.rewardItem.request.SearchOrganizationRequest;
import com.chinarewards.gwt.elt.client.rewardItem.request.SearchRewardsItemByIdRequest;
import com.chinarewards.gwt.elt.client.rewardItem.request.SearchRewardsItemRequest;
import com.chinarewards.gwt.elt.client.rewardItem.request.SearchRewardsItemStoreRequest;
import com.chinarewards.gwt.elt.client.rewardItem.request.SearchRewardsItemViewRequest;
import com.chinarewards.gwt.elt.client.rewardItem.request.SearchStaffRequest;
import com.chinarewards.gwt.elt.client.rewards.request.DeleteRewardsRequest;
import com.chinarewards.gwt.elt.client.rewards.request.SearchRewardsRequest;
import com.chinarewards.gwt.elt.client.staff.HrRegisterRequest;
import com.chinarewards.gwt.elt.client.user.DeleteUserRequest;
import com.chinarewards.gwt.elt.client.user.UserSearchRequest;
import com.chinarewards.gwt.elt.server.awardReward.AwardRewardActionHandler;
import com.chinarewards.gwt.elt.server.awardReward.AwardRewardAddActionHandler;
import com.chinarewards.gwt.elt.server.chooseStaff.SearchStaffActionHandler;
import com.chinarewards.gwt.elt.server.detailsOfAward.DetailsOfAwardActionHandler;
import com.chinarewards.gwt.elt.server.enterprise.EnterpriseActionHandler;
import com.chinarewards.gwt.elt.server.enterprise.EnterpriseInitActionHandler;
import com.chinarewards.gwt.elt.server.login.LoginActionHandler;
import com.chinarewards.gwt.elt.server.login.TokenValidActionHandler;
import com.chinarewards.gwt.elt.server.nominate.NominateActionHandler;
import com.chinarewards.gwt.elt.server.nominate.NominateAddActionHandler;
import com.chinarewards.gwt.elt.server.order.DeleteOrderHandler;
import com.chinarewards.gwt.elt.server.order.EditOrderHandler;
import com.chinarewards.gwt.elt.server.order.SearchOrderByIdHandler;
import com.chinarewards.gwt.elt.server.order.SearchOrderHandler;
import com.chinarewards.gwt.elt.server.order.UpdateOrderStatusHandler;
import com.chinarewards.gwt.elt.server.rewardItem.ActivationRewardsItemHandler;
import com.chinarewards.gwt.elt.server.rewardItem.ActivationRewardsItemStroeHandler;
import com.chinarewards.gwt.elt.server.rewardItem.CreateRewardsItemHandler;
import com.chinarewards.gwt.elt.server.rewardItem.DeleteRewardsItemHandler;
import com.chinarewards.gwt.elt.server.rewardItem.SearchOrganizationHandler;
import com.chinarewards.gwt.elt.server.rewardItem.SearchRewardsItemByIdHandler;
import com.chinarewards.gwt.elt.server.rewardItem.SearchRewardsItemHandler;
import com.chinarewards.gwt.elt.server.rewardItem.SearchRewardsItemStoreHandler;
import com.chinarewards.gwt.elt.server.rewardItem.SearchRewardsItemViewHandler;
import com.chinarewards.gwt.elt.server.rewardItem.SearchStaffHandler;
import com.chinarewards.gwt.elt.server.rewards.DeleteRewardsHandler;
import com.chinarewards.gwt.elt.server.rewards.SearchRewardsHandler;
import com.chinarewards.gwt.elt.server.staff.HrRegisterActionHandler;
import com.chinarewards.gwt.elt.server.user.DeleteUserActionHandler;
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
		
		//企业注册以后要改
		bindHandler(EnterpriseRequest.class, EnterpriseActionHandler.class);
		bindHandler(EnterpriseInitRequest.class, EnterpriseInitActionHandler.class);
		//奖 项
        bindHandler(CreateRewardsItemRequest.class,CreateRewardsItemHandler.class);
        //奖 项修改
        bindHandler(SearchRewardsItemByIdRequest.class,SearchRewardsItemByIdHandler.class);
        //奖项列表
        bindHandler(SearchRewardsItemRequest.class,SearchRewardsItemHandler.class);
      //奖项库列表
        bindHandler(SearchRewardsItemStoreRequest.class,SearchRewardsItemStoreHandler.class);
      //奖项详细
        bindHandler(SearchRewardsItemViewRequest.class,SearchRewardsItemViewHandler.class);
        //奖 项快速选人
        bindHandler(SearchOrganizationRequest.class,SearchOrganizationHandler.class);
        //奖 项弹出选择员工
        bindHandler(SearchStaffRequest.class,SearchStaffHandler.class);
      //删除奖项
      	bindHandler(DeleteRewardsItemRequest.class, DeleteRewardsItemHandler.class);
		
		//奖励列表
		bindHandler(SearchRewardsRequest.class, SearchRewardsHandler.class);
		bindHandler(SearchStaffChooseRequest.class, SearchStaffActionHandler.class);

		//颁奖
		bindHandler(AwardRewardInitRequest.class, AwardRewardActionHandler.class);

		bindHandler(AwardRewardAddRequest.class, AwardRewardAddActionHandler.class);

		//奖励详细
		bindHandler(DetailsOfAwardInitRequest.class, DetailsOfAwardActionHandler.class);
		
		//激活(应用)奖项
		bindHandler(ActivationRewardsItemRequest.class, ActivationRewardsItemHandler.class);
		
		//登录验证token
		bindHandler(TokenValidRequest.class, TokenValidActionHandler.class);
		
		//奖励删除
		bindHandler(DeleteRewardsRequest.class, DeleteRewardsHandler.class);
		//礼品列表
		bindHandler(SearchOrderRequest.class, SearchOrderHandler.class);
	   
		bindHandler(SearchOrderByIdRequest.class,SearchOrderByIdHandler.class);
		
		//礼品编辑
		bindHandler(EditOrderRequest.class, EditOrderHandler.class); 
		
		
		//礼品删除
		bindHandler(DeleteOrderRequest.class, DeleteOrderHandler.class); 
		//礼品修改状态
		bindHandler(UpdateOrderStatusRequest.class, UpdateOrderStatusHandler.class); 
		//用户删除(离职)
		bindHandler(DeleteUserRequest.class, DeleteUserActionHandler.class); 
		//奖项库.应用到奖项
		bindHandler(ActivationRewardsItemStoreRequest.class, ActivationRewardsItemStroeHandler.class); 
		
	}
}
