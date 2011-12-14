package com.chinarewards.gwt.elt.server.nominate;


import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.slf4j.Logger;

import com.chinarewards.elt.model.reward.search.RewardSearchVo;
import com.chinarewards.gwt.elt.client.nominate.NominateInitRequest;
import com.chinarewards.gwt.elt.client.nominate.NominateInitResponse;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.google.inject.Inject;

/**
 * The handler to correspond the HrRegisterRequest.
 * 
 * @author nicho
 * @since 2011年12月7日 09:41:42
 */
public class NominateActionHandler extends
		BaseActionHandler<NominateInitRequest, NominateInitResponse> {

	@InjectLogger
	Logger logger;



	@Inject
	public NominateActionHandler() {

	}

	@Override
	public NominateInitResponse execute(NominateInitRequest request,
			ExecutionContext response) throws DispatchException {

		NominateInitResponse Nomresponse = new NominateInitResponse();
		RewardSearchVo awardsVo=new RewardSearchVo();
	//	awardsVo.setName("奖励名称");
		Nomresponse.setAwardsVo("奖励名称");
		List<String> nominateList=new ArrayList<String>();//提名人列表
		nominateList.add("1号提名人");
		nominateList.add("2号提名人");
		List<String> candidateList=new ArrayList<String>();//候选人列表
		candidateList.add("Jol.liu");
		candidateList.add("Alice.liu");
		candidateList.add("Mervyn");
		candidateList.add("Nevy");
		candidateList.add("It.Lou");
		Nomresponse.setNominateList(nominateList);
		Nomresponse.setCandidateList(candidateList);
	
		return Nomresponse;
	}

	@Override
	public Class<NominateInitRequest> getActionType() {
		return NominateInitRequest.class;
	}

	@Override
	public void rollback(NominateInitRequest request,
			NominateInitResponse response, ExecutionContext context)
			throws DispatchException {
	}

}
