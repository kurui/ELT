package com.chinarewards.gwt.elt.server.department;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.service.org.CorporationService;
import com.chinarewards.elt.service.org.DepartmentService;
import com.chinarewards.gwt.elt.client.department.request.MergeDepartmentRequest;
import com.chinarewards.gwt.elt.client.department.request.MergeDepartmentResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.chinarewards.gwt.elt.util.UserRoleTool;
import com.google.inject.Inject;

/**
 * @author YanRui
 * */
public class MergeDepartmentHandler extends
		BaseActionHandler<MergeDepartmentRequest, MergeDepartmentResponse> {

	@InjectLogger
	Logger logger;
	DepartmentService departmentService;
	CorporationService corporationService;

	@Inject
	public MergeDepartmentHandler(DepartmentService departmentService,
			CorporationService corporationService) {
		this.departmentService = departmentService;
		this.corporationService = corporationService;
	}

	@Override
	public Class<MergeDepartmentRequest> getActionType() {
		return MergeDepartmentRequest.class;
	}

	@Override
	public MergeDepartmentResponse execute(MergeDepartmentRequest action,
			ExecutionContext context) throws DispatchException {

		String departmentIds = action.getDepartmentIds();

		UserContext uc = new UserContext();
		uc.setCorporationId(action.getUserSession().getCorporationId());
		uc.setLoginName(action.getUserSession().getLoginName());
		uc.setUserId(action.getUserSession().getToken());
		uc.setUserRoles(UserRoleTool.adaptToRole(action.getUserSession()
				.getUserRoles()));

		String message = departmentService.mergeDepartment(uc, departmentIds);

		return new MergeDepartmentResponse(message);
	}


	@Override
	public void rollback(MergeDepartmentRequest action,
			MergeDepartmentResponse result, ExecutionContext context)
			throws DispatchException {

	}

}
