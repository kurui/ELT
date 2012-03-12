package com.chinarewards.elt.service.reward;

import java.util.List;

import com.chinarewards.elt.dao.reward.PreWinnerDao;
import com.chinarewards.elt.domain.reward.person.PreWinnerLot;
import com.chinarewards.elt.model.common.SortingDetail;
import com.chinarewards.elt.model.reward.search.RewardGridSearchVo;
import com.chinarewards.elt.service.common.JPATestCase;

public class PreWinnerDaoTest extends JPATestCase {
	public void testRewardHistory() {

		PreWinnerDao preWinnerDao = injector.getInstance(PreWinnerDao.class);

		RewardGridSearchVo searchVo = new RewardGridSearchVo();
		SortingDetail sortDetail=new SortingDetail();
		
		searchVo.setSortingDetail(sortDetail);
		searchVo.getSortingDetail().setSort("reward.rewardItem.name");
		List<PreWinnerLot> preLotList=preWinnerDao.queryRewardHistoryData(searchVo);
		int count=preWinnerDao.queryRewardHistoryCount(searchVo);
		
		System.out.println(preLotList);
		System.out.println("count:"+count);
//		assertEquals(3,count);
	}

}
