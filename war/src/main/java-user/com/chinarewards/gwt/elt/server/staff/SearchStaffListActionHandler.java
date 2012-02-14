package com.chinarewards.gwt.elt.server.staff;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.service.staff.IStaffService;
import com.chinarewards.gwt.elt.client.staffList.request.SearchStaffListRequest;
import com.chinarewards.gwt.elt.client.staffList.request.SearchStaffListResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;

/**
 * The handler to correspond the SearchStaffListRequest.
 * 
 * @author nicho
 * @since 2011年12月7日 09:41:42
 */
public class SearchStaffListActionHandler extends
		BaseActionHandler<SearchStaffListRequest, SearchStaffListResponse> {

	@InjectLogger
	Logger logger;

	IStaffService staffService;


	@Inject
	public SearchStaffListActionHandler(IStaffService staffService) {
		this.staffService = staffService;
	}

	@Override
	public SearchStaffListResponse execute(SearchStaffListRequest request,
			ExecutionContext response) throws DispatchException {

		SearchStaffListResponse hrResponse = new SearchStaffListResponse();

		return hrResponse;
	}

	@Override
	public Class<SearchStaffListRequest> getActionType() {
		return SearchStaffListRequest.class;
	}

	@Override
	public void rollback(SearchStaffListRequest request,
			SearchStaffListResponse response, ExecutionContext context)
			throws DispatchException {
	}

}
