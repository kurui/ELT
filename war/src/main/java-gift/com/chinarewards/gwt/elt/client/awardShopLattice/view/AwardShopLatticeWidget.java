package com.chinarewards.gwt.elt.client.awardShopLattice.view;

import com.chinarewards.gwt.elt.client.awardShopLattice.presenter.AwardShopLatticePresenter.AwardShopLatticeDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

public class AwardShopLatticeWidget extends Composite implements
		AwardShopLatticeDisplay {
	@UiField
	InlineLabel awardName;
	@UiField
	InlineLabel integral;
	@UiField
	InlineLabel indate;
	@UiField
	Image photo;
	@UiField
	Button exchangeBtn;
	
	private static AwardShopLatticeWidgetUiBinder uiBinder = GWT
			.create(AwardShopLatticeWidgetUiBinder.class);

	interface AwardShopLatticeWidgetUiBinder extends
			UiBinder<Widget, AwardShopLatticeWidget> {
	}

	public AwardShopLatticeWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	public AwardShopLatticeWidget(final String awardName,final String integral,String indate,String photo,final String id) {
		initWidget(uiBinder.createAndBindUi(this));
		this.awardName.setText(awardName);
		this.integral.setText(integral);
		this.indate.setText(indate);
		this.photo.setUrl("/imageshow?imageName="+photo);
		exchangeBtn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("礼品名称:"+awardName+"-----积分:"+integral+"ID:"+id);
				
			}
		});
	}

}
