package com.chinarewards.gwt.elt.server.budget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.domain.org.Department;
import com.chinarewards.elt.model.org.search.DepartmentManageVo;
import com.chinarewards.elt.service.org.DepartmentService;
import com.chinarewards.gwt.elt.client.budget.model.DepartmentVo;
import com.chinarewards.gwt.elt.client.budget.request.InitDepartmentRequest;
import com.chinarewards.gwt.elt.client.budget.request.InitDepartmentResponse;
import com.chinarewards.gwt.elt.model.user.UserRoleVo;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;

/**
 * @author lw
 * */
public class InitDepartmentHandler extends	BaseActionHandler<InitDepartmentRequest, InitDepartmentResponse> {

	@InjectLogger
	Logger logger;
	DepartmentService departmentService;

	@Inject
	public InitDepartmentHandler(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@Override
	public Class<InitDepartmentRequest> getActionType() {
		return InitDepartmentRequest.class;
	}

	@Override
	public InitDepartmentResponse execute(InitDepartmentRequest action,
			ExecutionContext context) throws DispatchException {
		List<UserRoleVo> roleList = Arrays.asList(action.getUserSession().getUserRoles());
		InitDepartmentResponse resp = new InitDepartmentResponse();
		String type = action.getType();
		List<Department> listVo = new ArrayList<Department>();
		if (roleList.contains(UserRoleVo.CORP_ADMIN)&&type.equals("0")) {	//得到顶级部门
			Department rootDep = departmentService.getRootDepartmentOfCorporation(action.getUserSession().getCorporationId());
			listVo.add(rootDep);
		    resp.setResult(adapterToClient(listVo));//从服务端转为客户端
		} 
		if (roleList.contains(UserRoleVo.CORP_ADMIN)&&type.equals("1")) {	//得到一级部门
		    listVo = departmentService.getImmediacyDepartmentsOfCorporation(action.getUserSession().getCorporationId());
		    resp.setResult(adapterToClient(listVo));//从服务端转为客户端
		}  else if (roleList.contains(UserRoleVo.DEPT_MGR)&&type.equals("2")) {//得到主管的部门
		
			 List<Department> listManagedDep= departmentService.findDepartmentsManagedByStaffId(action.getUserSession().getStaffId());
			 resp.setResult(adapterToClient(listManagedDep));//从服务端转为客户端
		 } else if (roleList.contains(UserRoleVo.DEPT_MGR)&&type.equals("3")) {//得到主管的子部门
			
			 List<Department> listManagedDep= departmentService.findDepartmentsManagedByStaffId(action.getUserSession().getStaffId());
			 List<Department> listchildDep= new ArrayList<Department>();
			 for (Department item : listManagedDep) {
				 List<Department> sonDepList = departmentService.getImmediacyChildren(item.getId(),true);
				 for (Department son : sonDepList){
					 listchildDep.add(son);
				 }
			 }
			 resp.setResult(adapterToClient(listchildDep));//从服务端转为客户端
		 }else if (roleList.contains(UserRoleVo.DEPT_MGR)&&!type.equals("")) {//得到主管的一个部门的子部门
			
			 List<Department> sonDepList = departmentService.getImmediacyChildren(type,false);//type这里为传过来的所选主部门的ID
			 resp.setResult(adapterToClient(sonDepList));//从服务端转为客户端
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
	public void rollback(InitDepartmentRequest arg0,InitDepartmentResponse arg1, ExecutionContext arg2)
			throws DispatchException {
		// TODO Auto-generated method stub
		
	}

}
