package com.chinarewards.elt.service.org.impl;

import com.chinarewards.elt.domain.org.Corporation;
import com.chinarewards.elt.domain.org.Department;
import com.chinarewards.elt.domain.org.Organization;
import com.chinarewards.elt.domain.org.Staff;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class OrganizationfactoryImpl implements Organizationfactory {

	private final OrganizationProcessor staffProcessor;
	private final OrganizationProcessor corporationProcessor;
	private final OrganizationProcessor deptmentProcessor;

	@Inject
	public OrganizationfactoryImpl(
			@Named("StaffProcessor") OrganizationProcessor staffProcessor,
			@Named("CorporationProcessor") OrganizationProcessor corporationProcessor,
			@Named("DeptmentProcessor") OrganizationProcessor deptmentProcessor) {
		this.corporationProcessor = corporationProcessor;
		this.staffProcessor = staffProcessor;
		this.deptmentProcessor = deptmentProcessor;
	}

	public OrganizationProcessor generatorProcessor(Organization organization) {
		OrganizationProcessor res = null;
		if (organization instanceof Staff) {
			res = staffProcessor;
		} else if (organization instanceof Department) {
			res = deptmentProcessor;
		} else if (organization instanceof Corporation) {
			res = corporationProcessor;
		} else {
			throw new IllegalArgumentException("UNKNOW instance organization "
					+ organization.getClass().getSimpleName());
		}
		return res;
	}

}
