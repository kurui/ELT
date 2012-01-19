package com.chinarewards.gwt.elt.client.breadCrumbs.ui.impl;

import java.util.ArrayList;
import java.util.List;

import com.chinarewards.gwt.elt.client.breadCrumbs.model.MenuBreadVo;
import com.chinarewards.gwt.elt.client.breadCrumbs.ui.BreadCrumbsMenu;

public class BreadCrumbsMenuImpl implements BreadCrumbsMenu {
	
	List<List<MenuBreadVo>> allList = new ArrayList<List<MenuBreadVo>>();
	List<MenuBreadVo> toplist = new ArrayList<MenuBreadVo>();
	List<MenuBreadVo> list = new ArrayList<MenuBreadVo>();

	@Override
	public void addBreadCrumbsItem(String name,String url) {
		MenuBreadVo menuBreadVo = new MenuBreadVo();
		menuBreadVo.setMenuName(name);
		menuBreadVo.setMenuUrl(url);
		list.add(menuBreadVo);

	}
	@Override
	public void addBreadCrumbsItemTop(String name,String url) {
		MenuBreadVo menuBreadVo = new MenuBreadVo();
		menuBreadVo.setMenuName(name);
		menuBreadVo.setMenuUrl(url);
		toplist.add(menuBreadVo);

	}
	@Override
	public List<MenuBreadVo> getBreadCrumbsItem() {
		if(list.size()<=1)
		{
			list.addAll(0, toplist);
		}
		List<MenuBreadVo> newList=new ArrayList<MenuBreadVo>();
		for (MenuBreadVo vo:list) {
			newList.add(vo);
		}
		allList.add(newList);
		return list;
		
	}

	@Override
	public void cleanBreadCrumbsItem() {
		list.clear();
		
	}
	@Override
	public void cleanBreadCrumbsItemTop() {
		toplist.clear();
		
	}
	@Override
	public void cleanBreadCrumbsItemAll() {
		toplist.clear();
		list.clear();
		allList.clear();
		
	}
	@Override
	public List<MenuBreadVo> getHistoryBreadCrumbsItem() {
		List<MenuBreadVo> volist=null;
		if(allList.size()>1)
		{
			volist=allList.get(allList.size()-2);
			
		}
		if(allList.size()>1)
		{
			allList.remove(allList.size()-2);
		}
		return volist;
	}
}
