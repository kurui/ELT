/**
 * 
 */
package com.chinarewards.gwt.elt.client.nominate;

import java.util.List;

import net.customware.gwt.dispatch.shared.Action;

/**
 * An action which perform request to search user.
 * 
 * @author nicho
 * @since 2011年12月12日 
 */
public class NominateAddRequest implements Action<NominateAddResponse> {

	List<String> staffIds;
	List<String> candidateIds;
	public List<String> getCandidateIds() {
		return candidateIds;
	}

	public void setCandidateIds(List<String> candidateIds) {
		this.candidateIds = candidateIds;
	}

	String rewardId;
	
	public String getRewardId() {
		return rewardId;
	}

	public void setRewardId(String rewardId) {
		this.rewardId = rewardId;
	}

	/**
	 * For serialization
	 */
	public NominateAddRequest() {
	}

	public List<String> getStaffIds() {
		return staffIds;
	}

	public void setStaffIds(List<String> staffIds) {
		this.staffIds = staffIds;
	}

	public NominateAddRequest(List<String> staffIds,List<String> candidateIds,String rewardId) {
		this.staffIds=staffIds;
		this.candidateIds=candidateIds;
		this.rewardId=rewardId;
	}

}
