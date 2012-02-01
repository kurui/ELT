package com.chinarewards.gwt.elt.client.detailsOfGift.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.detailsOfGift.model.DetailsOfGiftClient;
import com.chinarewards.gwt.elt.client.detailsOfGift.presenter.DetailsOfGiftPresenter.DetailsOfGiftDisplay;
import com.chinarewards.gwt.elt.client.detailsOfGift.request.DetailsOfGiftRequest;
import com.chinarewards.gwt.elt.client.detailsOfGift.request.DetailsOfGiftResponse;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.win.Win;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class DetailsOfGiftPresenterImpl extends
		BasePresenter<DetailsOfGiftDisplay> implements DetailsOfGiftPresenter {

	final DispatchAsync dispatch;
	final ErrorHandler errorHandler;
	final SessionManager sessionManager;
	final Win win;
	DetailsOfGiftClient orderVo;

	@Inject
	public DetailsOfGiftPresenterImpl(EventBus eventBus,
			DispatchAsync dispatch, ErrorHandler errorHandler,
			SessionManager sessionManager, DetailsOfGiftDisplay display, Win win) {
		super(eventBus, display);
		this.dispatch = dispatch;
		this.errorHandler = errorHandler;
		this.sessionManager = sessionManager;
		this.win = win;

	}

	@Override
	public void bind() {
		init();

	}

	private void init() {
		dispatch.execute(new DetailsOfGiftRequest(orderVo.getGiftId()),
				new AsyncCallback<DetailsOfGiftResponse>() {
					@Override
					public void onFailure(Throwable e) {
						errorHandler.alert(e.getMessage());
					}

					@Override
					public void onSuccess(DetailsOfGiftResponse response) {
						
						display.setGiftName(response.getGiftName());
						display.setGiftNo(response.getGiftNo());
						display.setBrand(response.getBrand());
						display.setType(response.getType());
						display.setStock(response.getStock());
						display.setIntegral(response.getIntegral());
						display.setSummary(response.getSummary());
						display.setExplains(response.getExplains());
						display.setNotes(response.getNotes());
						display.setDispatchcycle(response.getDispatchcycle());
						display.setBusiness(response.getBusiness());
						display.setServicetell(response.getServicetell());
						display.setGiftPhoto(response.getGiftPhoto());
					}

				});
	}

	@Override
	public void initDetailsOfGift(DetailsOfGiftClient orderVo) {
		this.orderVo = orderVo;

	}

}
