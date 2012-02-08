package com.chinarewards.gwt.elt.client.core.view;

import java.util.Date;

import com.chinarewards.gwt.elt.client.core.presenter.StaffPresenter.StaffDisplay;
import com.chinarewards.gwt.elt.client.core.view.constant.ViewConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

public class StaffWidget extends Composite implements StaffDisplay {

	@UiField
	DockLayoutPanel dock;


	@UiField
	Anchor logBtn;

	@UiField
	InlineLabel message;


	@UiField
	Anchor collectionBtn;
	

	
	@UiField
	Anchor managementCenter;
	@UiField
	Anchor giftExchange;
	@UiField
	Anchor staffCorner;
	@UiField
	Anchor awardShop;
	
	@UiField
	Anchor viewPoints;
	@UiField
	Anchor winninghistory;
	@UiField
	Anchor participationAwards;
	@UiField
	Anchor otherAwards;
	@UiField
	Anchor exchangeHistory;
	@UiField
	Anchor myMessage;
	
	// Set the format of datepicker.
	DateTimeFormat dateFormat = DateTimeFormat
			.getFormat(ViewConstants.date_format_chinese);

	interface StaffWidgetBinder extends UiBinder<Widget, StaffWidget> {
	}

	private static StaffWidgetBinder uiBinder = GWT
			.create(StaffWidgetBinder.class);

	public StaffWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		//init();
	}
//	 Style styleOn;
//	 Style styleNo;
//		private void init() {
//			  styleOn=this.awardShop.getElement().getParentElement().getStyle();
//			  styleNo=this.myMessage.getElement().getParentElement().getStyle();
//			  viewPoints.addClickHandler(new ClickHandler() {
//
//				@Override
//				public void onClick(ClickEvent event) {
//					viewPoints.setStyleName(styleOn);	
//					winninghistory.setStyleName(styleNo);
//					participationAwards.setStyleName(styleNo);
//					otherAwards.setStyleName(styleNo);
//					exchangeHistory.setStyleName(styleNo);
//					myMessage.setStyleName(styleNo);
//					awardShop.setStyleName(styleNo);
//				}
//			});
//			  winninghistory.addClickHandler(new ClickHandler() {
//
//				@Override
//				public void onClick(ClickEvent event) {
//					viewPoints.setStyleName(styleNo);	
//					winninghistory.setStyleName(styleOn);
//					participationAwards.setStyleName(styleNo);
//					otherAwards.setStyleName(styleNo);
//					exchangeHistory.setStyleName(styleNo);
//					myMessage.setStyleName(styleNo);
//					awardShop.setStyleName(styleNo);
//				}
//			});
//			  participationAwards.addClickHandler(new ClickHandler() {
//
//				@Override
//				public void onClick(ClickEvent event) {
//					viewPoints.setStyleName(styleNo);	
//					winninghistory.setStyleName(styleNo);
//					participationAwards.setStyleName(styleOn);
//					otherAwards.setStyleName(styleNo);
//					exchangeHistory.setStyleName(styleNo);
//					myMessage.setStyleName(styleNo);
//					awardShop.setStyleName(styleNo);
//				}
//			});
//			  otherAwards.addClickHandler(new ClickHandler() {
//
//				@Override
//				public void onClick(ClickEvent event) {
//					viewPoints.setStyleName(styleNo);	
//					winninghistory.setStyleName(styleNo);
//					participationAwards.setStyleName(styleNo);
//					otherAwards.setStyleName(styleOn);
//					exchangeHistory.setStyleName(styleNo);
//					myMessage.setStyleName(styleNo);
//					awardShop.setStyleName(styleNo);
//				}
//			});
//			  exchangeHistory.addClickHandler(new ClickHandler() {
//
//				@Override
//				public void onClick(ClickEvent event) {
//					viewPoints.setStyleName(styleNo);	
//					winninghistory.setStyleName(styleNo);
//					participationAwards.setStyleName(styleNo);
//					otherAwards.setStyleName(styleNo);
//					exchangeHistory.setStyleName(styleOn);
//					myMessage.setStyleName(styleNo);
//					awardShop.setStyleName(styleNo);
//				}
//			});
//			  myMessage.addClickHandler(new ClickHandler() {
//
//				@Override
//				public void onClick(ClickEvent event) {
//					viewPoints.setStyleName(styleNo);	
//					winninghistory.setStyleName(styleNo);
//					participationAwards.setStyleName(styleNo);
//					otherAwards.setStyleName(styleNo);
//					exchangeHistory.setStyleName(styleNo);
//					myMessage.setStyleName(styleOn);
//					awardShop.setStyleName(styleNo);
//				}
//			});
//			  awardShop.addClickHandler(new ClickHandler() {
//
//				@Override
//				public void onClick(ClickEvent event) {
//					viewPoints.setStyleName(styleNo);	
//					winninghistory.setStyleName(styleNo);
//					participationAwards.setStyleName(styleNo);
//					otherAwards.setStyleName(styleNo);
//					exchangeHistory.setStyleName(styleNo);
//					myMessage.setStyleName(styleNo);
//					awardShop.setStyleName(styleOn);
//				}
//			});
//		}
	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public HasClickHandlers getlogBtn() {
		return logBtn;
	}


	@Override
	public DockLayoutPanel getDock() {
		return dock;
	}


	@Override
	public void setMessage(String userName) {
		String time = dateFormat.format(new Date());
		String msg = "欢迎你，" + userName + "！今天是:" + time;
		message.setText(msg);
	}




	@Override
	public HasClickHandlers getBtnCollection() {
		return collectionBtn;
	}

	@Override
	public HasClickHandlers getManagementCenter() {

		return managementCenter;
	}

	@Override
	public HasClickHandlers getGiftExchange() {
		return giftExchange;
	}

	@Override
	public HasClickHandlers getStaffCorner() {
		return staffCorner;
	}
	@Override
	public void disableManagementCenter() {
		managementCenter.setVisible(false);
		
	}

	@Override
	public void disableGiftExchange() {
		giftExchange.setVisible(false);
		
	}

	@Override
	public void disableStaffCorner() {
		staffCorner.setVisible(false);
		
	}

	@Override
	public HasClickHandlers getAwardShop() {
		return awardShop;
	}

	@Override
	public HasClickHandlers getViewPoints() {
		return this.viewPoints;
	}

	@Override
	public HasClickHandlers getWinninghistory() {
		return this.winninghistory;
	}

	@Override
	public HasClickHandlers getParticipationAwards() {
		return this.participationAwards;
	}

	@Override
	public HasClickHandlers getOtherAwards() {
		return this.otherAwards;
	}

	@Override
	public HasClickHandlers getExchangeHistory() {
		return this.exchangeHistory;
	}

	@Override
	public HasClickHandlers getMyMessage() {
		return this.myMessage;
	}
}
