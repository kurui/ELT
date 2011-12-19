package com.chinarewards.gwt.elt.client.rewards.presenter;


import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Panel;

public interface RewardsListPresenter extends Presenter<RewardsListPresenter.RewardsListDisplay> {

	public static interface RewardsListDisplay extends Display {

		public HasClickHandlers getNominateClickHandlers();


		Panel getResultPanel();

	}
}
