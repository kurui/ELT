package com.chinarewards.gwt.elt.client.nominate;

import java.util.List;

import net.customware.gwt.dispatch.shared.Result;

import com.chinarewards.elt.model.reward.search.RewardSearchVo;

/**
 * Models the response after user process request.
 * 
 * @author nicho
 * @since 2011年12月12日
 */
public class NominateInitResponse implements Result {

	private String awardsVo; //奖项Vo
	private List<String> candidateList;//候选人列表
	private List<String> nominateList;//提名人列表

	public String getAwardsVo() {
		return awardsVo;
	}

	public void setAwardsVo(String awardsVo) {
		this.awardsVo = awardsVo;
	}

	public List<String> getCandidateList() {
		return candidateList;
	}

	public void setCandidateList(List<String> candidateList) {
		this.candidateList = candidateList;
	}

	public List<String> getNominateList() {
		return nominateList;
	}

	public void setNominateList(List<String> nominateList) {
		this.nominateList = nominateList;
	}

	public NominateInitResponse() {

	}

	public NominateInitResponse(String awardsVo,List<String> candidateList,List<String> nominateList) {
		this.awardsVo = awardsVo;
		this.candidateList = candidateList;
		this.nominateList = nominateList;
	}


}
