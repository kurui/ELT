/**
 * 
 */
package com.chinarewards.gwt.elt.server.gift;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.domain.org.Corporation;
import com.chinarewards.elt.domain.reward.person.Judge;
import com.chinarewards.elt.domain.reward.rule.DirectCandidateData;
import com.chinarewards.elt.service.gift.GiftService;
import com.chinarewards.gwt.elt.client.gift.model.GiftVo;
import com.chinarewards.gwt.elt.client.gift.request.SearchGiftByIdRequest;
import com.chinarewards.gwt.elt.client.gift.request.SearchGiftByIdResponse;
import com.chinarewards.gwt.elt.client.rewards.model.OrganicationClient;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;

/**
 * @author yanrui
 * @since
 */
public class SearchGiftByIdHandler extends
		BaseActionHandler<SearchGiftByIdRequest, SearchGiftByIdResponse> {
	@InjectLogger
	Logger logger;
	GiftService giftService;

	@Inject
	public SearchGiftByIdHandler(GiftService giftService) {
		this.giftService = giftService;
	}

	@Override
	public SearchGiftByIdResponse execute(SearchGiftByIdRequest request,
			ExecutionContext context) throws DispatchException {
		logger.debug(" parameters:{}", request.getId());

		// GiftVo giftVo = giftService.fetchEntireGiftById(request.getId());
		// return new SearchGiftByIdResponse(adapter(giftService, giftVo));
		return null;
	}

	private GiftVo adapter(GiftService giftVoService, GiftVo item) {
		GiftVo giftVo = new GiftVo();
		giftVo.setId(item.getId());

		return giftVo;
	}

	private boolean isChooseAll(List<DirectCandidateData> participants) {
		for (DirectCandidateData p : participants) {
			if (p.getOrg() instanceof Corporation) {
				return true;
			}
		}

		return false;
	}

	private List<OrganicationClient> getOrgsFromParticipants(
			List<DirectCandidateData> participants) {
		List<OrganicationClient> orgs = new ArrayList<OrganicationClient>();
		for (DirectCandidateData p : participants) {
			orgs.add(new OrganicationClient(p.getOrg().getId(), p.getOrg()
					.getName()));
		}
		return orgs;
	}

	private List<OrganicationClient> getOrgsFromJudges(List<Judge> judge) {
		List<OrganicationClient> orgs = new ArrayList<OrganicationClient>();
		for (Judge p : judge) {
			orgs.add(new OrganicationClient(p.getStaff().getId(), p.getStaff()
					.getName()));
		}
		return orgs;
	}

	@Override
	public Class<SearchGiftByIdRequest> getActionType() {
		return SearchGiftByIdRequest.class;
	}

	@Override
	public void rollback(SearchGiftByIdRequest arg0,
			SearchGiftByIdResponse arg1, ExecutionContext arg2)
			throws DispatchException {
	}

}
