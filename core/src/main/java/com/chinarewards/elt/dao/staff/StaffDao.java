package com.chinarewards.elt.dao.staff;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Query;

import org.apache.commons.beanutils.BeanUtils;

import com.chinarewards.elt.common.BaseDao;
import com.chinarewards.elt.dao.org.DeptIdResolverDao;
import com.chinarewards.elt.domain.reward.person.Winner;
import com.chinarewards.elt.domain.staff.Staff;
import com.chinarewards.elt.model.user.DeleteMarkConstant;
import com.chinarewards.elt.model.vo.StaffSearchVo;
import com.chinarewards.elt.model.vo.StaffSearchVo.MultipleIdParam;
import com.chinarewards.elt.model.vo.StaffSearchVo.OneIdParam;
import com.chinarewards.elt.model.vo.WinnersRecordQueryResult;
import com.chinarewards.elt.model.vo.WinnersRecordQueryVo;
import com.chinarewards.elt.util.StringUtil;
import com.google.inject.Inject;

/**
 * 
 * @author yanxin
 * @deprecated you should use {@link com.chinarewards.elt.dao.org.StaffDao}
 *             instead.
 */
public class StaffDao extends BaseDao<Staff> {

	public class QueryStaffPageActionResult {

		private int total;

		private List<Staff> resultList;

		/**
		 * @return the total
		 */
		public int getTotal() {
			return total;
		}

		/**
		 * @param total
		 *            the total to set
		 */
		public void setTotal(int total) {
			this.total = total;
		}

		/**
		 * @return the resultList
		 */
		public List<Staff> getResultList() {
			return resultList;
		}

		/**
		 * @param resultList
		 *            the resultList to set
		 */
		public void setResultList(List<Staff> resultList) {
			this.resultList = resultList;
		}

	}

	private final DeptIdResolverDao deptIdDao;

	@Inject
	public StaffDao(DeptIdResolverDao deptIdDao) {
		this.deptIdDao = deptIdDao;
	}

	public QueryStaffPageActionResult queryStaffPageAction(
			StaffSearchVo searchVo) {

		logger.debug(" Process in queryStaffPageAction, searchVo:{}", searchVo);

		StaffSearchVo finalVo = searchVo;

		if (searchVo.getDeptParam() != null) {
			if (searchVo.getDeptParam() instanceof OneIdParam) {
				OneIdParam param = (OneIdParam) searchVo.getDeptParam();
				// convert to department IDs
				Set<String> deptIds = deptIdDao.findSiblingIds(
						param.getDeptId(), true);
				try {
					finalVo = (StaffSearchVo) BeanUtils.cloneBean(searchVo);
				} catch (Exception e) {
					logger.error("clone bean error.", e);
				}
				finalVo.setDeptParam(new MultipleIdParam(deptIds));
			}
		}
		logger.debug("finalVo:{}", finalVo.toString());

		QueryStaffPageActionResult result = new QueryStaffPageActionResult();

		result.setResultList(queryStaffPageActionData(finalVo));
		result.setTotal(queryStaffPageActionCount(finalVo));

		return result;
	}

	@SuppressWarnings("unchecked")
	private List<Staff> queryStaffPageActionData(StaffSearchVo searchVo) {
		return getStaffQuery(searchVo, SEARCH).getResultList();
	}

	private int queryStaffPageActionCount(StaffSearchVo searchVo) {
		return Integer.parseInt(getStaffQuery(searchVo, COUNT)
				.getSingleResult().toString());
	}

