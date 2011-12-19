package com.chinarewards.gwt.elt.client.rewardItem.view;

import java.util.Date;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.frequency.FrequencyCalculator;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.rewardItem.presenter.RewardsItemCreatePresenter.RewardsItemDisplay;
import com.chinarewards.gwt.elt.client.rewards.model.DayFrequencyClient;
import com.chinarewards.gwt.elt.client.rewards.model.FrequencyClient;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsAmountRuleClient;
import com.chinarewards.gwt.elt.client.rewards.model.UnifiedAmountRuleClient;
import com.chinarewards.gwt.elt.client.rewards.model.WeekFrequencyClient;
import com.chinarewards.gwt.elt.client.rewards.model.YearFrequencyClient;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.view.constant.CssStyleConstants;
import com.chinarewards.gwt.elt.client.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.inject.Inject;

public class RewardsItemWidget extends Composite implements RewardsItemDisplay {

	/** 基本信息 **/
	// 名称
	@UiField
	TextBox rewardsName;
	
	// 定义
	@UiField
	TextArea rewardsDefinition;
	// 标准
	@UiField
	TextArea standard;
	
	
	// 限制人数
	@UiField
	TextBox peopleSizeLimit;
	@UiField
	//总积分
	TextBox totalJF;
	@UiField
	TextBox rewardsFrom;
   //提前几天提名
	@UiField
	TextBox tmdays;

	/** 频率规则 **/
	// 频率是否生效
	@UiField RadioButton onetimes;
	@UiField RadioButton moretimes;
	// 频率设定文字
	@UiField
	Label settingText;
	// 频率设定按钮
	@UiField
	Button setting;
	// 开始时间
	@UiField
	DateBox startTime;
	//结束时间
	@UiField
	DateBox endTime;
	// 下次公布时间
	@UiField
	DateBox nextPublicTime;
	// 上次颁奖时间
	@UiField
	Label lastRewardsTime;
	// 下次颁奖时间
	@UiField
	DateBox nextRewardsTime;
	// 是否自动
	@UiField
	CheckBox autoCbx;
	// 特殊条件选择
	@UiField
	CheckBox specialCbx;
	// 生日奖
	@UiField
	RadioButton birthRadio;

	
	
	/** 选人规则 **/
	// 选人模块
	@UiField
	Panel staffPanel;

	
	// 保存或修改
	@UiField
	Button save;

	/** 存储有用的信息 **/
	FrequencyClient frequency;
	String rewardsUnit;

	// is inject
//	final DepartmentComboTree buildDept;
//	final DepartmentComboTree accountDept;

	// Set the format of datepicker.
	DateTimeFormat dateFormat = DateTimeFormat
			.getFormat(ViewConstants.date_format);

	interface RewardsItemWidgetBinder extends
			UiBinder<Widget, RewardsItemWidget> {

	}

	private static RewardsItemWidgetBinder uiBinder = GWT
			.create(RewardsItemWidgetBinder.class);

	//Win win;

		@Inject
		public RewardsItemWidget( DispatchAsync dispatch,
				ErrorHandler errorHandler, SessionManager sessionManager) {
			initWidget(uiBinder.createAndBindUi(this));
		//	this.buildDept = new DepartmentComboTree(dispatch, errorHandler,sessionManager);
		//	buildDept.setStyleName("text");
		//	this.accountDept = new DepartmentComboTree(dispatch, errorHandler,sessionManager);
		//	accountDept.setStyleName("text");
		//	this.win = win;
			init();
		}


