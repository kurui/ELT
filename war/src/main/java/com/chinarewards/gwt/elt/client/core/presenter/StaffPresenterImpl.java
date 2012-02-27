package com.chinarewards.gwt.elt.client.core.presenter;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.EltGinjector;
import com.chinarewards.gwt.elt.client.awardShop.plugin.AwardShopListConstants;
import com.chinarewards.gwt.elt.client.awardShop.request.SearchAwardShopRequest;
import com.chinarewards.gwt.elt.client.awardShop.request.SearchAwardShopResponse;
import com.chinarewards.gwt.elt.client.breadCrumbs.ui.BreadCrumbsMenu;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.core.PluginManager;
import com.chinarewards.gwt.elt.client.core.presenter.StaffPresenter.StaffDisplay;
import com.chinarewards.gwt.elt.client.core.request.StaffInitRequest;
import com.chinarewards.gwt.elt.client.core.request.StaffInitResponse;
import com.chinarewards.gwt.elt.client.core.ui.MenuProcessor;
import com.chinarewards.gwt.elt.client.corpBroadcast.plugin.CorpBroadcastConstants;
import com.chinarewards.gwt.elt.client.gift.model.GiftClient;
import com.chinarewards.gwt.elt.client.gift.model.GiftCriteria;
import com.chinarewards.gwt.elt.client.gift.model.GiftCriteria.GiftStatus;
import com.chinarewards.gwt.elt.client.gloryBroadcast.plugin.GloryBroadcastConstants;
import com.chinarewards.gwt.elt.client.login.LastLoginRoleRequest;
import com.chinarewards.gwt.elt.client.login.LastLoginRoleResponse;
import com.chinarewards.gwt.elt.client.login.event.LoginEvent;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.orderHistory.plugin.OrderHistoryConstants;
import com.chinarewards.gwt.elt.client.rewards.plugin.RewardsListStaffConstants;
import com.chinarewards.gwt.elt.client.smallControl.view.SmallRewardItemWindowWidget;
import com.chinarewards.gwt.elt.client.smallControl.view.SmallRewardWindowWidget;
import com.chinarewards.gwt.elt.client.smallControl.view.SmallShopWindowWidget;
import com.chinarewards.gwt.elt.client.staffHeavenIndex.plugin.StaffHeavenIndexConstants;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.model.rewards.RewardsPageClient;
import com.chinarewards.gwt.elt.model.user.UserRoleVo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Grid;
import com.google.inject.Inject;

