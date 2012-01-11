package com.chinarewards.gwt.elt.server.gift;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;
import org.slf4j.Logger;
import com.chinarewards.elt.domain.gift.Gift;
import com.chinarewards.elt.model.reward.frequency.DailyVo;
import com.chinarewards.elt.model.reward.frequency.MonthlyVo;
import com.chinarewards.elt.model.reward.frequency.RewardsFrequency;
import com.chinarewards.elt.model.reward.frequency.WeekDays;
import com.chinarewards.elt.model.reward.frequency.WeeklyVo;
import com.chinarewards.elt.model.reward.frequency.YearlyVo;
import com.chinarewards.elt.model.user.UserContext;
import com.chinarewards.elt.service.gift.GiftService;
import com.chinarewards.gwt.elt.client.gift.model.GiftVo;
import com.chinarewards.gwt.elt.client.gift.request.AddGiftRequest;
import com.chinarewards.gwt.elt.client.gift.request.AddGiftResponse;
import com.chinarewards.gwt.elt.client.rewards.model.DayFrequencyClient;
import com.chinarewards.gwt.elt.client.rewards.model.FrequencyClient;
import com.chinarewards.gwt.elt.client.rewards.model.MonthFrequencyClient;
import com.chinarewards.gwt.elt.client.rewards.model.WeekFrequencyClient;
import com.chinarewards.gwt.elt.client.rewards.model.YearFrequencyClient;
import com.chinarewards.gwt.elt.server.BaseActionHandler;
import com.chinarewards.gwt.elt.server.logger.InjectLogger;
import com.chinarewards.gwt.elt.util.UserRoleTool;
import com.google.inject.Inject;

public class AddGiftHandler extends
		BaseActionHandler<AddGiftRequest, AddGiftResponse> {

	@InjectLogger
	Logger logger;
	GiftService giftService;

	@Inject
	public AddGiftHandler(GiftService giftService) {
		this.giftService = giftService;
	}

	@Override
	public Class<AddGiftRequest> getActionType() {
		return AddGiftRequest.class;
	}

	@Override
	public AddGiftResponse execute(AddGiftRequest action,
			ExecutionContext context) throws DispatchException {
		logger.debug("AddGiftResponse , gift:" + action.getGiftVo().toString());
		logger.debug("AddGiftResponse ,rewardId=" + action.getGiftVo().getId());
		GiftVo giftVo = action.getGiftVo();
		Gift gift = assemblegift(giftVo);

		UserContext uc = new UserContext();
		uc.setCorporationId(action.getUserSession().getCorporationId());
		uc.setLoginName(action.getUserSession().getLoginName());
		uc.setUserId(action.getUserSession().getToken());
		uc.setUserRoles(UserRoleTool.adaptToRole(action.getUserSession()
				.getUserRoles()));
		Gift AdddItem = giftService.save(uc, gift);

		return new AddGiftResponse(AdddItem.getId());
	}

	// Convert from GiftVo to GeneratorGiftModel.
	private Gift assemblegift(GiftVo giftVo) {
		Gift gift = new Gift();
		System.out.println("assemblegift-=====giftVo.getId:"+giftVo.getId());
		 gift.setId(giftVo.getId());
		gift.setName(giftVo.getName());

		gift.setExplains(giftVo.getExplains().trim());
		gift.setType(giftVo.getType().trim());
		gift.setSource(giftVo.getSource().trim());
		// gift.setBusiness(giftVo.getBusiness().trim());
		gift.setAddress(giftVo.getAddress().trim());
		gift.setTell(giftVo.getTell().trim());
		gift.setStock(giftVo.getStock());
		gift.setPhoto(giftVo.getPhoto());
		// gift.setGiftStatus();
		// gift.setIndate(getIndate());

		// private String name; //礼品名
		// private String explains; //说明
		// private String type; //礼品类型
		// private String source; //来源
		// private String business; //供应商
		// private String address; //地址
		// private String tell; //电话
		// private int stock; //库存
		// private String photo; //图片
		// private GiftStatus status; //状态（上下架）
		// private boolean deleted; //删除状态
		// private Date indate ; //有效截止期

		return gift;
	}

	private RewardsFrequency adapterFrequency(FrequencyClient frequency) {

		RewardsFrequency ret = null;

		if (frequency instanceof DayFrequencyClient) {
			// daily
			ret = new DailyVo(((DayFrequencyClient) frequency).getInterval());
		} else if (frequency instanceof WeekFrequencyClient) {
			// weekly

			WeekDays[] days = new WeekDays[7];
			List<Integer> weekDays = ((WeekFrequencyClient) frequency)
					.getWeekDays();
			List<WeekDays> newWeekDays = new ArrayList<WeekDays>();
			for (int i : weekDays) {
				newWeekDays.add(getWeekConstbyInt(i));
			}

			WeekFrequencyClient weekFrequencyClient = (WeekFrequencyClient) frequency;

			ret = new WeeklyVo(weekFrequencyClient.getInterval(),
					newWeekDays.toArray(new WeekDays[0]));

		} else if (frequency instanceof MonthFrequencyClient) {

			MonthFrequencyClient casted = (MonthFrequencyClient) frequency;
			ret = new MonthlyVo(casted.getInterval(), casted.getMonthDay());

		} else if (frequency instanceof YearFrequencyClient) {

			YearFrequencyClient casted = (YearFrequencyClient) frequency;
			ret = new YearlyVo(casted.getInterval(), casted.getYearMonth(),
					casted.getYearMonthDay());

		}

		return ret;
	}

	private WeekDays getWeekConstbyInt(int i) {
		WeekDays wc = null;
		switch (i) {
		case 1:
			wc = WeekDays.MON;
			break;
		case 2:
			wc = WeekDays.TUS;
			break;
		case 3:
			wc = WeekDays.WEN;
			break;
		case 4:
			wc = WeekDays.THUR;
			break;
		case 5:
			wc = WeekDays.FRI;
			break;
		case 6:
			wc = WeekDays.SAT;
			break;
		case 7:
			wc = WeekDays.SUN;
		}

		return wc;
	}

	@Override
	public void rollback(AddGiftRequest action, AddGiftResponse result,
			ExecutionContext context) throws DispatchException {

	}

}
