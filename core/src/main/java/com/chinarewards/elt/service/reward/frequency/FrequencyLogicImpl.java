package com.chinarewards.elt.service.reward.frequency;

import java.util.Date;
import java.util.List;

import com.chinarewards.elt.dao.reward.FrequencyDao;
import com.chinarewards.elt.dao.reward.WeekFrequencyDaysDao;
import com.chinarewards.elt.domain.reward.frequency.Frequency;
import com.chinarewards.elt.domain.reward.frequency.WeekFrequency;
import com.chinarewards.elt.domain.reward.frequency.WeekFrequencyDays;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.common.DateRangeModel;
import com.chinarewards.elt.model.reward.frequency.RewardsFrequency;
import com.google.inject.Inject;

/**
 * The implementation of {@link FrequencyLogic}
 * 
 * @author yanxin
 * @since 1.0
 */
public class FrequencyLogicImpl implements FrequencyLogic {

	FrequencyFactory frequencyFactory;
	FrequencyDao frequencyDao;
	WeekFrequencyDaysDao weekFrequencyDaysDao;

	@Inject
	public FrequencyLogicImpl(FrequencyFactory frequencyFactory,
			FrequencyDao frequencyDao, WeekFrequencyDaysDao weekFrequencyDaysDao) {
		this.frequencyFactory = frequencyFactory;
		this.frequencyDao = frequencyDao;
		this.weekFrequencyDaysDao = weekFrequencyDaysDao;
	}

	@Override
	public void removeFrequencyFromRewardItem(String rewardItemId) {
		Frequency f = frequencyDao.findFrequencyByRewardItemId(rewardItemId);
		if (f instanceof WeekFrequency) {
			List<WeekFrequencyDays> weekDays = weekFrequencyDaysDao
					.findWeekFrequencyDaysByFrequencyId(f.getId());
			for (WeekFrequencyDays days : weekDays) {
				weekFrequencyDaysDao.delete(days);
			}
		}
		frequencyDao.delete(f);
	}
	
	@Override
	public void removeFrequencyFromRewardItemStore(String rewardItemStoreId) {
		Frequency f = frequencyDao.findFrequencyByRewardStoreItemId(rewardItemStoreId);
		if (f instanceof WeekFrequency) {
			List<WeekFrequencyDays> weekDays = weekFrequencyDaysDao
					.findWeekFrequencyDaysByFrequencyId(f.getId());
			for (WeekFrequencyDays days : weekDays) {
				weekFrequencyDaysDao.delete(days);
			}
		}
		frequencyDao.delete(f);
	}

	@Override
	public void bindFrequencyToRewardItem(SysUser caller, String rewardItemId,
			RewardsFrequency frequency) {
		frequencyFactory.getProcessor(frequency).bindFrequencyToRewardItem(
				caller, rewardItemId, frequency);
	}
	@Override
	public void bindFrequencyToRewardItemStore(SysUser caller, String rewardItemStoreId,
			RewardsFrequency frequency) {
		frequencyFactory.getProcessor(frequency).bindFrequencyToRewardItemStore(
				caller, rewardItemStoreId, frequency);
	}
	@Override
	public Frequency getFrequencyOfRewardItem(String rewardItemId) {
		Frequency f = frequencyDao.findFrequencyByRewardItemId(rewardItemId);
		if (f instanceof WeekFrequency) {
			List<WeekFrequencyDays> weekDays = weekFrequencyDaysDao
					.findWeekFrequencyDaysByFrequencyId(f.getId());
			WeekFrequency weekFrequency = (WeekFrequency) f;
			weekFrequency.setWeekFrequencyDays(weekDays);
		}

		return f;
	}

	@Override
	public DateRangeModel calDateRangeFromFrequency(Frequency frequency,
			Date currTime) {
		return frequencyFactory.getProcessor(frequency).generateDateRange(
				frequency, currTime);
	}

	@Override
	public String calRewardNameFromFrequency(String name, Frequency frequency,
			Date runTime) {
		return frequencyFactory.getProcessor(frequency).generateRewardName(
				name, frequency, runTime);
	}

	@Override
	public Date calNextAwardTime(Frequency frequency, Date lastRunTime) {
		return frequencyFactory.getProcessor(frequency).calNextRunTime(
				frequency, lastRunTime);
	}
}
