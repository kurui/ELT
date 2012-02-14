package com.chinarewards.gwt.elt.client.department.view;

import com.chinarewards.gwt.elt.client.department.presenter.DepartmentListPresenter.DepartmentListDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class DepartmentListWidget extends Composite implements
		DepartmentListDisplay {
	@UiField
	Panel resultPanel;

	@UiField
	Button addSameLevelBtn;
	@UiField
	Button addChildBtn;
	@UiField
	Button deleteBtn;
	@UiField
	Button editBtn;
	@UiField
	Button mergeBtn;
	@UiField
	Button synchBtn;


	@UiField
	Panel breadCrumbs;

	private static DepartmentWidgetUiBinder uiBinder = GWT
			.create(DepartmentWidgetUiBinder.class);

	interface DepartmentWidgetUiBinder extends
			UiBinder<Widget, DepartmentListWidget> {
	}

	public DepartmentListWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}


	@Override
	public Panel getResultPanel() {
		return resultPanel;
	}

	@Override
	public HasClickHandlers getAddSameLevelBtnClickHandlers() {
		return addSameLevelBtn;
	}


	@Override
	public void setBreadCrumbs(Widget breadCrumbs) {
		this.breadCrumbs.clear();
		this.breadCrumbs.add(breadCrumbs);
	}

	@Override
	public HasClickHandlers getAddChildBtnClickHandlers() {
		return addChildBtn;
	}

	@Override
	public HasClickHandlers getDeleteBtnClickHandlers() {
		return deleteBtn;
	}

	@Override
	public HasClickHandlers getEditBtnClickHandlers() {
		return editBtn;
	}

	@Override
	public HasClickHandlers getMergeBtnClickHandlers() {
		return mergeBtn;
	}

	@Override
	public HasClickHandlers getSynchBtnClickHandlers() {
		return synchBtn;
	}
}
