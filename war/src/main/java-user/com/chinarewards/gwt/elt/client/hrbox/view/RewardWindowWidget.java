package com.chinarewards.gwt.elt.client.hrbox.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class RewardWindowWidget extends Composite {

	@UiField
	Image Photo;
	@UiField
	Anchor rewardName;
	

	String shopId;
	private static GloryBroadcastWidgetUiBinder uiBinder = GWT
			.create(GloryBroadcastWidgetUiBinder.class);

	interface GloryBroadcastWidgetUiBinder extends
			UiBinder<Widget, RewardWindowWidget> {
	}

	public RewardWindowWidget(final String shopId, String shopName) {
		initWidget(uiBinder.createAndBindUi(this));
		this.shopId = shopId;
		this.rewardName.setText(shopName);
		this.Photo.setUrl("imageshow?imageName=");

		if (shopId != null) {

			this.rewardName.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
//					Platform.getInstance()
//							.getEditorRegistry()
//							.openEditor(
//									DetailsOfGiftConstants.EDITOR_DETAILSOFGIFT_SEARCH,
//									"EDITOR_DETAILSOFGIFT_SEARCH_DO_ID",
//									new DetailsOfGiftClient(shopId));

				}
			});
		}
	}

}
