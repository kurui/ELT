package com.chinarewards.gwt.elt.server.budget;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.domain.budget.AskBudget;
import com.chinarewards.elt.domain.budget.DepartmentBudget;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.service.budget.BudgetService;
import com.chinarewards.gwt.elt.client.budget.model.AskBudgetClientVo;
import com.chinarewards.gwt.elt.client.budget.request.AskBudgetAddRequest;
import com.chinarewards.gwt.elt.client.budget.request.AskBudgetAddResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.chinarewards.gwt.elt.util.UserRoleTool;
import com.google.inject.Inject;

/**
 * @author lw
 * @since 2012年1月10日 16:09:07
 */
public class AddAskBudgetHandler extends
		BaseActionHandler<AskBudgetAddRequest, AskBudgetAddResponse> {

	@InjectLogger
	Logger logger;

	BudgetService budgetService;

	@Inject
	public AddAskBudgetHandler(BudgetService budgetService) {
		this.budgetService = budgetService;

	}

	@Override
	public AskBudgetAddResponse execute(AskBudgetAddRequest request,
			ExecutionContext context) throws DispatchException {

		AskBudgetAddResponse resp = new AskBudgetAddResponse();

		AskBudgetClientVo clientVo = request.getBudgetVo();
		AskBudget serviceVo = adapter(clientVo);//从客户端转到实体
		
		UserContext uc = new UserContext();
		uc.setCorporationId(request.getUserSession().getCorporationId());
		uc.setLoginName(request.getUserSession().getLoginName());
		uc.setUserRoles(UserRoleTool.adaptToRole(request.getUserSession().getUserRoles()));
		uc.setUserId(request.getUserSession().getToken());
		AskBudget ask = budgetService.saveAskBudget(uc, serviceVo);
		if(ask.getId()!=null)
			resp.setMessage("success");
		return resp;
	}

	private AskBudget adapter(AskBudgetClientVo criteria) {
		AskBudget service = new AskBudget();
		service.setReason(criteria.getReason());
		service.setDepartmentId(criteria.getDepartmentId());
		service.setAskIntegral(criteria.getAskIntegral());
		service.setId(criteria.getId());
		service.setCorpBudgetId(criteria.getCorpBudgetId());
		if(criteria.getId()!=null&&!criteria.getId().equals("")){//ID不为空说明是审批操作
		   service.setView(criteria.getView());
		   service.setApproveIntegeral(criteria.getApproveIntegeral());
		   service.setStatus(criteria.getStatus());
		}
		return service;
	}
	
	@Override
	public Class<AskBudgetAddRequest> getActionType() {
		return AskBudgetAddRequest.class;
	}

	@Override
	public void rollback(AskBudgetAddRequest req, AskBudgetAddResponse resp,
			ExecutionContext cxt) throws DispatchException {
	}

}
