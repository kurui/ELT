package com.chinarewards.gwt.elt.client.messageLattice.view;

import com.chinarewards.gwt.elt.client.view.constant.ViewConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

public class MessageLatticeWidget extends Composite {

	@UiField
	Image photo;
	@UiField
	InlineLabel staffName;
	@UiField
	InlineLabel message;
	@UiField
	InlineLabel replyDate;
	@UiField
	Anchor replyBtn;
	DateTimeFormat dateFormat = DateTimeFormat
			.getFormat(ViewConstants.date_format);
	private static MessageListWidgetUiBinder uiBinder = GWT
			.create(MessageListWidgetUiBinder.class);

	interface MessageListWidgetUiBinder extends
			UiBinder<Widget, MessageLatticeWidget> {
	}

	public MessageLatticeWidget(String messageId,String photo,String staffName,String message,String replyDate) {
		initWidget(uiBinder.createAndBindUi(this));
		this.staffName.setText(staffName);
		this.message.setText(message);
		this.replyDate.setText(replyDate);
		this.photo.setUrl("imageshow?imageName="+photo);
	}

}
