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

		departmentVo.setId(display.getDepartmentId().getValue());
		departmentVo.setName(display.getDepartmentName().getValue());

		departmentVo.setParentId(display.getParentId().getValue());

		departmentVo.setLeaderId(display.getLeaderId().getValue());

		// private String superdeparmentId;
		// private String superdeparmentName;
		// private String childdeparmentIds;
		// private String childdeparmentNames;
		// private String peopleNumber;
		// private String yearintegral;
		// private String issueintegral;

		return departmentVo;
	}
}
