package com.chinarewards.gwt.elt.client.rewards.presenter;



import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.inject.Inject;

public class RewardsListPresenterImpl extends
		BasePresenter<RewardsListPresenter.RewardsListDisplay> implements
		RewardsListPresenter {

	private final DispatchAsync dispatcher;

	@Inject
	public RewardsListPresenterImpl(EventBus eventBus,
			RewardsListDisplay display,DispatchAsync dispatcher) {
		super(eventBus, display);
		this.dispatcher=dispatcher;
	}

	@Override
	public void bind() {
		registerHandler(display.getNominateClickHandlers().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent paramClickEvent) {
						
							Window.alert(".................");
						
					}
				}));
	}

	
	
	
}
