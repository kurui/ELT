package com.chinarewards.gwt.elt.client.dataprovider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.rewardItem.request.SearchRewardsItemRequest;
import com.chinarewards.gwt.elt.client.rewardItem.request.SearchRewardsItemResponse;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsItemClient;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsItemCriteria;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsTypeClient;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.model.PaginationDetailClient;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * 
 * @author Cream
 * @since 2010-12-23
 */
public class RewardsItemListViewAdapter extends	BaseDataProvider<RewardsItemClient> {

	final DispatchAsync dispatch;
	RewardsItemCriteria criteria;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;

	public RewardsItemListViewAdapter(DispatchAsync dispatch,
			ErrorHandler errorHandler, SessionManager sessionManager) {
		this.dispatch = dispatch;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
	}

	@Override
	public void fetchData(final int start, final int length) {
//		if (!GWT.isScript()) {
//			List<RewardsItemClient> list = new ArrayList<RewardsItemClient>();
//			for (int i = start; i < start + length; i++) {
//				RewardsItemClient item = new RewardsItemClient();
//				item.setName("rewards" + i);
//				RewardsTypeClient type = new RewardsTypeClient();
//				type.setId(i + "");
//				type.setName("name" + i);
//				item.setType(type);
//				item.setDefinition("行为定义" + i);
//				item.setStandard("标注哦" + i);
//				item.setCreatedBy("某某某");
//				item.setCreateAt(new Date());
//				list.add(item);
//			}
//
//			updateRowData(start, list);
//			updateRowCount(100, true);
//		} else
	//	{
			PaginationDetailClient pagination = new PaginationDetailClient();
			pagination.setStart(start);
			pagination.setLimit(length);
			getCriteria().setPagination(pagination);
			if (getSorting() != null) {
				getCriteria().setSorting(getSorting());
			}
			dispatch.execute(new SearchRewardsItemRequest(getCriteria()),
					new AsyncCallback<SearchRewardsItemResponse>() {
						@Override
						public void onFailure(Throwable e) {
							errorHandler.alert(e.getMessage());
						}

						@Override
						public void onSuccess(SearchRewardsItemResponse response) {
							updateRowData(start, response.getResult());
							updateRowCount(response.getTotal(), true);
						}

					});
		//}
	}

	public void setCriteria(RewardsItemCriteria criteria) {
		this.criteria = criteria;
	}

	public RewardsItemCriteria getCriteria() {
		if (criteria == null) {
			criteria = new RewardsItemCriteria();
		}
		return criteria;
	}
}
