package com.chinarewards.elt.service.reward.frequency;

import java.util.Date;

import com.chinarewards.elt.domain.reward.base.RewardItem;
import com.chinarewards.elt.domain.reward.frequency.Frequency;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.common.DateRangeModel;
import com.chinarewards.elt.model.reward.frequency.RewardsFrequency;

/**
 * The processor to deal with concrete cases about frequency. It defines some
 * commonly methods to that.
 * 
 * @author yanxin
 * @since 1.0
 */
public interface FrequencyProcessor {
	/**
	 * Bind the given frequency to the specified {@link RewardItem}.
	 * 
	 * @param rewardItemId
	 * @param frequency
	 * @return
	 */
	public Frequency bindFrequencyToRewardItem(SysUser caller,
			String rewardItemId, RewardsFrequency frequency);

	/**
	 * Get a suitable name according to the frequency. e.g. Every month run a
	 * time means the name may be "name"+ "January".
	 * 
	 * @param name
	 * @param frequency
	 * @return
	 */
	public String generateRewardName(String name, RewardsFrequency frequency);

	/**
	 * Calculate next run time according to the frequency.
	 * 
	 * @param frequency
	 * @param lastRunTime
	 * @return
	 */
	public Date calNextRunTime(RewardsFrequency frequency, Date lastRunTime);

	/**
	 * Calculate the data range from the specified {@link RewardsFrequency}.
	 * 
	 * @param frequency
	 * @param currTime
	 * @return
	 */
	public DateRangeModel generateDateRange(RewardsFrequency frequency,
			Date currTime);

	/**
	 * Calculate the date range from the specified {@link Frequency}
	 * 
	 * @param frequency
	 * @param currTime
	 * @return
	 */
	public DateRangeModel generateDateRange(Frequency frequency, Date currTime);
}
