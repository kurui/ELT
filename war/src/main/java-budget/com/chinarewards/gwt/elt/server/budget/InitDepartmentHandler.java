package com.chinarewards.gwt.elt.server.budget;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.domain.org.Department;
import com.chinarewards.elt.service.org.DepartmentLogic;

import com.chinarewards.gwt.elt.client.budget.model.DepartmentVo;
import com.chinarewards.gwt.elt.client.budget.request.InitDepartmentRequest;
import com.chinarewards.gwt.elt.client.budget.request.InitDepartmentResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;

/**
 * @author lw
 * */
public class InitDepartmentHandler extends	BaseActionHandler<InitDepartmentRequest, InitDepartmentResponse> {

	@InjectLogger
	Logger logger;
	DepartmentLogic departmentLogic;

	@Inject
	public InitDepartmentHandler(DepartmentLogic departmentLogic) {
		this.departmentLogic = departmentLogic;
	}

	@Override
	public Class<InitDepartmentRequest> getActionType() {
		return InitDepartmentRequest.class;
	}

	@Override
	public InitDepartmentResponse execute(InitDepartmentRequest action,
			ExecutionContext context) throws DispatchException {
			
		List<Department> listVo = departmentLogic.getImmediacyDepartmentsOfCorporation(action.getUserSession().getCorporationId());
		InitDepartmentResponse resp = new InitDepartmentResponse();
		resp.setResult(adapterToClient(listVo));//从服务端转为客户端

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
