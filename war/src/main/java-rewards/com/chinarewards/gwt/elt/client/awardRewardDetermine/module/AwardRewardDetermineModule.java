package com.chinarewards.gwt.elt.client.awardRewardDetermine.module;


import com.chinarewards.gwt.elt.client.awardRewardDetermine.presenter.AwardRewardDeterminePresenter;
import com.chinarewards.gwt.elt.client.awardRewardDetermine.presenter.AwardRewardDeterminePresenter.AwardRewardDetermineDisplay;
import com.chinarewards.gwt.elt.client.awardRewardDetermine.presenter.AwardRewardDeterminePresenterImpl;
import com.chinarewards.gwt.elt.client.awardRewardDetermine.view.AwardRewardDetermineWidget;
import com.google.gwt.inject.client.AbstractGinModule;

public class AwardRewardDetermineModule extends AbstractGinModule {

	@Override
	protected void configure() {
		bind(AwardRewardDeterminePresenter.class).to(AwardRewardDeterminePresenterImpl.class);
		bind(AwardRewardDetermineDisplay.class).to(AwardRewardDetermineWidget.class);
	}

}
