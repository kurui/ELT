package com.chinarewards.gwt.elt.client.colleagueParticular.presenter;

import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Panel;

public interface ColleagueParticularPresenter extends
		Presenter<ColleagueParticularPresenter.ColleagueParticularDisplay> {

	public void initColleagueParticular(String staffId);
	public static interface ColleagueParticularDisplay extends Display {

		Anchor getStaffView();

		Anchor getStaffBroadcast();

		Anchor getStaffGlory();

		Anchor getSendMessage();

		Panel getResultPanel();

	}
}
