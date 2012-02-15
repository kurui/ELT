package com.chinarewards.gwt.elt.server.department;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.domain.org.Department;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.service.org.DepartmentService;
import com.chinarewards.gwt.elt.client.department.model.DepartmentVo;
import com.chinarewards.gwt.elt.client.department.request.EditDepartmentRequest;
import com.chinarewards.gwt.elt.client.department.request.EditDepartmentResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.chinarewards.gwt.elt.util.UserRoleTool;
import com.google.inject.Inject;

/**
 * @author YanRui
 * */
public class EditDepartmentHandler extends
		BaseActionHandler<EditDepartmentRequest, EditDepartmentResponse> {

	@InjectLogger
	Logger logger;
	DepartmentService departmentService;

	@Inject
	public EditDepartmentHandler(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@Override
	public Class<EditDepartmentRequest> getActionType() {
		return EditDepartmentRequest.class;
	}

	@Override
	public EditDepartmentResponse execute(EditDepartmentRequest action,
			ExecutionContext context) throws DispatchException {
		logger.debug("AddDepartmentResponse , department:" + action.getDepartmentVo().toString());
		logger.debug("AddDepartmentResponse ,rewardId=" + action.getDepartmentVo().getId());

		DepartmentVo departmentVo = action.getDepartmentVo();

		Department department = assembleDepartment(departmentVo);

		UserContext uc = new UserContext();
		uc.setCorporationId(action.getUserSession().getCorporationId());
		uc.setLoginName(action.getUserSession().getLoginName());
		uc.setUserId(action.getUserSession().getToken());
		uc.setUserRoles(UserRoleTool.adaptToRole(action.getUserSession()
				.getUserRoles()));
		Department AdddItem = departmentService.save(uc, department);

		return new EditDepartmentResponse(AdddItem.getId());
	}

	/**
	 * Convert from DepartmentVo to GeneratorDepartmentModel.
	 */
	public static Department assembleDepartment(DepartmentVo departmentVo) {
		Department department = new Department();
		department.setId(departmentVo.getId());
		department.setName(departmentVo.getName());
		department.setLeader(departmentVo.getLeader());
		department.setPeopleNumber(departmentVo.getPeopleNumber());

//		private String leader;
//		private String superdeparmentId;
//		private String superdeparmentName;
//		private String childdeparmentIds;
//		private String childdeparmentNames;
//		private String peopleNumber;
//		private String yearintegral;
//		private String issueintegral;
		
		System.out.println("assembleDepartment(departmentVo):" + departmentVo.getLeader());



		return department;
	}

	@Override
	public void rollback(EditDepartmentRequest action, EditDepartmentResponse result,
			ExecutionContext context) throws DispatchException {

	}

}
