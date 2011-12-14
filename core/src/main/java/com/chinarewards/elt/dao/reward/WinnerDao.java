package com.chinarewards.elt.dao.reward;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import com.chinarewards.elt.common.BaseDao;
import com.chinarewards.elt.dao.org.StaffDao;
import com.chinarewards.elt.domain.org.Staff;
import com.chinarewards.elt.domain.reward.person.Winner;
import com.chinarewards.elt.model.reward.base.WinnerProcessFlag;
import com.google.inject.Inject;

/**
 * DAO of {@link Winner}
 * 
 * @author yanxin
 * @since 1.0
 */
public class WinnerDao extends BaseDao<Winner> {
	StaffDao staffDao;

	@Inject
	public WinnerDao(StaffDao staffDao) {
		this.staffDao = staffDao;
	}

	@SuppressWarnings("unchecked")
	public List<Winner> findUntreatedWinnersByRewardId(String rewardId) {
		return getEm()
				.createQuery(
						" FROM Winner win WHERE win.reward.id=:rewardId AND win.processFlag=:processFlag ")
				.setParameter("processFlag", WinnerProcessFlag.UNPROCESS)
				.setParameter("rewardId", rewardId).getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<Winner> findWinnersByRewardId(String rewardId) {
		return getEm()
				.createQuery(" FROM Winner win WHERE win.reward.id=:rewardId ")
				.setParameter("rewardId", rewardId).getResultList();

	}

	// 查出某个员工在某年获得的生日奖记录
	// 如果 2010-12-23 --- 2011-01-22(以哪一年为准呢).看具体的生日日期
	@SuppressWarnings("unchecked")
	public List<Winner> findWinnersByStaffAndDateRangeOfDobReward(
			String staffId, String rewardItemId, Date from, Date to) {

		Calendar c = Calendar.getInstance();
		c.setTime(from);
		int yearFrom = c.get(Calendar.YEAR);
		int monthFrom = c.get(Calendar.MONTH);
		int dayFrom = c.get(Calendar.DAY_OF_MONTH);
		c.setTime(to);
		int yearTo = c.get(Calendar.YEAR);
		int monthTo = c.get(Calendar.MONTH) + 1;
		int dayTo = c.get(Calendar.DAY_OF_MONTH);

		logger.debug(
				"yearFrom:{}, monthFrom{},dayFrom{}, yearTo:{},monthTo:{},dayTo:{}",
				new Object[] { yearFrom, monthFrom, dayFrom, yearTo, monthTo,
						dayTo });

		StringBuffer eql = new StringBuffer(
				" FROM Winner win WHERE win.organization.id = :staffId AND win.reward.rewardItem.id = :rewardItemId ");
		eql.append(" AND YEAR(win.reward.awardDate) = :year ");

		Query q = getEm().createQuery(eql.toString());
		q.setParameter("staffId", staffId);
		q.setParameter("rewardItemId", rewardItemId);
		if (yearFrom == yearTo) {
			q.setParameter("year", yearFrom);
		} else if (yearFrom < yearTo) {
			// Get which year the staff
			Staff staff = staffDao.findById(Staff.class, staffId);
			Date dob = staff.getDob();
			c.setTime(dob);
			if ((c.get(Calendar.MONTH) == monthFrom && c
					.get(Calendar.DAY_OF_MONTH) > dayFrom)
					|| c.get(Calendar.MONTH) > monthFrom) {
				q.setParameter("year", yearFrom);
			} else {
				q.setParameter("year", yearTo);
			}
		}
		return q.getResultList();
	}
}
