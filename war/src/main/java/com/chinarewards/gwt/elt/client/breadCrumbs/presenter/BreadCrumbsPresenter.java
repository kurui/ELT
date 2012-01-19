package com.chinarewards.gwt.elt.client.breadCrumbs.presenter;

import java.util.List;

import com.chinarewards.gwt.elt.client.breadCrumbs.model.MenuBreadVo;
import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.event.dom.client.HasClickHandlers;

public interface BreadCrumbsPresenter extends	Presenter<BreadCrumbsPresenter.BreadCrumbsDisplay> {

	void setChildName(String name);
	void cleanChildName();
	public static interface BreadCrumbsDisplay extends Display {

		void setTitleText(List<MenuBreadVo> menuBreadVo);
		HasClickHandlers getGoHistory();
	}
}
