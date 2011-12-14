package com.chinarewards.gwt.elt.client.rewardItem.view;

import java.util.Date;
import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.inject.Inject;
import com.chinarewards.gwt.elt.client.frequency.FrequencyCalculator;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.rewards.model.DayFrequencyClient;
import com.chinarewards.gwt.elt.client.rewards.model.FrequencyClient;
import com.chinarewards.gwt.elt.client.rewards.model.WeekFrequencyClient;
import com.chinarewards.gwt.elt.client.rewards.model.YearFrequencyClient;
import com.chinarewards.gwt.elt.client.rewardItem.presenter.RewardsItemCreatePresenter.RewardsItemDisplay;
import com.chinarewards.gwt.elt.client.rewards.model.DepartmentClient;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsAmountRuleClient;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsItemClient;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsTemplateClient;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsTypeClient;
import com.chinarewards.gwt.elt.client.rewards.model.SpecialCondition;
import com.chinarewards.gwt.elt.client.rewards.model.StaffLevelAmountRuleClient;
import com.chinarewards.gwt.elt.client.rewards.model.UnifiedAmountRuleClient;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.view.constant.CssStyleConstants;
import com.chinarewards.gwt.elt.client.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.gwt.user.client.ui.AbsolutePanel;
public class RewardsItemWidget extends Composite implements RewardsItemDisplay {

	

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
	@UiField AbsolutePanel panel1;
	@UiField Label sd;
	@UiField TextBox rewardsName;
	@UiField TextBox rewardsDefinition;
	@UiField TextBox standard;
	@UiField TextBox rewardsItemPrice;
	@UiField TextBox peopleSizeLimit;
	@UiField Button addTM;
	@UiField RadioButton onetimes;
	@UiField RadioButton moretimes;
	@UiField AbsolutePanel periodPlan;
	@UiField Label settingText;
	@UiField CheckBox autoCbx;
	@UiField CheckBox specialCbx;
	@UiField RadioButton birthRadio;
	@UiField Button save;
	@UiField DateBox startTime;
	@UiField DateBox endTime;
	@UiField TextBox calldays;
	@UiField TextBox createBy;
	@UiField TextBox rewardsFrom;
	@UiField DateBox nextRewardsTime;
	@UiField TextBox awarder;
	@UiField TextBox callday;
	@UiField DateBox nextPublicTime;
	@UiField Label lblnextPublicTime;
	// 选人模块
	@UiField
	Panel staffPanel;

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
		
		startTime.setFormat(new DateBox.DefaultFormat(dateFormat));
		endTime.setFormat(new DateBox.DefaultFormat(dateFormat));
		nextRewardsTime.setFormat(new DateBox.DefaultFormat(dateFormat));
		// settingText.setText("每1天一次");
		periodPlan.setVisible(false);
		onetimes.setChecked(true);
		birthRadio.setVisible(false);
		onetimes.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (event.getValue()) {
					periodPlan.setVisible(false);
				} else {
					periodPlan.setVisible(true);
				}
			}
		});
		moretimes.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (event.getValue()) {
					periodPlan.setVisible(true);
				} else {
					periodPlan.setVisible(false);
				}
			}
		});

		// 特殊条件
		specialCbx.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				
				if (event.getValue()) {
					birthRadio.setValue(true, true);
					birthRadio.setVisible(true);
				} else {
					birthRadio.setVisible(false);
				}

				
			}

		});

		// 生日奖
		birthRadio.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (event.getValue()) {
					FrequencyClient frequecny = getFrequencyObj();
					if (frequecny == null|| frequecny instanceof WeekFrequencyClient|| frequecny instanceof YearFrequencyClient) {
						Window.alert("生日奖必须为每日或每月，已重设为每天一次");
						DayFrequencyClient daily = new DayFrequencyClient();
						daily.setInterval(1);
						showFrequencyInfo(daily);
					}
				}
			}
		});

		// 入账部门
