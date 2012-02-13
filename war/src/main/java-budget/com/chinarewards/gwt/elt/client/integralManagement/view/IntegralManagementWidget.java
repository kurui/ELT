package com.chinarewards.gwt.elt.client.integralManagement.view;

import java.util.List;

import com.chinarewards.gwt.elt.client.integralManagement.model.Category;
import com.chinarewards.gwt.elt.client.integralManagement.model.ContactTreeViewModel;
import com.chinarewards.gwt.elt.client.integralManagement.presenter.IntegralManagementPresenter.IntegralManagementDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class IntegralManagementWidget extends Composite implements
		IntegralManagementDisplay {

	@UiField
	Panel cellTree;

	@UiField
	Label selectedLabel;
	private static IntegralManagementWidgetUiBinder uiBinder = GWT
			.create(IntegralManagementWidgetUiBinder.class);

	interface IntegralManagementWidgetUiBinder extends
			UiBinder<Widget, IntegralManagementWidget> {
	}

	public IntegralManagementWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public Panel getCellTree() {
		return cellTree;
	}

	@Override
	public void refresh(List<Category> result,String corporationId) {

		CellTree.Resources res = GWT.create(CellTree.BasicResources.class);

		CellTree tree = new CellTree(new ContactTreeViewModel(result,corporationId), null, res);
		tree.setAnimationEnabled(true);
		cellTree.clear();
		cellTree.add(tree);

	}

}
