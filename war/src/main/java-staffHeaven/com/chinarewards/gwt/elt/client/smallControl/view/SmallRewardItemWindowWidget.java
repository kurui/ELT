package com.chinarewards.gwt.elt.client.smallControl.view;

import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.rewardItem.plugin.RewardsItemConstants;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsItemClient;
import com.chinarewards.gwt.elt.client.rewards.plugin.RewardsListStaffConstants;
import com.chinarewards.gwt.elt.model.rewards.RewardsPageClient;
import com.chinarewards.gwt.elt.util.StringUtil;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

public class SmallRewardItemWindowWidget extends Composite {

	@UiField
	Anchor rewardItemName;
	@UiField
	InlineLabel integral;
	@UiField
	Image rewardItemPhoto;
	

	private static GloryBroadcastWidgetUiBinder uiBinder = GWT
			.create(GloryBroadcastWidgetUiBinder.class);

	interface GloryBroadcastWidgetUiBinder extends
			UiBinder<Widget, SmallRewardItemWindowWidget> {
	}

	public SmallRewardItemWindowWidget(final String rewardItemId,
			String rewardItemName, String integral,String rewardItemPhoto,final String thisAction) {
		initWidget(uiBinder.createAndBindUi(this));
		this.rewardItemName.setText(rewardItemName);
		this.integral.setText(integral);
		this.rewardItemPhoto.setUrl("elt/images/reward_emplate.png");
		
		if (rewardItemId != null) {

			this.rewardItemName.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {

					if (!StringUtil.isEmpty(thisAction)) {
						if ("RewardsItem_STAFF_RUSH".equals(thisAction)) {
							openRewardsItemView(rewardItemId);
						}else if("RewardsItem_STAFF_GETED".equals(thisAction)){
							openRewardHistoryStaff();
						} else if ("RewardsItem_ALL".equals(thisAction)) {
							openRewardsItemView(rewardItemId);
						}
					}

				}
			});
			
			this.rewardItemPhoto.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {

					if (!StringUtil.isEmpty(thisAction)) {
						if ("RewardsItem_STAFF_RUSH".equals(thisAction)) {
							openRewardsItemView(rewardItemId);
						} else if("RewardsItem_STAFF_GETED".equals(thisAction)){
							openRewardHistoryStaff();
						}else if ("RewardsItem_ALL".equals(thisAction)) {
							openRewardsItemView(rewardItemId);
						}
					}

				}
			});
		}
	}
	
	private void openRewardsItemView(String rewardsItemId) {
		RewardsItemClient client = new RewardsItemClient();
		client.setId(rewardsItemId);
		Platform.getInstance()
				.getEditorRegistry()
				.openEditor(RewardsItemConstants.EDITOR_REWARDSITEM_VIEW_STAFF,
						RewardsItemConstants.EDITOR_REWARDSITEM_VIEW_STAFF,
						client);
	}
	
	private void openRewardHistoryStaff(){
		RewardsPageClient client = new RewardsPageClient();
		Platform.getInstance()
				.getEditorRegistry()
				.openEditor(
						RewardsListStaffConstants.EDITOR_REWARDSLIST_STAFF_SEARCH,
						"EDITOR_REWARDSLIST_STAFF_SEARCH_DO_ID",
						client);
	}

}
