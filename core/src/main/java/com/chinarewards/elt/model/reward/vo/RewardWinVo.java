/**
 * 
 */
package com.chinarewards.elt.model.reward.vo;

import java.util.List;

import com.chinarewards.elt.domain.org.Staff;
import com.chinarewards.elt.domain.reward.base.Reward;
import com.chinarewards.elt.domain.reward.person.Winner;



public class RewardWinVo {
	Reward reward;
	List<Winner> winner;
	List<Staff> staffs;
	public RewardWinVo(Reward reward,List<Winner> winner)
	{
		this.reward=reward;
		this.winner=winner;
	}
	
	

	public List<Staff> getStaffs() {
		return staffs;
	}



	public void setStaffs(List<Staff> staffs) {
		this.staffs = staffs;
	}



	public Reward getReward() {
		return reward;
	}
	public void setReward(Reward reward) {
		this.reward = reward;
	}
	public List<Winner> getWinner() {
		return winner;
	}
	public void setWinner(List<Winner> winner) {
		this.winner = winner;
	}
	
}
