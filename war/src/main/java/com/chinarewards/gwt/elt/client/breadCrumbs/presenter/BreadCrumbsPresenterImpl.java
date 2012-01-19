package com.chinarewards.gwt.elt.client.breadCrumbs.presenter;

import java.util.List;

import com.chinarewards.gwt.elt.client.breadCrumbs.model.MenuBreadVo;
import com.chinarewards.gwt.elt.client.breadCrumbs.ui.BreadCrumbsMenu;
import com.chinarewards.gwt.elt.client.core.ui.MenuProcessor;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.win.Win;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;

public class BreadCrumbsPresenterImpl extends
		BasePresenter<BreadCrumbsPresenter.BreadCrumbsDisplay> implements
		BreadCrumbsPresenter {
	final MenuProcessor menuProcessor;
	final BreadCrumbsMenu breadCrumbsMenu;
	final Win win;

	@Inject
	public BreadCrumbsPresenterImpl(EventBus eventBus,
			BreadCrumbsDisplay display, MenuProcessor menuProcessor,
			BreadCrumbsMenu breadCrumbsMenu,Win win) {
		super(eventBus, display);
		this.menuProcessor = menuProcessor;
		this.breadCrumbsMenu = breadCrumbsMenu;
		this.win=win;
	}

	public void bind() {
		registerHandler(display.getGoHistory().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						List<MenuBreadVo> listvo=breadCrumbsMenu.getHistoryBreadCrumbsItem();
						if(listvo!=null)
						{
							display.setTitleText(listvo);
						}
						else
						{
							win.alert("无上一页");
						}
//						eventBus.fireEvent(new MenuClickEvent(
//								menuProcessor
//										.getMenuItem(RewardsListConstants.MENU_REWARDSLIST_SEARCH)));
					}
				}));

		 display.setTitleText(breadCrumbsMenu.getBreadCrumbsItem());

	}

}
