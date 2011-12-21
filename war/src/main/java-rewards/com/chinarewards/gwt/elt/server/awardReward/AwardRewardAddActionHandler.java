package com.chinarewards.gwt.elt.server.awardReward;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.domain.reward.person.NomineeLot;
import com.chinarewards.elt.model.reward.exception.JudgeException;
import com.chinarewards.elt.service.reward.nominee.NomineeService;
import com.chinarewards.gwt.elt.client.awardReward.request.AwardRewardAddRequest;
import com.chinarewards.gwt.elt.client.awardReward.request.AwardRewardAddResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;

/**
 * The handler to correspond the NominateAddActionHandler
 * 
 * @author nicho
 * @since 2011年12月21日 16:12:47
 */
public class AwardRewardAddActionHandler extends
		BaseActionHandler<AwardRewardAddRequest, AwardRewardAddResponse> {

	@InjectLogger
	Logger logger;

	NomineeService nomineeService;

	@Inject
	public AwardRewardAddActionHandler(NomineeService nomineeService) {
		this.nomineeService = nomineeService;
	}

	@Override
	public AwardRewardAddResponse execute(AwardRewardAddRequest request,
			ExecutionContext response) throws DispatchException {
		AwardRewardAddResponse Nomresponse=new AwardRewardAddResponse();
		
		try {
			NomineeLot lot=nomineeService.addNomineeLotToReward(request.getRewardId(), request.getStaffIds());
			Nomresponse.setNomineeLotId(lot.getId());
		} catch (JudgeException e) {
			e.printStackTrace();
		}
	
		return Nomresponse;
	}

	@Override
	public Class<AwardRewardAddRequest> getActionType() {
		return AwardRewardAddRequest.class;
	}

	@Override
	public void rollback(AwardRewardAddRequest request,
			AwardRewardAddResponse response, ExecutionContext context)
			throws DispatchException {
	}

}
