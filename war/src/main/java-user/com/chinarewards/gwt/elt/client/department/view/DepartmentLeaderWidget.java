package com.chinarewards.gwt.elt.client.department.view;

import java.util.List;

import com.chinarewards.gwt.elt.client.department.model.DepartmentLeaderTreeModel;
import com.chinarewards.gwt.elt.client.department.model.DepartmentNode;
import com.chinarewards.gwt.elt.client.department.model.MyTreeResources;
import com.chinarewards.gwt.elt.client.department.presenter.DepartmentLeaderPresenter.DepartmentLeaderDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class DepartmentLeaderWidget extends Composite implements
		DepartmentLeaderDisplay {
	@UiField
	Panel cellTree;

	@UiField
	Hidden currentDepartmentId;

	@UiField
	Button addChildBtn;
	@UiField
	Button deleteBtn;
	@UiField
	Button editBtn;
	
	String defaultBtnClassName;

	@UiField
	Panel breadCrumbs;

	private static DepartmentWidgetUiBinder uiBinder = GWT
			.create(DepartmentWidgetUiBinder.class);

	interface DepartmentWidgetUiBinder extends
			UiBinder<Widget, DepartmentLeaderWidget> {
	}

	public DepartmentLeaderWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void loadTreeData(List<DepartmentNode> nodeList,
			String corporationId) {
		DepartmentLeaderTreeModel treeModel = new DepartmentLeaderTreeModel(
				nodeList, corporationId,this);

		CellTree.Resources res = GWT.create(MyTreeResources.class);
		CellTree tree = new CellTree(treeModel, null, res);
		tree.setAnimationEnabled(true);
		
		
		cellTree.clear();
		cellTree.add(tree);
	
	}


	@Override
	public void setBreadCrumbs(Widget breadCrumbs) {
		this.breadCrumbs.clear();
		this.breadCrumbs.add(breadCrumbs);
	}

	@Override
	public Button getAddChildBtn() {
		return addChildBtn;
	}

	@Override
	public Button getDeleteBtn() {
		return deleteBtn;
	}

	@Override
	public Button getEditBtn() {
		return editBtn;
	}

	@Override
	public Panel getCellTree() {
		return cellTree;
	}

	@Override
	public Hidden getCurrentDepartmentId() {
		return currentDepartmentId;
	}
	
	@Override
	public String getDefaultBtnClassName() {
		return defaultBtnClassName;
	}
	
	@Override
	public void setDefaultBtnClassName(String defaultBtnClassName) {
		this.defaultBtnClassName=defaultBtnClassName;
	}

}
