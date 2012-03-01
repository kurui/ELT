package com.chinarewards.gwt.elt.client.colleagueParticular.view;


import com.chinarewards.gwt.elt.client.colleagueParticular.presenter.ColleagueParticularPresenter.ColleagueParticularDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class ColleagueParticularWidget extends Composite implements ColleagueParticularDisplay {

	@UiField
	Anchor staffView;
	@UiField
	Anchor staffBroadcast;
	@UiField
	Anchor staffGlory;
	@UiField
	Anchor sendMessage;
	@UiField
	Panel resultPanel;
	
	private static ColleagueParticularWidgetUiBinder uiBinder = GWT
			.create(ColleagueParticularWidgetUiBinder.class);

	interface ColleagueParticularWidgetUiBinder extends
			UiBinder<Widget, ColleagueParticularWidget> {
	}

	public ColleagueParticularWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}
	String styleOn="";
	String styleNo="";
	
	private void init() {
		styleOn=staffGlory.getElement().getParentElement().getAttribute("class");
		
		staffView.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				staffView.getElement().getParentElement().setAttribute("class", styleOn);						
				staffBroadcast.getElement().getParentElement().setAttribute("class", styleNo);						
				staffGlory.getElement().getParentElement().setAttribute("class", styleNo);						
				sendMessage.getElement().getParentElement().setAttribute("class", styleNo);						
					
			}
		});
		staffBroadcast.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				staffView.getElement().getParentElement().setAttribute("class", styleNo);						
				staffBroadcast.getElement().getParentElement().setAttribute("class", styleOn);						
				staffGlory.getElement().getParentElement().setAttribute("class", styleNo);						
				sendMessage.getElement().getParentElement().setAttribute("class", styleNo);						
					
			}
		});
		staffGlory.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				staffView.getElement().getParentElement().setAttribute("class", styleNo);						
				staffBroadcast.getElement().getParentElement().setAttribute("class", styleNo);						
				staffGlory.getElement().getParentElement().setAttribute("class", styleOn);						
				sendMessage.getElement().getParentElement().setAttribute("class", styleNo);						
					
			}
		});
		sendMessage.addClickHandler(new ClickHandler() {	
			@Override
			public void onClick(ClickEvent event) {
				staffView.getElement().getParentElement().setAttribute("class", styleNo);						
				staffBroadcast.getElement().getParentElement().setAttribute("class", styleNo);						
				staffGlory.getElement().getParentElement().setAttribute("class", styleNo);						
				sendMessage.getElement().getParentElement().setAttribute("class", styleOn);						
					
			}
		});
	}

	@Override
	public Anchor getStaffView() {
		return staffView;
	}

	@Override
	public Anchor getStaffBroadcast() {
		return staffBroadcast;
	}

	@Override
	public Anchor getStaffGlory() {
		return staffGlory;
	}

	@Override
	public Anchor getSendMessage() {
		return sendMessage;
	}

	@Override
	public Panel getResultPanel() {
		return resultPanel;
	}



}
