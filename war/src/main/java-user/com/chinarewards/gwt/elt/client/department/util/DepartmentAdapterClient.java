package com.chinarewards.gwt.elt.client.department.util;

import com.chinarewards.gwt.elt.client.department.model.DepartmentVo;
import com.chinarewards.gwt.elt.client.department.presenter.DepartmentPresenter.DepartmentDisplay;

/**
 * @author yanrui
 * */
public class DepartmentAdapterClient {
	/**
	 * 封装表单属性
	 * */
	public static DepartmentVo adapterDisplay(DepartmentDisplay display) {
		DepartmentVo departmentVo = new DepartmentVo();

		// // 基本信息
//		departmentVo.setDepartmentName(display.getDepartmentName().getValue().trim());

		// System.out.println("=======adapterDisplay:" +
		// departmentVo.getSource());

		return departmentVo;
	}
}
