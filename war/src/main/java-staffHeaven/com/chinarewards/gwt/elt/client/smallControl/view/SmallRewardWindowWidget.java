package com.chinarewards.gwt.elt.client.smallControl.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class SmallRewardWindowWidget extends Composite {


	@UiField
	Anchor rewardName;


	private static GloryBroadcastWidgetUiBinder uiBinder = GWT
			.create(GloryBroadcastWidgetUiBinder.class);

	interface GloryBroadcastWidgetUiBinder extends
			UiBinder<Widget, SmallRewardWindowWidget> {
	}

	public SmallRewardWindowWidget(final String rewardId, String rewardName) {
		initWidget(uiBinder.createAndBindUi(this));
		this.rewardName.setText(rewardName);

		if (rewardId != null) {

			this.rewardName.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					

				}
			});
		}
	}

}
