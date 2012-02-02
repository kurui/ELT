package com.chinarewards.gwt.elt.client.core.presenter;

import com.chinarewards.gwt.elt.client.EltGinjector;
import com.chinarewards.gwt.elt.client.core.PluginManager;
import com.chinarewards.gwt.elt.client.core.presenter.DockPresenter.DockDisplay;
import com.chinarewards.gwt.elt.client.core.ui.MenuProcessor;
import com.chinarewards.gwt.elt.client.core.ui.event.MenuClickEvent;
import com.chinarewards.gwt.elt.client.gift.plugin.GiftListConstants;
import com.chinarewards.gwt.elt.client.login.event.LoginEvent;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.rewardItem.plugin.RewardsItemConstants;
import com.chinarewards.gwt.elt.client.rewards.plugin.RewardsListConstants;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.user.plugin.UserConstants;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;

public class DockPresenterImpl extends BasePresenter<DockDisplay> implements
		DockPresenter {
	final PluginManager pluginManager;
	final SessionManager sessionManager;
	final EltGinjector injector;
	final MenuProcessor menuProcessor;

	@Inject
	public DockPresenterImpl(EventBus eventBus, DockDisplay display,
			SessionManager sessionManager, PluginManager pluginManager,
			EltGinjector injector, MenuProcessor menuProcessor) {
		super(eventBus, display);
		this.sessionManager = sessionManager;
		this.pluginManager = pluginManager;
		this.injector = injector;
		this.menuProcessor = menuProcessor;
	}

	public void bind() {
		registerHandler(display.getlogBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new LoginEvent(LoginEvent.LoginStatus.LOGOUT));
			}
		}));
		registerHandler(display.getBtnCollection().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("收藏");
			}
		}));
		registerHandler(display.getBtnEmail().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						display.setMenuTitle("收件箱");
						display.setMenu(null);
					}
				}));
		registerHandler(display.getBtnGb().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				display.setMenuTitle("广播");
				display.setMenu(null);
			}
		}));
		registerHandler(display.getBtnRewardItem().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						display.setMenuTitle("奖项");
						menuProcessor.initrender(display.getMenu(),
								"RewardItem");
						eventBus.fireEvent(new MenuClickEvent(
								menuProcessor
										.getMenuItem(RewardsItemConstants.MENU_REWARDSITEM_List)));
					}
				}));
		registerHandler(display.getBtnReward().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						display.setMenuTitle("应用奖项");
						menuProcessor.initrender(display.getMenu(), "Reward");
						eventBus.fireEvent(new MenuClickEvent(
								menuProcessor
										.getMenuItem(RewardsListConstants.MENU_REWARDSLIST_SEARCH)));
					}
				}));
		registerHandler(display.getBtnStaff().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						display.setMenuTitle("员工数据");
						menuProcessor.initrender(display.getMenu(), "Staff");
						eventBus.fireEvent(new MenuClickEvent(menuProcessor
								.getMenuItem(UserConstants.MENU_USER_SEARCH)));
					}
				}));
		registerHandler(display.getBtnSetting().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						display.setMenuTitle("设置");
						menuProcessor.initrender(display.getMenu(), "Setting");
						eventBus.fireEvent(new MenuClickEvent(menuProcessor
								.getMenuItem("sample")));
					}
				}));
		registerHandler(display.getBtnGift().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						display.setMenuTitle("兑换管理");
						menuProcessor.initrender(display.getMenu(), "Gift");
						eventBus.fireEvent(new MenuClickEvent(
								menuProcessor
										.getMenuItem(GiftListConstants.MENU_GIFTLIST_SEARCH)));
					}
				}));
		registerHandler(display.getManagementCenter().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						Window.alert("管理中心");
					}
				}));
		registerHandler(display.getGiftExchange().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						Window.alert("礼品兑换");
					}
				}));
		registerHandler(display.getStaffCorner().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						Window.alert("员工天地");
					}
				}));


	}

	public DockDisplay getDisplay() {
		return display;
	}

}
