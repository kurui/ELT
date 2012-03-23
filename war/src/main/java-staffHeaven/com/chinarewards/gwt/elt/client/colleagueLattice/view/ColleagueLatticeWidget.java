package com.chinarewards.gwt.elt.client.colleagueLattice.view;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.chooseOrganization.model.OrganSearchCriteria.OrganType;
import com.chinarewards.gwt.elt.client.colleagueParticular.plugin.ColleagueParticularConstants;
import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.core.ui.DialogCloseListener;
import com.chinarewards.gwt.elt.client.mailSave.dialog.MailSaveDialog;
import com.chinarewards.gwt.elt.client.messageSave.dialog.MessageSaveDialog;
import com.chinarewards.gwt.elt.client.messageSave.request.MessageSaveRequest;
import com.chinarewards.gwt.elt.client.messageSave.request.MessageSaveResponse;
import com.chinarewards.gwt.elt.client.rewards.model.OrganicationClient;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.win.Win;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Provider;

public class ColleagueLatticeWidget extends Composite {

	@UiField
	Image photo;
	@UiField
	Anchor staffName;
	@UiField
	InlineLabel deptName;
	
	@UiField
	Anchor quietly;
	@UiField
	Anchor dalliance;
	@UiField
	Anchor email;
	

	private static ColleagueLatticeWidgetUiBinder uiBinder = GWT
			.create(ColleagueLatticeWidgetUiBinder.class);

	interface ColleagueLatticeWidgetUiBinder extends
			UiBinder<Widget, ColleagueLatticeWidget> {
	}



	public ColleagueLatticeWidget(final String staffId,final String staffName,String deptName,String photo,final Provider<MessageSaveDialog> messageSaveDialog,final Provider<MailSaveDialog> mailSaveDialog,final Win win, final SessionManager sessionManager, final DispatchAsync dispatch) {

		initWidget(uiBinder.createAndBindUi(this));
		this.staffName.setText(staffName);
		this.deptName.setText(deptName);
		if(!StringUtil.isEmpty(photo))
		this.photo.setUrl("imageshow?imageName="+photo);
	
		this.staffName.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Platform.getInstance()
				.getEditorRegistry()
				.openEditor(
						ColleagueParticularConstants.EDITOR_COLLEAGUEPARTICULAR_SEARCH,
						"EDITOR_COLLEAGUEPARTICULAR_SEARCH_DO_ID", new OrganicationClient(staffId,staffName));
				
			}
		});
		this.photo.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Platform.getInstance()
				.getEditorRegistry()
				.openEditor(
						ColleagueParticularConstants.EDITOR_COLLEAGUEPARTICULAR_SEARCH,
						"EDITOR_COLLEAGUEPARTICULAR_SEARCH_DO_ID", new OrganicationClient(staffId,staffName));
				
			}
		});
		this.quietly.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(staffId.equals(sessionManager.getSession().getStaffId()))
				{
					win.alertStaff("不能给自己发悄悄话!");
					return ;
				}
				final MessageSaveDialog dialog = messageSaveDialog.get();
				dialog.initStaff(staffId, staffName);
				Platform.getInstance().getSiteManager().openDialog(dialog, new DialogCloseListener() {
					public void onClose(String dialogId,
							String instanceId) {
						
					}
				});
				
			}
		});

		this.dalliance.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(staffId.equals(sessionManager.getSession().getStaffId()))
				{
					win.alertStaff("不能调戏自己!");
					return ;
				}
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

			this.email.addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							if(staffId.equals(sessionManager.getSession().getStaffId()))
							{
								win.alertStaff("不能给自己发邮寄!");
								return ;
							}
							final MailSaveDialog dialog = mailSaveDialog.get();
							dialog.initStaff(staffId, staffName);
							Platform.getInstance().getSiteManager().openDialog(dialog, new DialogCloseListener() {
								public void onClose(String dialogId,
										String instanceId) {
									
								}
							});
							
						}
					});

	}

}
