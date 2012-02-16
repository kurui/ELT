package com.chinarewards.gwt.elt.client.department.view;

import java.util.ArrayList;
import java.util.List;

import com.chinarewards.gwt.elt.client.department.model.DepartmentManageTreeModel;
import com.chinarewards.gwt.elt.client.department.model.DepartmentNode;
import com.chinarewards.gwt.elt.client.department.presenter.DepartmentListPresenter.DepartmentListDisplay;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;

public class DepartmentListWidget extends Composite implements
		DepartmentListDisplay {
	@UiField
	Panel cellTree;

	@UiField
	Hidden currentDepartmentId;

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
	public void loadTreeData(List<DepartmentNode> nodeList, String corporationId,String departmentIds) {
		 final ProvidesKey<DepartmentNode> KEY_PROVIDER = new ProvidesKey<DepartmentNode>() {
		      public Object getKey(DepartmentNode item) {
		        return item == null ? null : item.getDepartmentId();
		      }
		    };
		    
		System.out.println("----------------DepartmentListWidget loadTreeData:============");
		final MultiSelectionModel<DepartmentNode> selectionModel = new MultiSelectionModel<DepartmentNode>(KEY_PROVIDER);
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					public void onSelectionChange(SelectionChangeEvent event) {
						StringBuilder sb = new StringBuilder();
						boolean first = true;
						List<DepartmentNode> selectedList = new ArrayList<DepartmentNode>(
								selectionModel.getSelectedSet());
						System.out.println("----------------DepartmentListWidget loadTreeData:===selectedSet===="+selectedList.size());
						// Collections.sort(selected);
						for (DepartmentNode node : selectedList) {
							if (first) {
								first = false;
							} else {
								sb.append(", ");
							}
							sb.append(node.getDepartmentId());
							
						}
						
						System.out.println("===========loadTreeData:"+sb);
						// selectedLabel.setText(sb.toString());
					}
				});

		departmentIds=currentDepartmentId.getValue();
		DepartmentManageTreeModel treeModel = new DepartmentManageTreeModel(
				nodeList, corporationId,departmentIds,selectionModel);

		CellTree.Resources res = GWT.create(CellTree.BasicResources.class);
		CellTree tree = new CellTree(treeModel, null, res);
		tree.setAnimationEnabled(true);
		cellTree.clear();
		cellTree.add(tree);
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

	@Override
	public Panel getCellTree() {
		return cellTree;
	}

	@Override
	public Hidden getCurrentDepartmentId() {
		return currentDepartmentId;
	}
}
