package com.chinarewards.gwt.elt.client.gift.presenter;

import java.util.Date;
import net.customware.gwt.dispatch.client.DispatchAsync;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.frequency.CalculatorSelectFactory;
import com.chinarewards.gwt.elt.client.frequency.RewardStartDateCalculator;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.gift.module.GiftClient;
import com.chinarewards.gwt.elt.client.gift.plugin.GiftConstants;
import com.chinarewards.gwt.elt.client.gift.request.AddGiftRequest;
import com.chinarewards.gwt.elt.client.gift.request.AddGiftResponse;
import com.chinarewards.gwt.elt.client.gift.request.SearchGiftByIdRequest;
import com.chinarewards.gwt.elt.client.gift.request.SearchGiftByIdResponse;
import com.chinarewards.gwt.elt.client.rewardItem.dialog.ChooseStaffWinDialog;
import com.chinarewards.gwt.elt.client.rewardItem.dialog.FrequencySettingDialog;
import com.chinarewards.gwt.elt.client.rewardItem.presenter.ChooseStaffBlockPresenter;
import com.chinarewards.gwt.elt.client.rewards.model.ParticipateInfoClient;
import com.chinarewards.gwt.elt.client.rewards.model.ParticipateInfoClient.EveryoneClient;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsBaseInfo;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class GiftPresenterImpl extends BasePresenter<GiftPresenter.GiftDisplay>
		implements GiftPresenter {
	String instanceId;// 修改时传过来的ID

	/**
	 * 是否为修改页，默认为false
	 */
	private boolean isEditPage = false;
	private String giftId;

	/**
	 * 选择员工模块
	 */
	private final ChooseStaffBlockPresenter staffBlock;
	private final DispatchAsync dispatcher;
	private final ErrorHandler errorHandler;
	private final Provider<FrequencySettingDialog> freProvider;
	// private final Win win;
	private final SessionManager sessionManager;
	private final RewardStartDateCalculator startDateCalculator;
	private final CalculatorSelectFactory factory;
	GiftClient item;

	// 用来判断修改前是否有平率
	private boolean isFrequency = false;
	private final Provider<ChooseStaffWinDialog> chooseStaffDialogProvider;

	@Inject
	public GiftPresenterImpl(EventBus eventBus,
			ChooseStaffBlockPresenter staffBlock, GiftDisplay display,
			DispatchAsync dispatcher, ErrorHandler errorHandler,
			Provider<FrequencySettingDialog> freProvider,
			RewardStartDateCalculator startDateCalculator,
			CalculatorSelectFactory factory, SessionManager sessionManager,
			Provider<ChooseStaffWinDialog> chooseStaffDialogProvider) {
		super(eventBus, display);
		this.dispatcher = dispatcher;
		this.staffBlock = staffBlock;
		this.errorHandler = errorHandler;
		this.freProvider = freProvider;
		this.startDateCalculator = startDateCalculator;
		this.factory = factory;
		this.sessionManager = sessionManager;
		this.chooseStaffDialogProvider = chooseStaffDialogProvider;
	}

	@Override
	public void bind() {
		// 绑定事件
		init();
		// 候选人面板显示
		staffBlock.bind();
		// display.initStaffBlock(staffBlock.getDisplay().asWidget());
	}

	private void init() {
		// 提名人按钮事件
		// registerHandler(display.getChooseStaffBtnClick().addClickHandler(
		// new ClickHandler() {
		// @Override
		// public void onClick(ClickEvent arg0) {
		// final ChooseStaffWinDialog dialog = chooseStaffDialogProvider
		// .get();
		// dialog.setNominee(false, true, null);
		// final HandlerRegistration registration = eventBus
		// .addHandler(ChooseStaffEvent.getType(),
		// new ChooseStaffHandler() {
		// @Override
		// public void chosenStaff(
		// List<StaffClient> list) {
		// for (StaffClient r : list) {
		//
		// if (!display
		// .getSpecialTextArea()
		// .containsItem(r)) {
		//
		// display.getSpecialTextArea()
		// .addItem(r);
		// }
		// }
		// }
		// });
		//
		// Platform.getInstance().getSiteManager()
		// .openDialog(dialog, new DialogCloseListener() {
		// public void onClose(String dialogId,
		// String instanceId) {
		// registration.removeHandler();
		// }
		// });
		// }
		// }));
		// 保存事件
		registerHandler(display.getSaveClick().addClickHandler(
				new ClickHandler() {
					@Override
					public void onClick(ClickEvent arg0) {
						// validate first!
						// if (!validateFirst()) {
						// return;
						// }

						GiftClient gift = new GiftClient();
						// 基本信息
						gift.setName(display.getName().getValue().trim());
						// gift.setType(new RewardsTypeClient(display
						// .getRewardsType(), ""));//
						// gift.setDefinition(display.getDefinition().getValue()
						// .trim());
						// gift.setStandard(display.getStandard().getValue()
						// .trim());

						if (!isEditPage) {
							gift.setId("");
							doSave(gift);
						} else {
							gift.setId(giftId);
							doEdit(gift);// 修改功能
						}
					}
				}));
	}

	public ParticipateInfoClient getNominateInfo() {
		ParticipateInfoClient nominateInfo = null;
		// List<OrganicationClient> orgs = new ArrayList<OrganicationClient>();
		// if (display.getAutoCbx().getValue() == false) { // 自动奖时没有提名人ID
		// for (String orgId : display.getNominateIds()) {
		// orgs.add(new OrganicationClient(orgId, ""));
		// }
		// }
		// nominateInfo = new SomeoneClient(orgs);
		return nominateInfo;
	}

	private void doSave(GiftClient gift) {
		dispatcher.execute(
				new AddGiftRequest(gift, sessionManager.getSession()),
				new AsyncCallback<AddGiftResponse>() {
					@Override
					public void onFailure(Throwable t) {
						errorHandler.alert(t.toString());
					}

					@Override
					public void onSuccess(AddGiftResponse response) {
						Window.alert("添加成功");
						// if(instanceId!=null||!instanceId.equals(""))
						Platform.getInstance()
								.getEditorRegistry()
								.openEditor(
										GiftConstants.EDITOR_GIFTLIST_SEARCH,
										"EDITOR_REWARDSITEM_List_DO_ID",
										instanceId);

					}
				});
	}

	private void doEdit(GiftClient gift) {
		if (Window.confirm("确定修改?")) {
			dispatcher.execute(
					new AddGiftRequest(gift, sessionManager.getSession()),
					new AsyncCallback<AddGiftResponse>() {
						@Override
						public void onFailure(Throwable t) {
							Window.alert("修改失败");
							Platform.getInstance()
									.getEditorRegistry()
									.closeEditor(GiftConstants.EDITOR_GIFT_ADD,
											instanceId);
						}

						@Override
						public void onSuccess(AddGiftResponse arg0) {
							Window.alert("修改成功");
							Platform.getInstance()
									.getEditorRegistry()
									.openEditor(
											GiftConstants.EDITOR_GIFTLIST_SEARCH,
											"EDITOR_REWARDSITEM_List_DO_ID",
											instanceId);
						}
					});
		}
	}

	// // setting a new frequency.
	// private void doSettingFrequency(FrequencyClient frequency) {
	// display.showFrequencyInfo(frequency);
	// Date startDate = display.getStartTime().getValue();
	//
	// Date currentDate = new Date();
	// // 开始时间设为依据时间
	// RewardDateInfo info = new SimpleRewardDateInfo(startDate, currentDate,
	// lastRewardsDate);
	// Date startTime = startDateCalculator.calculateStartDate(info);
	// // 初始化时默认开始时间是当日时间
	// if (startTime == null) {
	// startTime = new Date();
	// }
	// NextRewardsDateCalculator cal = factory.getCalculator();
	// Date nextRewardsDate = cal.calNextRewardsDate(startTime, frequency);
	// startDate = startDate == null ? new Date() : startDate;
	// if (!isEditPage || (startDate.getTime() > nextRewardsDate.getTime())) {
	// // 开始时间和下次颁奖时间一样
	// display.getStartTime().setValue(nextRewardsDate);
	// } else if (!isEditPage
	// || (nextRewardsDate.getTime() >= startDateCopy.getTime())) {
	// if (isFrequency) {
	// display.getStartTime().setValue(startDateCopy);
	// } else {
	// display.getStartTime().setValue(nextRewardsDate);
	// }
	// } else if (isEditPage
	// && nextRewardsDate.getTime() <= startDateCopy.getTime()) {
	// display.getStartTime().setValue(nextRewardsDate);
	// }
	//
	// display.getNextRewardsTime().setValue(nextRewardsDate);
	//
	// }
	//
	// private void modifyNextRewardsSetDate(Date rewardsDate) {
	// FrequencyClient frequency = display.getFrequencyObj();
	// boolean toD = false;
	// // 没设定频率的时候，终止！
	// if (frequency == null) {
	// return;
	// }
	//
	// Date currentDate = new Date();
	// RewardDateInfo info = new SimpleRewardDateInfo(rewardsDate,
	// currentDate, lastRewardsDate);
	// // The begin time which to cal
	// // the next rewarded time.
	// Date startTime = startDateCalculator.calculateStartDate(info);
	// NextRewardsDateCalculator cal = factory.getCalculator();
	// Date nextRewardsDate = cal.calNextRewardsDate(startTime, frequency);
	// display.getNextRewardsTime().setValue(nextRewardsDate);
	//
	// rewardsDate = display.getNextRewardsTime().getValue();
	// if (rewardsDate.getTime() < startDateCopy.getTime()) {
	// display.getStartTime().setValue(rewardsDate);
	// } else {
	// display.getStartTime().setValue(startDateCopy);
	// }
	//
	// if (toD) {
	//
	// endDateCopy = nextRewardsDate;
	// }
	//
	// // 修改下次公布时间
	// modifyNextPublishTime();
	// }
	//
	// // 修改下次颁奖时间
	// private void modifyNextPublishTime() {
	// Date startDate = display.getNextRewardsTime().getValue();
	// Date date = DateTool.addSomeDay(startDate, 0 - day);
	// display.getNextPublishTime().setValue(date);
	// nextPublishCopy = date;
	//
	// }

	// private void modifyNextPublishTimeClew() {
	// DateTimeFormat format = DateTimeFormat
	// .getFormat(ViewConstants.date_format);
	// Date NextRewardsTimeDate = display.getNextRewardsTime().getValue();
	// if ((NextRewardsTimeDate.getTime() <= rewardsDateCopuy.getTime())
	// && !okAndNo) {
	// okAndNo = true;
	// Window.confirm("建议下次颁奖时间应该在上次颁奖时间("
	// + format.format(rewardsDateCopuy)
	// + ")之后！<br/>按“确定”回复到以前的设置！<br/>按“取消”使用新的时间！");
	//
	// } else if (NextRewardsTimeDate.getTime() > rewardsDateCopuy.getTime()) {
	// okAndNo = false;
	// nextRewardsDateCopy = NextRewardsTimeDate;
	//
	// }
	// }

	// // 奖项验证方法
	// private boolean validateFirst() {
	// boolean flag = true;
	// StringBuilder errorMsg = new StringBuilder();
	// if (display.getRewardsName().getValue() == null
	// || "".equals(display.getRewardsName().getValue().trim())) {
	// errorMsg.append("请填写奖项名称!<br>");
	//
	// flag = false;
	// }
	//
	// // 员工选择
	// if (staffBlock.getDisplay().isSomeone().getValue() == true) {
	// if (staffBlock.getDisplay().getRealOrginzationIds() == null) {
	// errorMsg.append("请选择候奖员工!<br>");
	// flag = false;
	//
	// }
	// if (staffBlock.getDisplay().getRealOrginzationIds() != null
	// && staffBlock.getDisplay().getRealOrginzationIds().size() == 0) {
	// errorMsg.append("请选择候奖员工!<br>");
	// flag = false;
	//
	// }
	// }
	//
	// if (display.getRewardsType() == null
	// || "".equals(display.getRewardsType().trim())) {
	// errorMsg.append("请选择奖项类型!<br>");
	// flag = false;
	// }
	//
	// if (display.getTotalJF() == null
	// || display.getTotalJF().intValue() <= 0) {
	// errorMsg.append("总积分额度出错，总积分要是正整数!<br>");
	// flag = false;
	// } else if (display.getRewardsFrom() == null
	// || display.getRewardsFrom().intValue() <= 0) {
	// errorMsg.append("每人得奖积分额度出错，积分要是正整数!<br>");
	// flag = false;
	// }
	//
	// if (display.getRewardsFrom() != null) {
	// if (display.getRewardsFrom().intValue() == 0) {
	// errorMsg.append("每人得奖积分“0”!<br>");
	// flag = false;
	// }
	// if (display.getRewardsFrom().intValue() > display.getTotalJF()
	// .intValue()) {
	// errorMsg.append("总积分不能小于个人得分!<br>");
	// flag = false;
	// }
	// }
	//
	// if (staffBlock.getDisplay().isSomeone().getValue() == true) { // 选择部分人员
	//
	// if (display.getPeopleSizeLimit().getValue() == null
	// || "".equals(display.getPeopleSizeLimit().getValue().trim())) {
	// errorMsg.append("请正确填写获奖名额(正整数)!<br>");
	// flag = false;
	// }
	// if (display.getPeopleSizeLimit().getValue() != null
	// && !"".equals(display.getPeopleSizeLimit().getValue()
	// .trim())) {
	// int limitPeople = Integer.parseInt(display.getPeopleSizeLimit()
	// .getValue());
	// if (limitPeople == 0 || limitPeople < 0) {
	// errorMsg.append("请正确填写获奖名额(正整数)!<br>");
	// flag = false;
	// }
	// }
	// }
	// if (display.getStartTime().getValue() == null
	// || "".equals(display.getStartTime().getValue())) {
	// errorMsg.append("开始时间不能为空<br>");
	// flag = false;
	// }
	//
	// // 周期性
	// if (display.getEnableCbx().getValue() == true) {
	//
	// if (display.getNextRewardsTime().getValue() == null
	// || "".equals(display.getNextRewardsTime().getValue())) {
	// errorMsg.append("下次颁奖时间不能为空!<br>");
	// flag = false;
	// } else if (display.getStartTime().getValue().getTime() > display
	// .getNextRewardsTime().getValue().getTime()) {
	// errorMsg.append("开始时间要小于或等于下次颁奖时间<br>");
	// flag = false;
	// }
	// if (display.getNominateIds().size() > 0
	// && (display.getTmday() == null
	// || display.getTmday().equals("") || display
	// .getTmday().intValue() <= 0)) {
	// errorMsg.append(" 要提前提名的天数是正整数!<br>");
	// flag = false;
	// }
	// // 生日奖校验
	// if (display.getSpecialCbx().getValue()
	// && display.getBirthRadio().getValue()) {
	// if (display.getFrequencyObj() == null) {
	// errorMsg.append("请选择频率!<br>");
	// flag = false;
	// } else if (display.getFrequencyObj() instanceof WeekFrequencyClient) {
	// errorMsg.append("生日奖不能为按周的频率!<br>");
	// flag = false;
	// } else if (display.getFrequencyObj() instanceof YearFrequencyClient) {
	// errorMsg.append("生日奖不能为按年的频率!<br>");
	// flag = false;
	// } else if (display.getFrequencyObj() instanceof DayFrequencyClient
	// && display.getFrequencyObj().getInterval() > 200) {
	// errorMsg.append("生日奖按日的频率，最大只能设到每200日<br>");
	// flag = false;
	// } else if (display.getFrequencyObj() instanceof MonthFrequencyClient
	// && display.getFrequencyObj().getInterval() > 12) {
	// errorMsg.append("生日奖按月的频率，最大只能设到每12月<br>");
	// flag = false;
	// }
	// } else {
	// // 普通有频率奖校验
	// if (display.getFrequencyObj() == null) {
	// errorMsg.append("请选择频率!<br>");
	// flag = false;
	// } else if (display.getFrequencyObj() instanceof WeekFrequencyClient
	// && display.getFrequencyObj().getInterval() > 20) {
	// errorMsg.append("按周的频率，最大只能设到每20周!<br>");
	// flag = false;
	// } else if (display.getFrequencyObj() instanceof YearFrequencyClient
	// && display.getFrequencyObj().getInterval() > 5) {
	// errorMsg.append("按年的频率，最大只能设到每5年!<br>");
	// flag = false;
	// } else if (display.getFrequencyObj() instanceof DayFrequencyClient
	// && display.getFrequencyObj().getInterval() > 200) {
	// errorMsg.append("按日的频率，最大只能设到每200日<br>");
	// flag = false;
	// } else if (display.getFrequencyObj() instanceof MonthFrequencyClient
	// && display.getFrequencyObj().getInterval() > 36) {
	// errorMsg.append("按月的频率，最大只能设到每36月<br>");
	// flag = false;
	// }
	// }
	//
	// if (!display.getAutoCbx().getValue()) {// 不是自动奖
	// if (display.getNextPublishTime().getValue() == null) {
	// errorMsg.append("请填写下一次公布颁奖时间!<br>");
	// flag = false;
	// } else if (display.getNextPublishTime().getValue() != null
	// && display.getNextRewardsTime().getValue() != null
	// && display.getNextPublishTime().getValue()
	// .after(display.getNextRewardsTime().getValue())) {
	// errorMsg.append("下一次公布奖励时间必须在下一次颁奖时间之前!<br>");
	// flag = false;
	// }
	// }
	// } else {// 一次性
	// if (display.getExpectTime().getValue() == null
	// || "".equals(display.getExpectTime().getValue())) {
	// errorMsg.append("预计颁奖时间不能为空!<br>");
	// flag = false;
	// } else if (display.getStartTime().getValue().getTime() > display
	// .getExpectTime().getValue().getTime()) {
	// errorMsg.append("开始时间要小于预计颁奖时间<br>");
	// flag = false;
	// }
	// if (display.getNominateIds().size() > 0
	// && (display.getTmdays() == null
	// || display.getTmdays().equals("") || display
	// .getTmdays().intValue() <= 0)) {
	// errorMsg.append(" 要提前提名的天数是正整数!<br>");
	// flag = false;
	// }
	//
	// // if(display.getNominateIds().size()==0){
	// // errorMsg.append("请选择提名人!<br>");
	// // flag = false;
	// // }
	// }
	// if (!flag) {
	// errorHandler.alert(errorMsg.toString());
	// }
	// return flag;
	// }

	// @Override
	// public void initInstanceId(String instanceId, GiftClient item) {
	// this.instanceId = instanceId;
	// initDataToEditGift(item);
	// }

	private void initDataToEditGift(final GiftClient item) {
		String id = item.getId();

		isEditPage = true;
		{
			dispatcher.execute(new SearchGiftByIdRequest(id),
					new AsyncCallback<SearchGiftByIdResponse>() {
						@Override
						public void onFailure(Throwable arg0) {
							errorHandler.alert("查询奖项出错!");
							Platform.getInstance()
									.getEditorRegistry()
									.closeEditor(GiftConstants.EDITOR_GIFT_ADD,
											instanceId);
						}

						@Override
						public void onSuccess(SearchGiftByIdResponse response) {
							GiftClient item = response.getGift();
							clear();
							// display.showGift(item);
							//
							// // 初始前一次颁奖时间
							// rewardsDateCopuy = item.getLastRewardedDate();
							// // 初始结束时间副本
							// endDateCopy = item.getEndTime();
							// // 初始下次公布时间副本
							// nextPublishCopy = item.getNextPublishTime();
							// // 记录最初的开始时间
							// Date stD = item.getStartTime();
							// if (stD == null)
							// stD = new Date();
							// startDateCopy = stD;
							// // 记录第一次赋值的颁奖时间
							// nextRewardsDateCopy = item.getNextTime();
							// if (nextPublishCopy != null
							// && nextRewardsDateCopy != null) {
							// day = DateTool.getIntervalDays(nextPublishCopy,
							// nextRewardsDateCopy);
							// }
							// // 用了记录修改前是否有平率
							// if (item.getFrequency() != null) {
							// isFrequency = true;
							// }
							// // display.showHistoryBtn();
							// staffBlock.getDisplay().showParticipateInfo(
							// item.getBaseInfo());
							// setId(item.getId());
							// lastRewardsDate = item.getLastRewardedDate();

						}
					});
			}
	}

	private void clear() {
		RewardsBaseInfo baseInfo = new RewardsBaseInfo();
		baseInfo.setParticipateInfo(new EveryoneClient());
		staffBlock.getDisplay().showParticipateInfo(baseInfo);
		display.clear();
	}

	public void setId(String id) {
		this.giftId = id;
		isEditPage = true;
	}
}
