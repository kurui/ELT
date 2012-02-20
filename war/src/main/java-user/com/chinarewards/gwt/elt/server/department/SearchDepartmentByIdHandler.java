package com.chinarewards.gwt.elt.server.department;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.domain.org.Department;
import com.chinarewards.elt.domain.org.Staff;
import com.chinarewards.elt.service.org.DepartmentService;
import com.chinarewards.elt.service.staff.IStaffService;
import com.chinarewards.gwt.elt.client.department.model.DepartmentVo;
import com.chinarewards.gwt.elt.client.department.request.SearchDepartmentByIdRequest;
import com.chinarewards.gwt.elt.client.department.request.SearchDepartmentByIdResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;

/**
 * @author yanrui
 */
public class SearchDepartmentByIdHandler extends
		BaseActionHandler<SearchDepartmentByIdRequest, SearchDepartmentByIdResponse> {
	@InjectLogger
	Logger logger;
	DepartmentService departmentService;
	IStaffService staffService;

	@Inject
	public SearchDepartmentByIdHandler(DepartmentService departmentService,IStaffService staffService) {
		this.departmentService = departmentService;
		this.staffService=staffService;
	}

	@Override
	public SearchDepartmentByIdResponse execute(SearchDepartmentByIdRequest request,
			ExecutionContext context) throws DispatchException {
		logger.debug(" parameters:{}", request.getId());
		Department department = departmentService.findDepartmentById(request.getId());
		return new SearchDepartmentByIdResponse(adapter(department));

	}

	private DepartmentVo adapter(Department department) {
		DepartmentVo departmentVo = new DepartmentVo();
		if(department!=null){
			departmentVo.setId(department.getId());
			departmentVo.setName(department.getName());
			departmentVo.setLeaderId(department.getLeaderId());
			
			Staff staff = null;
			if (department.getLeaderId()!=null) {
				staff=staffService.findStaffById(department.getLeaderId());
			}
			if(staff!=null){				
				departmentVo.setLeaderName(staff.getName());
			}
			
//			System.out.println(department.getLeaderId()+"==================leaderId==name:"+departmentVo.getLeaderName());
			
			Department parent=department.getParent();
			if (parent!=null) {
				departmentVo.setParentId(parent.getId());
				departmentVo.setParentName(parent.getName());
			}else{
				
			}
			
//			System.out.println("===================SearchByIdHandler=="+departmentVo.getParentName());
			
//			private String superdeparmentId;
//			private String superdeparmentName;

//			private String childdeparmentIds;
//			private String childdeparmentNames;
//			private String peopleNumber;
//			private String yearintegral;
//			private String issueintegral;

		}else{
			System.err.println("SearchDepartmentById adapter()====department is null===");
		}
		
		return departmentVo;
	}

	@Override
	public Class<SearchDepartmentByIdRequest> getActionType() {
		return SearchDepartmentByIdRequest.class;
	}

	@Override
	public void rollback(SearchDepartmentByIdRequest arg0,
			SearchDepartmentByIdResponse arg1, ExecutionContext arg2)
			throws DispatchException {
	}

}
