package com.chinarewards.gwt.elt.client.department.request;

import java.util.List;
import net.customware.gwt.dispatch.shared.Result;
import com.chinarewards.elt.domain.org.Department;

/**
 * @author yanrui
 * @since
 */
public class SearchDepartmentByCorpIdResponse implements Result {

	private List<Department> departmentList;

	public SearchDepartmentByCorpIdResponse() {

	}

	public SearchDepartmentByCorpIdResponse(List<Department> departmentList) {
		this.departmentList = departmentList;
	}

	public List<Department> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<Department> departmentList) {
		this.departmentList = departmentList;
	}

}
