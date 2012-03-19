package com.chinarewards.gwt.elt.client.license.model;

import com.chinarewards.gwt.elt.model.PaginationDetailClient;
import com.chinarewards.gwt.elt.model.SortingDetailClient;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author yanrui
 */
public class LicenseCriteria implements IsSerializable {
	public LicenseCriteria() {
	}

	public static enum LicenseStatus {

		/* 上架 */
		SHELVES("上架"),

		/* 下架 */
		SHELF("下架");

		private final String displayName;

		LicenseStatus(String displayName) {
			this.displayName = displayName;
		}

		public String getDisplayName() {
			return displayName;
		}

	}

	private PaginationDetailClient pagination;

	private SortingDetailClient sorting;

	private String id;

	private String name;
	private  int integral;

	public int getIntegral() {
		return integral;
	}

	public void setIntegral(int integral) {
		this.integral = integral;
	}

	public LicenseStatus getStatus() {
		return status;
	}

	public void setStatus(LicenseStatus status) {
		this.status = status;
	}

	private LicenseStatus status;

	public PaginationDetailClient getPagination() {
		return pagination;
	}

	public void setPagination(PaginationDetailClient pagination) {
		this.pagination = pagination;
	}

	public SortingDetailClient getSorting() {
		return sorting;
	}

	public void setSorting(SortingDetailClient sorting) {
		this.sorting = sorting;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
