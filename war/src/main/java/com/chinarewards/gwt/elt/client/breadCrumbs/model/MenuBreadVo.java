package com.chinarewards.gwt.elt.client.breadCrumbs.model;

import java.io.Serializable;

import com.chinarewards.gwt.elt.client.core.ui.MenuItem;

public class MenuBreadVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String menuName;
	String leftmenuName;
	MenuItem menuUrl;
	
	public String getLeftmenuName() {
		return leftmenuName;
	}
	public void setLeftmenuName(String leftmenuName) {
		this.leftmenuName = leftmenuName;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public MenuItem getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(MenuItem menuUrl) {
		this.menuUrl = menuUrl;
	}


}