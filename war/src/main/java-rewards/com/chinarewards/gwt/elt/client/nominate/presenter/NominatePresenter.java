package com.chinarewards.gwt.elt.client.nominate.presenter;


import java.util.List;

import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.event.dom.client.HasClickHandlers;

public interface NominatePresenter extends Presenter<NominatePresenter.NominateDisplay> {
	
	public static interface NominateDisplay extends Display {

		public HasClickHandlers getNominateClickHandlers();

		public void setName(String name);
		public void setExplain(String explain);
		public void setCondition(String condition);
		public void setIntegral(String integral);
		public void setRecordName(String recordName);
		public void setNumber(String number);
		public void setNominate(String nominate);
		public void setCandidate(List<String> candidate);
		public void setAwardNature(String awardNature);
		public void setBegindate(String begindate);
		public void setAwarddate(String awarddate);
		public void setNominateMessage(String nominateMessage);
		public void setAwardName(String awardName);

		
		public List<String> getCandidateList();
	}
}
