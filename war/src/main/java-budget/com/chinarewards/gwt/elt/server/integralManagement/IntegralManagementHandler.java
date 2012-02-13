package com.chinarewards.gwt.elt.server.integralManagement;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.gwt.elt.client.integralManagement.model.Category;
import com.chinarewards.gwt.elt.client.integralManagement.request.IntegralManagementRequest;
import com.chinarewards.gwt.elt.client.integralManagement.request.IntegralManagementResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;

/**
 * @author nicho
 * */
public class IntegralManagementHandler extends
		BaseActionHandler<IntegralManagementRequest, IntegralManagementResponse> {

	@InjectLogger
	Logger logger;



	@Override
	public IntegralManagementResponse execute(IntegralManagementRequest action,
			ExecutionContext context) throws DispatchException {
		List<Category> rs=new ArrayList<Category>();
		for (int i = 0; i < 5; i++) {
			Category c=new Category("部门名称"+i, "积分", "积分", "部门ID");
			rs.add(c);
		}
		
		return new IntegralManagementResponse(rs);
	
	}
	
	
	
	@Override
	public Class<IntegralManagementRequest> getActionType() {
		return IntegralManagementRequest.class;
	}

	@Override
	public void rollback(IntegralManagementRequest arg0,
			IntegralManagementResponse arg1, ExecutionContext arg2)
			throws DispatchException {
		
	}

	
}