	private Query getStaffQuery(StaffSearchVo searchVo, String type) {
		Map<String, Object> param = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();
		if (SEARCH.equals(type)) {
			hql.append(" SELECT staff FROM Staff staff WHERE 1=1 ");
		} else if (COUNT.equals(type)) {
			hql.append(" SELECT COUNT(staff) FROM Staff staff WHERE 1=1 ");
		}
		// 关键字 姓名/部门/卡号/邮箱
		if (!StringUtil.isEmptyString(searchVo.getKeywords())) {
			hql.append(" AND (UPPER(staff.name) LIKE :keywords "
					// + " OR UPPER(staff.department.name) LIKE :keywords "
					+ " OR UPPER(staff.emailAddress) LIKE :keywords "
					+ " OR UPPER(staff.memberCardNumber) LIKE :keywords)");
			param.put("keywords", "%"
					+ searchVo.getKeywords().trim().toUpperCase() + "%");
		}
		if (!StringUtil.isEmptyString(searchVo.getDeptId())) {
			hql.append(" AND staff.department.id = :deptId ");
			param.put("deptId", searchVo.getDeptId());
		}
		// /*if (!StringUtil.isEmptyString(searchVo.getStaffName())) {
		// hql.append(" AND UPPER(staff.name) LIKE :name ");
		// param.put("name", "%" + searchVo.getStaffName() + "%");
		// }
		// if (!StringUtil.isEmptyString(searchVo.getStaffCardNumber())) {
		// hql.append(" AND staff.memberCardNumber LIKE :memberCardNumber ");
		// param.put("memberCardNumber", "%" + searchVo.getStaffCardNumber()
		// + "%");
		// }
		// if
		// (!StringUtil.isEmptyString(searchVo.getStaffMobiletelephoneNumber()))
		// {
		// hql.append(" AND staff.mobileTelephoneNumber LIKE :mobileTelephoneNumber ");
		// param.put("mobileTelephoneNumber",
		// "%" + searchVo.getStaffMobiletelephoneNumber() + "%");
		// }*/
		if (searchVo.getDeleteMarkConstant() != null) {
			hql.append(" AND staff.deleteMarkConstant = :deleteMarkConstant ");
			param.put("deleteMarkConstant", searchVo.getDeleteMarkConstant());
		}
		if (searchVo.getEnterpriseId() != null) {
			hql.append(" AND staff.corporation.id =:corporationId");
			param.put("corporationId", searchVo.getEnterpriseId());
		}
		// department
		if (searchVo.getDeptParam() != null) {

			if (searchVo.getDeptParam() instanceof OneIdParam) {
				hql.append(" AND staff.department.id = :deptId ");
				OneIdParam idParam = (OneIdParam) searchVo.getDeptParam();
				param.put("deptId", idParam.getDeptId());
			} else if (searchVo.getDeptParam() instanceof MultipleIdParam) {
				hql.append(" AND staff.department.id IN (:deptIds) ");
				MultipleIdParam idParam = (MultipleIdParam) searchVo
						.getDeptParam();
				param.put("deptIds", idParam.getIds());
			}

		}
		// ORDER BY
		if (SEARCH.equals(type)) {
			if (searchVo.getSortingDetail() != null) {
				hql.append(" ORDER BY staff."
						+ searchVo.getSortingDetail().getSort() + " "
						+ searchVo.getSortingDetail().getDirection());
			} else {
				hql.append(" ORDER BY staff.lastUpdateTime DESC ");
			}
		}
		logger.debug(" HQL:{} ", hql);
		Query query = getEm().createQuery(hql.toString());
		if (SEARCH.equals(type)) {
			if (searchVo.getPaginationDetail() != null) {
				int limit = searchVo.getPaginationDetail().getLimit();
				int start = searchVo.getPaginationDetail().getStart();

				logger.debug("pagination - start{}, limit:{}", new Object[] {
						start, limit });

				query.setMaxResults(limit);
				query.setFirstResult(start);
			}
		}
		if (param.size() > 0) {
			Set<String> key = param.keySet();
			for (String s : key) {
				query.setParameter(s, param.get(s));
			}
		}
		return query;
	}

