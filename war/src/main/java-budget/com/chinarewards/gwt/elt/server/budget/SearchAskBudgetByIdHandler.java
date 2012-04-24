package com.chinarewards.gwt.elt.server.budget;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.domain.budget.AskBudget;
import com.chinarewards.elt.domain.budget.CorpBudget;
import com.chinarewards.elt.domain.org.Department;
import com.chinarewards.elt.service.budget.BudgetService;
import com.chinarewards.elt.service.org.DepartmentService;
import com.chinarewards.gwt.elt.client.budget.model.AskBudgetClientVo;
import com.chinarewards.gwt.elt.client.budget.request.SearchAskBudgetByIdRequest;
import com.chinarewards.gwt.elt.client.budget.request.SearchAskBudgetByIdResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;

/**
 * @author yanrui
 */
public class SearchAskBudgetByIdHandler
		extends
		BaseActionHandler<SearchAskBudgetByIdRequest, SearchAskBudgetByIdResponse> {
	@InjectLogger
	Logger logger;

	BudgetService budgetService;
	DepartmentService departService;
	@Inject
	public SearchAskBudgetByIdHandler(BudgetService budgetService,DepartmentService departService) {
		this.budgetService = budgetService;
		this.departService=departService;
	}

	@Override
	public SearchAskBudgetByIdResponse execute(
			SearchAskBudgetByIdRequest request, ExecutionContext context)
			throws DispatchException {
		
		AskBudget askBudget = budgetService.findAskBudgetById(request.getId());
		return new SearchAskBudgetByIdResponse(adapter(askBudget));

	}

	private AskBudgetClientVo adapter(AskBudget askBudget) {
		AskBudgetClientVo askBudgetVo = new AskBudgetClientVo();
		if(askBudget!=null){
			askBudgetVo.setApprovedate(askBudget.getApprovedate());
			askBudgetVo.setApproveIntegeral(askBudget.getApproveIntegeral());
			if(askBudget.getApproveuser()!=null)
			    askBudgetVo.setApproveuser(askBudget.getApproveuser().getName());
			else
				askBudgetVo.setApproveuser("");
			askBudgetVo.setAskIntegral(askBudget.getAskIntegral());
			askBudgetVo.setId(askBudget.getId());
			askBudgetVo.setReason(askBudget.getReason());
			askBudgetVo.setRecorddate(askBudget.getRecorddate());
			askBudgetVo.setRecorduser(askBudget.getRecorduser().getName());
			askBudgetVo.setStatus(askBudget.getStatus());
			askBudgetVo.setView(askBudget.getView());
			
			Department department = departService.findDepartmentById(askBudget.getDepartmentId());
			askBudgetVo.setDepName(department.getName());
			askBudgetVo.setDepartmentId(askBudget.getDepartmentId());
			CorpBudget corpBudget =budgetService.findCorpBudgetById(askBudget.getCorpBudgetId());
			 askBudgetVo.setCorpBudget(corpBudget.getBudgetTitle());
			 askBudgetVo.setCorpBudgetId(askBudget.getCorpBudgetId());
			
		}else{
			askBudgetVo.setId("");
		}		

		return askBudgetVo;
	}

	@Override
	public Class<SearchAskBudgetByIdRequest> getActionType() {
		return SearchAskBudgetByIdRequest.class;
	}

	@Override
	public void rollback(SearchAskBudgetByIdRequest arg0,
			SearchAskBudgetByIdResponse arg1, ExecutionContext arg2)
			throws DispatchException {
	}

}
