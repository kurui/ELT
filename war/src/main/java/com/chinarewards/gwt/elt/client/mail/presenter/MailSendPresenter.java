package com.chinarewards.gwt.elt.client.mail.presenter;

import com.chinarewards.gwt.elt.client.mvp.DialogPresenter;
import com.chinarewards.gwt.elt.client.mvp.Display;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;

public interface MailSendPresenter extends DialogPresenter<MailSendPresenter.MailSendDisplay> {

	public void initBroadcastStaff(String staffId,String staffName);
	
	public static interface MailSendDisplay extends Display {

		public HasClickHandlers getSaveBtnClickHandlers();

		public HasClickHandlers getReturnBtnClickHandlers();
			
		String getContent();
		void setContent(String text);
		void setStaffName(String name);
		void setStaffId(String id);
		public String getStaffId();
		public HasValue<String> getStaffName();
	
		public String getTitle() ;
	}
}