public class StaffPresenterImpl extends BasePresenter<StaffDisplay> implements
		StaffPresenter {
	final PluginManager pluginManager;
	final SessionManager sessionManager;
	final EltGinjector injector;
	final MenuProcessor menuProcessor;
	final DispatchAsync dispatchAsync;
	final BreadCrumbsMenu breadCrumbsMenu;

	@Inject
	public StaffPresenterImpl(EventBus eventBus, StaffDisplay display,
			SessionManager sessionManager, PluginManager pluginManager,
			EltGinjector injector, MenuProcessor menuProcessor,
			DispatchAsync dispatchAsync, BreadCrumbsMenu breadCrumbsMenu) {
		super(eventBus, display);
		this.sessionManager = sessionManager;
		this.pluginManager = pluginManager;
		this.injector = injector;
		this.menuProcessor = menuProcessor;
		this.dispatchAsync = dispatchAsync;
		this.breadCrumbsMenu = breadCrumbsMenu;
	}

	public void bind() {
		List<UserRoleVo> roleslt = new ArrayList<UserRoleVo>();
		UserRoleVo[] roles = sessionManager.getSession().getUserRoles();

		if (roles.length > 0) {
			for (UserRoleVo r : roles) {
				roleslt.add(r);
			}
			if (!roleslt.contains(UserRoleVo.CORP_ADMIN)
					&& !roleslt.contains(UserRoleVo.DEPT_MGR)) {
				display.disableManagementCenter();
			}
			if (!roleslt.contains(UserRoleVo.GIFT)) {
				display.disableGiftExchange();
			}
			if (!roleslt.contains(UserRoleVo.STAFF)) {
				display.disableStaffCorner();
			}
		}
//		init();
		registerHandler(display.getlogBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new LoginEvent(LoginEvent.LoginStatus.LOGOUT));
			}
		}));
		registerHandler(display.getBtnCollection().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						Window.alert("收藏");
					}
				}));

		registerHandler(display.getManagementCenter().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						UserRoleVo role = UserRoleVo.DEPT_MGR;

						for (int i = 0; i < sessionManager.getSession()
								.getUserRoles().length; i++) {
							if (UserRoleVo.CORP_ADMIN == sessionManager
									.getSession().getUserRoles()[i]) {
								role = UserRoleVo.CORP_ADMIN;
							}
						}
						dispatchAsync.execute(new LastLoginRoleRequest(
								sessionManager.getSession().getToken(), role),
								new AsyncCallback<LastLoginRoleResponse>() {

									@Override
									public void onFailure(Throwable e) {
										// Window.alert("系统切换出错");
									}

									@Override
									public void onSuccess(
											LastLoginRoleResponse resp) {
										// 成功
										if ("success".equals(resp.getFal()))
											GWT.log("success update last login role ");

									}
								});
						Window.Location.reload();
					}
				}));
		registerHandler(display.getGiftExchange().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						dispatchAsync.execute(new LastLoginRoleRequest(
								sessionManager.getSession().getToken(),
								UserRoleVo.GIFT),
								new AsyncCallback<LastLoginRoleResponse>() {

									@Override
									public void onFailure(Throwable e) {
										// Window.alert("系统切换出错");
									}

									@Override
									public void onSuccess(
											LastLoginRoleResponse resp) {
										// 成功
										if ("success".equals(resp.getFal()))
											GWT.log("success update last login role ");

									}
								});
						Window.Location.reload();
					}
				}));


		// 查看积分
		registerHandler(display.getViewPoints().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										AwardShopListConstants.EDITOR_AWARDSHOPLIST_SEARCH,
										"EDITOR_AWARDSHOPLIST_SEARCH_DO_ID",
										null);
					}
				}));
		// 获奖历史
		registerHandler(display.getWinninghistory().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						RewardsPageClient client = new RewardsPageClient();
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										RewardsListStaffConstants.EDITOR_REWARDSLIST_STAFF_SEARCH,
										"EDITOR_REWARDSLIST_STAFF_SEARCH_DO_ID",
										client);
					}
				}));

//		// 我参与的奖项
//		registerHandler(display.getParticipationAwards().addClickHandler(
//				new ClickHandler() {
//					@Override
//					public void onClick(ClickEvent event) {
//						Platform.getInstance()
//								.getEditorRegistry()
//								.openEditor(
//										AwardShopListConstants.EDITOR_AWARDSHOPLIST_SEARCH,
//										"EDITOR_AWARDSHOPLIST_SEARCH_DO_ID",
//										null);
//					}
//				}));
//
//		// 公司其他奖项
//		registerHandler(display.getOtherAwards().addClickHandler(
//				new ClickHandler() {
//					@Override
//					public void onClick(ClickEvent event) {
//						Platform.getInstance()
//								.getEditorRegistry()
//								.openEditor(
//										AwardShopListConstants.EDITOR_AWARDSHOPLIST_SEARCH,
//										"EDITOR_AWARDSHOPLIST_SEARCH_DO_ID",
//										null);
//					}
//				}));

//		registerHandler(display.getExchangeHistory().addClickHandler(
//				new ClickHandler() {
//					@Override
//					public void onClick(ClickEvent event) {
//						Platform.getInstance()
//								.getEditorRegistry()
//								.openEditor(
//										OrderHistoryConstants.EDITOR_ORDERHISTORY_SEARCH,
//										"EDITOR_ORDERHISTORY_SEARCH_DO_ID",
//										null);
//					}
//				}));
//		// 员工首页
//		registerHandler(display.getStaffHeavenIndex().addClickHandler(
//				new ClickHandler() {
//					@Override
//					public void onClick(ClickEvent event) {
//						Platform.getInstance()
//								.getEditorRegistry()
//								.openEditor(
//										StaffHeavenIndexConstants.EDITOR_STAFFHEAVENINDEX_SEARCH,
//										"EDITOR_STAFFHEAVENINDEX_SEARCH_DO_ID",
//										null);
//					}
//				}));

