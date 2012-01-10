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
import com.chinarewards.elt.domain.reward.frequency.Frequency;
import com.chinarewards.elt.domain.reward.person.Judge;
import com.chinarewards.elt.domain.reward.rule.CandidateRule;
import com.chinarewards.elt.domain.reward.rule.DirectCandidateData;
import com.chinarewards.elt.domain.reward.rule.DirectCandidateRule;
import com.chinarewards.elt.domain.reward.rule.DobRule;
import com.chinarewards.elt.model.reward.base.RequireAutoAward;
import com.chinarewards.elt.model.reward.base.RequireAutoGenerate;
import com.chinarewards.elt.model.reward.vo.RewardItemVo;
import com.chinarewards.elt.service.reward.RewardItemService;
import com.chinarewards.gwt.elt.client.gift.module.GiftClient;
import com.chinarewards.gwt.elt.client.gift.request.SearchGiftByIdRequest;
import com.chinarewards.gwt.elt.client.gift.request.SearchGiftByIdResponse;
import com.chinarewards.gwt.elt.client.rewards.model.FrequencyClient;
import com.chinarewards.gwt.elt.client.rewards.model.OrganicationClient;
import com.chinarewards.gwt.elt.client.rewards.model.ParticipateInfoClient;
import com.chinarewards.gwt.elt.client.rewards.model.ParticipateInfoClient.EveryoneClient;
import com.chinarewards.gwt.elt.client.rewards.model.ParticipateInfoClient.SomeoneClient;
import com.chinarewards.gwt.elt.client.rewards.model.SpecialCondition;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.chinarewards.gwt.elt.server.rewardItem.FrequencyHelper;
import com.google.inject.Inject;

/**
 * @author yanrui
 * @since 
 */
public class SearchGiftByIdHandler
		extends
		BaseActionHandler<SearchGiftByIdRequest, SearchGiftByIdResponse> {
	@InjectLogger
	Logger logger;
	RewardItemService rewardItemService;

	@Inject
	public SearchGiftByIdHandler(RewardItemService rewardItemService) {
		this.rewardItemService = rewardItemService;
	}

	@Override
	public SearchGiftByIdResponse execute(
			SearchGiftByIdRequest request, ExecutionContext context)
			throws DispatchException {
		logger.debug(" parameters:{}", request.getId());
		
		RewardItemVo rewardsItem = rewardItemService.fetchEntireRewardItemById(request.getId());
		return new SearchGiftByIdResponse(adapter(rewardItemService,
				rewardsItem));
	}

	

	private GiftClient adapter(RewardItemService rewardsItemService,
			RewardItemVo item) {
		GiftClient client = new GiftClient();
		client.setId(item.getId());
		client.setName(item.getName());
//		client.setExplains(item.getExplains());
//		private String id;
//		private String name; // ÀñÆ·Ãû
//		private String explains; // ËµÃ÷
//		private String type; // ÀñÆ·ÀàÐÍ
//		private String source; // À´Ô´
//		private String business; // ¹©Ó¦ÉÌ
//		private String address; // µØÖ·
//		private String tell; // µç»°
//		private int stock; // ¿â´æ
//		private String photo; // Í¼Æ¬
//		private boolean status; // ×´Ì¬£¨ÉÏÏÂ¼Ü£©
//		private boolean deleted; // É¾³ý×´Ì¬
//		private Date indate; // ÓÐÐ§½ØÖ¹ÆÚ
//		private Date recorddate; // Â¼ÈëÊ±¼ä
//		private String recorduser; // Â¼ÈëÈË
//		private Date updatetime; // ÐÞ¸ÄÊ±¼ä
		
	
	
		
		
		return client;
	}

	private boolean isChooseAll(List<DirectCandidateData> participants) {
		for (DirectCandidateData p : participants) {
			if (p.getOrg() instanceof Corporation) {
				return true;
			}
		}

		return false;
	}

	private List<OrganicationClient> getOrgsFromParticipants(List<DirectCandidateData> participants) {
		List<OrganicationClient> orgs = new ArrayList<OrganicationClient>();
		for (DirectCandidateData p : participants) {
			orgs.add(new OrganicationClient(p.getOrg().getId(), p.getOrg().getName()));
		}
		return orgs;
	}
	
	private List<OrganicationClient> getOrgsFromJudges(List<Judge> judge) {
		List<OrganicationClient> orgs = new ArrayList<OrganicationClient>();
		for (Judge p : judge) {
			orgs.add(new OrganicationClient(p.getStaff().getId(), p.getStaff().getName()));
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
