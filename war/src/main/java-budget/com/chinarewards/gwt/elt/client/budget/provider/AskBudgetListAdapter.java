package com.chinarewards.gwt.elt.client.budget.provider;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.budget.model.AskBudgetClientVo;
import com.chinarewards.gwt.elt.client.budget.presenter.AskBudgetListPresenter.AskBudgetListDisplay;
import com.chinarewards.gwt.elt.client.budget.request.SearchAskBudgetRequest;
import com.chinarewards.gwt.elt.client.budget.request.SearchAskBudgetResponse;
import com.chinarewards.gwt.elt.client.dataprovider.BaseDataProvider;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.model.PaginationDetailClient;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AskBudgetListAdapter extends BaseDataProvider<AskBudgetClientVo> {

	final DispatchAsync dispatch;
	final AskBudgetClientVo criteria;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;
	final AskBudgetListDisplay display;

	public AskBudgetListAdapter(DispatchAsync dispatch, AskBudgetClientVo criteria,
			ErrorHandler errorHandler, SessionManager sessionManager, AskBudgetListDisplay display) {
		this.dispatch = dispatch;
		this.criteria = criteria;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
		this.display=display;
	}

	public void fetchData(final int start, final int length) {
//		 if (!GWT.isScript()) {
//		 List<DepBudgetVo> list = new ArrayList<DepBudgetVo>();
//		 for (int i = start; i < start + length; i++) {
//			 DepBudgetVo item = new DepBudgetVo();
//		 item.setId("id" + i);
//		 item.setDepartmentName("gift" + i);
//		 item.setBudgetIntegral(i);
//		 item.setUseIntegeral(100);
//		 list.add(item);
//		 }
//		
//		 updateRowData(start, list);
//		 updateRowCount(100, true);
//		 } else {
		PaginationDetailClient pagination = new PaginationDetailClient();
		pagination.setStart(start);
		pagination.setLimit(length);
		criteria.setPagination(pagination);
		if (getSorting() != null) {
			criteria.setSorting(getSorting());
		}
		dispatch.execute(new SearchAskBudgetRequest(criteria, sessionManager.getSession()),
				new AsyncCallback<SearchAskBudgetResponse>() {
					@Override
					public void onFailure(Throwable e) {
						errorHandler.alert("查询失败");
					}

					@Override
					public void onSuccess(SearchAskBudgetResponse response) {
						updateRowData(start, response.getResult());
						updateRowCount(response.getTotal(), true);
						
					}

				});
//	}
	 }

}
