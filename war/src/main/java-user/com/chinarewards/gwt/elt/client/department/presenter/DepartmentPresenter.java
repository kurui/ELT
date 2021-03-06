package com.chinarewards.gwt.elt.client.department.presenter;

import com.chinarewards.gwt.elt.client.department.model.DepartmentVo;
import com.chinarewards.gwt.elt.client.mvp.Display;
import com.chinarewards.gwt.elt.client.mvp.Presenter;
import com.chinarewards.gwt.elt.client.rewards.model.OrganicationClient;
import com.chinarewards.gwt.elt.client.widget.SpecialTextArea;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public interface DepartmentPresenter extends
		Presenter<DepartmentPresenter.DepartmentDisplay> {

	public static interface DepartmentDisplay extends Display {

		public Hidden getDepartmentId();

		public HasValue<String> getDepartmentName();

		public Hidden getParentId();
//------显示内容
		public Label getChilddepartment();

		public Label getPeopleNumber();

		public Label getYearintegral();

		public Label getIssueintegral();
		
		public Label getProcesRewarditemCount();
		
//--------end -----
		public HasClickHandlers getSaveClick();

		public HasClickHandlers getChooseLeaderBtnClick();

		public void clear();

		public Hidden getFormValidate();
		
		public HasClickHandlers getBackClick();

		void setBreadCrumbs(Widget breadCrumbs);

		public void initAddDepartment(DepartmentVo giftVo);

		public void initEditDepartment(DepartmentVo giftVo);

		public void initSaveSameLevelDepartment(DepartmentVo departmentVo);

		public void initSaveChildDepartment(DepartmentVo departmentVo);

		public SpecialTextArea<OrganicationClient> getLeaderArea();
		
		public SpecialTextArea<OrganicationClient> getPreLeaderArea();

		public ListBox getParentList();

	}

	public void initEditor(String giftId, String thisAction);
}
