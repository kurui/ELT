package com.chinarewards.gwt.elt.client.breadCrumbs.ui;

import java.util.List;

import com.chinarewards.gwt.elt.client.breadCrumbs.model.MenuBreadVo;


public interface BreadCrumbsMenu {

	void addBreadCrumbsItem(String name,String url);
	void addBreadCrumbsItemTop(String name,String url);
	void cleanBreadCrumbsItem();
	void cleanBreadCrumbsItemAll();
	List<MenuBreadVo> getBreadCrumbsItem();

}
