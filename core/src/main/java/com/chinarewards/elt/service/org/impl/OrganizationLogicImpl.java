package com.chinarewards.elt.service.org.impl;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinarewards.elt.dao.org.OrganizationDao;
import com.chinarewards.elt.domain.org.Organization;
import com.chinarewards.elt.service.org.OrganizationLogic;
import com.chinarewards.elt.util.StringUtil;
import com.google.inject.Inject;

public class OrganizationLogicImpl implements OrganizationLogic {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private final OrganizationDao organizationDao;

	@Inject
	protected OrganizationLogicImpl(EntityManager em,
			OrganizationDao organizationDao) {
		this.organizationDao = organizationDao;
	}

	public Organization findOrganizationById(String id) {
		logger.debug(" Process in findOrganizationById method, parameter id:"
				+ id);
		Organization organization = organizationDao.findById(
				Organization.class, id);
		if (organization == null
				|| StringUtil.isEmptyString(organization.getId())) {
			logger.debug("find Organization by Id fail Organization.id:" + id);
			throw new IllegalArgumentException(
					"find Organization by Id fail Organization.id:" + id);
		}
		return organization;
	}
}
