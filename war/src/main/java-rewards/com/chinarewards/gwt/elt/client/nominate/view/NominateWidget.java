package com.chinarewards.gwt.elt.client.nominate.view;

import java.util.ArrayList;
import java.util.List;

import com.chinarewards.gwt.elt.client.nominate.presenter.NominatePresenter.NominateDisplay;
import com.chinarewards.gwt.elt.model.nominate.CandidateParamVo;
import com.chinarewards.gwt.elt.model.nominate.JudgeParamVo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class NominateWidget extends Composite implements NominateDisplay {

	@UiField
	Button nominatebutton;
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
	VerticalPanel candidate;
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
	private static HrRegisterWidgetUiBinder uiBinder = GWT
			.create(HrRegisterWidgetUiBinder.class);

	interface HrRegisterWidgetUiBinder extends UiBinder<Widget, NominateWidget> {
	}

	public NominateWidget() {
		initWidget(uiBinder.createAndBindUi(this));
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
			judgeStr+=judge.get(i).getName()+",";
			
		}
		InlineLabel nominatelab = new InlineLabel(judgeStr);
		this.judge.add(nominatelab);
		

	}

	@Override
	public void setCandidate(List<CandidateParamVo> candidate) {
		boolean fal = true;//是否创建人界面
		if (fal) {
			String str = "";
			for (int i = 0; i < 20; i++) {
				str += "&nbsp";
			}
			HTML label = new HTML(str + "被提名次数");

			this.candidate.add(label);
		}

		for (int i = 0; i < candidate.size(); i++) {
			CandidateParamVo candidateVo =candidate.get(i);
			String checkBoxName=candidateVo.getName();
			String name=candidateVo.getName();
			String staffid=candidateVo.getStaffid();
			if (fal) {
				String str2 = "";
				for (int j = name.length(); j < 20; j++) {
					str2 += "-";
				}
				checkBoxName =name+ str2 + candidateVo.getNominateCount();// 是否显示被提名次数
			}
			CheckBox checkBox = new CheckBox(checkBoxName);
			checkBox.getElement().setAttribute("staffid", staffid);
	


			this.candidate.add(checkBox);
		}

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
	
	@SuppressWarnings("deprecation")
	@Override
	public List<String> getCandidateList() {
		List<String> idlist = new ArrayList<String>();
		
		for (int i=0;i<candidate.getWidgetCount();i++){
		    Widget widget = candidate.getWidget(i);
		    if (widget instanceof CheckBox){
		        CheckBox checkBox = (CheckBox) widget;
		        if(checkBox.isChecked()==true)
		        {
		        	idlist.add(checkBox.getElement().getAttribute("staffid"));
		        }
		    }

		}

		return idlist;
	}

	

}
