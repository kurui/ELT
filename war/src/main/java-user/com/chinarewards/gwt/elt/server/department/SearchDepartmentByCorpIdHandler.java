package com.chinarewards.gwt.elt.server.department;

import java.util.List;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.domain.org.Department;
import com.chinarewards.elt.service.org.DepartmentService;
import com.chinarewards.gwt.elt.client.department.request.SearchDepartmentByCorpIdRequest;
import com.chinarewards.gwt.elt.client.department.request.SearchDepartmentByCorpIdResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;

/**
 * @author yanrui
 */
public class SearchDepartmentByCorpIdHandler
		extends
		BaseActionHandler<SearchDepartmentByCorpIdRequest, SearchDepartmentByCorpIdResponse> {
	@InjectLogger
	Logger logger;
	DepartmentService departmentService;

	@Inject
	public SearchDepartmentByCorpIdHandler(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@Override
	public SearchDepartmentByCorpIdResponse execute(
			SearchDepartmentByCorpIdRequest request, ExecutionContext context)
			throws DispatchException {
		logger.debug(" parameters:{}", request.getCorporationId());
		List<Department> departmentList = departmentService
				.getWholeDepartmentsOfCorporation(request.getCorporationId());
		SearchDepartmentByCorpIdResponse response = new SearchDepartmentByCorpIdResponse();
		response.setDepartmentList(departmentList);
		return response;

	}

	@Override
	public Class<SearchDepartmentByCorpIdRequest> getActionType() {
		return SearchDepartmentByCorpIdRequest.class;
	}

	@Override
	public void rollback(SearchDepartmentByCorpIdRequest arg0,
			SearchDepartmentByCorpIdResponse arg1, ExecutionContext arg2)
			throws DispatchException {
	}

}
