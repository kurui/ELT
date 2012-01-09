package com.chinarewards.gwt.elt.client.core.ui.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.chinarewards.gwt.elt.client.awardReward.plugin.AwardRewardConstants;
import com.chinarewards.gwt.elt.client.core.ui.MenuItem;
import com.chinarewards.gwt.elt.client.core.ui.MenuProcessor;
import com.chinarewards.gwt.elt.client.core.ui.event.MenuClickEvent;
import com.chinarewards.gwt.elt.client.detailsOfAward.plugin.DetailsOfAwardConstants;
import com.chinarewards.gwt.elt.client.mvp.EventBus;
import com.chinarewards.gwt.elt.client.nominate.plugin.NominateConstants;
import com.chinarewards.gwt.elt.client.rewardItem.plugin.RewardsItemConstants;
import com.chinarewards.gwt.elt.client.rewards.plugin.RewardsListConstants;
import com.chinarewards.gwt.elt.client.user.plugin.UserConstants;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class ButtonMenuProcessor implements MenuProcessor {

	final EventBus eventBus;
	List<MenuItem> items = new LinkedList<MenuItem>();
	MenuNode root = new MenuNode(null);

	@Inject
	public ButtonMenuProcessor(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	public void add(MenuItem item) {
		items.add(item);
	}

	public MenuItem getMenuItem(String menuId) {
		if (menuId != null) {
			// XXX use smarter data structures
			for (MenuItem i : items) {
				if (menuId.endsWith(i.getMenuId())) {
					return i;
				}
			}
		}
		return null;
	}

	public void render(Panel container) {
		// organize tree
		// sort according to menuID string length
		Collections.sort(items, new Comparator<MenuItem>() {
			public int compare(MenuItem paramT1, MenuItem paramT2) {
				return paramT1.getMenuId().length()
						- paramT2.getMenuId().length();
			}
		});

		// pack into the MenuNode structure
		for (MenuItem m : items) {
			// append children recursively
			root.appendChild(new MenuNode(m));
		}

		ScrollPanel menuWrapper = new ScrollPanel(createButtonMenuWidget(null));
		container.add(menuWrapper);
	}

	/**
	 * 
	 * 
	 * @param parent
	 * @param node
	 */
	private Widget createButtonMenuWidget(String name) {

		VerticalPanel grid = new VerticalPanel();
		grid.setWidth("100%");
		// int i = 0;
		for (MenuNode node : root.getChildren()) {
			Button button = new Button();
			final MenuItem menuItem = node.getValue();
			if (name != null) {
				List<String> items = getMenuItemName(name);
				if (!items.contains(menuItem.getMenuId()))
					continue;
			}
			else
			{
				break;
			}
			button.setText(menuItem.getTitle());
			button.setStyleName("gwt-menu-Button");
			button.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent paramClickEvent) {
					eventBus.fireEvent(new MenuClickEvent(menuItem));
				}
			});
			grid.add(button);
			// i++;
		}

		return grid;
	}

	private List<String> getMenuItemName(String keyname) {
		List<String> items = new ArrayList<String>();
		if ("RewardItem".equals(keyname)) {
			items.add(RewardsItemConstants.MENU_REWARDSITEM_ADD);
			items.add(RewardsItemConstants.MENU_REWARDSITEM_List);
		} else if ("Reward".equals(keyname)) {
			items.add(NominateConstants.MENU_NOMINATE_SEARCH);
			items.add(AwardRewardConstants.MENU_AWARDREWARD_SEARCH);
			items.add(DetailsOfAwardConstants.MENU_DETAILSOFAWARD_SEARCH);
			items.add(RewardsListConstants.MENU_REWARDSLIST_SEARCH);
		} else if ("Staff".equals(keyname)) {
			items.add(UserConstants.MENU_USER_SEARCH);
		} else if ("Setting".equals(keyname)) {
			items.add("sample");
		}else if("Gift".equals(keyname))
		{
			//items.add("xxx");
		}
		return items;
	}

	@Override
	public void initrender(Panel container, String name) {
		// organize tree
		// sort according to menuID string length
		Collections.sort(items, new Comparator<MenuItem>() {
			public int compare(MenuItem paramT1, MenuItem paramT2) {
				return paramT1.getMenuId().length()
						- paramT2.getMenuId().length();
			}
		});

		// pack into the MenuNode structure
		// for (MenuItem m : items) {
		// // append children recursively
		// root.appendChild(new MenuNode(m));
		// }

		ScrollPanel menuWrapper = new ScrollPanel(createButtonMenuWidget(name));
		container.clear();
		container.add(menuWrapper);

	}

}
