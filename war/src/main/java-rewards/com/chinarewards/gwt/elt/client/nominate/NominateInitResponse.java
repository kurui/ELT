package com.chinarewards.gwt.elt.client.nominate;

import java.util.List;

import net.customware.gwt.dispatch.shared.Result;

/**
 * Models the response after user process request.
 * 
 * @author nicho
 * @since 2011年12月12日
 */
public class NominateInitResponse implements Result {

	private String awardsVo; //奖项Vo
	private List candidateList;//候选人列表
	private List nominateList;//提名人列表

	public String getAwardsVo() {
		return awardsVo;
	}

	public void setAwardsVo(String awardsVo) {
		this.awardsVo = awardsVo;
	}

	public List getCandidateList() {
		return candidateList;
	}

	public void setCandidateList(List candidateList) {
		this.candidateList = candidateList;
	}

	public List getNominateList() {
		return nominateList;
	}

	public void setNominateList(List nominateList) {
		this.nominateList = nominateList;
	}

	public NominateInitResponse() {

	}

	public NominateInitResponse(String awardsVo,List candidateList,List nominateList) {
		this.awardsVo = awardsVo;
		this.candidateList = candidateList;
		this.nominateList = nominateList;
	}


}
