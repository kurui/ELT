package com.chinarewards.gwt.elt.client.mail.presenter;

import java.util.List;

import com.chinarewards.gwt.elt.client.mvp.DialogPresenter;
import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.rewards.model.OrganicationClient;
import com.chinarewards.gwt.elt.client.widget.SpecialTextArea;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.user.client.ui.Panel;

public interface MailSendAllPresenter extends DialogPresenter<MailSendAllPresenter.MailSendAllDisplay> {

	public void initBroadcastStaff(String staffId,String staffName);
	
	public static interface MailSendAllDisplay extends Display {

		public HasClickHandlers getSaveBtnClickHandlers();

		public HasClickHandlers getReturnBtnClickHandlers();
		public HasKeyUpHandlers getContentKeyUpHandlers();
		void setMessage(String text);
	
		public HasClickHandlers getChooseBtnClickHandlers();

		void displaySelectStaff();
		String getContent();

		Panel getBroadcastOrDeptTextAreaPanel();

		void setContent(String text);

		// 得到候选人的id,和名称
		List<String[]> getRealOrginzationIds();

		SpecialTextArea<OrganicationClient> getSpecialTextArea();
		
		public String getTitle() ;
	}
}
