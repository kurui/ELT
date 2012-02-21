package com.chinarewards.gwt.elt.server.register;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.chinarewards.elt.service.org.CorporationService;
import com.chinarewards.gwt.elt.client.register.request.RegisterInitRequest;
import com.chinarewards.gwt.elt.client.register.request.RegisterInitResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.google.inject.Inject;

public class RegisterInitActionHandler extends
		BaseActionHandler<RegisterInitRequest, RegisterInitResponse> {

	CorporationService corporationService;

	@Inject
	public RegisterInitActionHandler(CorporationService corporationService) {
		this.corporationService = corporationService;
	}

	@Override
	public Class<RegisterInitRequest> getActionType() {
		return RegisterInitRequest.class;
	}

	@Override
	public RegisterInitResponse execute(RegisterInitRequest request,
			ExecutionContext context) throws DispatchException {
		
		 int sum =  corporationService.getCorp();
		 RegisterInitResponse resp = new RegisterInitResponse();
		 resp.setCount(sum);
		 return resp;
				
	}

	@Override
	public void rollback(RegisterInitRequest arg0,
			RegisterInitResponse arg1, ExecutionContext arg2)
			throws DispatchException {
		// TODO Auto-generated method stub

	}

}