	/**
	 * 过滤离职的员工
	 * 
	 * @param anyNumber
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Staff> getStaffByCardNoOrMobileNo(String anyNumber) {
		return getEm()
				.createQuery(
						" FROM Staff a WHERE "
								+ " (a.mobileTelephoneNumber=:mobileTelephoneNumber "
								+ " OR a.memberCardNumber=:memberCardNumber) "
								+ " AND a.deleteMarkConstant=:deleteMarkConstant ")
				.setParameter("deleteMarkConstant", DeleteMarkConstant.ACTIVING)
				.setParameter("mobileTelephoneNumber", anyNumber)
				.setParameter("memberCardNumber", anyNumber).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Staff> getStaffByDepartmentId(String departmentId) {
		return getEm()
				.createQuery(
						" FROM Staff staff WHERE staff.department.id=:departmentId AND staff.deleteMarkConstant=:deleteMarkConstant ")
				.setParameter("deleteMarkConstant", DeleteMarkConstant.ACTIVING)
				.setParameter("departmentId", departmentId).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Staff> getStaffIdsUsingSomeProperties(String corporationId,
			String flag, int limit, boolean isOrgIdsLimit, List<String> orgIds) {
		StringBuffer eql = new StringBuffer("FROM Staff staff WHERE 1 = 1 ");
		eql.append(" AND UPPER(staff.name) LIKE UPPER(:name)");
		eql.append(" AND staff.corporation.id=:corporationId ");
		eql.append(" AND staff.deleteMarkConstant = :deleteMarkConstant ");
		if (isOrgIdsLimit) {
			eql.append(" AND (staff.id IN(:orgIds) OR staff.department.id IN(:orgIds)) ");
		}
		Query query = getEm().createQuery(eql.toString());
		query.setParameter("name", "%" + flag + "%");
		query.setParameter("deleteMarkConstant", DeleteMarkConstant.ACTIVING);
		query.setParameter("corporationId", corporationId);
		if (isOrgIdsLimit) {
			query.setParameter("orgIds", orgIds);
		}

		return query.setMaxResults(limit).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Staff> queryStaffLoginDataByCorpIdData(String corpId,
			String loginData) {
		return getEm()
				.createQuery(
						" FROM Staff staff WHERE "
								+ " staff.corporation.id = :corpId"
								+ " AND (staff.memberCardNumber=:loginData "
								+ " OR UPPER(staff.emailAddress)=:loginData "
								+ " OR staff.mobileTelephoneNumber=:loginData) "
								+ " AND staff.deleteMarkConstant=:deleteMarkConstant ")
				.setParameter("corpId", corpId)
				.setParameter("loginData", loginData)
				.setParameter("deleteMarkConstant", DeleteMarkConstant.ACTIVING)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Staff> queryStaffLoginData(String falg) {
		return getEm()
				.createQuery(
						" FROM Staff staff WHERE "
								+ " (staff.memberCardNumber=:falg "
								+ " OR UPPER(staff.emailAddress)=:falg "
								+ " OR staff.mobileTelephoneNumber=:falg) "
								+ " AND staff.deleteMarkConstant=:deleteMarkConstant ")
				.setParameter("falg", falg)
				.setParameter("deleteMarkConstant", DeleteMarkConstant.ACTIVING)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<WinnersRecordQueryResult> queryWinnerRewardsData(
			WinnersRecordQueryVo queryVo, String corporationId) {
		List<WinnersRecordQueryResult> res = new ArrayList<WinnersRecordQueryResult>();
		// List<Object[]> staffs = getWinnerRewardsQueryBySql(queryVo, SEARCH,
		// corporationId).getResultList();
		// logger.debug("staff size:{}", staffs.size());
		// for (Object[] o : staffs) {
		//
		// WinnersRecordQueryResult re = new WinnersRecordQueryResult();
		// //
		// Staff s = new Staff();
		// s.setId((String) o[0]);
		// s.setName((String) o[1]);
		// s.setDateOfEmployment((Date) o[2]);
		// //
		// StaffLevel level = new StaffLevel();
		// level.setId((String) o[6]);
		// s.setStaffLevel(level);
		// re.setStaff(s);
		// //
		// Department d = new Department();
		// d.setName((String) o[3]);
		// d.setId((String) o[7]);
		// re.setDepartment(d);
		// re.setCount(Integer.parseInt(((BigDecimal) o[4]).toString()));
		// re.setLastWinnerTime(o[5] == null ? null : (Date) o[5]);
		//
		// // done
		// res.add(re);
		// }
		return res;
	};

	public int queryWinnerRewardsCount(WinnersRecordQueryVo queryVo,
			String corporationId) {
		int total = Integer.parseInt(getWinnerRewardsQueryBySql(queryVo, COUNT,
				corporationId).getSingleResult().toString());
		logger.debug("total num:{}", total);
		return total;
	}

	/**
	 * 查找某员工一段时间内的获奖次数
	 * 
	 * @param staffId
	 * @param from
	 *            from,to both null, search all.
	 * @param to
	 * @return
	 */
	public int queryRewardsTimesInPeriod(String staffId, Date from, Date to) {
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT COUNT(win) FROM Winners win WHERE win.organization.id = :staffId");
		if (from != null) {
			hql.append(" AND win.winnerTime > :from");
		}
		if (to != null) {
			hql.append(" AND win.winnerTime < :to");
		}

		Query query = getEm().createQuery(hql.toString());
		query.setParameter("staffId", staffId);
		if (from != null) {
			query.setParameter("from", from);
		}
		if (to != null) {
			query.setParameter("to", to);
		}

		return Integer.parseInt(query.getSingleResult().toString());
	}

