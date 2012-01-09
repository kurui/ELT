package com.chinarewards.gwt.elt.client.rewardItem.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.frequency.FrequencyCalculator;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.rewardItem.presenter.RewardsItemCreatePresenter.RewardsItemDisplay;
import com.chinarewards.gwt.elt.client.rewards.model.DayFrequencyClient;
import com.chinarewards.gwt.elt.client.rewards.model.FrequencyClient;
import com.chinarewards.gwt.elt.client.rewards.model.OrganicationClient;
import com.chinarewards.gwt.elt.client.rewards.model.ParticipateInfoClient;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsItemClient;
import com.chinarewards.gwt.elt.client.rewards.model.SpecialCondition;
import com.chinarewards.gwt.elt.client.rewards.model.WeekFrequencyClient;
import com.chinarewards.gwt.elt.client.rewards.model.YearFrequencyClient;
import com.chinarewards.gwt.elt.client.rewards.model.ParticipateInfoClient.EveryoneClient;
import com.chinarewards.gwt.elt.client.rewards.model.ParticipateInfoClient.SomeoneClient;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.view.OrganizationSpecialTextArea;
import com.chinarewards.gwt.elt.client.view.constant.CssStyleConstants;
import com.chinarewards.gwt.elt.client.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.client.widget.SpecialTextArea;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RadioButton;
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
   //一次性的提前几天提名
	@UiField
	TextBox tmdays;
	//多次性的提前几天提名
	@UiField
	TextBox tmday;
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
	
	// 下次公布时间
	@UiField
	DateBox nextPublicTime;
	// 上次颁奖时间
	@UiField
	Label lastRewardsTime;
	// 下次颁奖时间
	@UiField
	DateBox nextRewardsTime;
	//期望颁奖时间
	@UiField
	DateBox expectTime;
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
	// 候选人模块
	@UiField
	Panel staffPanel;

	
	// 保存或修改
	@UiField
	Button save;

	/** 存储有用的信息 **/
	FrequencyClient frequency;
	String rewardsUnit;
	
   //提名选人的按钮
	@UiField
	Button chooseBtns;
	//显示提名人的面板
	@UiField
	Panel staffAreaPanel;
	
	SpecialTextArea<OrganicationClient> staffArea;
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
			init();
		}


	private void init() {
		staffArea = new OrganizationSpecialTextArea();
		staffAreaPanel.add(staffArea);//提名人面板
		//rewardsDefinition.setHeight("2.5em");
		//standard.setHeight("2.5em");
		startTime.setFormat(new DateBox.DefaultFormat(dateFormat));
		//	String siteId = DOM.getElementById("tr1").getNodeName();

         //隐藏周期性
		settingText.getElement().getParentElement().getParentElement().getParentElement().addClassName(CssStyleConstants.hidden);
		nextRewardsTime.setFormat(new DateBox.DefaultFormat(dateFormat));
		nextPublicTime.setFormat(new DateBox.DefaultFormat(dateFormat));
		expectTime.setFormat(new DateBox.DefaultFormat(dateFormat));
		// settingText.setText("每1天一次");
		birthRadio.getElement().addClassName(CssStyleConstants.hidden);
		//周期性选择
		onetimes.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (event.getValue()) {
					settingText.getElement().getParentElement().getParentElement().getParentElement().addClassName(CssStyleConstants.hidden);
					expectTime.getElement().getParentElement().getParentElement().getParentElement().removeClassName(CssStyleConstants.hidden);
				} else {
					settingText.getElement().getParentElement().getParentElement().getParentElement().removeClassName(CssStyleConstants.hidden);
					expectTime.getElement().getParentElement().getParentElement().getParentElement().addClassName(CssStyleConstants.hidden);
				}
			}
		});
		moretimes.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				if (event.getValue()) {
					settingText.getElement().getParentElement().getParentElement().getParentElement().removeClassName(CssStyleConstants.hidden);
					expectTime.getElement().getParentElement().getParentElement().getParentElement().addClassName(CssStyleConstants.hidden);
				} else {
					settingText.getElement().getParentElement().getParentElement().getParentElement().addClassName(CssStyleConstants.hidden);
					expectTime.getElement().getParentElement().getParentElement().getParentElement().removeClassName(CssStyleConstants.hidden);
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
					chooseBtns.getElement().getParentElement()//隐藏提名
					.getParentElement()
					.addClassName(CssStyleConstants.hidden);
					nextPublicTime.getElement().getParentElement()
							.getParentElement()
							.addClassName(CssStyleConstants.hidden);
				} else {
					chooseBtns.getElement().getParentElement()//隐藏提名
					.getParentElement()
					.removeClassName(CssStyleConstants.hidden);
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
	public HasClickHandlers getChooseStaffBtnClick() {//提名人选择事件
		return chooseBtns;
	}
	@Override
	public SpecialTextArea<OrganicationClient> getSpecialTextArea() {
		return staffArea;
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

	
	@Override
	public HasValue<String> getPeopleSizeLimit() {
		return peopleSizeLimit;
	}

	
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


	@Override
	public Integer getTmday() {
		
		if (tmday.getText() == null
				|| "".equals(tmday.getText().trim())) {
			return null;
		} else {
			try {
				int d = Integer.parseInt(tmday.getText().trim());
				return d;
			} catch (Exception e) {
				return new Integer(-1);
			}
		}
	}

	@Override
	public HasValue<Date> getExpectTime() {
		// TODO Auto-generated method stub
		return this.expectTime;
	}


	


	@Override
	public List<String> getNominateIds() {
		List<String> nominateIds = new ArrayList<String>();
		List<OrganicationClient> existKeys = staffArea.getItemList();
		for (OrganicationClient key : existKeys) {
			// if (staffMap.containsKey(key.getId())) {
			// OrganicationClient org = staffMap.get(key.getId());
			nominateIds.add(key.getId());
			// }
		}
		return nominateIds;
	}
   
	// 显示提名人
	@Override
	public void showJudgeInfo(RewardsItemClient info){
			staffArea.clear();
			ParticipateInfoClient participateInfo = info.getTmInfo();
			 if (participateInfo instanceof SomeoneClient) {
					for (OrganicationClient org : ((SomeoneClient) participateInfo)	.getOrganizations()) {
						if (!staffArea.containsItem(org)) {
							staffArea.addItem(org);
						}
					}
			}
	}

	public void showRewardsItem(RewardsItemClient rewardsItem) {
		if (rewardsItem.getFrequency() != null) {
			// 显示出下次颁奖时间
			nextRewardsTime.getElement().getParentElement().getParentElement()
					.removeClassName(CssStyleConstants.hidden);
			// 把开始时间设成只读
			//startTime.setEnabled(false);
			
		} 
       
		rewardsName.setText(rewardsItem.getName());
		rewardsDefinition.setText(rewardsItem.getDefinition());
		standard.setText(rewardsItem.getStandard());
		rewardsUnit = rewardsItem.getRewardsUnit();

		
		if (rewardsItem.getFrequency() != null	&& rewardsItem.isGeneratedRewards()) {
			//startTime.setEnabled(false);
		}
	
	  showJudgeInfo(rewardsItem);//显示要修改的提名人
	  startTime.setValue(rewardsItem.getStartTime());
	  nextRewardsTime.setValue(rewardsItem.getNextTime());
	  nextPublicTime.setValue(rewardsItem.getNextPublishTime());
	  peopleSizeLimit.setValue(StringUtil.valueOf(rewardsItem.getSizeLimit()));
      rewardsFrom.setValue(StringUtil.valueOf(rewardsItem.getRewardsFrom()));
      tmday.setValue(StringUtil.valueOf(rewardsItem.getTmdays()));
	  tmdays.setValue(StringUtil.valueOf(rewardsItem.getTmdays()));
	  totalJF.setValue(StringUtil.valueOf(rewardsItem.getTotalJF()));
	   expectTime.setValue(rewardsItem.getNextTime());
	  nextPublicTime.setValue(rewardsItem.getNextPublishTime());
	  showFrequencyInfo(rewardsItem.getFrequency());
	  autoCbx.setValue(rewardsItem.isAuto(), true);
	  if(rewardsItem.isAuto()==false)//隐藏提名
	     chooseBtns.getElement().getParentElement().getParentElement().removeClassName(CssStyleConstants.hidden);
	  else
		  chooseBtns.getElement().getParentElement().getParentElement().addClassName(CssStyleConstants.hidden);
		specialCbx.setValue(rewardsItem.isHasSpecialCondition(), true);
		if (SpecialCondition.birth == rewardsItem.getCondition()) {
			birthRadio.setValue(true);
		} else {
			birthRadio.setValue(false);
		}
       if(rewardsItem.isPeriodEnable()==true){
    	   moretimes.setValue(true,true);
       }else{
    	   onetimes.setValue(true,true);
       }
		
	}	
	
}
