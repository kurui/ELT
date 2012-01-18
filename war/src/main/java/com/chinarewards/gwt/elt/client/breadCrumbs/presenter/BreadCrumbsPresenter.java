package com.chinarewards.gwt.elt.client.breadCrumbs.presenter;

import java.util.List;

import com.chinarewards.gwt.elt.client.breadCrumbs.model.MenuBreadVo;
import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;

public interface BreadCrumbsPresenter extends	Presenter<BreadCrumbsPresenter.BreadCrumbsDisplay> {


	public static interface BreadCrumbsDisplay extends Display {

		void setTitleText(List<MenuBreadVo> menuBreadVo);
	}
}
