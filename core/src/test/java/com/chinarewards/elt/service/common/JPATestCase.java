package com.chinarewards.elt.service.common;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.chinarewards.elt.dao.org.CorporationDao;
import com.chinarewards.elt.dao.user.SysUserDao;
import com.chinarewards.elt.domain.org.Corporation;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.model.user.UserRole;

public abstract class JPATestCase extends GuiceTestCase {

	final protected EntityManager em;

	public JPATestCase() {
		super();
		this.em = injector.getInstance(EntityManager.class);
	}

	public EntityManager getEm() {
		return em;
	}

	public void begin() {
		em.getTransaction().begin();
	}

	public void commit() {
		em.getTransaction().commit();
	}

	public void rollback() {
		em.getTransaction().rollback();
	}

	protected void setUp() throws Exception {
		super.setUp();
		logger.debug("Begin a Transaction");
		em.getTransaction().begin();
	}

	protected void tearDown() throws Exception {
		if (em.getTransaction().isActive()) {
			rollback();
			logger.debug("Shutdown testcase, rolled-back transaction!");
		}
		super.tearDown();
	}

	protected void initSysParams() {
		// system parameter configuration.
		// injector.getInstance(SysConfigureImpl.class).configure();
	}

	protected void persist(Object o) {
		em.persist(o);
	}

	protected UserContext getUserContext() {
		Corporation corporation = new Corporation();
		corporation.setName("initCorportaion");
		CorporationDao corporationDao = this.injector
				.getInstance(CorporationDao.class);
		corporationDao.save(corporation);

		return getUserContext(corporation.getId());
	}

	protected UserContext getUserContext(String corporationId) {
		CorporationDao corporationDao = this.injector
				.getInstance(CorporationDao.class);
		Corporation corporation = corporationDao.findById(Corporation.class,
				corporationId);

		// corporation.setCorporationId(enterpriseId);
		// corporation.setName("initCorportaion");
		// CorporationDao corporationDao = this.injector
		// .getInstance(CorporationDao.class);
		// corporationDao.save(corporation);

		// a user record is required
		SysUser caller = new SysUser();
		caller.setUserName("aa80064a4b4");
		caller.setPassword("12345680064a4b480064a4b4");
		caller.setCorporation(corporation);
		SysUserDao dao = this.injector.getInstance(SysUserDao.class);
		dao.save(caller);

		// prepare user context
		List<UserRole> roles = new ArrayList<UserRole>();
		roles.add(UserRole.CORP_ADMIN);

		UserContext ctx = new UserContext();
		ctx.setCorporationId(corporation.getId());
		ctx.setUserId(caller.getId());
		ctx.setLoginName(caller.getUserName());
		ctx.setUserRoles(roles.toArray(new UserRole[roles.size()]));

		return ctx;
	}

}
