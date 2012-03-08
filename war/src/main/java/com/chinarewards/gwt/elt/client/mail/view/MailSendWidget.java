package com.chinarewards.gwt.elt.client.mail.view;

import com.chinarewards.gwt.elt.client.mail.presenter.MailSendPresenter.MailSendDisplay;
import com.chinarewards.gwt.elt.client.rewards.model.OrganicationClient;
import com.chinarewards.gwt.elt.client.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.client.widget.SpecialTextArea;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class MailSendWidget extends Composite implements MailSendDisplay {

	@UiField
	TextArea content;
	@UiField
	TextBox title;
	@UiField
	Button saveBtn;
	@UiField
	Button returnBtn;
		
	@UiField
	TextBox staffName;
	@UiField
    Hidden staffId;
	@UiField
	InlineLabel message;
	SpecialTextArea<OrganicationClient> staffTextArea;
	DateTimeFormat dateFormat = DateTimeFormat
			.getFormat(ViewConstants.date_format);
	private static MailSendWidgetUiBinder uiBinder = GWT
			.create(MailSendWidgetUiBinder.class);

	interface MailSendWidgetUiBinder extends
			UiBinder<Widget, MailSendWidget> {
	}

	public MailSendWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		message.setText("150");
	}

	@Override
	public HasClickHandlers getSaveBtnClickHandlers() {
		return saveBtn;
	}

	@Override
	public HasClickHandlers getReturnBtnClickHandlers() {
		return returnBtn;
	}

	@Override
	public String getContent() {
		return content.getValue();
	}


	@Override
	public void setContent(String text) {
		content.setText(text);
	}

	@Override
	public void setStaffName(String name) {
		this.staffName.setText(name);
		
	}

	@Override
	public void setStaffId(String id) {
		this.staffId.setValue(id);
		
	}

	@Override
	public String getStaffId() {
		// TODO Auto-generated method stub
		return this.staffId.getValue();
	}

	@Override
	public HasValue<String> getStaffName() {
		// TODO Auto-generated method stub
		return this.staffName;
	}
	
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return this.title.getValue();
	}
	@Override
	public void setMessage(String text) {
		this.message.setText(text);
		
	}

	@Override
	public HasKeyUpHandlers getContentKeyUpHandlers() {
		// TODO Auto-generated method stub
		return content;
	}
}
