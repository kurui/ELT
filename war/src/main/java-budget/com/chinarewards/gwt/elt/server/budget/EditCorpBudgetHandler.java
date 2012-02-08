package com.chinarewards.gwt.elt.server.budget;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.domain.budget.CorpBudget;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.gwt.elt.client.budget.model.CorpBudgetVo;
import com.chinarewards.gwt.elt.client.budget.request.EditCorpBudgetRequest;
import com.chinarewards.gwt.elt.client.budget.request.EditCorpBudgetResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.chinarewards.gwt.elt.util.UserRoleTool;

/**
 * @author YanRui
 * */
public class EditCorpBudgetHandler extends
		BaseActionHandler<EditCorpBudgetRequest, EditCorpBudgetResponse> {

	@InjectLogger
	Logger logger;
//	CorpBudgetService corpBudgetService;

//	@Inject
//	public EditCorpBudgetHandler(CorpBudgetService corpBudgetService) {
//		this.corpBudgetService = corpBudgetService;
//	}

	@Override
	public Class<EditCorpBudgetRequest> getActionType() {
		return EditCorpBudgetRequest.class;
	}

	@Override
	public EditCorpBudgetResponse execute(EditCorpBudgetRequest action,
			ExecutionContext context) throws DispatchException {
		logger.debug("AddCorpBudgetResponse , corpBudget:" + action.getCorpBudgetVo().toString());
		logger.debug("AddCorpBudgetResponse ,rewardId=" + action.getCorpBudgetVo().getId());

		CorpBudgetVo corpBudgetVo = action.getCorpBudgetVo();

		CorpBudget corpBudget = assembleCorpBudget(corpBudgetVo);

		UserContext uc = new UserContext();
		uc.setCorporationId(action.getUserSession().getCorporationId());
		uc.setLoginName(action.getUserSession().getLoginName());
		uc.setUserId(action.getUserSession().getToken());
		uc.setUserRoles(UserRoleTool.adaptToRole(action.getUserSession()
				.getUserRoles()));
//		CorpBudget AdddItem = corpBudgetService.save(uc, corpBudget);

//		return new EditCorpBudgetResponse(AdddItem.getId());
		return null;
	}

	/**
	 * Convert from CorpBudgetVo to GeneratorCorpBudgetModel.
	 */
	public static CorpBudget assembleCorpBudget(CorpBudgetVo corpBudgetVo) {
		CorpBudget corpBudget = new CorpBudget();
		corpBudget.setId(corpBudgetVo.getId());
//		corpBudget.setName(corpBudgetVo.getName());



		return corpBudget;
	}

	@Override
	public void rollback(EditCorpBudgetRequest action, EditCorpBudgetResponse result,
			ExecutionContext context) throws DispatchException {

	}

}
