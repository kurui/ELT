package com.chinarewards.gwt.elt.client.dataprovider;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.rewardItem.request.SearchRewardsRequest;
import com.chinarewards.gwt.elt.client.rewardItem.request.SearchRewardsResponse;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsClient;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsCriteria;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.model.PaginationDetailClient;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class RewardsListViewAdapter extends BaseDataProvider<RewardsClient> {

	final DispatchAsync dispatch;
	RewardsCriteria criteria;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;

	public RewardsListViewAdapter(DispatchAsync dispatch,
			RewardsCriteria criteria, ErrorHandler errorHandler,
			SessionManager sessionManager) {
		this.dispatch = dispatch;
		this.criteria = criteria;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
	}

	public void fetchData(final int start, final int length) {
//		if (!GWT.isScript()) {
//			List<RewardsClient> list = new ArrayList<RewardsClient>();
//			for (int i = start; i < start + length; i++) {
//				RewardsClient item = new RewardsClient();
//				item.setId("id" + i);
//				item.setName("rewards" + i);
//				//item.setStatus(RewardsStatus.TO_BE_ISSUE);
//				list.add(item);
//			}
//
//			updateRowData(start, list);
//			updateRowCount(100, true);
//		} else {
			PaginationDetailClient pagination = new PaginationDetailClient();
			pagination.setStart(start);
			pagination.setLimit(length);
			getCriteria().setPagination(pagination);
			if (getSorting() != null) {
				getCriteria().setSorting(getSorting());
			}
			dispatch.execute(new SearchRewardsRequest(getCriteria()),
					new AsyncCallback<SearchRewardsResponse>() {
						@Override
						public void onFailure(Throwable e) {
							errorHandler.alert(e.getMessage());
						}

						@Override
						public void onSuccess(SearchRewardsResponse response) {
							updateRowData(start, response.getResult());
							updateRowCount(response.getTotal(), true);
						}

					});
//		}
	}

	public void setCriteria(RewardsCriteria criteria) {
		this.criteria = criteria;
	}

	private RewardsCriteria getCriteria() {
		if (criteria == null) {
			criteria = new RewardsCriteria();
		}

		return criteria;
	}
}
