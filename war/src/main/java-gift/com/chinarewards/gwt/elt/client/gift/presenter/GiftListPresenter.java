package com.chinarewards.gwt.elt.client.gift.presenter;


import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Panel;

public interface GiftListPresenter extends Presenter<GiftListPresenter.GiftListDisplay> {


	public static interface GiftListDisplay extends Display {

		public HasClickHandlers getSearchBtnClickHandlers();
		HasValue<String> getName();
		HasValue<String> getDefinition();
		
		Panel getResultPanel();
		Panel getResultpage();
		HasValue<Boolean> getNowJudge();

	}
}
