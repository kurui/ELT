package com.chinarewards.elt.service.gift;

import com.chinarewards.elt.domain.gift.Gift;
import com.chinarewards.elt.domain.org.Corporation;
import com.chinarewards.elt.domain.user.SysUser;
import com.chinarewards.elt.model.common.PageStore;
import com.chinarewards.elt.model.org.CorporationVo;
import com.chinarewards.elt.model.reward.search.RewardItemSearchVo;
import com.chinarewards.elt.model.reward.vo.RewardItemVo;
import com.chinarewards.elt.model.user.UserContext;

/**
 * Service of corporation.
 * 
 * @author lw
 * @since 1.5
 */
public interface GiftService {

	/**
	 * 保存
	 * @param context
	 * @param gift
	 * @return
	 */
	public Gift save(UserContext context, Gift gift);

	/**
	 * 查找根据ID
	 * @param id
	 * @return
	 */
	public Gift findGiftById(String id);
	/**
	 * 删除礼品根据ID
	 * @param id
	 * @return
	 */
	public String deleteGift(String id);
	/**
	 * 礼品列表
	 * @param context
	 * @param gift
	 * @return
	 */
	public PageStore<Gift> giftList(UserContext context,Gift gift);

	/**
	 * 上下架
	 * @param id
	 * @return
	 */
	public String updateStatus(String id);
}
