package com.chinarewards.gwt.elt.client.gift.presenter;

import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.chinarewards.gwt.elt.client.gift.model.GiftClient;
import com.chinarewards.gwt.elt.client.gift.model.GiftVo;
import com.google.gwt.event.dom.client.HasClickHandlers;

public interface GiftViewPresenter extends
		Presenter<GiftViewPresenter.GiftViewDisplay> {
	void initInstanceId(String instanceId, GiftClient item);

	public static interface GiftViewDisplay extends Display {


		public HasClickHandlers getBackClick();

		public HasClickHandlers getUpdateClick();

		public void showGift(GiftVo giftVo);

	}
}