	/**
	 * 查询某员工最后一次获奖时间
	 */
	@SuppressWarnings("unchecked")
	public Date queryLastRewardsTime(String staffId) {
		Date lastTime = null;
		String hql = "SELECT win FROM Winners win WHERE win.organization.id = :staffId ORDER BY win.winnerTime DESC";
		List<Winner> list = getEm().createQuery(hql)
				.setParameter("staffId", staffId).getResultList();
		if (list.size() > 0) {
			lastTime = list.get(0).getWinTime();
		}

		return lastTime;
	}

	/**
	 * 原sql:
	 * <p>
	 * 
	 * <code>
	 * select staff.id,staff.name as staffName, staff.createTime,
	 * dept.name as deptName, count(winner.organization_id) as num,
	 * max(winner.winnertime) as lastRewardsTime from organization staff left
	 * join (select o.id as id,o.ro_type as ro_type,o.organization_id as
	 * organization_id,o.winnertime as winnertime from rewardsobj o where
	 * o.ro_type = 'WINNER' and rewards.status = 'REWARDED' and rewards.builderDept_id in (:managedDeptIds)) winner 
	 * on winner.organization_id = staff.id left
	 * join organization dept on staff.department_id = dept.id where
	 * staff.org_type = 'staff' group by staff.id, staff.name, staff.createTime,
	 * dept.name order by num desc
	 * </code>
	 * 
	 * @param queryVo
	 * @param type
	 * @param corporationId
	 * @return
	 */
	public Query getWinnerRewardsQueryBySql(WinnersRecordQueryVo queryVo,
			String type, String corporationId) {

		logger.debug(
				" Process in getWinnerRewardsQueryBySql method, type:{}, corpId:{}, queryVo:{}",
				new Object[] { type, corporationId, queryVo.toString() });

		StringBuffer sql = new StringBuffer();
		Map<String, Object> param = new HashMap<String, Object>();
		if (SEARCH.equals(type)) {
			sql.append(" select staff.id, staff.name as staffName, staff.dateOfEmployment, dept.name as deptName,");
			sql.append(" count(winner.organization_id) as num, max(winner.winnertime) as lastRewardsTime, staff.staffLevel_id, dept.id AS deptId ");
			sql.append(" from organization staff left join ");
			// Get Winners record to count win numbers.
			sql.append(" (select o.id as id,o.ro_type as ro_type,o.organization_id as organization_id,o.winnertime as winnertime ");
			sql.append("  from rewardsobj o left join reward on o.rewards_id = rewards.id ");
			if (queryVo.getManagedDeptIds() != null
					&& !queryVo.getManagedDeptIds().isEmpty()) {
				sql.append("  where o.ro_type = 'WINNER' and rewards.status = 'REWARDED' and rewards.builderDept_id in (:managedDeptIds)) winner ");
			} else {
				sql.append("  where o.ro_type = 'WINNER' and rewards.status = 'REWARDED' ) winner ");
			}
			sql.append(" on winner.organization_id = staff.id ");
			sql.append(" left join organization dept on staff.department_id = dept.id ");
			sql.append(" where staff.org_type = 'staff'");
		} else if (COUNT.equals(type)) {
			sql.append("select count(*) from organization staff where staff.org_type='staff' ");
		}
		sql.append(" AND staff.deleteMarkConstant = :deleteMarkConstant ");
		param.put("deleteMarkConstant", "ACTIVING");

		if (!StringUtil.isEmptyString(queryVo.getKey())) {
			sql.append(" AND ( Upper(staff.name) LIKE Upper(:key)");
			sql.append(" OR Upper(staff.mobileTelephoneNumber) LIKE Upper(:key)");
			sql.append(" OR Upper(staff.emailAddress) LIKE Upper(:key)");
			sql.append(" OR Upper(staff.memberCardNumber) LIKE Upper(:key) )");
			param.put("key", "%" + queryVo.getKey() + "%");
		}

		if (queryVo.getSubDeptIds() != null
				&& !queryVo.getSubDeptIds().isEmpty()) {
			sql.append(" AND staff.department_id in (:deptIds)");
			param.put("deptIds", queryVo.getSubDeptIds());
		}

		// if corporation id is missing, will return empty result.
		sql.append(" AND staff.corporation_id=:corporationId ");
		logger.debug("corporationId:{}", corporationId);
		param.put("corporationId", corporationId);
		if (queryVo.isFilterRewardsParticipants()) {
			if (queryVo.getOrgIds() != null && queryVo.getOrgIds().size() != 0) {
				sql.append(" AND (staff.id IN (:orgIds) OR staff.department_id IN (:orgIds) OR staff.corporation_id IN (:orgIds)) ");
				param.put("orgIds", queryVo.getOrgIds());
			} else {
				// 不要出任何数据
				sql.append(" AND 1<>1 ");
			}
		}

		if (SEARCH.equals(type)) {
			sql.append(" GROUP BY staff.id, staff.name, staff.dateOfEmployment, dept.name,staff.staffLevel_id, dept.id ");
			if (queryVo.getSortingDetail() != null) {
				sql.append(" ORDER BY ")
						.append(queryVo.getSortingDetail().getSort())
						.append(" ")
						.append(queryVo.getSortingDetail().getDirection());
			}
		}

		logger.debug("native sql:{}", sql);

		// XXX make this EQL instead of native query
		Query q = getEm().createNativeQuery(sql.toString());
		if (SEARCH.equals(type)) {
			if (queryVo.getManagedDeptIds() != null
					&& !queryVo.getManagedDeptIds().isEmpty()) {
				q.setParameter("managedDeptIds", queryVo.getManagedDeptIds());
			}
			if (queryVo.getPaginationDetail() != null) {
				q.setMaxResults(queryVo.getPaginationDetail().getLimit());
				q.setFirstResult(queryVo.getPaginationDetail().getStart());
			}
		}
		for (String key : param.keySet()) {
			q.setParameter(key, param.get(key));
		}
		return q;
	}

