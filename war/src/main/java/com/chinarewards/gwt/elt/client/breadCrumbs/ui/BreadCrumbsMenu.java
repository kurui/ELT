package com.chinarewards.gwt.elt.client.breadCrumbs.ui;

import java.util.List;

import com.chinarewards.gwt.elt.client.breadCrumbs.model.MenuBreadVo;
import com.chinarewards.gwt.elt.client.core.ui.MenuItem;


public interface BreadCrumbsMenu {

	void addBreadCrumbsItem(String name,MenuItem menuItem);
	void addBreadCrumbsItemTop(String name,MenuItem menuItem,String leftmenuName);
	void cleanBreadCrumbsItem();
	void cleanBreadCrumbsItemTop();
	void cleanBreadCrumbsItemAll();
	void cleanChildName();
	List<MenuBreadVo> getBreadCrumbsItem();
	List<MenuBreadVo> getHistoryBreadCrumbsItem();
	void setChildName(String name);

}
