package com.chinarewards.gwt.elt.client.core.view;

import java.util.Date;

import com.chinarewards.gwt.elt.client.core.presenter.StaffPresenter.StaffDisplay;
import com.chinarewards.gwt.elt.client.core.view.constant.ViewConstants;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class StaffWidget extends Composite implements StaffDisplay {

	@UiField
	Panel dock;
	@UiField
	Panel smaillShopWindow;
	@UiField
	Panel rewardPanel;
	
	@UiField
	Anchor logBtn;

	@UiField
	InlineLabel message;


	@UiField
	Anchor collectionBtn;
	@UiField
	Anchor more;
	
	
	
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
	@UiField
	Anchor staffHeavenIndex;
	@UiField
	Anchor staffAnchor;
	@UiField
	Anchor corpBroadcastAnchor;
	@UiField
	Anchor gloryAnchor;
	@UiField
	Anchor settingAnchor;
	
	@UiField
	Image photo;
	@UiField
	InlineLabel staffName;
	@UiField
	InlineLabel station;
	@UiField
	InlineLabel deptName;
	@UiField
	InlineLabel integral;
	@UiField
	InlineLabel integral2;
	
	@UiField
	Anchor myWinReward;
	@UiField
	Anchor allReward;
	@UiField
	Anchor effortRewardItem;
	@UiField
	Anchor allRewardItem;
	
	// Set the format of datepicker.
	DateTimeFormat dateFormat = DateTimeFormat
			.getFormat(ViewConstants.date_format_chinese);

	interface StaffWidgetBinder extends UiBinder<Widget, StaffWidget> {
	}

	private static StaffWidgetBinder uiBinder = GWT
			.create(StaffWidgetBinder.class);

	public StaffWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}
	 String styleOn="";
	 String styleNo="";
	 
	 String anchorOn="";
	 String anchorNo="";
	 
	 String styleRewardOn="";
	 String styleRewardNo="";
	 
		private void init() {
			styleRewardOn=myWinReward.getElement().getParentElement().getAttribute("class");
			
			
			effortRewardItem.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					effortRewardItem.getElement().getParentElement().setAttribute("class", styleRewardOn);						
					allRewardItem.getElement().getParentElement().setAttribute("class", styleRewardNo);						
				}
			});
			allRewardItem.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					effortRewardItem.getElement().getParentElement().setAttribute("class", styleRewardNo);						
					allRewardItem.getElement().getParentElement().setAttribute("class", styleRewardOn);						
				}
			});
			
			
			myWinReward.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					myWinReward.getElement().getParentElement().setAttribute("class", styleRewardOn);						
					allReward.getElement().getParentElement().setAttribute("class", styleRewardNo);						
				}
			});
			allReward.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					myWinReward.getElement().getParentElement().setAttribute("class", styleRewardNo);						
					allReward.getElement().getParentElement().setAttribute("class", styleRewardOn);						
				}
			});
			
			
			anchorOn=this.staffHeavenIndex.getStyleName();
			anchorNo=this.staffAnchor.getStyleName();
			staffHeavenIndex.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					staffHeavenIndex.setStyleName(anchorOn);
					staffAnchor.setStyleName(anchorNo);
					corpBroadcastAnchor.setStyleName(anchorNo);
					gloryAnchor.setStyleName(anchorNo);
					settingAnchor.setStyleName(anchorNo);
					
				}
			});
			staffAnchor.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					staffHeavenIndex.setStyleName(anchorNo);
					staffAnchor.setStyleName(anchorOn);
					corpBroadcastAnchor.setStyleName(anchorNo);
					gloryAnchor.setStyleName(anchorNo);
					settingAnchor.setStyleName(anchorNo);
					
				}
			});
			corpBroadcastAnchor.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					staffHeavenIndex.setStyleName(anchorNo);
					staffAnchor.setStyleName(anchorNo);
					corpBroadcastAnchor.setStyleName(anchorOn);
					gloryAnchor.setStyleName(anchorNo);
					settingAnchor.setStyleName(anchorNo);
					
				}
			});
			gloryAnchor.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					staffHeavenIndex.setStyleName(anchorNo);
					staffAnchor.setStyleName(anchorNo);
					corpBroadcastAnchor.setStyleName(anchorNo);
					gloryAnchor.setStyleName(anchorOn);
					settingAnchor.setStyleName(anchorNo);
					
				}
			});
			settingAnchor.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					staffHeavenIndex.setStyleName(anchorNo);
					staffAnchor.setStyleName(anchorNo);
					corpBroadcastAnchor.setStyleName(anchorNo);
					gloryAnchor.setStyleName(anchorNo);
					settingAnchor.setStyleName(anchorOn);
					
				}
			});
			
			
			  styleOn=this.viewPoints.getElement().getParentElement().getAttribute("class");
			  viewPoints.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					viewPoints.getElement().getParentElement().setAttribute("class", styleOn);	
					winninghistory.getElement().getParentElement().setAttribute("class", styleNo);	
					participationAwards.getElement().getParentElement().setAttribute("class", styleNo);	
					otherAwards.getElement().getParentElement().setAttribute("class", styleNo);	
					exchangeHistory.getElement().getParentElement().setAttribute("class", styleNo);	
					myMessage.getElement().getParentElement().setAttribute("class", styleNo);	
					awardShop.getElement().getParentElement().setAttribute("class", styleNo);	

				}
			});
			  winninghistory.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					viewPoints.getElement().getParentElement().setAttribute("class", styleNo);	
					winninghistory.getElement().getParentElement().setAttribute("class", styleOn);
					participationAwards.getElement().getParentElement().setAttribute("class", styleNo);
					otherAwards.getElement().getParentElement().setAttribute("class", styleNo);
					exchangeHistory.getElement().getParentElement().setAttribute("class", styleNo);
					myMessage.getElement().getParentElement().setAttribute("class", styleNo);
					awardShop.getElement().getParentElement().setAttribute("class", styleNo);
				}
			});
			  participationAwards.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					viewPoints.getElement().getParentElement().setAttribute("class", styleNo);	
					winninghistory.getElement().getParentElement().setAttribute("class", styleNo);
					participationAwards.getElement().getParentElement().setAttribute("class", styleOn);
					otherAwards.getElement().getParentElement().setAttribute("class", styleNo);
					exchangeHistory.getElement().getParentElement().setAttribute("class", styleNo);
					myMessage.getElement().getParentElement().setAttribute("class", styleNo);
					awardShop.getElement().getParentElement().setAttribute("class", styleNo);
				}
			});
			  otherAwards.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					viewPoints.getElement().getParentElement().setAttribute("class", styleNo);	
					winninghistory.getElement().getParentElement().setAttribute("class", styleNo);
					participationAwards.getElement().getParentElement().setAttribute("class", styleNo);
					otherAwards.getElement().getParentElement().setAttribute("class", styleOn);
					exchangeHistory.getElement().getParentElement().setAttribute("class", styleNo);
					myMessage.getElement().getParentElement().setAttribute("class", styleNo);
					awardShop.getElement().getParentElement().setAttribute("class", styleNo);
				}
			});
			  exchangeHistory.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					viewPoints.getElement().getParentElement().setAttribute("class", styleNo);	
					winninghistory.getElement().getParentElement().setAttribute("class", styleNo);
					participationAwards.getElement().getParentElement().setAttribute("class", styleNo);
					otherAwards.getElement().getParentElement().setAttribute("class", styleNo);
					exchangeHistory.getElement().getParentElement().setAttribute("class", styleOn);
					myMessage.getElement().getParentElement().setAttribute("class", styleNo);
					awardShop.getElement().getParentElement().setAttribute("class", styleNo);
				}
			});
			  myMessage.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					viewPoints.getElement().getParentElement().setAttribute("class", styleNo);	
					winninghistory.getElement().getParentElement().setAttribute("class", styleNo);
					participationAwards.getElement().getParentElement().setAttribute("class", styleNo);
					otherAwards.getElement().getParentElement().setAttribute("class", styleNo);
					exchangeHistory.getElement().getParentElement().setAttribute("class", styleNo);
					myMessage.getElement().getParentElement().setAttribute("class", styleOn);
					awardShop.getElement().getParentElement().setAttribute("class", styleNo);
				}
			});
			  awardShop.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					viewPoints.getElement().getParentElement().setAttribute("class", styleNo);	
					winninghistory.getElement().getParentElement().setAttribute("class", styleNo);
					participationAwards.getElement().getParentElement().setAttribute("class", styleNo);
					otherAwards.getElement().getParentElement().setAttribute("class", styleNo);
					exchangeHistory.getElement().getParentElement().setAttribute("class", styleNo);
					myMessage.getElement().getParentElement().setAttribute("class", styleNo);
					awardShop.getElement().getParentElement().setAttribute("class", styleOn);
				}
			});
		}
	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public HasClickHandlers getlogBtn() {
		return logBtn;
	}


	@Override
	public Panel  getDock() {
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
	@Override
	public HasClickHandlers getStaffHeavenIndex() {
		return staffHeavenIndex;
	}
	@Override
	public HasClickHandlers getStaffAnchor() {
		return staffAnchor;
	}
	@Override
	public HasClickHandlers getCorpBroadcastAnchor() {
		return corpBroadcastAnchor;
	}
	@Override
	public HasClickHandlers getGloryAnchor() {
		return gloryAnchor;
	}
	@Override
	public HasClickHandlers getSettingAnchor() {
		return settingAnchor;
	}
	@Override
	public void setPhoto(String photo) {
		this.photo.setUrl("imageshow?imageName="+photo);
	}
	@Override
	public void setStaffName(String staffName) {
		this.staffName.setText(staffName);
	}
	@Override
	public void setStation(String station) {
		this.station.setText(station);
	}
	@Override
	public void setDeptName(String deptName) {
		this.deptName.setText(deptName);
	}
	@Override
	public void setIntegral(int integral) {
		this.integral.setText(integral+"");
		this.integral2.setText(integral+"");
	}
	@Override
	public Panel getSmaillShopWindow() {
		return smaillShopWindow;
	}
	@Override
	public HasClickHandlers getMore() {
		return more;
	}
	@Override
	public Panel getRewardPanel() {
		return rewardPanel;
	}
}