//		registerHandler(display.getAwardShop().addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				Platform.getInstance()
//				.getEditorRegistry()
//				.openEditor(
//						AwardShopListConstants.EDITOR_AWARDSHOPLIST_SEARCH,
//						"EDITOR_AWARDSHOPLIST_SEARCH_DO_ID", null);
//			}
//		}));
//
//		//公司广播
//		registerHandler(display.getCorpBroadcastAnchor().addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				Platform.getInstance()
//				.getEditorRegistry()
//				.openEditor(
//						CorpBroadcastConstants.EDITOR_CORPBROADCAST_SEARCH,
//						"EDITOR_CORPBROADCAST_SEARCH_DO_ID", null);
//			}
//		}));
//		//光荣榜
//		registerHandler(display.getGloryAnchor().addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				Platform.getInstance()
//				.getEditorRegistry()
//				.openEditor(
//						GloryBroadcastConstants.EDITOR_GLORYBROADCAST_SEARCH,
//						"EDITOR_GLORYBROADCAST_SEARCH_DO_ID", null);
//			}
//		}));


	}

	void init()
	{
		//加载员工信息....
		dispatchAsync.execute(new StaffInitRequest(sessionManager.getSession().getStaffId()),
				new AsyncCallback<StaffInitResponse>() {

					@Override
					public void onFailure(Throwable e) {
				
					}

					@Override
					public void onSuccess(StaffInitResponse resp) {
						display.setStaffName(resp.getName());
						display.setDeptName(resp.getDeptName());
						display.setIntegral(resp.getIntegral());
						display.setPhoto(resp.getPhoto());
						display.setStation(resp.getStation());
					}
				});
		//加载小橱窗控件
	
		GiftCriteria criteria = new GiftCriteria();
		criteria.setStatus(GiftStatus.SHELVES);
		// 查询参数....待添加
		dispatchAsync.execute(new SearchAwardShopRequest(criteria, sessionManager
				.getSession().getCorporationId(), sessionManager.getSession()
				.getUserRoles(), sessionManager.getSession().getToken()),
				new AsyncCallback<SearchAwardShopResponse>() {
					@Override
					public void onFailure(Throwable e) {
						Window.alert(e.getMessage());
					}

					@Override
					public void onSuccess(SearchAwardShopResponse response) {

						List<GiftClient> giftList = response.getResult();
						int index = 0;
						Grid grid = new Grid(3, 2);

						// Add images to the grid
						int numRows = grid.getRowCount();
						int numColumns = grid.getColumnCount();
						for (int row = 0; row < numRows; row++) {
							for (int col = 0; col < numColumns; col++) {
								if (index < giftList.size()) {
									GiftClient clint = giftList.get(index);
									grid.setWidget(
											row,
											col,
											new SmallShopWindowWidget(clint.getId(),clint.getName(),clint.getIntegral()+"",clint.getPhoto()));
									index++;
								} else {
									grid.setWidget(row, col,
											new SmallShopWindowWidget(null,"","0",null));
								}
							}
						}

						// Return the panel
						grid.ensureDebugId("cwGrid");

						display.getSmaillShopWindow().clear();
						display.getSmaillShopWindow().add(grid);

					}

				});
		display.getMore().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Platform.getInstance()
				.getEditorRegistry()
				.openEditor(
						AwardShopListConstants.EDITOR_AWARDSHOPLIST_SEARCH,
						"EDITOR_AWARDSHOPLIST_SEARCH_DO_ID", null);
				
			}
		});
		
		//奖励小控件加载
		
		Grid grid = new Grid(5, 1);

		// Add images to the grid
		int numRows = grid.getRowCount();
		int numColumns = grid.getColumnCount();
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++) {
				
					grid.setWidget(
							row,
							col,
							new SmallRewardWindowWidget("11","test"));
			
				
			}
		}

		// Return the panel
		grid.ensureDebugId("cwGridreward");

		display.getRewardPanel().clear();
		display.getRewardPanel().add(grid);
		
		//奖项小控件加载
		Grid grid2 = new Grid(5, 1);

		// Add images to the grid
		int numRows2 = grid2.getRowCount();
		int numColumns2 = grid2.getColumnCount();
		for (int row = 0; row < numRows2; row++) {
			for (int col = 0; col < numColumns2; col++) {
				
					grid2.setWidget(
							row,
							col,
							new SmallRewardItemWindowWidget("11","test","50",null));
			
				
			}
		}

		// Return the panel
		grid.ensureDebugId("cwGridreward");

		display.getRewardItemPanel().clear();
		display.getRewardItemPanel().add(grid2);
		
	}
	public StaffDisplay getDisplay() {
		return display;
	}

}
