package com.chinarewards.gwt.elt.client.breadCrumbs.presenter;

import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.google.inject.Inject;

public class BreadCrumbsPresenterImpl extends
		BasePresenter<BreadCrumbsPresenter.BreadCrumbsDisplay>
		implements BreadCrumbsPresenter {



	@Inject
	public BreadCrumbsPresenterImpl(EventBus eventBus,
			BreadCrumbsDisplay display) {
		super(eventBus, display);

	}

	public void bind() {
		
	}

	
}
