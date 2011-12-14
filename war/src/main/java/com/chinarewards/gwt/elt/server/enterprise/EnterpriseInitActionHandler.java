package com.chinarewards.gwt.elt.server.enterprise;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.chinarewards.elt.domain.enterprise.Enterprise;
import com.chinarewards.elt.service.enterprise.IEnterpriseService;
import com.chinarewards.gwt.elt.client.enterprise.EnterpriseInitRequest;
import com.chinarewards.gwt.elt.client.enterprise.EnterpriseInitResponse;
import com.chinarewards.gwt.elt.client.enterprise.model.EnterpriseVo;
import com.chinarewards.gwt.elt.model.ClientException;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.google.inject.Inject;

public class EnterpriseInitActionHandler extends
		BaseActionHandler<EnterpriseInitRequest, EnterpriseInitResponse> {

	IEnterpriseService enterpriseService;

	@Inject
	public EnterpriseInitActionHandler(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	@Override
	public Class<EnterpriseInitRequest> getActionType() {
		return EnterpriseInitRequest.class;
	}

	@Override
	public EnterpriseInitResponse execute(EnterpriseInitRequest request, ExecutionContext context)	throws DispatchException {
		    Enterprise enterprise = enterpriseService.getEnterpriseInfo();
		if (enterprise != null) {
			   EnterpriseInitResponse resp = new EnterpriseInitResponse();
			   EnterpriseVo vo = new EnterpriseVo();
			   vo.setAddress(enterprise.getAddress());
			   vo.setCellphone(enterprise.getCellphone());
			   vo.setCorporation(enterprise.getCorporation());
			   vo.setEmail(enterprise.getEmail());
			   vo.setEnterpriseName(enterprise.getEnterpriseName());
			   vo.setFax(enterprise.getFax());
			   vo.setLinkman(enterprise.getLinkman());
			   vo.setRemark(enterprise.getRemark());
			   vo.setTell(enterprise.getTell());
		       vo.setWeb(enterprise.getWeb());
		       vo.setId(enterprise.getId());
			   resp.setEnterprise(vo);
			return resp;
		} else {
			return null;
		}
	}
	
	


	@Override
	public void rollback(EnterpriseInitRequest arg0,
			EnterpriseInitResponse arg1, ExecutionContext arg2)
			throws DispatchException {
		// TODO Auto-generated method stub
		
	}

}
