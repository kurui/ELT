package com.chinarewards.elt.model.vo;

import java.io.Serializable;
import java.util.List;

import com.chinarewards.elt.model.common.PaginationDetail;
import com.chinarewards.elt.model.common.SortingDetail;
import com.chinarewards.elt.domain.org.Corporation;
import com.chinarewards.elt.domain.org.Members;
public class TeamListVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The paging detail, contains the info it requires.
	 */
	private PaginationDetail paginationDetail;

	/**
	 * Sorting detail.
	 */
	private SortingDetail sortingDetail;
	private String id;
	private String corpid ;
	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	private String name; // 组名
	private String code;
	private String description;
	private List<Members>  membersList;

	public TeamListVo() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Members> getMembersList() {
		return membersList;
	}

	public void setMembersList(List<Members> membersList) {
		this.membersList = membersList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PaginationDetail getPaginationDetail() {
		return paginationDetail;
	}

	public void setPaginationDetail(PaginationDetail paginationDetail) {
		this.paginationDetail = paginationDetail;
	}

	public SortingDetail getSortingDetail() {
		return sortingDetail;
	}

	public void setSortingDetail(SortingDetail sortingDetail) {
		this.sortingDetail = sortingDetail;
	}

}