package com.chinarewards.gwt.elt.client.breadCrumbs.presenter;

import java.util.ArrayList;
import java.util.List;

import com.chinarewards.gwt.elt.client.breadCrumbs.model.MenuBreadVo;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.google.inject.Inject;

public class BreadCrumbsPresenterImpl extends
		BasePresenter<BreadCrumbsPresenter.BreadCrumbsDisplay>
		implements BreadCrumbsPresenter {



	@Inject
	public BreadCrumbsPresenterImpl(EventBus eventBus,
			BreadCrumbsDisplay display) {
		super(eventBus, display);

	}

	public void bind() {
		List<MenuBreadVo> list=new ArrayList<MenuBreadVo>();
		MenuBreadVo menuBreadVo1=new MenuBreadVo();
		menuBreadVo1.setMenuName("应用奖项");
		menuBreadVo1.setMenuUrl("yy");
		MenuBreadVo menuBreadVo2=new MenuBreadVo();
		menuBreadVo2.setMenuName("应用奖项列表");
		list.add(menuBreadVo1);
		list.add(menuBreadVo2);
		
		display.setTitleText(list);
	}

	
}
