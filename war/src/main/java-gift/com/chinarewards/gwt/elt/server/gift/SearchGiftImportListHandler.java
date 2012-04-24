package com.chinarewards.gwt.elt.server.gift;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.domain.gift.ImportGiftRaw;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.common.PaginationDetail;
import com.chinarewards.elt.model.common.SortingDetail;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.model.vo.ImportGiftSearchVo;
import com.chinarewards.elt.service.gift.ImportGiftService;
import com.chinarewards.gwt.elt.client.gift.model.ImportGiftListClient;
import com.chinarewards.gwt.elt.client.gift.request.SearchGiftImportListRequest;
import com.chinarewards.gwt.elt.client.gift.request.SearchGiftImportListResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.chinarewards.gwt.elt.util.UserRoleTool;
import com.google.inject.Inject;

/**
 * The handler to correspond the ImportGiftListRequest.
 * 
 * @author yanrui
 * @since 1.5.2
 */
public class SearchGiftImportListHandler extends
		BaseActionHandler<SearchGiftImportListRequest, SearchGiftImportListResponse> {

	@InjectLogger
	Logger logger;

	ImportGiftService importGiftService;

	@Inject
	public SearchGiftImportListHandler(ImportGiftService importGiftService) {
		this.importGiftService = importGiftService;
	}

	@Override
	public SearchGiftImportListResponse execute(SearchGiftImportListRequest request,
			ExecutionContext response) throws DispatchException {

		SearchGiftImportListResponse staffResponse = new SearchGiftImportListResponse();
		ImportGiftSearchVo criteria=new ImportGiftSearchVo();
		if (request.getCriteria().getPagination() != null) {
			PaginationDetail detail = new PaginationDetail();
			detail.setLimit(request.getCriteria().getPagination().getLimit());
			detail.setStart(request.getCriteria().getPagination().getStart());

			criteria.setPaginationDetail(detail);
		}

		if (request.getCriteria().getSorting() != null) {
			SortingDetail sortingDetail = new SortingDetail();
			sortingDetail.setSort(request.getCriteria().getSorting().getSort());
			sortingDetail.setDirection(request.getCriteria().getSorting().getDirection());
			criteria.setSortingDetail(sortingDetail);
		}
		criteria.setBatchId(request.getCriteria().getBatchId());
		criteria.setTitlefal(request.getCriteria().isTitlefal());
		criteria.setImportfal(request.getCriteria().isImportfal());
		UserContext context=new UserContext();
		context.setCorporationId(request.getSession().getCorporationId());
		context.setUserId(request.getSession().getToken());
		context.setLoginName(request.getSession().getLoginName());
		context.setUserRoles(UserRoleTool.adaptToRole(request.getSession().getUserRoles()));
		
		PageStore<ImportGiftRaw> result=importGiftService.findImportGiftList(criteria);
		List<ImportGiftListClient> lt=new ArrayList<ImportGiftListClient>();
		for (ImportGiftRaw raw:result.getResultList()) {
			ImportGiftListClient client=new ImportGiftListClient();
			client.setId(raw.getId());
			
			client.setName(raw.getName());
			client.setSourceText(raw.getSourceText());
			client.setPrice(raw.getPrice());
			client.setIntegral(raw.getIntegral());
			client.setStock(raw.getStock());
			client.setStatusText(raw.getStatusText());
//			private String name;
//			private String source;
//			private String sourceText;
//			private String price;
//			private String integral;
//			private String stock;
//			private String status;
//			private String statusText;
			
			client.setImportfal(raw.getImportfal());
			lt.add(client);

		}
		staffResponse.setResult(lt);
		staffResponse.setTotal(result.getResultCount());
		staffResponse.setSelectTotal(importGiftService.findImportGiftCount(criteria));
		
		return staffResponse;
	}

	@Override
	public Class<SearchGiftImportListRequest> getActionType() {
		return SearchGiftImportListRequest.class;
	}

	@Override
	public void rollback(SearchGiftImportListRequest request,
			SearchGiftImportListResponse response, ExecutionContext context)
			throws DispatchException {
	}

}
