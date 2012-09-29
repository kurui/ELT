package com.chinarewards.gwt.elt.server.budget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.domain.org.Department;
import com.chinarewards.elt.service.org.DepartmentService;
import com.chinarewards.gwt.elt.client.budget.model.DepartmentVo;
import com.chinarewards.gwt.elt.client.budget.request.InitManageDepRequest;
import com.chinarewards.gwt.elt.client.budget.request.InitManageDepResponse;
import com.chinarewards.gwt.elt.model.user.UserRoleVo;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;

/**
 * @author lw
 * */
public class InitManageDepHandler extends	BaseActionHandler<InitManageDepRequest, InitManageDepResponse> {

	@InjectLogger
	Logger logger;
	DepartmentService departmentService;

	@Inject
	public InitManageDepHandler(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@Override
	public Class<InitManageDepRequest> getActionType() {
		return InitManageDepRequest.class;
	}

	@Override
	public InitManageDepResponse execute(InitManageDepRequest action,
			ExecutionContext context) throws DispatchException {
		List<UserRoleVo> roleList = Arrays.asList(action.getUserSession().getUserRoles());
		InitManageDepResponse resp = new InitManageDepResponse();
		
	   if (roleList.contains(UserRoleVo.DEPT_MGR)) {//得到主管的子部门
			// listVo = departmentService.getImmediacyChildren(action.getUserSession().getDepartmentId());
			 List<Department> listManagedDep= departmentService.findDepartmentsManagedByStaffId(action.getUserSession().getStaffId());
			 
			 resp.setResult(adapterToClient(listManagedDep));//从服务端转为客户端
		 }
		return resp;
	   }
	
        //从服务端得到的数据到客户端在列表显示的数据
		public static List<DepartmentVo> adapterToClient(List<Department> service) {
			List<DepartmentVo> resultList = new ArrayList<DepartmentVo>();

			for (Department item : service) {
				DepartmentVo client = new DepartmentVo();
				client.setId(item.getId());
				client.setDepartmentName(item.getName());
				resultList.add(client);
			}

			return resultList;
		}	

	@Override
	public void rollback(InitManageDepRequest arg0,InitManageDepResponse arg1, ExecutionContext arg2)
			throws DispatchException {
		// TODO Auto-generated method stub
		
	}

}
