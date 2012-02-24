package com.chinarewards.gwt.elt.client.smallControl.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

public class SmallShopWindowWidget extends Composite {
	
	@UiField
	Image shopPhoto;
	@UiField
	InlineLabel shopName;
	@UiField
	InlineLabel integral;
	
	String shopId;
	private static GloryBroadcastWidgetUiBinder uiBinder = GWT
			.create(GloryBroadcastWidgetUiBinder.class);

	interface GloryBroadcastWidgetUiBinder extends
			UiBinder<Widget, SmallShopWindowWidget> {
	}

	public SmallShopWindowWidget(String shopId,String shopName,String integral,String shopPhoto) {
		initWidget(uiBinder.createAndBindUi(this));
		this.shopId=shopId;
		this.shopName.setText(shopName);
		this.integral.setText(integral);
		this.shopPhoto.setUrl("imageshow?imageName="+shopPhoto);
	}

}
