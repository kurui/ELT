package com.chinarewards.gwt.elt.client.nominate.view;

import java.util.ArrayList;
import java.util.List;

import com.chinarewards.gwt.elt.client.nominate.presenter.NominatePresenter.NominateDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
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
	Label number;
	@UiField
	VerticalPanel nominate;
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
	Label nominateMessage;

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
	public void setNominate(List<String> nominate) {
		HTML nominatelab = new HTML("显示提名人");
		this.nominate.add(nominatelab);

	}

	@Override
	public void setCandidate(List<String> candidate) {
		boolean fal = false;//是否创建人界面
		if (fal) {
			String str = "";
			for (int i = 0; i < 20; i++) {
				str += "&nbsp";
			}
			HTML label = new HTML(str + "被提名次数");

			this.candidate.add(label);
		}

		for (int i = 0; i < candidate.size(); i++) {
			String count = candidate.get(i).toString();
			String checkBoxName=count;
			if (fal) {
				String str2 = "";
				for (int j = count.length(); j < 20; j++) {
					str2 += "-";
				}
				checkBoxName =count+ str2 + Random.nextInt(10);// 是否显示被提名次数
			}
			CheckBox checkBox = new CheckBox(checkBoxName);
			checkBox.ensureDebugId("cwCheckBox-" + checkBoxName);
			checkBox.setName(count);

			// // Disable the weekends
			// if (i >= 5) {
			// checkBox.setEnabled(false);
			// }

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
		        	idlist.add(checkBox.getName());
		        }
		    }

		}

//		NodeList<Element> nodelist = this.candidate.getElement().getElementsByTagName("checkBox");
//		for (int i = 0; i < nodelist.getLength(); i++) {
//			String xx = nodelist.getItem(i).getAttribute("ensureDebugId");
//			
//		}
		return idlist;
	}

}