//		needAccountDept
//				.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
//
//					@Override
//					public void onValueChange(ValueChangeEvent<Boolean> event) {
//						if (event.getValue()) {
//							accountDeptPanel.getElement().getParentElement()
//									.getParentElement()
//									.removeClassName(CssStyleConstants.hidden);
//							if (buildDept.getSelectedItem() != null) {
//								accountDept.setDefaultValue(buildDept
//										.getSelectedItem());
//							}
//						} else {
//							accountDeptPanel.getElement().getParentElement()
//									.getParentElement()
//									.addClassName(CssStyleConstants.hidden);
//						}
//					}
//				});
		// 自动颁奖
		autoCbx.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (event.getValue()) {
					rewardsFrom.setValue(null);
					rewardsFrom.setEnabled(false);
					nextPublicTime.setVisible(false);
					lblnextPublicTime.setVisible(false);
					
				} else {
					rewardsFrom.setEnabled(true);
					nextPublicTime.setVisible(true);
					lblnextPublicTime.setVisible(true);
}

			}

		});
	}

	@Override
	public Widget asWidget() {
		return this;
	}



	@Override
	public String getBuildDept() {
//		return buildDept.getSelectedItem() != null ? buildDept
//				.getSelectedItem().getId() : null;
		return "";
	}

	@Override
	public String getAccountOfDept() {
//		if (!needAccountDept.getValue()) {
//			return getBuildDept();
//		} else {
//			return accountDept.getSelectedItem() != null ? accountDept
//					.getSelectedItem().getId() : null;
//		}
		return "";
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
	//	buildDept.setDefaultValue(null);
	//	needAccountDept.setValue(false, true);
	//	accountDept.setDefaultValue(null);
//		rewardsFrom.setValue("");
//		rewardsTo.setValue("");
		//unifiedRuleRbtn.setValue(true, true);
		//enableCbx.setValue(false, true);
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

//	@Override
//	public Integer getRewardsFrom() {
//		if (rewardsFrom.getText() == null
//				|| "".equals(rewardsFrom.getText().trim())) {
//			return null;
//		} else {
//			try {
//				int d = Integer.parseInt(rewardsFrom.getText().trim());
//				return d;
//			} catch (Exception e) {
//				return new Integer(-1);
//			}
//		}
//	}

//	@Override
//	public Integer getRewardsTo() {
//		if (rewardsTo.getText() == null
//				|| "".equals(rewardsTo.getText().trim())) {
//			return null;
//		} else {
//			try {
//				int d = Integer.parseInt(rewardsTo.getText().trim());
//				return d;
//			} catch (Exception e) {
//				return new Integer(-1);
//			}
//		}
//	}

	@Override
	public FrequencyClient getFrequencyObj() {
		return frequency;
	}

	@Override
	public HasValue<Date> getStartTime() {
		return startTime;
	}

	

	@Override
	public HasValue<Date> getEndTime() {
		return endTime;
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

	@Override
	public void initStaffBlock(Widget w) {
		staffPanel.add(w);
	}
	@Override
	public HasValue<String> getRewardsJF() {
		// TODO Auto-generated method stub
		return this.rewardsFrom;
	}

	

	@Override
	public void showChooseDept(List<String> deptIds) {
		if (deptIds != null) {
			//buildDept.setRoot(deptIds);
			//accountDept.setRoot(deptIds);
		}

		//buildDeptPanel.add(buildDept);
		//accountDeptPanel.add(accountDept);
	}

	

	

	@Override
	public HasValue<String> getPeopleSizeLimit() {
		return peopleSizeLimit;
	}

	

//	// 选中type
//	private void showType(RewardsTypeClient type) {
//		// Choose the special item of select!
//		for (int i = 0; i < rewardsType.getItemCount(); i++) {
//			if (type != null && rewardsType.getValue(i).equals(type.getId())) {
//				rewardsType.setSelectedIndex(i);
//				break;
//			}
//		}
//	}

	

	// 显示设立部门和入账部门
//	private void showBuilderAndAccountDept(DepartmentClient builderDept,
//			DepartmentClient accountDept) {
//		if (builderDept != null && builderDept.getId() != null
//				&& accountDept.getId() != null) {
//			if (builderDept.getId().equals(accountDept.getId())) {
//				needAccountDept.setValue(false, true);
//				//this.buildDept.setDefaultValue(new DepartmentClient(builderDept
//				//		.getId(), builderDept.getName()));
//			} else {
//				needAccountDept.setValue(true, true);
////				this.buildDept.setDefaultValue(new DepartmentClient(builderDept
////						.getId(), builderDept.getName()));
////				this.accountDept.setDefaultValue(new DepartmentClient(
////						accountDept.getId(), accountDept.getName()));
//			}
//		}
//	}

	@Override
	public void showFrequencyInfo(FrequencyClient frequency) {
		String text = FrequencyCalculator.getTextFromFrequency(frequency);
		this.frequency = frequency;
		settingText.setText(text);
	}

	@Override
	public void showRewardsItem(RewardsItemClient rewardsItem) {
		if (rewardsItem.getFrequency() != null) {
			// 显示出下次颁奖时间
			nextRewardsTime.getElement().getParentElement().getParentElement()
					.removeClassName(CssStyleConstants.hidden);
			// 把开始时间设成只读
			startTime.setEnabled(false);
			//rewardsHistoryThree.getElement().addClassName(CssStyleConstants.hidden);
		} else {
			//rewardsHistoryThree.getElement().removeClassName(CssStyleConstants.hidden);
		}

		// 如果是修改就隐藏“建立新的”，“从模板库中选择”这两按
		//chooseFromLibrary.getElement().addClassName(CssStyleConstants.hidden);
		//createNew.getElement().addClassName(CssStyleConstants.hidden);

		rewardsName.setTitle(rewardsItem.getName());
		rewardsDefinition.setText(rewardsItem.getDefinition());
		standard.setText(rewardsItem.getStandard());
		rewardsUnit = rewardsItem.getRewardsUnit();

		// //修改时，如果先没有设定平率，则初始化为每日一次
		// if(rewardsItem.getFrequency()==null){
		// Date now=new Date();
		// DayFrequencyClient f = new DayFrequencyClient(1);
		// rewardsItem.setFrequency(f);
		// rewardsItem.setStartTime(now);
		// rewardsItem.setNextPublishTime(DateTool.addSomeDay(now, -1));
		// rewardsItem.setNextTime(now);
		// }

		if (rewardsItem.getFrequency() != null
				&& rewardsItem.isGeneratedRewards()) {
			startTime.setEnabled(false);
		}
		startTime.setValue(rewardsItem.getStartTime());
		endTime.setValue(rewardsItem.getEndTime());
		nextRewardsTime.setValue(rewardsItem.getNextTime());
		//nextPublicTime.setValue(rewardsItem.getNextPublishTime());
		//showBuilderAndAccountDept(rewardsItem.getBuilderDept(),	rewardsItem.getAccountDept());
		
		// add lastRewardsTime
		//showLastRewardsPanel(rewardsItem.getLastRewardedDate());

		//showType(rewardsItem.getType());
		//showAmountRuleData(rewardsItem.getAmountRule());
		showFrequencyInfo(rewardsItem.getFrequency());
		//enableCbx.setValue(rewardsItem.isEnable(), true);
		autoCbx.setValue(rewardsItem.isAuto(), true);
		specialCbx.setValue(rewardsItem.isHasSpecialCondition(), true);
		if (SpecialCondition.birth == rewardsItem.getCondition()) {
			birthRadio.setValue(true);
		} else {
			birthRadio.setValue(false);
		}

		if (rewardsItem.getCreateAt() != null
				&& rewardsItem.getCreatedBy() != null) {
			createBy.setText(dateFormat.format(rewardsItem.getCreateAt()));
			createBy.setText(rewardsItem.getCreatedBy());
			// Show them
			createBy.getElement().getParentElement().getParentElement()
					.removeClassName(CssStyleConstants.hidden);
			createBy.getElement().getParentElement().getParentElement()
					.removeClassName(CssStyleConstants.hidden);
		}
	}
    
	
	@Override
	public String getRewardsType() {
		// TODO Auto-generated method stub
		return null;
	}

	
	

	@Override
	public HasClickHandlers getFrequencySettingClick() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HasValue<Date> getNextPublishTime() {
		// TODO Auto-generated method stub
		return null;
	}

	
	

	@Override
	public void initTypes(List<RewardsTypeClient> types) {
		// TODO Auto-generated method stub
		
	}
	

	

	@Override
	public HasClickHandlers getAmountSettingClick() {
		// TODO Auto-generated method stub
		return null;
	}

	

	

	
	@Override
	public void showRewardsTemplate(RewardsTemplateClient tmeplate,
			boolean initDo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showHistoryBtn() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNextRewardsTimeVisible(boolean visible) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TextBox getRewardsFromFocus() {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public HasValueChangeHandlers<Boolean> enableCbxClick() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CheckBox getAutoCbxElement() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public void showRewardsTemplate(RewardsTemplateClient template,
//			boolean initDo) {
//		rewardsName.setTitle(template.getName());
//		rewardsDefinition.setText(template.getDefinition());
//		standard.setText(template.getStandard());
//		rewardsUnit = template.getRewardsUnit();
//		// nextPublicTime.setValue(null);
//		// nextRewardsTime.setValue(null);
//		if (template.getFrequencyObj() != null && initDo) {
//			enableCbx.setValue(true);
//			autoCbx.getElement().getParentElement().getParentElement()
//					.getParentElement()
//					.removeClassName(CssStyleConstants.hidden);
//		} else {
//			enableCbx.setValue(false);
//		}
//
//		showType(template.getType());
//		if (template.getFrequencyObj() != null)
//			showFrequencyInfo(template.getFrequencyObj());
//	}

//	@Override
//	public void showHistoryBtn() {
//		rewardsHistory.getElement().removeClassName(CssStyleConstants.hidden);
//		rewardsHistoryTwo.getElement()
//				.removeClassName(CssStyleConstants.hidden);
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.chinarewards.hr.gwt.mvp.client.rewards.presenter.
	 * RewardsItemCreatePresenter
	 * .RewardsItemCreateDisplay#setNextRewardsTimeVisible(boolean)
	 */
//	@Override
//	public void setNextRewardsTimeVisible(boolean visible) {
//		this.nextRewardsTime.setVisible(visible);
//	}
//
//	@Override
//	public void setIsAmountLevel(String msg) {
//
//		isAmountLevel.setText(msg);
//
//	}
//
//	@Override
//	public TextBox getRewardsFromFocus() {
//		return rewardsFrom;
//	}
//
//	@Override
//	public TextBox getRewardsToFocus() {
//		return rewardsTo;
//	}
//
//	@Override
//	public HasValueChangeHandlers<Boolean> enableCbxClick() {
//		return enableCbx;
//	}

	

	
}