	private void init() {
		rewardsDefinition.setHeight("2.5em");
		standard.setHeight("2.5em");
		startTime.setFormat(new DateBox.DefaultFormat(dateFormat));
		endTime.setFormat(new DateBox.DefaultFormat(dateFormat));
	//	String siteId = DOM.getElementById("tr1").getNodeName();

         //隐藏周期性
		settingText.getElement().getParentElement().getParentElement().getParentElement().addClassName(CssStyleConstants.hidden);
		nextRewardsTime.setFormat(new DateBox.DefaultFormat(dateFormat));
		nextPublicTime.setFormat(new DateBox.DefaultFormat(dateFormat));
		// settingText.setText("每1天一次");
		birthRadio.getElement().addClassName(CssStyleConstants.hidden);
		//周期性选择
		onetimes.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (event.getValue()) {
					settingText.getElement().getParentElement().getParentElement().getParentElement().addClassName(CssStyleConstants.hidden);
				} else {
					settingText.getElement().getParentElement().getParentElement().getParentElement().removeClassName(CssStyleConstants.hidden);
				}
			}
		});
		moretimes.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (event.getValue()) {
					settingText.getElement().getParentElement().getParentElement().getParentElement().removeClassName(CssStyleConstants.hidden);
				} else {
					settingText.getElement().getParentElement().getParentElement().getParentElement().addClassName(CssStyleConstants.hidden);
				}
			}
		});
		// 特殊条件
		specialCbx.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (event.getValue()) {
					birthRadio.setValue(true, true);
					birthRadio.getElement().removeClassName(
							CssStyleConstants.hidden);
				} else {
					birthRadio.getElement().addClassName(
							CssStyleConstants.hidden);
				}

			}

		});

		// 生日奖
		birthRadio.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (event.getValue()) {
					FrequencyClient frequecny = getFrequencyObj();
					if (frequecny == null
							|| frequecny instanceof WeekFrequencyClient
							|| frequecny instanceof YearFrequencyClient) {
						Window.alert("生日奖必须为每日或每月，已重设为每天一次");
						DayFrequencyClient daily = new DayFrequencyClient();
						daily.setInterval(1);
						showFrequencyInfo(daily);
					}
				}
			}
		});

		
		// 自动颁奖
		autoCbx.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (event.getValue()) {
					
					nextPublicTime.getElement().getParentElement()
							.getParentElement()
							.addClassName(CssStyleConstants.hidden);
				} else {
					
					nextPublicTime.getElement().getParentElement()
							.getParentElement()
							.removeClassName(CssStyleConstants.hidden);
				}

			}

		});
	}
//	public native void show() /*-{
//       $wnd.alert("dd");
//	}-*/;
	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public HasClickHandlers getFrequencySettingClick() {
		return setting;
	}

	@Override
	public HasValue<Date> getNextPublishTime() {
		return nextPublicTime;
	}

	

	
	@Override
	public HasClickHandlers getSaveClick() {
		return save;
	}

	
	
	
	@Override
	public void clear() {
		rewardsName.setTitle("");
		/* 增加请选择下拉选项 */
		//rewardsType.setSelectedIndex(0);
		rewardsDefinition.setText("");
		standard.setText("");
		startTime.setValue(null);
		endTime.setValue(null);
		
		peopleSizeLimit.setValue("");
		frequency = null;
		settingText.setText("");
		autoCbx.setValue(false, true);
		specialCbx.setValue(false, true);
		birthRadio.setValue(false, true);

		//nextPublicTime.setValue(null);
		nextRewardsTime.setValue(null);
		//lastRewardsTime.setText("");
		// 清空设定规则为为设定
		//setIsAmountLevel("未设定");
	}
	@Override
	public HasValue<String> getRewardsName() {
		return rewardsName;
	}

	
	@Override
	public HasValue<String> getDefinition() {
		return rewardsDefinition;
	}

	@Override
	public HasValue<String> getStandard() {
		return standard;
	}

	@Override
	public Integer getRewardsFrom() {
		if (rewardsFrom.getText() == null
				|| "".equals(rewardsFrom.getText().trim())) {
			return null;
		} else {
			try {
				int d = Integer.parseInt(rewardsFrom.getText().trim());
				return d;
			} catch (Exception e) {
				return new Integer(-1);
			}
		}
	}

	

	@Override
	public FrequencyClient getFrequencyObj() {
		return frequency;
	}

	@Override
	public HasValue<Date> getStartTime() {
		return startTime;
	}

	@Override
	public void initStaffBlock(Widget w) {
		staffPanel.add(w);
	}

	
	@Override
	public HasValue<Date> getNextRewardsTime() {
		return nextRewardsTime;
	}

	@Override
	public HasValue<Boolean> getAutoCbx() {
		return autoCbx;
	}

	@Override
	public String getRewardsUnit() {
		return rewardsUnit;
	}

	@Override
	public HasValueChangeHandlers<Date> getStartTimeChangeHandler() {
		return startTime;
	}

	@Override
	public HasValueChangeHandlers<Date> getRewardsTimeChangeHandler() {
		return nextRewardsTime;
	}

	@Override
	public HasValue<Boolean> getSpecialCbx() {
		return specialCbx;
	}

	@Override
	public HasValue<Boolean> getBirthRadio() {
		return birthRadio;
	}


	

	
	private void showLastRewardsPanel(Date lastRewardsDate) {
		if (lastRewardsDate != null) {
			lastRewardsTime.setText("上次颁奖时间："
					+ dateFormat.format(lastRewardsDate));
		} else {
			lastRewardsTime.setText("没有找到上次颁奖时间");
		}

	}

	
	@Override
	public HasValue<String> getPeopleSizeLimit() {
		return peopleSizeLimit;
	}

	

	

	// show amount rule data
	private void showAmountRuleData(RewardsAmountRuleClient ruleClient) {
		if (ruleClient == null) {
			// do nothing
		} else if (ruleClient instanceof UnifiedAmountRuleClient) {
			// unified rule
			
			rewardsFrom.setValue(StringUtil
					.valueOf(((UnifiedAmountRuleClient) ruleClient)
							.getRewardsFrom()));
			
		} 
	}

