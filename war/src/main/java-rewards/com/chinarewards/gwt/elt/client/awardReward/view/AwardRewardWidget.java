package com.chinarewards.gwt.elt.client.awardReward.view;

import java.util.ArrayList;
import java.util.List;

import com.chinarewards.gwt.elt.client.awardReward.presenter.AwardRewardPresenter.AwardRewardDisplay;
import com.chinarewards.gwt.elt.client.rewards.model.OrganicationClient;
import com.chinarewards.gwt.elt.client.view.OrganizationSpecialTextArea;
import com.chinarewards.gwt.elt.client.widget.SpecialTextArea;
import com.chinarewards.gwt.elt.model.nominate.JudgeParamVo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class AwardRewardWidget extends Composite implements AwardRewardDisplay {

	@UiField
	Button nominatebutton;
	@UiField
	Button notWinbutton;
	
	@UiField
	Label name;
	@UiField
	Label explain;
	@UiField
	Label condition;
	@UiField
	Label integral;
	@UiField
	Label recordName;
	@UiField
	InlineLabel number;
	@UiField
	VerticalPanel judge;
	@UiField
	Label awardNature;
	@UiField
	Label begindate;
	@UiField
	Label awarddate;
	@UiField
	Label awardName;
	@UiField
	InlineLabel nominateMessage;

	@UiField
	InlineLabel expectNominateDate;
	@UiField
	InlineLabel nominateStaff;
	@UiField
	InlineLabel awardAmt;
	
	
	// 选人模块
	@UiField
	Panel staffPanel;
	@UiField
	Panel breadCrumbs;
	@UiField
	CheckBox emailCheckbox;
	@UiField
	CheckBox messageCheckbox;

	@UiField
	Button chooseBtns;

	@UiField
	Panel staffAreaPanel;
	
	SpecialTextArea<OrganicationClient> staffTextArea;
	private static AwardRewardWidgetUiBinder uiBinder = GWT
			.create(AwardRewardWidgetUiBinder.class);

	interface AwardRewardWidgetUiBinder extends UiBinder<Widget, AwardRewardWidget> {
	}

	public AwardRewardWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		 staffTextArea = new OrganizationSpecialTextArea();		
		 staffAreaPanel.add(staffTextArea);
	}

	@Override
	public HasClickHandlers getNominateClickHandlers() {
		return nominatebutton;
	}

	@Override
	public void setName(String name) {
		this.name.setText(name);

	}

	@Override
	public void setExplain(String explain) {
		this.explain.setText(explain);

	}

	@Override
	public void setCondition(String condition) {
		this.condition.setText(condition);

	}

	@Override
	public void setIntegral(String integral) {
		this.integral.setText(integral);

	}

	@Override
	public void setRecordName(String recordName) {
		this.recordName.setText(recordName);

	}

	@Override
	public void setNumber(String number) {
		this.number.setText(number);

	}
	@Override
	public void setAwardAmt(String awardAmt) {
		this.awardAmt.setText(awardAmt);

	}
	@Override
	public void setJudge(List<JudgeParamVo> judge) {
		String judgeStr="";
		for (int i = 0; i < judge.size(); i++) {
			if ("NOMINATED".equals(judge.get(i).getJudgeStatus()))
				judgeStr += judge.get(i).getName() + "(已完成提名),";
			else
				judgeStr += judge.get(i).getName() + ",";
			
		}
		InlineLabel nominatelab = new InlineLabel(judgeStr);
		this.judge.add(nominatelab);
		

	}



	@Override
	public void setAwardNature(String awardNature) {
		this.awardNature.setText(awardNature);
	}

	@Override
	public void setBegindate(String begindate) {
		this.begindate.setText(begindate);
	}

	@Override
	public void setAwarddate(String awarddate) {
		this.awarddate.setText(awarddate);

	}

	@Override
	public void setNominateMessage(String nominateMessage) {
		this.nominateMessage.setText(nominateMessage);
	}

	@Override
	public void setAwardName(String awardName) {
		this.awardName.setText(awardName);
	}
	@Override
	public void setExpectNominateDate(String expectNominateDate) {
		this.expectNominateDate.setText(expectNominateDate);
		
	}

	@Override
	public void setNominateStaff(String nominateStaff) {
		this.nominateStaff.setText(nominateStaff);
	}

	
	@Override
	public void initStaffPanel(Widget w) {
		staffPanel.add(w);
	}
	@Override
	public void setBreadCrumbs(Widget breadCrumbs) {
		this.breadCrumbs.clear();
		this.breadCrumbs.add(breadCrumbs);
		
	}

	@Override
	public HasClickHandlers getNotWinClickHandlers() {
		return notWinbutton;
	}
	@Override
	public CheckBox getEmailCheckbox() {
		return emailCheckbox;
	}

	@Override
	public CheckBox getMessageCheckbox() {
		return messageCheckbox;
	}

	@Override
	public HasClickHandlers getChooseStaffBtnClick() {
		return chooseBtns;
	}
	@Override
	public SpecialTextArea<OrganicationClient> getSpecialTextArea() {
		return staffTextArea;
	}
	@Override
	public List<String> getRealOrginzationIds() {
		List<String> realOrginzationIds = new ArrayList<String>();
		List<OrganicationClient> existKeys = staffTextArea.getItemList();
		for (OrganicationClient key : existKeys) {
			realOrginzationIds.add(key.getId());
		}
		return realOrginzationIds;
	}
}
