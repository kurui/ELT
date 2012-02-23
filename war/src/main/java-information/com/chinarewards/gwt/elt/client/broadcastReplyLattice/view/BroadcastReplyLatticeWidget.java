package com.chinarewards.gwt.elt.client.broadcastReplyLattice.view;

import com.chinarewards.gwt.elt.client.view.constant.ViewConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class BroadcastReplyLatticeWidget extends Composite  {



	DateTimeFormat dateFormat = DateTimeFormat
			.getFormat(ViewConstants.date_format);
	private static BroadcastReplyLatticeWidgetUiBinder uiBinder = GWT
			.create(BroadcastReplyLatticeWidgetUiBinder.class);

	interface BroadcastReplyLatticeWidgetUiBinder extends
			UiBinder<Widget, BroadcastReplyLatticeWidget> {
	}

	public BroadcastReplyLatticeWidget() {
		initWidget(uiBinder.createAndBindUi(this));

	}

	

}
