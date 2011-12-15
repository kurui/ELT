package com.chinarewards.gwt.elt.server.nominate;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.service.reward.RewardService;
import com.chinarewards.gwt.elt.client.nominate.NominateAddRequest;
import com.chinarewards.gwt.elt.client.nominate.NominateAddResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;

/**
 * The handler to correspond the NominateAddActionHandler
 * 
 * @author nicho
 * @since 2011年12月7日 09:41:42
 */
public class NominateAddActionHandler extends
		BaseActionHandler<NominateAddRequest, NominateAddResponse> {

	@InjectLogger
	Logger logger;

	RewardService rewardService;

	@Inject
	public NominateAddActionHandler(RewardService rewardService) {
		this.rewardService = rewardService;
	}

	@Override
	public NominateAddResponse execute(NominateAddRequest request,
			ExecutionContext response) throws DispatchException {
		NominateAddResponse Nomresponse=new NominateAddResponse();
		Nomresponse.setNomineeLotId("11111111xxx");
		return Nomresponse;
	}

	@Override
	public Class<NominateAddRequest> getActionType() {
		return NominateAddRequest.class;
	}

	@Override
	public void rollback(NominateAddRequest request,
			NominateAddResponse response, ExecutionContext context)
			throws DispatchException {
	}

}
