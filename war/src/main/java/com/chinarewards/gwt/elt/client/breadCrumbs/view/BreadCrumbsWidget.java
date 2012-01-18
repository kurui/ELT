package com.chinarewards.gwt.elt.client.breadCrumbs.view;

import java.util.List;

import com.chinarewards.gwt.elt.client.breadCrumbs.model.MenuBreadVo;
import com.chinarewards.gwt.elt.client.breadCrumbs.presenter.BreadCrumbsPresenter.BreadCrumbsDisplay;
import com.chinarewards.gwt.elt.client.widget.Span;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class BreadCrumbsWidget extends Composite implements BreadCrumbsDisplay {
	
	@UiField
	Panel titleText;
	
	public BreadCrumbsWidget() {
		initWidget(uiBinder.createAndBindUi(this));

	}

	interface ChooseStaffPanelBinder extends
			UiBinder<Widget, BreadCrumbsWidget> {
	}

	private static ChooseStaffPanelBinder uiBinder = GWT
			.create(ChooseStaffPanelBinder.class);

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public void setTitleText(List<MenuBreadVo> menuBreadVo) {
		for (MenuBreadVo menu:menuBreadVo) {
			if(menu.getMenuUrl()!=null)
			{
				Anchor a=new Anchor();
				a.setText(menu.getMenuName());
				a.setStyleName("breadcrumbsAnchor");
				titleText.add(a);
				HTML h=new HTML("-->");
				h.setStyleName("breadcrumbsdiv");
				titleText.add(h);
			}
			else
			{
				Span s=new Span();
				s.setText(menu.getMenuName());
				titleText.add(s);
			}
			
		}
		
	}

}
