package com.chinarewards.gwt.elt.client.hrbox.view;

import com.chinarewards.gwt.elt.client.core.Platform;
import com.chinarewards.gwt.elt.client.core.presenter.DockPresenter;
import com.chinarewards.gwt.elt.client.core.ui.MenuProcessor;
import com.chinarewards.gwt.elt.client.detailsOfAward.plugin.DetailsOfAwardConstants;
import com.chinarewards.gwt.elt.client.rewards.model.RewardsClient;
import com.chinarewards.gwt.elt.model.rewards.RewardPageType;
import com.chinarewards.gwt.elt.model.rewards.RewardsPageClient;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class RewardWindowWidget extends Composite {

	
	@UiField
	Anchor rewardName;
	@UiField
	Anchor image;
	 final MenuProcessor menuProcessor;
	final DockPresenter dockPresenter;
	String shopId;
	private static GloryBroadcastWidgetUiBinder uiBinder = GWT
			.create(GloryBroadcastWidgetUiBinder.class);

	interface GloryBroadcastWidgetUiBinder extends
			UiBinder<Widget, RewardWindowWidget> {
	}

	public RewardWindowWidget(final String shopId, String shopName,final MenuProcessor menuProcessor, final DockPresenter dockPresenter) {
		initWidget(uiBinder.createAndBindUi(this));
		this.shopId = shopId;
		this.rewardName.setText(shopName);
		this.menuProcessor = menuProcessor;
        this.dockPresenter = dockPresenter;

		if (shopId != null) {

			this.rewardName.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					dockPresenter.getDisplay().changeTopMenu("Reward");
					dockPresenter.getDisplay().setMenuTitle("应用奖项");
					menuProcessor.initrender(dockPresenter.getDisplay().getMenu(), "Reward");
					RewardsPageClient rpc=new RewardsPageClient();
					rpc.setTitleName("已颁奖历史");
					rpc.setPageType(RewardPageType.DETAILSOFAWARDPAGE);
					RewardsClient o = new RewardsClient();
					o.setId(shopId);
					Platform.getInstance()
					.getEditorRegistry()
					.openEditor(
							DetailsOfAwardConstants.EDITOR_DETAILSOFAWARD_SEARCH,
							DetailsOfAwardConstants.EDITOR_DETAILSOFAWARD_SEARCH
									+ shopId, o);
					menuProcessor.changItemColor("已颁奖历史");

				}
			});
        this.image.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					dockPresenter.getDisplay().changeTopMenu("Reward");
					dockPresenter.getDisplay().setMenuTitle("应用奖项");
					menuProcessor.initrender(dockPresenter.getDisplay().getMenu(), "Reward");
					RewardsPageClient rpc=new RewardsPageClient();
					rpc.setTitleName("已颁奖历史");
					rpc.setPageType(RewardPageType.DETAILSOFAWARDPAGE);
					RewardsClient o = new RewardsClient();
					o.setId(shopId);
					Platform.getInstance()
					.getEditorRegistry()
					.openEditor(
							DetailsOfAwardConstants.EDITOR_DETAILSOFAWARD_SEARCH,
							DetailsOfAwardConstants.EDITOR_DETAILSOFAWARD_SEARCH
									+ shopId, o);
					menuProcessor.changItemColor("已颁奖历史");

				}
			});
		}
	}

}
