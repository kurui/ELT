package com.chinarewards.gwt.elt.server.budget;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.model.budget.search.AskBudgetVo;
import com.chinarewards.elt.model.budget.search.DepartmentBudgetVo;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.common.PaginationDetail;
import com.chinarewards.elt.model.common.SortingDetail;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.service.budget.BudgetService;
import com.chinarewards.gwt.elt.client.budget.model.AskBudgetClientVo;
import com.chinarewards.gwt.elt.client.budget.model.DepBudgetVo;
import com.chinarewards.gwt.elt.client.budget.request.SearchAskBudgetRequest;
import com.chinarewards.gwt.elt.client.budget.request.SearchAskBudgetResponse;
import com.chinarewards.gwt.elt.client.budget.request.SearchDepBudgetRequest;
import com.chinarewards.gwt.elt.client.budget.request.SearchDepBudgetResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.chinarewards.gwt.elt.util.UserRoleTool;
import com.google.inject.Inject;

/**
 * @author lw
 * @since 2012年1月10日 16:09:07
 */
public class SearchAskBudgetHandler extends
		BaseActionHandler<SearchAskBudgetRequest, SearchAskBudgetResponse> {

	@InjectLogger
	Logger logger;

	BudgetService budgetService;
   
	@Inject
	public SearchAskBudgetHandler(BudgetService budgetService) {
		this.budgetService = budgetService;

	}

	@Override
	public SearchAskBudgetResponse execute(SearchAskBudgetRequest request,
			ExecutionContext context) throws DispatchException {

		SearchAskBudgetResponse resp = new SearchAskBudgetResponse();

		AskBudgetClientVo clientVo = request.getBudgetVo();
		AskBudgetVo serviceVo = adapter(clientVo);//从客户端转到model
		PageStore<AskBudgetVo> budgetPage = null;

		UserContext uc = new UserContext();
		uc.setCorporationId(request.getUserSession().getCorporationId());
		uc.setLoginName(request.getUserSession().getLoginName());
		uc.setUserRoles(UserRoleTool.adaptToRole(request.getUserSession().getUserRoles()));
		uc.setUserId(request.getUserSession().getToken());
	
		budgetPage = budgetService.askBudgetList(uc, serviceVo);
		List<AskBudgetClientVo> listVo = adapterToClient(budgetPage.getResultList());//从服务端转为客户端
		resp.setTotal(listVo.size());
		resp.setResult(listVo);

		return resp;
	}

	private AskBudgetVo adapter(AskBudgetClientVo criteria) {
		   AskBudgetVo vo = new AskBudgetVo();
			vo.setManageDepId(criteria.getManageDepId());
			vo.setCorpBudgetId(criteria.getCorpBudgetId());
			vo.setDepartmentId(criteria.getDepartmentId());
		     vo.setDeleted(0);//查没有删除的数据
		if (criteria.getPagination() != null) {
			PaginationDetail detail = new PaginationDetail();
			detail.setLimit(criteria.getPagination().getLimit());
			detail.setStart(criteria.getPagination().getStart());
			vo.setPaginationDetail(detail);
		}

		if (criteria.getSorting() != null) {
			SortingDetail sortingDetail = new SortingDetail();
			sortingDetail.setSort(criteria.getSorting().getSort());
			sortingDetail.setDirection(criteria.getSorting().getDirection());
			vo.setSortingDetail(sortingDetail);
		}
		return vo;
	}
	//从服务端得到的数据到客户端在列表显示的数据
		public static List<AskBudgetClientVo> adapterToClient(List<AskBudgetVo> service) {
			List<AskBudgetClientVo> resultList = new ArrayList<AskBudgetClientVo>();

			for (AskBudgetVo item : service) {
				AskBudgetClientVo client = new AskBudgetClientVo();
				client.setApprovedate(item.getApprovedate());
				client.setApproveIntegeral(item.getApproveIntegeral());
				client.setApproveuser(item.getApproveuser());
				client.setAskIntegral(item.getAskIntegral());
				client.setId(item.getId());
				client.setReason(item.getReason());
				client.setRecorddate(item.getRecorddate());
				client.setRecorduser(item.getRecorduser());
				client.setRecordname(item.getRecordname());
				client.setStatus(item.getStatus());
				client.setView(item.getView());
				client.setDepName(item.getDepName());
				client.setCorpBudget(item.getCorpBudget());
				resultList.add(client);
			}
           
			return resultList;
		}
	@Override
	public Class<SearchAskBudgetRequest> getActionType() {
		return SearchAskBudgetRequest.class;
	}

	@Override
	public void rollback(SearchAskBudgetRequest req, SearchAskBudgetResponse resp,
			ExecutionContext cxt) throws DispatchException {
	}

}
