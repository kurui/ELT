package com.chinarewards.gwt.elt.client.smallControl.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

public class SmallRewardItemWindowWidget extends Composite {
	@UiField
	Image rewardItemPhoto;
	@UiField
	Anchor rewardItemName;
	@UiField
	InlineLabel integral;

	private static GloryBroadcastWidgetUiBinder uiBinder = GWT
			.create(GloryBroadcastWidgetUiBinder.class);

	interface GloryBroadcastWidgetUiBinder extends
			UiBinder<Widget, SmallRewardItemWindowWidget> {
	}

	public SmallRewardItemWindowWidget(final String rewardItemId,
			String rewardItemName, String integral,String rewardItemPhoto) {
		initWidget(uiBinder.createAndBindUi(this));
		this.rewardItemName.setText(rewardItemName);
		this.integral.setText(integral);
		this.rewardItemPhoto.setUrl("imageshow?imageName=" + rewardItemPhoto);
		if (rewardItemId != null) {

			this.rewardItemName.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {

				}
			});
		}
	}

}
