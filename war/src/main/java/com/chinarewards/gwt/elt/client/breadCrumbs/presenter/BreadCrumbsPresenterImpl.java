package com.chinarewards.gwt.elt.client.breadCrumbs.presenter;

import com.chinarewards.gwt.elt.client.breadCrumbs.ui.BreadCrumbsMenu;
import com.chinarewards.gwt.elt.client.core.ui.MenuProcessor;
import com.chinarewards.gwt.elt.client.core.ui.event.MenuClickEvent;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.rewards.plugin.RewardsListConstants;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;

public class BreadCrumbsPresenterImpl extends
		BasePresenter<BreadCrumbsPresenter.BreadCrumbsDisplay> implements
		BreadCrumbsPresenter {
	final MenuProcessor menuProcessor;
	final BreadCrumbsMenu breadCrumbsMenu;

	@Inject
	public BreadCrumbsPresenterImpl(EventBus eventBus,
			BreadCrumbsDisplay display, MenuProcessor menuProcessor,
			BreadCrumbsMenu breadCrumbsMenu) {
		super(eventBus, display);
		this.menuProcessor = menuProcessor;
		this.breadCrumbsMenu = breadCrumbsMenu;
	}

	public void bind() {
		registerHandler(display.getGoHistory().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {

						eventBus.fireEvent(new MenuClickEvent(
								menuProcessor
										.getMenuItem(RewardsListConstants.MENU_REWARDSLIST_SEARCH)));
					}
				}));

		// MenuBreadVo menuBreadVo1 = new MenuBreadVo();
		// menuBreadVo1.setMenuName("应用奖项");
		// menuBreadVo1.setMenuUrl("yy");
		// MenuBreadVo menuBreadVo2 = new MenuBreadVo();
		// menuBreadVo2.setMenuName("应用奖项列表");
		// list.add(menuBreadVo1);
		// list.add(menuBreadVo2);

		 display.setTitleText(breadCrumbsMenu.getBreadCrumbsItem());

	}

}
