package com.chinarewards.gwt.elt.client.corpBroadcast.presenter;

import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.user.client.ui.Panel;

public interface CorpBroadcastPresenter extends
		Presenter<CorpBroadcastPresenter.CorpBroadcastDisplay> {

	public static interface CorpBroadcastDisplay extends Display {

		void setDataCount(String text);

		Panel getResultPanel();

		Panel getResultpage();

	}
}