	/**
	 * Find staff records which fulfill any one of the following:
	 * 
	 * <ul>
	 * <li>The given IDs is a staff ID.</li>
	 * <li>The given ID is a department ID, and a staff does belong to that
	 * department. Only direct relation between this department and staff will
	 * be returned.</li>
	 * <li>The given ID is a corporation ID, and any staff which belong to this
	 * corporation.</li>
	 * </ul>
	 * 
	 * @param orgIds
	 *            a list of IDs which can represent staff, department which any
	 *            staff belongs to and corporation ID which staff does belong
	 *            to, as described above.
	 * @return a list of staff which match the criteria, or an empty list if no
	 *         result is found.
	 */
	@SuppressWarnings("unchecked")
	public List<String> findStaffByStaffIdsAssignedDeptIdsStaffOfCorporation(
			List<String> orgIds) {
		logger.debug("orgIds size: {}", new Object[] { orgIds.size() });
		return getEm()
				.createQuery(
						" SELECT DISTINCT(staff.id) FROM Staff staff WHERE 1=1 "
								+ " AND staff.deleteMarkConstant=:deleteMarkConstant "
								+ " AND (staff.id IN (:orgIds) OR staff.department.id IN (:orgIds) OR staff.corporation.id IN (:orgIds)) ")
				.setParameter("orgIds", orgIds)
				.setParameter("deleteMarkConstant", DeleteMarkConstant.ACTIVING)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<String> findStaffDobConditionAndOrgIds(Date from, Date to,
			String corporationId) {
		StringBuffer eql = new StringBuffer(
				"SELECT DISTINCT(staff.id) FROM Staff staff WHERE 1=1 ");
		eql.append(" AND staff.deleteMarkConstant=:deleteMarkConstant ");
		eql.append(" AND staff.corporation.id=:corporationId ");

		Calendar c = Calendar.getInstance();
		c.setTime(from);
		int yearFrom = c.get(Calendar.YEAR);
		int monthFrom = c.get(Calendar.MONTH) + 1; // XXX why it need to offset?
		int dayFrom = c.get(Calendar.DAY_OF_MONTH);
		c.setTime(to);
		int yearTo = c.get(Calendar.YEAR);
		int monthTo = c.get(Calendar.MONTH) + 1; // XXX why it needs to offset?
		int dayTo = c.get(Calendar.DAY_OF_MONTH);

		// 如果不是闰年，而且dayTo是28号，那么加一天.
		// special day 2.29 -- 平时选不到2.29,如果to为2.28且当年没有2.29.那就延长到2.29
		// XXX there are leap year library. This may not be a good
		// implementation.
		// TODO extract to a external function
		if (!((0 == yearTo % 400) || (0 == yearTo % 4 && 0 != yearTo % 100))
				&& (monthTo == 2 && dayTo == 28)) {
			dayTo++;
		}

		if (yearFrom == yearTo) {
			eql.append(" AND ( (MONTH(staff.dob) = :monthFrom AND DAY(staff.dob) > :dayFrom) ");
			if (monthFrom == monthTo) {
				eql.append("  AND (MONTH(staff.dob) = :monthTo AND DAY(staff.dob) <= :dayTo) ) ");
			} else {
				eql.append(" OR (MONTH(staff.dob) > :monthFrom AND MONTH(staff.dob) < :monthTo) ");
				eql.append(" OR (MONTH(staff.dob) = :monthTo AND DAY(staff.dob) <= :dayTo) ) ");
			}
		} else if (yearFrom < yearTo) {
			eql.append(" AND ( (MONTH(staff.dob) = :monthFrom AND DAY(staff.dob) > :dayFrom)");
			eql.append(" OR (MONTH(staff.dob) > :monthFrom OR MONTH(staff.dob) < :monthTo) ");
			eql.append(" OR (MONTH(staff.dob) = :monthTo AND DAY(staff.dob) <= :dayTo) ) ");

		} else {
			// Data error
			eql.append(" AND 1 <> 1");
		}
		// return getEm()
		// .createQuery(
		// " SELECT DISTINCT(staff.id) FROM Staff staff WHERE 1=1 "
		// + " AND staff.deleteMarkConstant=:deleteMarkConstant "
		// + " AND staff.corporation.id=:corporationId "
		// + " AND staff.dob>:from AND staff.dob<=:to ")
		// .setParameter("from", from)
		// .setParameter("to", to)
		// .setParameter("deleteMarkConstant", DeleteMarkConstant.ACTIVING)
		// .setParameter("corporationId", corporationId).getResultList();

		logger.debug(
				"yearFrom:{}, monthFrom:{}, dayFrom:{}, yearTo:{}, monthTo:{}, dayTo:{}",
				new Object[] { yearFrom, monthFrom, dayFrom, yearTo, monthTo,
						dayTo });

		return getEm()
				.createQuery(eql.toString())
				.setParameter("monthFrom", monthFrom)
				.setParameter("dayFrom", dayFrom)
				.setParameter("monthTo", monthTo)
				.setParameter("dayTo", dayTo)
				.setParameter("deleteMarkConstant", DeleteMarkConstant.ACTIVING)
				.setParameter("corporationId", corporationId).getResultList();
	}
}
