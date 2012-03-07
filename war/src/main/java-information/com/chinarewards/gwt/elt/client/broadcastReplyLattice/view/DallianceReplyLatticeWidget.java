package com.chinarewards.gwt.elt.client.broadcastReplyLattice.view;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.chooseOrganization.model.OrganSearchCriteria.OrganType;
import com.chinarewards.gwt.elt.client.messageSave.request.MessageSaveRequest;
import com.chinarewards.gwt.elt.client.messageSave.request.MessageSaveResponse;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.view.constant.ViewConstants;
import com.chinarewards.gwt.elt.client.win.Win;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

public class DallianceReplyLatticeWidget extends Composite {

	@UiField
	InlineLabel deptName;
	@UiField
	InlineLabel staffName;

	@UiField
	InlineLabel createDate;

	@UiField
	Anchor dallianceBtn;

	DateTimeFormat dateFormat = DateTimeFormat
			.getFormat(ViewConstants.date_format);

	Win win;
	DispatchAsync dispatch;
	SessionManager sessionManager;
	String broadcastId;
	DallianceReplyLatticeWidget widget;
	int replyNumber;
	private static BroadcastReplyLatticeWidgetUiBinder uiBinder = GWT
			.create(BroadcastReplyLatticeWidgetUiBinder.class);

	interface BroadcastReplyLatticeWidgetUiBinder extends
			UiBinder<Widget, DallianceReplyLatticeWidget> {
	}

	public DallianceReplyLatticeWidget(final Win win,
			final DispatchAsync dispatch, final SessionManager sessionManager,
			String deptName, final String staffName, String createDate, final String staffId) {
		this.win = win;
		this.dispatch = dispatch;
		this.sessionManager = sessionManager;

		this.widget = this;

		initWidget(uiBinder.createAndBindUi(this));
		if (!StringUtil.isEmpty(deptName))
			this.deptName.setText(deptName);
		if (!StringUtil.isEmpty(staffName))
			this.staffName.setText(staffName);
		if (!StringUtil.isEmpty(createDate))
			this.createDate.setText(createDate);

		this.dallianceBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				MessageSaveRequest request = new MessageSaveRequest();

				request.setSession(sessionManager.getSession());

//				request.setContent(display.getContent());
				List<String[]> organList=new ArrayList<String[]>();

						String[] nameAndId = new String[3];
						nameAndId[0] = staffId;
						nameAndId[1] = staffName;
						nameAndId[2] = OrganType.STAFF.toString();
						organList.add(nameAndId);
			
				request.setOrganList(organList);
				request.setQuietlyOrDalliance("DALLIANCE");
				
				dispatch.execute(request,
						new AsyncCallback<MessageSaveResponse>() {

							@Override
							public void onFailure(Throwable t) {
								win.alert(t.getMessage());
							}

							@Override
							public void onSuccess(
									MessageSaveResponse resp) {
								win.alertStaff("调戏成功!");

							}
						});
			}
		});

	}

}
