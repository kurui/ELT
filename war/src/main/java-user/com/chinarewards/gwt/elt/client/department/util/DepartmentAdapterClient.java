package com.chinarewards.gwt.elt.client.department.util;

import java.util.ArrayList;
import java.util.List;

import com.chinarewards.gwt.elt.client.department.model.DepartmentVo;
import com.chinarewards.gwt.elt.client.department.presenter.DepartmentPresenter.DepartmentDisplay;
import com.chinarewards.gwt.elt.client.rewards.model.OrganicationClient;

/**
 * @author yanrui
 * */
public class DepartmentAdapterClient {
	/**
	 * 封装表单属性
	 * */
	public static DepartmentVo adapterDisplay(DepartmentDisplay display) {
		DepartmentVo departmentVo = new DepartmentVo();

		departmentVo.setId(display.getDepartmentId().getValue());
		departmentVo.setName(display.getDepartmentName().getValue());

		departmentVo.setParentId(display.getParentId().getValue());

		List<OrganicationClient> leaderList= display.getLeaderArea().getItemList();
		List<String> leaderIds=new ArrayList<String>();
		for (int i = 0; i < leaderList.size(); i++) {
			leaderIds.add(leaderList.get(i).getId());
		}
		departmentVo.setLeaderIds(leaderIds);
		
		//之前的leader
		List<OrganicationClient> preLeaderList= display.getPreLeaderArea().getItemList();
		List<String> preLeaderIds=new ArrayList<String>();
		for (int i = 0; i < preLeaderList.size(); i++) {
			preLeaderIds.add(preLeaderList.get(i).getId());
		}
		departmentVo.setPreLeaderIds(preLeaderIds);

		return departmentVo;
	}
}
