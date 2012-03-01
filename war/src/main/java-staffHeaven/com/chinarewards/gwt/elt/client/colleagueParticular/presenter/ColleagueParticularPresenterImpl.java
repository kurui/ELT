package com.chinarewards.gwt.elt.client.colleagueParticular.presenter;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.chinarewards.gwt.elt.client.corpBroadcast.presenter.CorpBroadcastPresenter;
import com.chinarewards.gwt.elt.client.gloryBroadcast.presenter.GloryBroadcastPresenter;
import com.chinarewards.gwt.elt.client.messageSave.presenter.MessageSavePresenter;
import com.chinarewards.gwt.elt.client.mvp.BasePresenter;
import com.chinarewards.gwt.elt.client.mvp.ErrorHandler;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.staffView.presenter.StaffViewPresenter;
import com.chinarewards.gwt.elt.client.support.SessionManager;
import com.chinarewards.gwt.elt.client.win.Win;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;

public class ColleagueParticularPresenterImpl extends
		BasePresenter<ColleagueParticularPresenter.ColleagueParticularDisplay> implements
		ColleagueParticularPresenter {

//	private final DispatchAsync dispatch;
//	private final SessionManager sessionManager;
//	private final Win win;
	final ErrorHandler errorHandler;
	String staffId;
	GloryBroadcastPresenter gloryBroadcastPresenter;
	CorpBroadcastPresenter corpBroadcastPresenter;
	MessageSavePresenter messageSavePresenter;
	StaffViewPresenter staffViewPresenter;
	@Inject
	public ColleagueParticularPresenterImpl(EventBus eventBus,
			ColleagueParticularDisplay display, DispatchAsync dispatch,
			SessionManager sessionManager,Win win,ErrorHandler errorHandler,GloryBroadcastPresenter gloryBroadcastPresenter,CorpBroadcastPresenter corpBroadcastPresenter,MessageSavePresenter messageSavePresenter,StaffViewPresenter staffViewPresenter) {
		super(eventBus, display);
	//	this.dispatch = dispatch;
	//	this.sessionManager = sessionManager;
		this.errorHandler=errorHandler;
	//	this.win=win;
		this.gloryBroadcastPresenter=gloryBroadcastPresenter;
		this.corpBroadcastPresenter=corpBroadcastPresenter;
		this.messageSavePresenter=messageSavePresenter;
		this.staffViewPresenter=staffViewPresenter;
	}

	@Override
	public void bind() {
		init();
		gloryBroadcastPresenter.bind();
		display.getResultPanel().clear();
		display.getResultPanel().add(gloryBroadcastPresenter.getDisplay().asWidget());
		display.getResultPanel().getElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().setAttribute("style", "margin-left: 0px;");
		display.getResultPanel().getElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().setAttribute("style", "margin-right: 0px;");
	}
	
	private void init()
	{
		display.getStaffView().addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				unbindAll();
				staffViewPresenter.initStaffView_Colleague(staffId,true);
				staffViewPresenter.bind();
				display.getResultPanel().clear();
				display.getResultPanel().add(staffViewPresenter.getDisplay().asWidget());
				
				display.getResultPanel().getElement().getFirstChildElement().getFirstChildElement().setAttribute("style", "padding-top: 0px;");
				display.getResultPanel().getElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().setAttribute("style", "display: none;");
				display.getResultPanel().getElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().getNextSiblingElement().setAttribute("style", "display: none;");
				display.getResultPanel().getElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().getNextSiblingElement().getNextSiblingElement().setAttribute("style", "display: none;");
				display.getResultPanel().getElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().getNextSiblingElement().getNextSiblingElement().getNextSiblingElement().setAttribute("style", "float: left;width: 100%;");
				display.getResultPanel().getElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().getNextSiblingElement().getNextSiblingElement().getNextSiblingElement().getNextSiblingElement().setAttribute("style", "display: none;");
				display.getResultPanel().getElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().getNextSiblingElement().getNextSiblingElement().getNextSiblingElement().getNextSiblingElement().getNextSiblingElement().setAttribute("style", "display: none;");
				display.getResultPanel().getElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().getNextSiblingElement().getNextSiblingElement().getNextSiblingElement().getNextSiblingElement().getNextSiblingElement().getNextSiblingElement().setAttribute("style", "display: none;");
				display.getResultPanel().getElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().getNextSiblingElement().getNextSiblingElement().getNextSiblingElement().getNextSiblingElement().getNextSiblingElement().getNextSiblingElement().getNextSiblingElement().setAttribute("style", "display: none;");
				display.getResultPanel().getElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().getNextSiblingElement().getNextSiblingElement().getNextSiblingElement().getNextSiblingElement().getNextSiblingElement().getNextSiblingElement().getNextSiblingElement().getNextSiblingElement().setAttribute("style", "display: none;");
				display.getResultPanel().getElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().getNextSiblingElement().getNextSiblingElement().getNextSiblingElement().getNextSiblingElement().getNextSiblingElement().getNextSiblingElement().getNextSiblingElement().getNextSiblingElement().getNextSiblingElement().setAttribute("style", "display: none;");
			}
		});
		display.getStaffBroadcast().addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				unbindAll();
				corpBroadcastPresenter.initStaffBroadcast(staffId);
				corpBroadcastPresenter.bind();
				display.getResultPanel().clear();
				display.getResultPanel().add(corpBroadcastPresenter.getDisplay().asWidget());
				display.getResultPanel().getElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().setAttribute("style", "margin-left: 0px;");
				display.getResultPanel().getElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().setAttribute("style", "margin-right: 0px;");
				
				display.getResultPanel().getElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().getNextSiblingElement().setAttribute("style", "margin-left: 0px;");
				display.getResultPanel().getElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().getNextSiblingElement().getFirstChildElement().setAttribute("style", "margin-right: 0px;");
				
			}
		});
		display.getStaffGlory().addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				unbindAll();
				gloryBroadcastPresenter.bind();
				display.getResultPanel().clear();
				display.getResultPanel().add(gloryBroadcastPresenter.getDisplay().asWidget());
				display.getResultPanel().getElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().setAttribute("style", "margin-left: 0px;");
				display.getResultPanel().getElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().setAttribute("style", "margin-right: 0px;");
			}
		});
		display.getSendMessage().addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				unbindAll();
				messageSavePresenter.bind();
				display.getResultPanel().clear();
				display.getResultPanel().add(messageSavePresenter.getDisplay().asWidget());
				display.getResultPanel().getElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().setAttribute("style", "margin: 0px;");
				display.getResultPanel().getElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().setAttribute("style", "border: 0 none;");
				display.getResultPanel().getElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().getNextSiblingElement().setAttribute("style", "display: none;");
				display.getResultPanel().getElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().getNextSiblingElement().getNextSiblingElement().setAttribute("style", "display: none;");
				display.getResultPanel().getElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().getFirstChildElement().getNextSiblingElement().getNextSiblingElement().getNextSiblingElement().setAttribute("style", "display: none;");
						
			}
		});
	}

	@Override
	public void initColleagueParticular(String staffId) {
			this.staffId=staffId;
	}
	private void unbindAll()
	{
		gloryBroadcastPresenter.unbind();
		corpBroadcastPresenter.unbind();
		messageSavePresenter.unbind();
		staffViewPresenter.unbind();
	}
	



}
