package com.chinarewards.gwt.elt.client.awardRewardDetermine.presenter;

import java.util.List;

import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.chinarewards.gwt.elt.client.rewards.model.OrganicationClient;
import com.chinarewards.gwt.elt.client.widget.SpecialTextArea;
import com.chinarewards.gwt.elt.model.awardReward.WinnerParamVo;
import com.chinarewards.gwt.elt.model.nominate.JudgeParamVo;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;

public interface AwardRewardDeterminePresenter extends
		Presenter<AwardRewardDeterminePresenter.AwardRewardDetermineDisplay> {

	public void initReward(String rewardModel, String instanceId, int headcount);

	public static interface AwardRewardDetermineDisplay extends Display {

		public HasClickHandlers getNominateClickHandlers();

		public void setName(String name);

		public void setExplain(String explain);

		public void setCondition(String condition);

		public void setIntegral(String integral);

		public void setRecordName(String recordName);

		public void setNumber(String number);

		public void setJudge(List<JudgeParamVo> nominate);

		public void setAwardNature(String awardNature);

		public void setBegindate(String begindate);

		public void setAwarddate(String awarddate);

		public void setNominateMessage(String nominateMessage);

		public void setExpectNominateDate(String expectNominateDate);

		public void setNominateStaff(String nominateStaff);

		public void setAwardName(String awardName);

		public void setAwardAmt(String awardAmt);


		void setBreadCrumbs(Widget breadCrumbs);

		public void setWinners(List<WinnerParamVo> winners);
		
		
		CheckBox getEmailCheckbox();
		
		CheckBox getMessageCheckbox();
		HasClickHandlers getChooseStaffBtnClick();
		SpecialTextArea<OrganicationClient> getSpecialTextArea();
		// 得到候选人的id
		List<String> getRealOrginzationIds();
	}
}
