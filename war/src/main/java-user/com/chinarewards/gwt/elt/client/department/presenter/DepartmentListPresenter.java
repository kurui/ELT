package com.chinarewards.gwt.elt.client.department.presenter;

import java.util.List;

import com.chinarewards.gwt.elt.client.department.model.DepartmentNode;
import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public interface DepartmentListPresenter extends
		Presenter<DepartmentListPresenter.DepartmentListDisplay> {

	public static interface DepartmentListDisplay extends Display {

		public Hidden getCurrentDepartmentId();
		
		public Button getAddSameLevelBtn();

		public Button getAddChildBtn();

		public Button getDeleteBtn();

		public Button getEditBtn();

		public Button getMergeBtn();

		public Button getSynchBtn();

		Panel getCellTree();
		Panel getTreeTablePanel();

		void setBreadCrumbs(Widget breadCrumbs);

		void loadTreeData(List<DepartmentNode> result, String corporationId);

	}
	public void initEditor();
}
