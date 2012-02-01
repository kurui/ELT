package com.chinarewards.gwt.elt.client.detailsOfGift.presenter;


import com.chinarewards.gwt.elt.client.detailsOfGift.model.DetailsOfGiftClient;
import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;

public interface DetailsOfGiftPresenter extends Presenter<DetailsOfGiftPresenter.DetailsOfGiftDisplay> {

	public void initDetailsOfGift(DetailsOfGiftClient orderVo);
	public static interface DetailsOfGiftDisplay extends Display {


	
	
	}
}
