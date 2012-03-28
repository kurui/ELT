package com.chinarewards.gwt.elt.client.mail.view;

import java.util.ArrayList;
import java.util.List;

import com.chinarewards.gwt.elt.client.mail.presenter.MailSendAllPresenter.MailSendAllDisplay;
import com.chinarewards.gwt.elt.client.rewards.model.OrganicationClient;
import com.chinarewards.gwt.elt.client.view.OrganizationSpecialTextArea;
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
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class MailSendAllWidget extends Composite implements MailSendAllDisplay {

	@UiField
	TextArea content;
	@UiField
	TextBox title;
	@UiField
	Button saveBtn;
	@UiField
	Button returnBtn;
		
	@UiField
	InlineLabel message;
	@UiField
	Button chooseBtn;

	@UiField
	Panel staffOrDeptTextAreaPanel;
	SpecialTextArea<OrganicationClient> staffTextArea;
	DateTimeFormat dateFormat = DateTimeFormat
			.getFormat(ViewConstants.date_format);
	private static MailSendAllWidgetUiBinder uiBinder = GWT
			.create(MailSendAllWidgetUiBinder.class);

	interface MailSendAllWidgetUiBinder extends
			UiBinder<Widget, MailSendAllWidget> {
	}

	public MailSendAllWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		message.setText("250");
		staffTextArea = new OrganizationSpecialTextArea();
		staffTextArea.getElement().getFirstChildElement().setClassName("token-input-list-facebook2");
		staffOrDeptTextAreaPanel.add(staffTextArea);
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
	@Override
	public Panel getBroadcastOrDeptTextAreaPanel() {
		return staffOrDeptTextAreaPanel;
	}
	@Override
	public HasClickHandlers getChooseBtnClickHandlers() {
		return chooseBtn;
	}
	@Override
	public List<String[]> getRealOrginzationIds() {
		List<String[]> realOrginzationIds = new ArrayList<String[]>();
		List<OrganicationClient> existKeys = staffTextArea.getItemList();
		for (OrganicationClient key : existKeys) {
			String[] nameAndId = new String[3];
			nameAndId[0] = key.getId();
			nameAndId[1] = key.getName();
			nameAndId[2] = key.getType().toString();
			realOrginzationIds.add(nameAndId);
		}
		return realOrginzationIds;
	}
	@Override
	public void displaySelectStaff() {
		chooseBtn.setVisible(false);
	
	}

	@Override
	public SpecialTextArea<OrganicationClient> getSpecialTextArea() {
		// TODO Auto-generated method stub
		 return staffTextArea;
	}
}
