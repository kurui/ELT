package com.chinarewards.elt.domain.org;

import java.util.Date;

import javax.persistence.ManyToOne;

public class Members {

    private static final long serialVersionUID = 8744769908375326416L;
    
    private String id; 
    @ManyToOne
	private Team teamId; //组
	@ManyToOne
	private Staff staff;//成员ID
	private int memberType;//成员类型0 组员，1 负责人
	private int  deleted;  //0 存在，1 删除
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Team getTeamId() {
		return teamId;
	}
	public void setTeamId(Team teamId) {
		this.teamId = teamId;
	}
	public Staff getStaff() {
		return staff;
	}
	public void setStaffId(Staff staff) {
		this.staff = staff;
	}
	public int getMemberType() {
		return memberType;
	}
	public void setMemberType(int memberType) {
		this.memberType = memberType;
	}
	public int getDeleted() {
		return deleted;
	}
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	public String getRecorduser() {
		return recorduser;
	}
	public void setRecorduser(String recorduser) {
		this.recorduser = recorduser;
	}
	public Date getRecorddate() {
		return recorddate;
	}
	public void setRecorddate(Date recorddate) {
		this.recorddate = recorddate;
	}
	private String recorduser;
	private Date  recorddate;
}