//	// 显示设立部门和入账部门
//	private void showBuilderAndAccountDept(DepartmentClient builderDept,
//			DepartmentClient accountDept) {
//		if (builderDept != null && builderDept.getId() != null
//				&& accountDept.getId() != null) {
//			if (builderDept.getId().equals(accountDept.getId())) {
//				needAccountDept.setValue(false, true);
//				this.buildDept.setDefaultValue(new DepartmentClient(builderDept
//						.getId(), builderDept.getName()));
//			} else {
//				needAccountDept.setValue(true, true);
//				this.buildDept.setDefaultValue(new DepartmentClient(builderDept
//						.getId(), builderDept.getName()));
//				this.accountDept.setDefaultValue(new DepartmentClient(
//						accountDept.getId(), accountDept.getName()));
//			}
//		}
//	}

	@Override
	public void showFrequencyInfo(FrequencyClient frequency) {
		String text = FrequencyCalculator.getTextFromFrequency(frequency);
		this.frequency = frequency;
		settingText.setText(text);
	}

	
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinarewards.hr.gwt.mvp.client.rewards.presenter.
	 * RewardsItemCreatePresenter
	 * .RewardsItemCreateDisplay#setNextRewardsTimeVisible(boolean)
	 */
	@Override
	public void setNextRewardsTimeVisible(boolean visible) {
		this.nextRewardsTime.setVisible(visible);
	}

	

	@Override
	public TextBox getRewardsFromFocus() {
		return rewardsFrom;
	}

	
	

	@Override
	public CheckBox getAutoCbxElement() {
		return autoCbx;
	}


	@Override
	public Integer getTmdays() {
		
		if (tmdays.getText() == null
				|| "".equals(tmdays.getText().trim())) {
			return null;
		} else {
			try {
				int d = Integer.parseInt(tmdays.getText().trim());
				return d;
			} catch (Exception e) {
				return new Integer(-1);
			}
		}
	}


	@Override
	public HasValue<Boolean> getEnableCbx() {
		
		return moretimes;
	}


	@Override
	public HasValueChangeHandlers<Boolean> onetimesClick() {
		// TODO Auto-generated method stub
		return onetimes;
	}


	@Override
	public HasValueChangeHandlers<Boolean> moretimesClick() {
		// TODO Auto-generated method stub
		return moretimes;
	}


	@Override
	public HasValue<Date> getEndTime() {
		// TODO Auto-generated method stub
		return this.endTime;
	}


	@Override
	public String getRewardsType() {
		// 暂时没有设置启用
		return "客户自定义";
	}


	@Override
	public Integer getTotalJF() {
		
		if (totalJF.getText() == null
				|| "".equals(totalJF.getText().trim())) {
			return null;
		} else {
			try {
				int d = Integer.parseInt(totalJF.getText().trim());
				return d;
			} catch (Exception e) {
				return new Integer(-1);
			}
		}
	}


	
	


	


	
}
